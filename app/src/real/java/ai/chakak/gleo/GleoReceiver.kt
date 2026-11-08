package ai.chakak.gleo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ai.chakak.ChakakApp
import ai.chakak.core.Category
import ai.chakak.core.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

const val CAPTURE_RECORD = "CAPTURE_RECORD"
const val ADD_RECORD_MEMO = "ADD_RECORD_MEMO"

/**
 * Gleo sends `<packageName>.<FUNCTION>` (observed on the Pleos Connect v2.1.0 emulator); the Gleo guide documents the bare
 * function name. Both resolve to the schema function name, which is also the callback `functionName`.
 */
fun gleoFunction(packageName: String, action: String?): String? =
    action?.removePrefix("$packageName.")?.takeIf { it == CAPTURE_RECORD || it == ADD_RECORD_MEMO }

/**
 * Implements the Gleo Interface contract only: action + ACTION_PARAM in, APP_ACTION_RESPONSE out. The guide defines no
 * caller verification, so any app can reach this receiver; memo writes stay bound to the one-time token, and responses are
 * addressed to the Gleo package so record IDs and tokens are never broadcast to other apps.
 */
class GleoReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val function = gleoFunction(context.packageName, intent.action) ?: return
        val receivedElapsed = android.os.SystemClock.elapsedRealtime()
        val receivedAt = System.currentTimeMillis()
        val raw = intent.getStringExtra("ACTION_PARAM") ?: "{}"
        if(raw.length > 8192) return
        val target = context.getString(ai.chakak.R.string.gleo_package)
        val pending = goAsync()
        val app = context.applicationContext as ChakakApp
        app.scope.launch {
            var response = reply(function, "fail", "The action could not be completed.")
            var platformStarted = false
            try {
                withTimeout((6000 - (android.os.SystemClock.elapsedRealtime() - receivedElapsed)).coerceAtLeast(1)) {
                    app.ready.await()
                    val decoded = org.json.JSONTokener(raw).nextValue()
                    val params = when(decoded) { is JSONObject -> decoded; is String -> JSONObject(decoded); else -> error("Invalid parameters") }
                    val categoryInput = params.opt("category")
                    require(categoryInput == null || categoryInput is String)
                    val requestInput = params.opt("requestId")
                    require(requestInput == null || (requestInput is String && requestInput.length <= 128))
                    // Independent voice policy: approved headless DRIVE capture never opens/unlocks the UI.
                    app.platform.start()
                    platformStarted = true
                    // Wait only until gear and UX restrictions are known, then decide; the wait shortens the collection budget.
                    withTimeoutOrNull(1500) { app.platform.accessResolved.first { it } }
                    if(!app.platform.voiceAllowed.value) { response = reply(function, "fail", "Voice recording is not available in the current driving state."); return@withTimeout }
                    if(function == CAPTURE_RECORD) {
                        val id = params.optString("requestId").ifBlank { UUID.randomUUID().toString() }
                        val category = Category.valueOf(params.optString("category").ifBlank { Category.UNCLASSIFIED.name })
                        val created = app.voiceEngine.capture(id, category, receivedAt)
                        val record = app.store.records.first().find { it.id == id }
                        if(record == null) { response = reply(CAPTURE_RECORD, "fail", "No record is available for this request."); return@withTimeout }
                        val data = JSONObject().put("recordId", id)
                        val complete = !record.collecting && record.observations.all { it.status == Status.OK }
                        val saved = if(complete) "The record has been saved." else "The record has been saved with some information unavailable."
                        if(created) {
                            // The base record is already committed. "asking" lets Gleo offer one memo turn. Gleo routes the reply to
                            // ADD_RECORD_MEMO only when it mentions a memo, and sends blank recordId/memoToken, so this record becomes
                            // the single pending memo target. "No", silence, or expiry simply leaves the record as saved.
                            val token = UUID.randomUUID().toString()
                            app.store.issueMemoToken(id, token, System.currentTimeMillis() + 60_000)
                            data.put("memoToken", token)
                            response = reply(CAPTURE_RECORD, "asking", "$saved To add a memo, say memo followed by what you want to note.", data)
                        } else response = reply(CAPTURE_RECORD, "success", saved, data)
                    } else {
                        val id = params.opt("recordId") as? String ?: ""
                        val token = params.opt("memoToken") as? String ?: ""
                        val memo = params.get("memo") as? String ?: error("Invalid memo")
                        require(id.length <= 128 && token.length <= 128 && memo.length <= 2000)
                        val now = System.currentTimeMillis()
                        val saved = memo.isNotBlank() &&
                            if(id.isNotBlank() && token.isNotBlank()) app.store.addMemo(id, token, now, memo) else app.store.addPendingMemo(now, memo)
                        response = reply(ADD_RECORD_MEMO, if(saved) "success" else "fail", if(saved) "The memo has been saved." else "The memo context is invalid or expired. Edit the record while parked.")
                    }
                }
            } catch (_: Exception) {
                // Do not log speech, coordinates, or tokens. A failed memo never deletes a base record.
            } finally {
                if(platformStarted) app.platform.stop()
                try { context.sendBroadcast(Intent("ai.pleos.playground.intent.action.APP_ACTION_RESPONSE").setPackage(target)
                    .putExtra("packageName", context.packageName).putExtra("response", response.toString()))
                } finally { pending.finish() }
            }
        }
    }
    private fun reply(function: String, status: String, message: String, data: JSONObject? = null) = JSONObject()
        .put("functionName", function).put("status", status).put("message", message).apply {
            if(data != null) put("data", JSONArray().put(data).toString())
        }
}
