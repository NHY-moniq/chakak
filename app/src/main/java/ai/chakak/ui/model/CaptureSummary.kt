package ai.chakak.ui.model

import ai.chakak.core.Category
import ai.chakak.core.Record
import ai.chakak.core.Status

/**
 * 기록 한 건의 수집 결과 요약. 목록 행과 상세 요약이 같은 판정을 쓴다.
 * 누락을 숨기지 않는다(spec 5.5): 관측 항목이 0개인 기록은 완료가 아니다.
 */
sealed interface CaptureSummary {
    /** 아직 수집 중인 기록 */
    data object Collecting : CaptureSummary
    /** 수집 항목이 하나도 없는 기록. 완료로 보지 않는다 */
    data object NoObservations : CaptureSummary
    /** 주행 제한 등으로 수집이 중단된 항목이 있음. 중단 전에 받은 관측값 수도 함께 보여준다 */
    data class Interrupted(val okCount: Int, val total: Int) : CaptureSummary
    /** 모든 항목에 관측값이 있음 */
    data class Complete(val total: Int) : CaptureSummary
    /** 일부 항목만 관측값이 있음 */
    data class Partial(val okCount: Int, val total: Int) : CaptureSummary
    /** 모든 항목이 실패·미지원·권한 없음 등으로 관측값이 없음 */
    data class NoneCollected(val total: Int) : CaptureSummary
}

/**
 * 판정 우선순위:
 * 1. [Record.collecting] → [CaptureSummary.Collecting]
 * 2. 관측 항목 0개 → [CaptureSummary.NoObservations]
 * 3. INTERRUPTED 항목이 하나라도 있음 → [CaptureSummary.Interrupted]
 * 4. 모두 OK → [CaptureSummary.Complete]
 * 5. OK가 하나도 없음 → [CaptureSummary.NoneCollected]
 * 6. 그 외 → [CaptureSummary.Partial]
 *
 * 수집이 끝났는데 PENDING으로 남은 항목은 관측값 없음으로 센다(완료로 보지 않음).
 */
fun Record.captureSummary(): CaptureSummary {
    if (collecting) return CaptureSummary.Collecting
    if (observations.isEmpty()) return CaptureSummary.NoObservations
    val total = observations.size
    val okCount = observations.count { it.status == Status.OK }
    if (observations.any { it.status == Status.INTERRUPTED }) return CaptureSummary.Interrupted(okCount, total)
    return when (okCount) {
        total -> CaptureSummary.Complete(total)
        0 -> CaptureSummary.NoneCollected(total)
        else -> CaptureSummary.Partial(okCount, total)
    }
}

fun CaptureSummary.label(): String = when (this) {
    CaptureSummary.Collecting -> "수집 중"
    CaptureSummary.NoObservations -> "수집 항목 없음"
    is CaptureSummary.Interrupted -> "수집 중단 · $okCount/$total 수집"
    is CaptureSummary.Complete -> "수집 완료"
    is CaptureSummary.Partial -> "일부 정보 없음 · $okCount/$total 수집"
    is CaptureSummary.NoneCollected -> "수집된 정보 없음 · 0/$total"
}

fun Status.label(): String = when (this) {
    Status.PENDING -> "수집 중"
    Status.OK -> "관측값 있음"
    Status.UNAVAILABLE -> "유효한 관측값 없음"
    Status.UNSUPPORTED -> "지원하지 않음"
    Status.DENIED -> "권한 없음"
    Status.TIMEOUT -> "응답 시간 초과"
    Status.ERROR -> "수집 오류"
    Status.INTERRUPTED -> "수집 중단"
}

/** spec 9.2: 필터는 전체·장소·차량 상태. */
fun Category.label(): String = when (this) {
    Category.VEHICLE_STATUS -> "차량 상태"
    Category.PLACE -> "장소"
    Category.UNCLASSIFIED -> "미분류"
}

/** 수집 항목 키의 표시 이름. 모르는 키는 숨기지 않고 그대로 보여준다. */
fun observationKeyLabel(key: String): String = when (key) {
    "location" -> "위치"
    "address" -> "주소"
    "odometer" -> "누적 주행 거리"
    "telltales" -> "경고등 상태"
    "tires" -> "타이어 상태"
    else -> key
}
