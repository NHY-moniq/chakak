package ai.chakak.ui.model

import ai.chakak.core.Category
import ai.chakak.core.Observation
import ai.chakak.core.Record
import ai.chakak.core.Source
import ai.chakak.core.Status
import ai.chakak.platform.GuidanceTarget
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = android.app.Application::class)
class GuidanceTargetTest {
    private fun record(title: String = "", vararg observations: Observation) =
        Record("r", 0, Category.PLACE, Source.REAL, title = title, collecting = false, observations = observations.toList())

    @Test fun coordinatesTitleAndEmbeddedAddressBecomeTheDestination() {
        val location = Observation("location", Status.OK, """{"latitude":37.41272,"longitude":127.09468,"address":" 수정구 시흥동 "}""")
        assertEquals(GuidanceTarget(37.41272, 127.09468, "판교 카페", "수정구 시흥동"), record("판교 카페", location).guidanceTarget())
    }

    @Test fun addressObservationWinsAndUntitledRecordUsesDisplayTitle() {
        val location = Observation("location", Status.OK, """{"latitude":37.5,"longitude":127.0,"address":"구 주소"}""")
        val address = Observation("address", Status.OK, "서울 영등포구 여의동")
        assertEquals(GuidanceTarget(37.5, 127.0, UNTITLED_RECORD, "서울 영등포구 여의동"), record("", location, address).guidanceTarget())
    }

    @Test fun addressIsOptional() {
        val location = Observation("location", Status.OK, """{"latitude":37.5,"longitude":127.0}""")
        assertNull(record("a", location, Observation("address", Status.TIMEOUT)).guidanceTarget()!!.address)
    }

    @Test fun recordsWithoutUsableCoordinatesAreNotDestinations() {
        assertNull(record("a").guidanceTarget())
        assertNull(record("a", Observation("location", Status.DENIED)).guidanceTarget())
        assertNull(record("a", Observation("location", Status.OK, "TEST: location sample")).guidanceTarget())
        assertNull(record("a", Observation("location", Status.OK, """{"latitude":137.5,"longitude":127.0}""")).guidanceTarget())
        assertNull(record("a", Observation("location", Status.TIMEOUT, """{"latitude":37.5,"longitude":127.0}""")).guidanceTarget())
    }
}
