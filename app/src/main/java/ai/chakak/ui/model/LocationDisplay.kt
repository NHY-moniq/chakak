package ai.chakak.ui.model

import ai.chakak.core.Observation
import ai.chakak.core.Record
import ai.chakak.platform.GuidanceTarget
import ai.chakak.core.Status
import androidx.compose.runtime.Immutable

const val PLACE_LOCATION_NOTICE = "기록 당시 차량 위치이며, 주변 시설의 정확한 위치와 다를 수 있습니다."
const val ADDRESS_NOTICE = "주소는 좌표로 찾은 근사값이라 주변 시설 위치와 다를 수 있어요"
const val ADDRESS_FAILED = "주소를 가져오지 못했어요"
const val NO_LOCATION_ITEM = "위치 항목을 수집하지 않은 기록입니다."
const val EMPTY_VALUE = "값이 비어 있습니다"

/**
 * 위치 패널 내용.
 * - [headline]: 큰 줄(주소 또는 조회 안내). null이면 [headlineStatus]를 배지로 보여준다.
 * - [details]: 작은 줄(관측 시각, 주소 실패 사유).
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

/** 저장된 주소와 관측 시각. 좌표와 원문 JSON은 화면에 노출하지 않는다. */
fun locationDisplay(location: Observation?, address: Observation?, commandAt: Long, clock: TimelineClock): LocationDisplay {
    val addressText = savedLocationAddress(location, address)
    return LocationDisplay(
        headline = addressText ?: when {
            location == null -> NO_LOCATION_ITEM
            location.status == Status.OK -> ADDRESS_FAILED
            else -> null
        },
        headlineStatus = location?.status?.takeIf { addressText == null && it != Status.OK },
        details = listOfNotNull(location?.observedLine(commandAt, clock),
            address?.takeIf { it.status != Status.OK }?.let { "$ADDRESS_FAILED · ${it.statusReason()}" }),
        notice = if (addressText != null) ADDRESS_NOTICE else PLACE_LOCATION_NOTICE,
        addressShown = addressText != null,
    )

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

/** Address stored separately or embedded by Navi in the location payload. */
internal fun savedLocationAddress(location: Observation?, address: Observation?): String? {
    val explicit = address?.takeIf { it.status == Status.OK }?.value?.trim()?.takeIf { it.isNotEmpty() }
    return explicit ?: location?.takeIf { it.status == Status.OK }?.value?.let { raw ->
        runCatching { org.json.JSONObject(raw).optString("address").trim().takeIf { it.isNotEmpty() && it != "null" } }.getOrNull()
    }
}

/** Valid recorded coordinates are retained for navigation, but not shown as an address. */
fun Record.guidanceTarget(): GuidanceTarget? {
    val location = observation(ObservationKeys.LOCATION)?.takeIf { it.status == Status.OK } ?: return null
    val reading = parseLocation(location.value) ?: return null
    return GuidanceTarget(reading.latitude, reading.longitude, displayTitle(),
        savedLocationAddress(location, observation(ObservationKeys.ADDRESS)))
}
