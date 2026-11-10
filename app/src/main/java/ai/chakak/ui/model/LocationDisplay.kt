package ai.chakak.ui.model

import ai.chakak.core.Observation
import ai.chakak.core.Status
import androidx.compose.runtime.Immutable

const val PLACE_LOCATION_NOTICE = "기록 당시 차량 위치이며, 주변 시설의 정확한 위치와 다를 수 있습니다."
const val ADDRESS_NOTICE = "주소는 좌표로 찾은 근사값이라 주변 시설 위치와 다를 수 있어요"
const val ADDRESS_FAILED = "주소를 가져오지 못했어요"
const val NO_LOCATION_ITEM = "위치 항목을 수집하지 않은 기록입니다."
const val EMPTY_VALUE = "값이 비어 있습니다"

/**
 * 위치 패널 내용.
 * - [headline]: 큰 줄(주소 → 좌표 → 원문). null이면 [headlineStatus]를 배지로 보여준다.
 * - [details]: 작은 줄(좌표·신뢰도·관측 시각, 주소 실패 사유).
 * - [notice]: 주소를 보여줄 때는 주소 근사값 안내, 그 외에는 차량 위치 안내. 항상 있다.
 */
@Immutable
data class LocationDisplay(
    val headline: String?,
    val headlineStatus: Status?,
    val details: List<String>,
    val notice: String,
    val addressShown: Boolean,
)

/**
 * 주소(선택 항목 "address")가 OK면 주소를 크게, 좌표는 작게.
 * 주소 항목이 있지만 실패면 좌표를 크게 + "주소를 가져오지 못했어요 · 사유".
 * 주소 항목이 없으면(현재 플랫폼) 좌표를 크게, 실패 문구 없음.
 */
fun locationDisplay(location: Observation?, address: Observation?, commandAt: Long, clock: TimelineClock): LocationDisplay {
    val addressText = address?.takeIf { it.status == Status.OK }?.value?.trim()?.takeIf { it.isNotEmpty() }
    val coordinate = location?.let { coordinateLine(it) }
    val meta = location?.let { locationMeta(it, commandAt, clock) }.orEmpty()
    val addressFailure = address?.takeIf { addressText == null }?.let { "$ADDRESS_FAILED · ${it.statusReason()}" }
    return if (addressText != null) {
        val coordinatePart = coordinate ?: location?.let { "좌표 ${it.statusReason()}" }
        LocationDisplay(addressText, null, listOfNotNull(joinMeta(listOfNotNull(coordinatePart) + meta)), ADDRESS_NOTICE, true)
    } else {
        LocationDisplay(
            headline = coordinate ?: if (location == null) NO_LOCATION_ITEM else null,
            headlineStatus = location?.status?.takeIf { coordinate == null },
            details = listOfNotNull(joinMeta(meta), addressFailure),
            notice = PLACE_LOCATION_NOTICE,
            addressShown = false,
        )
    }
}

/** 좌표(파싱 실패 시 원문). 값이 없으면 null */
private fun coordinateLine(location: Observation): String? = when {
    location.status != Status.OK -> null
    location.value.isNullOrBlank() -> EMPTY_VALUE
    else -> parseLocation(location.value)?.coordinatesText() ?: location.value
}

/** 신뢰도 · 관측 시각. 관측 날짜가 명령 수신 날짜와 다르면 날짜를 붙인다 */
private fun locationMeta(location: Observation, commandAt: Long, clock: TimelineClock): List<String> {
    val confidence = parseLocation(location.value)?.confidence?.let { "신뢰도 ${formatNumber(it, clock.locale)}" }
    return listOfNotNull(confidence, location.observedLine(commandAt, clock))
}

/**
 * 항목별 관측 시각 줄(spec 5.4: 명령 수신 시각과 구분). 관측 시각이 있으면 "관측 오전 9:34",
 * 값은 있는데 시각이 없으면 "관측 시각 정보 없음". 값도 시각도 없는 실패 항목은 null(상태 배지만 보인다).
 */
fun Observation.observedLine(commandAt: Long, clock: TimelineClock): String? =
    if (observedAt == null && status != Status.OK && value.isNullOrBlank()) null else observedTimeText(observedAt, commandAt, clock)

private fun joinMeta(parts: List<String>): String? = parts.takeIf { it.isNotEmpty() }?.joinToString(" · ")

/** 값이 없을 때의 사유(상태 이름). OK인데 값이 비면 그렇게 알린다 */
fun Observation.statusReason(): String = if (status == Status.OK) EMPTY_VALUE else status.label()
