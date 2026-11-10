package ai.chakak.platform
import ai.chakak.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
/** 내비게이션에 넘길 목적지. 기록 당시 차량 좌표와 기록 제목이다. */
data class GuidanceTarget(val latitude: Double, val longitude: Double, val name: String, val address: String?)
/**
 * RESTRICTED: 요청 도중 주차 상태가 풀려 목적지를 보내지 않음. TIMEOUT: 목적지는 보냈지만 내비게이션 응답을 확인하지 못함.
 */
enum class GuidanceResult { STARTED, UNSUPPORTED, DENIED, RESTRICTED, FAILED, TIMEOUT }
/** MIC: 차량 마이크를 열지 못함. UNAVAILABLE: 음성 인식 서비스·권한·네트워크를 쓸 수 없음. */
enum class DictationFailure { MIC, UNAVAILABLE, FAILED }
sealed interface DictationEvent {
    /** 지금 말하고 있는 문장의 인식 결과. [final]이면 그 문장이 확정됐다. */
    data class Text(val text: String, val final: Boolean) : DictationEvent
    data class Failed(val reason: DictationFailure) : DictationEvent
}
interface Platform {
    val source: Source
    val touchAllowed: StateFlow<Boolean>
    val voiceAllowed: StateFlow<Boolean> get() = touchAllowed
    /** True once gear and UX restriction state are known, so voice access can be decided without further waiting. */
    val accessResolved: StateFlow<Boolean>
    val providers: List<CaptureProvider>
    /** Hands the destination to the vehicle navigation and brings it to the foreground. Mock has no navigation. */
    suspend fun startGuidance(target: GuidanceTarget): GuidanceResult = GuidanceResult.UNSUPPORTED
    /**
     * Speech-to-text for the parked memo editor. Collecting the flow opens the vehicle microphone; cancelling it or the end of
     * recognition releases it. Null where the platform has no speech recognition (mock, non-automotive).
     */
    val dictation: (() -> Flow<DictationEvent>)? get() = null
    fun start()
    fun stop()
}
