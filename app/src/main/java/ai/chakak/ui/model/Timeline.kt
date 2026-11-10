package ai.chakak.ui.model

import ai.chakak.core.Record
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

/**
 * 타임라인 표시 기준. 오늘/어제 판정과 시각 표시를 한곳에서 정한다.
 * 값은 날짜 단위라 같은 날 안에서는 같은 값(equals)이 되어 `remember` 키로 써도 매 구성마다 무효화되지 않는다.
 * 앱은 `ProvideTimelineClock`(날짜·시각·시간대 변경 방송으로 갱신)으로, Preview는 고정 값으로 [LocalTimelineClock]에 넣는다.
 */
@Immutable
data class TimelineClock(val today: LocalDate, val zone: ZoneId, val locale: Locale) {
    companion object {
        /** 기기 날짜·시간대·로캘 */
        fun system(): TimelineClock {
            val zone = ZoneId.systemDefault()
            return TimelineClock(LocalDate.now(zone), zone, Locale.getDefault())
        }
    }
}

/** 화면이 쓸 기준. 앱 루트와 Preview가 제공한다. 제공되지 않으면(null) [currentTimelineClock]이 기기 값을 읽는다. */
val LocalTimelineClock = staticCompositionLocalOf<TimelineClock?> { null }

/** 지금 화면이 쓸 기준. 제공된 값이 없으면 기기 날짜를 읽는다(같은 날이면 같은 값) */
@Composable
@ReadOnlyComposable
fun currentTimelineClock(): TimelineClock = LocalTimelineClock.current ?: TimelineClock.system()

/** 하루 묶음. [records]는 입력 순서를 유지한다. */
@Immutable
data class DayGroup(val date: LocalDate, val header: String, val records: List<Record>)

/**
 * 기록을 명령 수신 날짜(기기 시간대)별로 묶는다. 묶음 순서와 묶음 안 순서는 입력(저장소) 순서를 따른다.
 * 머리: "오늘 · 11월 9일 일요일", "어제 · 11월 8일 토요일", 그 외 "11월 6일 목요일".
 */
fun groupByDay(records: List<Record>, clock: TimelineClock): List<DayGroup> {
    return records
        .groupBy { localDateOf(it.createdAt, clock) }
        .map { (date, items) -> DayGroup(date, dayHeader(date, clock.today, clock.locale), items.toList()) }
}

const val TODAY_LABEL = "오늘"
const val YESTERDAY_LABEL = "어제"
private const val DAY_PATTERN = "M월 d일 EEEE"
private const val MONTH_DAY_PATTERN = "M월 d일"
private const val YEAR_MONTH_DAY_PATTERN = "yyyy년 M월 d일"
const val OBSERVED_PREFIX = "관측"
const val OBSERVED_TIME_UNKNOWN = "관측 시각 정보 없음"
const val COMMAND_RECEIVED_PREFIX = "명령 수신"

/** 날짜 머리. 요일 이름은 [locale]을 따른다. */
fun dayHeader(date: LocalDate, today: LocalDate, locale: Locale): String {
    val label = date.format(DateTimeFormatter.ofPattern(DAY_PATTERN, locale))
    return when (date) {
        today -> "$TODAY_LABEL · $label"
        today.minusDays(1) -> "$YESTERDAY_LABEL · $label"
        else -> label
    }
}

/** 짧은 시각. ko: "오전 9:34" */
fun formatClockTime(millis: Long, clock: TimelineClock): String =
    Instant.ofEpochMilli(millis).atZone(clock.zone)
        .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(clock.locale))

/** 요일까지 있는 날짜. ko: "2026년 11월 9일 일요일" */
fun formatFullDate(millis: Long, clock: TimelineClock): String =
    Instant.ofEpochMilli(millis).atZone(clock.zone)
        .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(clock.locale))

/** 명령 수신 머리 줄. ko: "명령 수신 2026년 11월 9일 월요일 오전 9:34" */
fun commandReceivedText(millis: Long, clock: TimelineClock): String =
    "$COMMAND_RECEIVED_PREFIX ${formatFullDate(millis, clock)} ${formatClockTime(millis, clock)}"

/**
 * 관측 시각. 명령 수신([commandAt])과 같은 날이면 시각만("오전 9:34"), 다른 날이면 날짜를 붙여
 * ("11월 8일 오후 6:40", 해가 다르면 "2025년 11월 8일 …") 오래된 마지막 관측값을 새 값으로 읽지 않게 한다(spec 9.7).
 */
fun formatObservedClock(observedAt: Long, commandAt: Long, clock: TimelineClock): String {
    val observed = localDateOf(observedAt, clock)
    val command = localDateOf(commandAt, clock)
    val time = formatClockTime(observedAt, clock)
    val pattern = when {
        observed == command -> return time
        observed.year == command.year -> MONTH_DAY_PATTERN
        else -> YEAR_MONTH_DAY_PATTERN
    }
    return "${observed.format(DateTimeFormatter.ofPattern(pattern, clock.locale))} $time"
}

/** "관측 오전 9:34". 관측 시각이 없으면 [OBSERVED_TIME_UNKNOWN] */
fun observedTimeText(observedAt: Long?, commandAt: Long, clock: TimelineClock): String =
    observedAt?.let { "$OBSERVED_PREFIX ${formatObservedClock(it, commandAt, clock)}" } ?: OBSERVED_TIME_UNKNOWN

private fun localDateOf(millis: Long, clock: TimelineClock): LocalDate = Instant.ofEpochMilli(millis).atZone(clock.zone).toLocalDate()
