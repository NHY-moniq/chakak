package ai.chakak.ui

import ai.chakak.core.Observation
import ai.chakak.core.Status
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.TimeZone

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = android.app.Application::class)
class ObservationPresentationTest {
    @Test fun koreanTimeDoesNotDependOnDeviceTimezone() {
        val original = TimeZone.getDefault()
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"))
            assertEquals("1970.01.01 09:00:00 KST", formatRecordTime(0))
        } finally { TimeZone.setDefault(original) }
    }
    @Test fun unknownUnitsAndMissingTiresAreNotInvented() {
        assertEquals(listOf("누적 주행 거리" to "1,234 (단위 미확인)"),
            Observation("odometer", Status.OK, """{"rawValue":1234,"unit":null}""").displayLines())
        assertEquals(listOf("앞 왼쪽" to "137 (단위 미확인)", "뒤 오른쪽" to "관측값 없음"),
            Observation("tires", Status.UNAVAILABLE, """[{"area":"UNKNOWN, LEFT_FRONT","rawValue":137,"unit":null},{"area":"RIGHT_REAR","rawValue":null}]""").displayLines())
    }
    @Test fun emptyWarningsDoNotClaimVehicleIsHealthy() {
        assertEquals(listOf("경고등" to "보고된 항목 없음"), Observation("telltales", Status.OK, "[]").displayLines())
    }
    @Test fun malformedStoredValuesDoNotCrashOrExposeJson() {
        assertEquals(listOf("관측값" to "표시할 수 없는 형식"), Observation("location", Status.OK, "{invalid").displayLines())
    }
}
