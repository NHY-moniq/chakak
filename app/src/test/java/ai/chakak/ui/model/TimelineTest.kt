package ai.chakak.ui.model

import ai.chakak.core.Category
import ai.chakak.core.Record
import ai.chakak.core.Source
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Locale

class TimelineTest {
    private val seoul = ZoneId.of("Asia/Seoul")
    /** 오늘 = 2026-11-09(월) */
    private val clock = TimelineClock(LocalDate.of(2026, 11, 9), seoul, Locale.KOREA)

    private fun at(day: Int, hour: Int, minute: Int = 0) =
        ZonedDateTime.of(2026, 11, day, hour, minute, 0, 0, seoul).toInstant().toEpochMilli()

    private fun record(id: String, millis: Long) = Record(id, millis, Category.PLACE, Source.MOCK, collecting = false)

    @Test fun headersForTodayYesterdayAndOlder() {
        val today = LocalDate.of(2026, 11, 9)
        assertEquals("오늘 · 11월 9일 월요일", dayHeader(today, today, Locale.KOREA))
        assertEquals("어제 · 11월 8일 일요일", dayHeader(today.minusDays(1), today, Locale.KOREA))
        assertEquals("11월 6일 금요일", dayHeader(today.minusDays(3), today, Locale.KOREA))
    }

    @Test fun groupsKeepInputOrderAndMergeSameDay() {
        val records = listOf(
            record("a", at(9, 9, 34)), record("b", at(9, 8, 12)),
            record("c", at(8, 18, 40)), record("d", at(6, 17, 2)),
        )
        val groups = groupByDay(records, clock)
        assertEquals(listOf("오늘 · 11월 9일 월요일", "어제 · 11월 8일 일요일", "11월 6일 금요일"), groups.map { it.header })
        assertEquals(listOf("a", "b"), groups[0].records.map { it.id })
        assertEquals(listOf("d"), groups[2].records.map { it.id })
    }

    @Test fun dayBoundaryFollowsZone() {
        // 2026-11-08 23:30 KST = 2026-11-08 14:30 UTC
        val lateYesterday = at(8, 23, 30)
        assertEquals("어제 · 11월 8일 일요일", groupByDay(listOf(record("x", lateYesterday)), clock).single().header)
        val utcClock = clock.copy(zone = ZoneId.of("UTC"))
        // UTC 기준 기록 = 11-08 14:30 → 여전히 어제
        assertEquals("어제 · 11월 8일 일요일", groupByDay(listOf(record("x", lateYesterday)), utcClock).single().header)
        // 11-09 00:30 KST는 UTC로 11-08 15:30 → UTC 시계에서는 어제, 서울 시계에서는 오늘
        val earlyToday = at(9, 0, 30)
        assertEquals("오늘 · 11월 9일 월요일", groupByDay(listOf(record("y", earlyToday)), clock).single().header)
        assertEquals("어제 · 11월 8일 일요일", groupByDay(listOf(record("y", earlyToday)), utcClock).single().header)
    }

    @Test fun emptyInputGivesNoGroups() {
        assertEquals(emptyList<DayGroup>(), groupByDay(emptyList(), clock))
    }

    @Test fun clockTimeAndFullDateInKorean() {
        assertEquals("오전 9:34", formatClockTime(at(9, 9, 34), clock))
        assertEquals("오후 6:40", formatClockTime(at(8, 18, 40), clock))
        assertEquals("2026년 11월 9일 월요일", formatFullDate(at(9, 9, 34), clock))
    }

    @Test fun clockEqualWithinSameDaySoRememberKeysStayStable() {
        val a = TimelineClock(LocalDate.of(2026, 11, 9), seoul, Locale.KOREA)
        assertEquals(clock, a)
        assertEquals(clock.hashCode(), a.hashCode())
        assertNotEquals(clock, clock.copy(today = LocalDate.of(2026, 11, 10)))
    }

    @Test fun midnightRolloverMovesTodayToYesterday() {
        val records = listOf(record("a", at(9, 9, 34)))
        assertEquals("오늘 · 11월 9일 월요일", groupByDay(records, clock).single().header)
        val nextDay = clock.copy(today = LocalDate.of(2026, 11, 10))
        assertEquals("어제 · 11월 9일 월요일", groupByDay(records, nextDay).single().header)
    }

    @Test fun observedTimeShowsOnlyTimeOnSameDayAsCommand() {
        assertEquals("오전 9:30", formatObservedClock(at(9, 9, 30), at(9, 9, 34), clock))
        assertEquals("관측 오전 9:30", observedTimeText(at(9, 9, 30), at(9, 9, 34), clock))
    }

    @Test fun observedTimeIncludesDateWhenStale() {
        assertEquals("11월 8일 오후 6:40", formatObservedClock(at(8, 18, 40), at(9, 9, 34), clock))
        val lastYear = ZonedDateTime.of(2025, 12, 31, 23, 50, 0, 0, seoul).toInstant().toEpochMilli()
        assertEquals("관측 2025년 12월 31일 오후 11:50", observedTimeText(lastYear, at(9, 9, 34), clock))
    }

    @Test fun observedDateComparedInClockZone() {
        // 11-09 00:10 KST 관측, 11-09 00:20 KST 명령 → 서울에서는 같은 날, UTC에서는 같은 날(11-08)
        assertEquals("오전 12:10", formatObservedClock(at(9, 0, 10), at(9, 0, 20), clock))
        // 11-08 23:50 KST 관측, 11-09 00:20 KST 명령 → 서울에서는 다른 날
        assertEquals("11월 8일 오후 11:50", formatObservedClock(at(8, 23, 50), at(9, 0, 20), clock))
    }

    @Test fun missingObservedTimeIsStated() {
        assertEquals(OBSERVED_TIME_UNKNOWN, observedTimeText(null, at(9, 9, 34), clock))
    }

    @Test fun commandReceivedLineHasDateAndTime() {
        assertEquals("명령 수신 2026년 11월 9일 월요일 오전 9:34", commandReceivedText(at(9, 9, 34), clock))
    }
}
