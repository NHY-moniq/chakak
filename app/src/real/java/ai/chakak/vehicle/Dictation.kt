package ai.chakak.vehicle

import android.content.Context
import ai.chakak.platform.DictationEvent
import ai.chakak.platform.DictationFailure
import ai.pleos.playground.stt.SpeechToText
import ai.pleos.playground.stt.constant.ErrorType
import ai.pleos.playground.stt.constant.Mode
import ai.pleos.playground.stt.listener.OnInitializeListener
import ai.pleos.playground.stt.listener.ResultListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Locale

internal fun ErrorType.toDictationFailure() = when(this) {
    ErrorType.MIC_ERROR, ErrorType.AUDIO_ERROR -> DictationFailure.MIC
    ErrorType.PERMISSION_DENIED, ErrorType.UNAUTHENTICATED, ErrorType.NETWORK_ERROR, ErrorType.NOT_SUPPORTED_API,
    ErrorType.UNAVAILABLE_LANGUAGE -> DictationFailure.UNAVAILABLE
    else -> DictationFailure.FAILED
}

/**
 * One Pleos SpeechToText session. The SDK object is created per session and released when the flow ends, so the vehicle
 * microphone is never held while the memo editor is idle. Recognised speech is not logged.
 */
fun pleosDictation(context: Context): Flow<DictationEvent> = callbackFlow {
    val stt = SpeechToText(context.applicationContext, STT_MODE)
    val listener = object : ResultListener {
        override fun onUpdated(stt: String, completed: Boolean) { trySend(DictationEvent.Text(stt, completed)); if(completed) close() }
        // The final transcript can arrive just after the end signal; closing at once would drop the last words.
        override fun onEndedRecognition() { launch { delay(FINAL_RESULT_GRACE_MILLIS); close() } }
        override fun onError(errorType: ErrorType) { trySend(DictationEvent.Failed(errorType.toDictationFailure())); close() }
        override fun onReady() {}
        override fun onStartedRecognition() {}
        override fun onUpdatedEpdData(on: Long, off: Long) {}
        override fun onUpdatedRms(rms: Double) {}
    }
    try {
        stt.initialize(object : OnInitializeListener {
            override fun onSuccess() {
                try { stt.addListener(listener); stt.setLocale(Locale.KOREA); stt.request() }
                catch (_: Exception) { trySend(DictationEvent.Failed(DictationFailure.FAILED)); close() }
            }
            override fun onFailure(errorType: ErrorType) { trySend(DictationEvent.Failed(errorType.toDictationFailure())); close() }
        })
    } catch (_: Exception) { trySend(DictationEvent.Failed(DictationFailure.UNAVAILABLE)); close() }
    awaitClose { runCatching { stt.stop() }; runCatching { stt.removeListener(listener) }; runCatching { stt.release() } }
}

// HYBRID uses server recognition once the app is registered with a Pleos Playground client ID/secret (SpeechToText.registerApp)
// and otherwise falls back to the on-device engine, which is what runs today.
private val STT_MODE = Mode.HYBRID
private const val FINAL_RESULT_GRACE_MILLIS = 500L
