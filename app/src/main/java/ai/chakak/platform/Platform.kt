package ai.chakak.platform
import ai.chakak.core.*
import kotlinx.coroutines.flow.StateFlow
interface Platform {
    val source: Source
    val touchAllowed: StateFlow<Boolean>
    val providers: List<CaptureProvider>
    fun start()
    fun stop()
}
