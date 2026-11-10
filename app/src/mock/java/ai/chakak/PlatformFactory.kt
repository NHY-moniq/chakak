package ai.chakak
import android.content.Context
import ai.chakak.core.*
import ai.chakak.platform.Platform
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

enum class MockScenario { SUCCESS, PARTIAL, DENIED, UNSUPPORTED, ERROR, TIMEOUT }
class MockPlatform : Platform {
    override val source = Source.MOCK
    override val touchAllowed = MutableStateFlow(true)
    override val accessResolved = MutableStateFlow(true)
    var scenario = MockScenario.SUCCESS
    override val providers = (listOf("location") + ai.chakak.ui.recordedVehicleFieldLabels.keys).map { field -> object : CaptureProvider {
        override val key = field
        override suspend fun read(): Observation {
            val selected = scenario
            delay(if (selected == MockScenario.TIMEOUT) 6000 else 150)
            val status = when(selected) {
                MockScenario.SUCCESS, MockScenario.TIMEOUT -> Status.OK
                MockScenario.PARTIAL -> if (field == "location") Status.UNAVAILABLE else Status.OK
                MockScenario.DENIED -> Status.DENIED
                MockScenario.UNSUPPORTED -> Status.UNSUPPORTED
                MockScenario.ERROR -> Status.ERROR
            }
            return Observation(key, status, if(status == Status.OK) "TEST: $field sample" else null, System.currentTimeMillis())
        }
    } }
    override fun start() {}
    override fun stop() {}
}
fun createPlatform(context: Context): Platform = MockPlatform()
