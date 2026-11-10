package ai.chakak.ui.model

import ai.chakak.core.Category
import ai.chakak.core.Observation
import ai.chakak.core.Record
import ai.chakak.core.Status
import androidx.compose.runtime.Immutable

const val NO_LOCATION_INFO = "위치 정보 없음"
const val NO_LIT_TELLTALES = "켜진 항목 없음"
const val OBSERVATION_NOTE = "기록 순간에 조회한 값이에요. 진단 결과나 지금 상태가 아니에요."
const val MISSING_WHEEL = "없음"
const val UNSUPPORTED_WHEEL = "미지원"
const val LOCATION_PENDING = "위치 확인 중"
const val LOCATION_RAW_ONLY = "위치 원문 있음"
const val UNIT_NOT_PROVIDED = "단위 정보 없음"

/**
 * 타임라인 카드의 한 줄 메타.
 * - 장소: 주소 → 메모 첫 줄(따옴표) → 좌표 → 위치 원문 표시 → "위치 확인 중"(수집 중) → "위치 정보 없음"
 * - 차량 상태: 수집 완료면 "차량 상태 · 수집 완료", 아니면 "차량 상태"(사유는 카드의 상태 배지가 보여준다)
 * - 미분류: 메모 첫 줄이 있으면 따옴표로 덧붙인다
 * 메모는 운전자가 쓴 글이라 차량 값과 헷갈리지 않게 항상 “…”로 감싼다.
 */
fun Record.rowMetaLine(): String {
    val category = category.label()
    val detail = when (this.category) {
        Category.PLACE -> placeHint()
        Category.VEHICLE_STATUS -> captureSummary().takeIf { it is CaptureSummary.Complete }?.label()
        Category.UNCLASSIFIED -> quotedMemo()
    }
    return detail?.let { "$category · $it" } ?: category
}

/** 행에 상태 배지를 보일지. 수집 완료가 아니면 항상 보인다(누락을 숨기지 않음) */
fun Record.showsStatusBadge(): Boolean = captureSummary() !is CaptureSummary.Complete

private fun Record.placeHint(): String {
    val address = observation(ObservationKeys.ADDRESS)?.takeIf { it.status == Status.OK }?.value?.trim()?.takeIf { it.isNotEmpty() }
    return address ?: quotedMemo() ?: locationHint(observation(ObservationKeys.LOCATION))
}

/** 좌표 → (형식을 모르는 값) 원문 있음 → 확인 중 → 없음 */
private fun locationHint(location: Observation?): String {
    val raw = location?.takeIf { it.status == Status.OK }?.value?.takeIf { it.isNotBlank() }
    return when {
        raw != null -> parseLocation(raw)?.coordinatesText() ?: LOCATION_RAW_ONLY
        location?.status == Status.PENDING -> LOCATION_PENDING
        else -> NO_LOCATION_INFO
    }
}

private fun Record.quotedMemo(): String? = memo.lineSequence().map { it.trim() }.firstOrNull { it.isNotEmpty() }?.let { "“$it”" }

/** 분류별 개수. 탭 라벨("장소 7")에 쓴다. null 키는 전체 */
fun categoryCounts(records: List<Record>): Map<Category?, Int> {
    val byCategory = records.groupingBy { it.category }.eachCount()
    return mapOf<Category?, Int>(null to records.size) + Category.entries.associateWith { byCategory[it] ?: 0 }
}

/** "4개 중 3개 수집" */
fun Record.collectedCountText(): String = "${observations.size}개 중 ${observations.count { it.status == Status.OK }}개 수집"

/**
 * 요약 타일 한 칸: 값 문장([value]) 그리고/또는 상태([status]).
 * 일부 바퀴만 값이 있는 타이어는 값과 항목 상태를 함께 보인다.
 * [note]는 "단위 정보 없음" 같은 보조 줄, [observed]는 항목별 관측 시각 줄(spec 5.4).
 */
@Immutable
data class ObservationSummary(
    val label: String,
    val value: String?,
    val status: Status?,
    val note: String? = null,
    val observed: String? = null,
)

/** 장소 상세 하단 요약. 위치·주소는 패널에서 보여주므로 뺀다 */
fun Record.summaryTiles(clock: TimelineClock): List<ObservationSummary> =
    otherObservations(ObservationKeys.locationKeys).map { it.summary(clock).copy(observed = it.observedLine(createdAt, clock)) }

/** 값이 있으면 사람이 읽는 짧은 문장, 없으면 상태 */
fun Observation.summary(clock: TimelineClock): ObservationSummary {
    val label = observationKeyLabel(key)
    // 타이어는 일부 바퀴만 값이 있어도(UNAVAILABLE) 있는 값을 보여주고, 항목 상태도 함께 보인다
    val tires = if (key == ObservationKeys.TIRES) parseTires(value) else null
    if (status != Status.OK && tires == null) return ObservationSummary(label, null, status)
    val itemStatus = status.takeIf { it != Status.OK }
    val raw = value?.takeIf { it.isNotBlank() } ?: return ObservationSummary(label, EMPTY_VALUE, itemStatus)
    val odometer = if (key == ObservationKeys.ODOMETER) parseOdometer(raw) else null
    val text = when (key) {
        ObservationKeys.ODOMETER -> odometer?.text(clock.locale)
        ObservationKeys.TELLTALES -> parseTelltales(raw)?.let(::telltaleSummary)
        ObservationKeys.TIRES -> tires?.let { tireSummary(it, clock) }
        ObservationKeys.LOCATION -> parseLocation(raw)?.coordinatesText()
        else -> null
    }
    val unitMissing = (odometer != null && odometer.unit == null) || (tires?.unitMissing() == true)
    return ObservationSummary(label, text ?: raw, itemStatus, note = UNIT_NOT_PROVIDED.takeIf { unitMissing })
}

private fun telltaleSummary(items: List<TelltaleReading>): String {
    val lit = items.filter { it.lit }
    return if (lit.isEmpty()) NO_LIT_TELLTALES else lit.joinToString(", ") { telltaleLabel(it) }
}

/**
 * "33 · 33 · 32 · 없음 psi" — 앞 왼쪽, 앞 오른쪽, 뒤 왼쪽, 뒤 오른쪽 순. 단위는 하나로 같을 때만 붙인다.
 * 값이 없는 바퀴는 [MISSING_WHEEL](조회했으나 값 없음), 목록에 없던 바퀴는 [UNSUPPORTED_WHEEL](미지원)로 구분한다.
 */
fun tireSummary(tires: TireReadings, clock: TimelineClock): String {
    val values = Wheel.entries.map { wheel ->
        when {
            !tires.has(wheel) -> UNSUPPORTED_WHEEL
            else -> tires.valueOf(wheel)?.let { formatNumber(it.value, clock.locale) } ?: MISSING_WHEEL
        }
    }
    return values.joinToString(" · ") + (tires.singleUnit()?.let { " $it" } ?: "")
}

/** 값이 있는 바퀴가 있는데 어느 바퀴도 단위를 받지 못했는지 */
private fun TireReadings.unitMissing(): Boolean = readings.values.filterNotNull().let { it.isNotEmpty() && it.all { m -> m.unit == null } }

/** 값이 있는 바퀴의 단위가 하나로 같으면 그 단위, 아니면 null */
private fun TireReadings.singleUnit(): String? = Wheel.entries.mapNotNull { valueOf(it)?.unit }.distinct().singleOrNull()
