package ai.chakak.ui.model

import ai.chakak.core.Observation
import ai.chakak.core.Status
import org.json.JSONObject
import org.json.JSONArray

internal data class VehicleCell(val area: String, val label: String, val value: String, val status: Status, val active: Boolean?)

internal val seatRows = listOf(
    listOf("ROW_1_LEFT", "ROW_1_CENTER", "ROW_1_RIGHT"),
    listOf("ROW_2_LEFT", "ROW_2_CENTER", "ROW_2_RIGHT"),
    listOf("ROW_3_LEFT", "ROW_3_CENTER", "ROW_3_RIGHT"),
)

internal fun vehicleAreaLabel(area: String): String = when (area) {
    "ROW_1_LEFT" -> "1열 왼쪽"
    "ROW_1_CENTER" -> "1열 중앙"
    "ROW_1_RIGHT" -> "1열 오른쪽"
    "ROW_2_LEFT" -> "2열 왼쪽"
    "ROW_2_CENTER" -> "2열 중앙"
    "ROW_2_RIGHT" -> "2열 오른쪽"
    "ROW_3_LEFT" -> "3열 왼쪽"
    "ROW_3_CENTER" -> "3열 중앙"
    "ROW_3_RIGHT" -> "3열 오른쪽"
    "LEFT_FRONT" -> "앞 왼쪽"
    "RIGHT_FRONT" -> "앞 오른쪽"
    "LEFT_REAR" -> "뒤 왼쪽"
    "RIGHT_REAR" -> "뒤 오른쪽"
    "FRONT" -> "앞"
    "REAR" -> "뒤"
    "값" -> "전체"
    else -> "기타 위치"
}

internal fun Status.shortLabel(): String = when (this) {
    Status.OK -> "확인됨"
    Status.PENDING -> "수집 중"
    Status.UNAVAILABLE -> "값 없음"
    Status.UNSUPPORTED -> "미지원"
    Status.DENIED -> "권한 없음"
    Status.TIMEOUT -> "응답 없음"
    Status.ERROR -> "조회 오류"
    Status.INTERRUPTED -> "수집 중단"
}

/** Combined areas are split once; a more specific area wins, even if its read failed. */
internal fun Observation.visualCells(): List<VehicleCell> = runCatching {
    val entries = JSONObject(value ?: "{}").optJSONArray("entries") ?: return@runCatching emptyList()
    val candidates = mutableListOf<Pair<Int, VehicleCell>>()
    for (i in 0 until entries.length()) {
        val entry = entries.getJSONObject(i)
        val areas = entry.optString("area").split(',').map(String::trim)
            .filter { it.isNotEmpty() && it != "UNKNOWN" }.ifEmpty { listOf("UNKNOWN") }
        val status = runCatching { Status.valueOf(entry.optString("status")) }.getOrDefault(Status.ERROR)
        val raw = entry.opt("value")
        val unit = entry.optString("unit").takeIf { !entry.isNull("unit") && it.isNotBlank() }
        val active = if (status != Status.OK) null else when (raw) {
            is Boolean -> raw
            "ON", "ENABLED", "OPEN", "LOCKED" -> true
            "OFF", "DISABLED", "CLOSED", "UNLOCKED" -> false
            else -> null
        }
        val text = if (status != Status.OK) status.shortLabel() else when (raw) {
            null, JSONObject.NULL -> "값 없음"
            is Boolean -> when {
                "BrakePedal" in key -> if (raw) "밟음" else "놓음"
                else -> if (raw) "켜짐" else "꺼짐"
            }
            is Number -> formatNumber(raw.toDouble(), java.util.Locale.KOREA) + (unit?.let { " $it" } ?: " · 단위 미확인")
            is JSONArray -> if (raw.length() == 0) "없음" else "${raw.length()}개 항목"
            is JSONObject -> "상세 값 있음"
            else -> when (raw.toString()) {
                "ON", "ENABLED" -> "켜짐"
                "OFF", "DISABLED" -> "꺼짐"
                "OPEN" -> "열림"
                "CLOSED" -> "닫힘"
                "LOCKED" -> "잠김"
                "UNLOCKED" -> "잠금 해제"
                "NORMAL" -> "정상"
                "LOW" -> "낮음"
                "HIGH" -> "높음"
                "UNKNOWN" -> "미확인"
                "AUTO" -> "자동"
                "PARK" -> "주차"
                else -> "상세 값"
            }
        }
        areas.forEach { candidates += areas.size to VehicleCell(it, vehicleAreaLabel(it), text, status, active) }
    }
    candidates.groupBy { it.second.area }.values.map { it.minBy { candidate -> candidate.first }.second }
}.getOrDefault(emptyList())
