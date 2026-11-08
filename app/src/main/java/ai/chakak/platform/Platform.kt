package ai.chakak.platform
import ai.chakak.core.*
import kotlinx.coroutines.flow.StateFlow
interface Platform {
    val source: Source
    val touchAllowed: StateFlow<Boolean>
    val voiceAllowed: StateFlow<Boolean> get() = touchAllowed
    /** True once gear and UX restriction state are known, so voice access can be decided without further waiting. */
    val accessResolved: StateFlow<Boolean>
    val providers: List<CaptureProvider>
    fun start()
    fun stop()
}
