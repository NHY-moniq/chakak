package ai.chakak.ui.model

import ai.chakak.core.Category
import ai.chakak.core.Observation
import ai.chakak.core.Record
import ai.chakak.core.Source
import ai.chakak.core.Status
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = android.app.Application::class)
class RecordPresentationTest {
    private val seoul = ZoneId.of("Asia/Seoul")
    private val observedAt = ZonedDateTime.of(2026, 11, 9, 9, 34, 0, 0, seoul).toInstant().toEpochMilli()
    private val clock = TimelineClock(LocalDate.of(2026, 11, 9), seoul, Locale.KOREA)
    private val locationJson = """{"latitude":37.52191,"longitude":126.93994,"confidence":0.8}"""

    private fun record(category: Category, memo: String = "", vararg observations: Observation) =
        Record("r", 0, category, Source.MOCK, memo = memo, collecting = false, observations = observations.toList())

    private fun location(status: Status = Status.OK, value: String? = locationJson) = Observation("location", status, value, observedAt)

    @Test fun placeMetaPrefersAddressThenMemoThenCoordinates() {
        val address = Observation("address", Status.OK, "서울 어딘가 1")
        assertEquals("장소 · 서울 어딘가 1", record(Category.PLACE, "메모", location(), address).rowMetaLine())
        assertEquals("장소 · “첫 줄”", record(Category.PLACE, "\n 첫 줄 \n둘째", location()).rowMetaLine())
        assertEquals("장소 · 37.5219, 126.9399", record(Category.PLACE, "", location()).rowMetaLine())
        assertEquals("장소 · 위치 정보 없음", record(Category.PLACE, "", location(Status.DENIED, null)).rowMetaLine())
    }

    @Test fun vehicleMetaShowsCompletionOnlyWhenComplete() {
        val ok = Observation("odometer", Status.OK, "x")
        assertEquals("차량 상태 · 수집 완료", record(Category.VEHICLE_STATUS, "", ok).rowMetaLine())
        val partial = record(Category.VEHICLE_STATUS, "", ok, Observation("tires", Status.TIMEOUT))
        assertEquals("차량 상태", partial.rowMetaLine())
        assertTrue(partial.showsStatusBadge())
        assertFalse(record(Category.VEHICLE_STATUS, "", ok).showsStatusBadge())
    }

    @Test fun unclassifiedMeta() {
        assertEquals("미분류", record(Category.UNCLASSIFIED).rowMetaLine())
        assertEquals("미분류 · “기둥 C-14”", record(Category.UNCLASSIFIED, "기둥 C-14").rowMetaLine())
    }

    @Test fun categoryCountsIncludeZeroes() {
        val records = listOf(record(Category.PLACE), record(Category.PLACE), record(Category.VEHICLE_STATUS))
        assertEquals(mapOf(null to 3, Category.UNCLASSIFIED to 0, Category.PLACE to 2, Category.VEHICLE_STATUS to 1), categoryCounts(records))
    }

    @Test fun addressShownLargeWithCoordinatesSmall() {
        val display = locationDisplay(location(), Observation("address", Status.OK, "서울 어딘가 1"), observedAt, clock)
        assertEquals("서울 어딘가 1", display.headline)
        assertEquals(listOf("37.5219, 126.9399 · 신뢰도 0.8 · 관측 오전 9:34"), display.details)
        assertEquals(ADDRESS_NOTICE, display.notice)
        assertTrue(display.addressShown)
    }

    @Test fun addressFailedShowsCoordinatesAndReason() {
        val display = locationDisplay(location(), Observation("address", Status.TIMEOUT), observedAt, clock)
        assertEquals("37.5219, 126.9399", display.headline)
        assertEquals("$ADDRESS_FAILED · 응답 시간 초과", display.details.last())
        assertEquals(PLACE_LOCATION_NOTICE, display.notice)
    }

    @Test fun noAddressItemShowsNoFailureMessage() {
        val display = locationDisplay(location(), null, observedAt, clock)
        assertEquals("37.5219, 126.9399", display.headline)
        assertTrue(display.details.none { it.contains(ADDRESS_FAILED) })
        assertEquals(PLACE_LOCATION_NOTICE, display.notice)
    }

    @Test fun locationFailureUsesStatusNotInventedValue() {
        val display = locationDisplay(location(Status.DENIED, null), null, observedAt, clock)
        assertNull(display.headline)
        assertEquals(Status.DENIED, display.headlineStatus)
        assertEquals(PLACE_LOCATION_NOTICE, locationDisplay(null, null, observedAt, clock).notice)
        assertEquals(NO_LOCATION_ITEM, locationDisplay(null, null, observedAt, clock).headline)
    }

    @Test fun unparseableLocationFallsBackToRaw() {
        assertEquals("TEST: location sample", locationDisplay(location(value = "TEST: location sample"), null, observedAt, clock).headline)
    }

    @Test fun summariesUseParsedValuesOrRawOrStatus() {
        assertEquals("12,480", Observation("odometer", Status.OK, """{"rawValue":12480,"unit":null}""").summary(clock).value)
        assertEquals("TEST: odometer sample", Observation("odometer", Status.OK, "TEST: odometer sample").summary(clock).value)
        assertEquals(NO_LIT_TELLTALES, Observation("telltales", Status.OK, """[{"type":"ABS","state":"OFF"}]""").summary(clock).value)
        assertEquals("타이어 공기압", Observation("telltales", Status.OK, """[{"type":"TPMS","state":"ON"}]""").summary(clock).value)
        val failed = Observation("telltales", Status.TIMEOUT).summary(clock)
        assertNull(failed.value)
        assertEquals(Status.TIMEOUT, failed.status)
    }

    @Test fun partialTiresSummaryKeepsKnownValues() {
        val tires = Observation(
            "tires", Status.UNAVAILABLE,
            """[{"area":"LEFT_FRONT","rawValue":33},{"area":"RIGHT_FRONT","rawValue":33},{"area":"LEFT_REAR","rawValue":32},{"area":"RIGHT_REAR","rawValue":null}]""",
        )
        assertEquals("33 · 33 · 32 · 없음", tires.summary(clock).value)
    }

    @Test fun tireWheelsMarkMissingAndUnsupported() {
        val tires = Observation("tires", Status.UNAVAILABLE, """[{"area":"LEFT_FRONT","rawValue":33},{"area":"RIGHT_REAR","rawValue":null}]""")
        val wheels = tireWheels(tires, clock.locale)!!
        assertEquals(WheelDisplay(Wheel.FrontLeft, "33", null), wheels[0])
        assertEquals(WheelDisplay(Wheel.FrontRight, null, Status.UNSUPPORTED), wheels[1])
        assertEquals(WheelDisplay(Wheel.RearRight, null, Status.UNAVAILABLE), wheels[3])
        assertEquals(
            "타이어 공기압: 앞 왼쪽 33, 앞 오른쪽 지원하지 않음, 뒤 왼쪽 지원하지 않음, 뒤 오른쪽 유효한 관측값 없음",
            tiresContentDescription(wheels),
        )
    }

    @Test fun tireTimeoutAppliesToAllWheelsAndRawFallsBack() {
        val wheels = tireWheels(Observation("tires", Status.TIMEOUT), clock.locale)!!
        assertTrue(wheels.all { it.missing && it.status == Status.TIMEOUT })
        assertNull(tireWheels(Observation("tires", Status.OK, "TEST: tires sample"), clock.locale))
    }

    @Test fun collectedCount() {
        val r = record(Category.VEHICLE_STATUS, "", Observation("a", Status.OK), Observation("b", Status.DENIED))
        assertEquals("2개 중 1개 수집", r.collectedCountText())
    }

    @Test fun telltaleLabelsFallBackToSdkName() {
        assertEquals("타이어 공기압 (깜빡임)", telltaleLabel(TelltaleReading("TPMS", "BLINK", null)))
        assertEquals("DRIVE_MODE_ECO", telltaleLabel(TelltaleReading("DRIVE_MODE_ECO", "ON", null)))
    }

    @Test fun addressHasLabel() {
        assertEquals("주소", observationKeyLabel("address"))
    }

    private val previousEvening = ZonedDateTime.of(2026, 11, 8, 18, 40, 0, 0, seoul).toInstant().toEpochMilli()

    @Test fun pendingLocationRowSaysChecking() {
        val collecting = Record("r", 0, Category.PLACE, Source.MOCK, collecting = true, observations = listOf(Observation("location", Status.PENDING)))
        assertEquals("장소 · $LOCATION_PENDING", collecting.rowMetaLine())
    }

    @Test fun unparseableOkLocationRowHintsRaw() {
        assertEquals("장소 · $LOCATION_RAW_ONLY", record(Category.PLACE, "", location(value = "TEST: location sample")).rowMetaLine())
    }

    @Test fun staleLocationShowsObservedDate() {
        val stale = Observation("location", Status.UNAVAILABLE, null, previousEvening)
        val display = locationDisplay(stale, null, observedAt, clock)
        assertEquals(Status.UNAVAILABLE, display.headlineStatus)
        assertEquals(listOf("관측 11월 8일 오후 6:40"), display.details)
    }

    @Test fun observedLineRules() {
        assertEquals("관측 오전 9:34", location().observedLine(observedAt, clock))
        assertEquals(OBSERVED_TIME_UNKNOWN, Observation("odometer", Status.OK, "x").observedLine(observedAt, clock))
        assertNull(Observation("odometer", Status.TIMEOUT).observedLine(observedAt, clock))
    }

    @Test fun summaryTilesCarryObservedTime() {
        val odo = Observation("odometer", Status.OK, """{"rawValue":12480,"unit":null}""", previousEvening)
        val tile = Record("r", observedAt, Category.PLACE, Source.MOCK, collecting = false, observations = listOf(odo)).summaryTiles(clock).single()
        assertEquals("관측 11월 8일 오후 6:40", tile.observed)
    }

    @Test fun summaryNotesMissingUnit() {
        assertEquals(UNIT_NOT_PROVIDED, Observation("odometer", Status.OK, """{"rawValue":12480,"unit":null}""").summary(clock).note)
        val withUnit = Observation("odometer", Status.OK, """{"rawValue":12480,"unit":"km"}""").summary(clock)
        assertEquals("12,480 km", withUnit.value)
        assertNull(withUnit.note)
        val tires = Observation("tires", Status.OK, """[{"area":"LEFT_FRONT","rawValue":33}]""").summary(clock)
        assertEquals(UNIT_NOT_PROVIDED, tires.note)
    }

    @Test fun partialTiresKeepItemStatusAndSeparateUnsupported() {
        val tires = Observation("tires", Status.UNAVAILABLE, """[{"area":"LEFT_FRONT","rawValue":33},{"area":"RIGHT_REAR","rawValue":null}]""")
        val tile = tires.summary(clock)
        assertEquals("33 · $UNSUPPORTED_WHEEL · $UNSUPPORTED_WHEEL · $MISSING_WHEEL", tile.value)
        assertEquals(Status.UNAVAILABLE, tile.status)
    }
}
