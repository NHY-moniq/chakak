package ai.chakak.gleo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ai.chakak.ChakakApp
import ai.chakak.core.Category
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

const val CAPTURE_RECORD = "CAPTURE_RECORD"

/**
 * Gleo sends `<packageName>.<FUNCTION>` (observed on the Pleos Connect v2.1.0 emulator); the Gleo guide documents the bare
 * function name. Both resolve to the schema function name, which is also the callback `functionName`.
 */
fun gleoFunction(packageName: String, action: String?): String? =
    action?.removePrefix("$packageName.")?.takeIf { it == CAPTURE_RECORD }

/**
 * Implements the Gleo Interface contract only: action + ACTION_PARAM in, APP_ACTION_RESPONSE out. The guide defines no
 * caller verification, so any app can reach this receiver; responses are addressed to the Gleo package so record IDs are
 * never broadcast to other apps.
 *
 * Voice captures only. Memos are written on the parked screen: Gleo neither routes the answer to a follow-up question back
 * to the asking app nor waits through a pause before a memo spoken with the request (see CLAUDE.md).
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
                    // Independent voice policy: approved headless DRIVE capture never opens/unlocks the UI.
                    app.platform.start()
                    platformStarted = true
                    // Wait only until gear and UX restrictions are known, then decide; the wait shortens the collection budget.
                    withTimeoutOrNull(1500) { app.platform.accessResolved.first { it } }
                    if(!app.platform.voiceAllowed.value) { response = reply(function, "fail", "Voice recording is not available in the current driving state."); return@withTimeout }
                    // Gleo's planner invents parameter values (it sent the literal "unique_request_id"), so a caller-supplied
                    // ID would make later captures look like duplicates. Every voice capture gets its own ID.
                    val id = UUID.randomUUID().toString()
                    val category = Category.valueOf(params.optString("category").ifBlank { Category.UNCLASSIFIED.name })
                    app.voiceEngine.capture(id, category, receivedAt)
                    if(app.store.records.first().none { it.id == id }) { response = reply(function, "fail", "No record is available for this request."); return@withTimeout }
                    // Per-item gaps (e.g. engine oil on an EV) are shown on the parked screen, not spoken.
                    response = reply(function, "success", "The record has been saved.", JSONObject().put("recordId", id))
                }
            } catch (_: Exception) {
                // Do not log speech or coordinates.
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
