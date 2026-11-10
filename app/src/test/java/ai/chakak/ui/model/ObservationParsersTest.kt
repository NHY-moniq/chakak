package ai.chakak.ui.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Locale

/** real PlatformFactory가 저장하는 JSON 형식과, 읽을 수 없는 값(Mock 문자열·깨진 JSON) */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = android.app.Application::class)
class ObservationParsersTest {
    private val mockValue = "TEST: location sample"

    @Test fun locationRealFormat() {
        val reading = parseLocation("""{"latitude":37.52191,"longitude":126.93994,"confidence":0.8}""")
        assertEquals(LocationReading(37.52191, 126.93994, 0.8), reading)
        assertEquals("37.5219, 126.9399", reading!!.coordinatesText())
    }

    @Test fun locationWithoutConfidence() {
        assertEquals(LocationReading(1.0, 2.0, null), parseLocation("""{"latitude":1.0,"longitude":2.0}"""))
    }

    @Test fun locationUnparseable() {
        assertNull(parseLocation(mockValue))
        assertNull(parseLocation(null))
        assertNull(parseLocation("{broken"))
        assertNull(parseLocation("""{"latitude":37.5}"""))
        assertNull(parseLocation("""{"latitude":137.5,"longitude":10}"""))
        assertNull(parseLocation("""[{"latitude":1,"longitude":2}]"""))
    }

    @Test fun odometerRealFormatHasNoUnit() {
        assertEquals(Measurement(12480.5, null), parseOdometer("""{"rawValue":12480.5,"unit":null}"""))
        assertEquals(Measurement(10.0, "km"), parseOdometer("""{"rawValue":10,"unit":"km"}"""))
    }

    @Test fun odometerUnparseable() {
        assertNull(parseOdometer("TEST: odometer sample"))
        assertNull(parseOdometer("""{"rawValue":null,"unit":null}"""))
        assertNull(parseOdometer("""{"unit":"km"}"""))
        assertNull(parseOdometer("""{"rawValue":"NaN"}"""))
    }

    @Test fun telltalesRealFormat() {
        val parsed = parseTelltales(
            """[{"type":"TPMS","state":"ON","warningCount":1},{"type":"ABS","state":"OFF","warningCount":0},{"type":"MASTER_WARNING","state":"BLINK","warningCount":2}]""",
        )!!
        assertEquals(3, parsed.size)
        assertEquals(listOf("TPMS", "MASTER_WARNING"), parsed.filter { it.lit }.map { it.type })
        assertTrue(parsed[2].blinking)
        assertEquals(1, parsed[0].warningCount)
        assertEquals(emptyList<TelltaleReading>(), parseTelltales("[]"))
    }

    @Test fun telltalesUnparseable() {
        assertNull(parseTelltales("TEST: telltales sample"))
        assertNull(parseTelltales("""[{"state":"ON"}]"""))
        assertNull(parseTelltales("""[1,2]"""))
        assertNull(parseTelltales("""{"type":"TPMS"}"""))
    }

    @Test fun tiresRealFormatWithMissingWheel() {
        val tires = parseTires(
            """[{"area":"LEFT_FRONT","rawValue":33.0,"unit":null},{"area":"RIGHT_FRONT","rawValue":33.5,"unit":null},""" +
                """{"area":"LEFT_REAR","rawValue":32,"unit":null},{"area":"RIGHT_REAR","rawValue":null,"unit":null}]""",
        )!!
        assertEquals(Measurement(33.0, null), tires.valueOf(Wheel.FrontLeft))
        assertEquals(Measurement(33.5, null), tires.valueOf(Wheel.FrontRight))
        assertTrue(tires.has(Wheel.RearRight))
        assertNull(tires.valueOf(Wheel.RearRight))
    }

    @Test fun tiresAreaCoveringSeveralWheels() {
        val tires = parseTires("""[{"area":"LEFT_FRONT, RIGHT_FRONT","rawValue":30,"unit":null}]""")!!
        assertEquals(Measurement(30.0, null), tires.valueOf(Wheel.FrontLeft))
        assertEquals(Measurement(30.0, null), tires.valueOf(Wheel.FrontRight))
        assertFalse(tires.has(Wheel.RearLeft))
    }

    @Test fun tiresUnparseable() {
        assertNull(parseTires("TEST: tires sample"))
        assertNull(parseTires("[]"))
        assertNull(parseTires("""[{"area":"UNKNOWN","rawValue":1}]"""))
        assertNull(parseTires("[not json"))
    }

    @Test fun numberFormattingKeepsValueAndUnit() {
        assertEquals("12,480", formatNumber(12480.0, Locale.KOREA))
        assertEquals("12,480.5", formatNumber(12480.5, Locale.KOREA))
        assertEquals("33", Measurement(33.0, null).text(Locale.KOREA))
        assertEquals("33 psi", Measurement(33.0, "psi").text(Locale.KOREA))
    }

    @Test fun telltaleWarningCountNullIsAbsent() {
        val items = parseTelltales("""[{"type":"TPMS","state":"ON","warningCount":null},{"type":"ABS","state":"OFF","warningCount":2}]""")!!
        assertNull(items[0].warningCount)
        assertEquals(2, items[1].warningCount)
    }

    @Test fun duplicateWheelKeepsNonNullValue() {
        val valueFirst = parseTires("""[{"area":"LEFT_FRONT","rawValue":33},{"area":"LEFT_FRONT, RIGHT_FRONT","rawValue":null}]""")!!
        assertEquals(Measurement(33.0, null), valueFirst.valueOf(Wheel.FrontLeft))
        assertTrue(valueFirst.has(Wheel.FrontRight))
        assertNull(valueFirst.valueOf(Wheel.FrontRight))
        val nullFirst = parseTires("""[{"area":"LEFT_REAR","rawValue":null},{"area":"LEFT_REAR","rawValue":31}]""")!!
        assertEquals(Measurement(31.0, null), nullFirst.valueOf(Wheel.RearLeft))
    }
}
