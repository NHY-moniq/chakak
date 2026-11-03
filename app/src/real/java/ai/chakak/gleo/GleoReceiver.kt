package ai.chakak.gleo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import ai.chakak.ChakakApp
import ai.chakak.core.Category
import ai.chakak.core.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import org.json.JSONArray
import org.json.JSONObject
import java.security.MessageDigest
import java.util.UUID

/** Accepts only the configured Gleo package/signature with platform-provided sender identity. */
class GleoReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val target = trustedSender(context) ?: return
        if(intent.action !in setOf("CAPTURE_RECORD", "ADD_RECORD_MEMO")) return
        val receivedElapsed = android.os.SystemClock.elapsedRealtime()
        val receivedAt = System.currentTimeMillis()
        val raw = intent.getStringExtra("ACTION_PARAM") ?: "{}"
        if(raw.length > 8192) return
        val pending = goAsync()
        val app = context.applicationContext as ChakakApp
        app.scope.launch {
            var response = reply(intent.action!!, "fail", "The action could not be completed.")
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
                    val allowed = withTimeoutOrNull(500) { app.platform.voiceAllowed.first { it } } == true
                    if(!allowed) { response = reply(intent.action!!, "fail", "Voice recording is not available in the current driving state."); return@withTimeout }
                    if(intent.action == "CAPTURE_RECORD") {
                        val id = params.optString("requestId").ifBlank { UUID.randomUUID().toString() }
                        val category = Category.valueOf(params.optString("category", "UNCLASSIFIED"))
                        val created = app.voiceEngine.capture(id, category, receivedAt)
                        val record = app.store.records.first().find { it.id == id }
                        if(record == null) { response = reply("CAPTURE_RECORD", "fail", "No record is available for this request."); return@withTimeout }
                        val data = JSONObject().put("recordId", id)
                        // Follow-up remains opt-in only after schema approval; no open microphone session here.
                        if(created && context.resources.getBoolean(ai.chakak.R.bool.gleo_follow_up_approved)) {
                            val token = UUID.randomUUID().toString()
                            app.store.issueMemoToken(id, token, System.currentTimeMillis() + 60_000)
                            data.put("memoToken", token)
                        }
                        val complete = !record.collecting && record.observations.all { it.status == Status.OK }
                        response = reply("CAPTURE_RECORD", "success", if(complete) "The record has been saved." else "The record has been saved with some information unavailable.", data)
                    } else {
                        val id = params.get("recordId") as? String ?: error("Invalid record ID")
                        val token = params.get("memoToken") as? String ?: error("Invalid token")
                        val memo = params.get("memo") as? String ?: error("Invalid memo")
                        require(id.length <= 128 && token.length <= 128 && memo.length <= 2000)
                        val saved = context.resources.getBoolean(ai.chakak.R.bool.gleo_follow_up_approved) && memo.isNotBlank() &&
                            app.store.addMemo(id, token, System.currentTimeMillis(), memo)
                        response = reply("ADD_RECORD_MEMO", if(saved) "success" else "fail", if(saved) "The memo has been saved." else "The memo context is invalid or expired. Edit the record while parked.")
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
    private fun reply(action: String, status: String, message: String, data: JSONObject? = null) = JSONObject()
        .put("functionName", action).put("status", status).put("message", message).apply {
            if(data != null) put("data", JSONArray().put(data).toString())
        }
    private fun trustedSender(context: Context): String? {
        if(Build.VERSION.SDK_INT < 34 || sentFromUid < 0) return null
        val expectedPackage = context.getString(ai.chakak.R.string.gleo_sender_package)
        val expectedDigest = context.getString(ai.chakak.R.string.gleo_sender_sha256)
        if(expectedPackage.isBlank() || !expectedDigest.matches(Regex("[0-9a-fA-F]{64}"))) return null
        if(context.packageManager.getPackagesForUid(sentFromUid)?.contains(expectedPackage) != true) return null
        return runCatching {
            val signatures = context.packageManager.getPackageInfo(expectedPackage, PackageManager.GET_SIGNING_CERTIFICATES).signingInfo?.apkContentsSigners.orEmpty()
            if(signatures.any { signature -> MessageDigest.getInstance("SHA-256").digest(signature.toByteArray()).joinToString("") { "%02x".format(it) }.equals(expectedDigest, true) }) expectedPackage else null
        }.getOrNull()
    }
}
