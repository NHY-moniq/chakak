package ai.chakak.ui

import android.location.Address
import ai.chakak.core.Observation
import ai.chakak.core.Status
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = android.app.Application::class)
class LocationAddressTest {
    @Test fun roadsParcelsAndKoreanLocalizedForeignAddressesAreAccepted() {
        listOf("경기도 광주시 삼동 삼지곡길 95", "경기도 광주시 삼동 123-4",
            "미국 캘리포니아주 마운틴뷰 앰피시어터 파크웨이 1600").forEach { text ->
            val address = Address(Locale.KOREA).apply { setAddressLine(0, text) }
            assertEquals(text, address.localizedAddress())
        }
    }
    @Test fun originalForeignAddressIsPreservedWhenTranslationIsUnavailable() {
        val text = "1600 Amphitheatre Parkway, Mountain View, CA 94043, USA"
        assertEquals(text, Address(Locale.KOREA).apply { setAddressLine(0, text) }.localizedAddress())
    }
    @Test fun componentsAreUsedWithoutRequiringRoadOrBuildingNumber() {
        val address = Address(Locale.KOREA).apply {
            adminArea = "경기도"
            subAdminArea = "광주시"
            locality = "광주시"
            subLocality = "삼동"
            featureName = "123-4"
        }
        assertEquals("경기도 광주시 삼동 123-4", address.localizedAddress())
        assertNull(Address(Locale.KOREA).localizedAddress())
    }
    @Test fun deniedMalformedAndOutOfRangeCoordinatesAreNotGeocoded() {
        val json = """{"latitude":37.4,"longitude":127.2}"""
        assertEquals(Coordinates(37.4, 127.2), Observation("location", Status.OK, json).coordinates())
        assertNull(Observation("location", Status.DENIED, json).coordinates())
        assertNull(Observation("location", Status.OK, "{}").coordinates())
        assertNull(Observation("location", Status.OK, """{"latitude":91,"longitude":127}""").coordinates())
    }
}
