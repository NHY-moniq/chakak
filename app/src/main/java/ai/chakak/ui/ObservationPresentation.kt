package ai.chakak.ui

import ai.chakak.core.Observation
import org.json.JSONArray
import org.json.JSONObject
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val koreanTime = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss 'KST'", Locale.KOREA)
    .withZone(ZoneId.of("Asia/Seoul"))

internal fun formatRecordTime(timestamp: Long): String = koreanTime.format(Instant.ofEpochMilli(timestamp))

internal fun Observation.displayLines(): List<Pair<String, String>> {
    val raw = value ?: return listOf("관측값" to "없음")
    return runCatching {
        if(raw.trimStart().startsWith("{")) {
            val entries = JSONObject(raw).optJSONArray("entries")
            if(entries != null) return (0 until entries.length()).map { index ->
                val entry = entries.getJSONObject(index)
                val area = entry.getString("area").split(", ").filter { it != "UNKNOWN" }.joinToString(" · ") {
                    when(it) { "LEFT_FRONT" -> "앞 왼쪽"; "RIGHT_FRONT" -> "앞 오른쪽"; "LEFT_REAR" -> "뒤 왼쪽"; "RIGHT_REAR" -> "뒤 오른쪽"; else -> it }
                }.ifBlank { "위치 미확인" }
                val status = entry.getString("status")
                val rawValue = entry.opt("value")
                val unit = entry.optString("unit").takeIf { !entry.isNull("unit") && it.isNotBlank() }
                area to if(status == "OK") when {
                    rawValue is Number && unit != null -> "$rawValue $unit"
                    rawValue is Boolean && key in setOf("vehicle.getHvacAcState", "vehicle.getHvacRecirculationState") -> if(rawValue) "ON (켜짐)" else "OFF (꺼짐)"
                    else -> readableVehicleValue(rawValue)
                } else when(status) {
                    "DENIED" -> "권한 없음"; "UNSUPPORTED" -> "미지원"; "TIMEOUT" -> "응답 시간 초과"
                    "ERROR" -> "조회 오류"; else -> "관측값 없음"
                }
            }
        }
        when (key) {
            "location" -> JSONObject(raw).let { location -> listOf(
                "위도" to location.get("latitude").toString(),
                "경도" to location.get("longitude").toString(),
                "위치 신뢰도" to location.optString("confidence", "알 수 없음")
            ) }
            "odometer" -> listOf("누적 주행 거리" to JSONObject(raw).measurement())
            "tires" -> JSONArray(raw).let { tires ->
                if (tires.length() == 0) listOf("타이어" to "보고된 항목 없음")
                else (0 until tires.length()).map { index ->
                    val tire = tires.getJSONObject(index)
                    val areas = tire.getString("area").split(',').map { it.trim() }
                    val known = areas.filter { it != "UNKNOWN" }
                    val label = (known.ifEmpty { areas }).joinToString(" · ") { area -> when (area) {
                        "LEFT_FRONT" -> "앞 왼쪽"; "RIGHT_FRONT" -> "앞 오른쪽"
                        "LEFT_REAR" -> "뒤 왼쪽"; "RIGHT_REAR" -> "뒤 오른쪽"
                        "UNKNOWN" -> "위치 미확인"; else -> area
                    } }
                    label to tire.measurement()
                }
            }
            "telltales" -> JSONArray(raw).let { lights ->
                if (lights.length() == 0) listOf("경고등" to "보고된 항목 없음")
                else (0 until lights.length()).map { index ->
                    val light = lights.getJSONObject(index)
                    val state = when (val state = light.getString("state")) {
                        "ON" -> "켜짐"; "OFF" -> "꺼짐"; "BLINKING" -> "깜빡임"; else -> state
                    }
                    light.getString("type").replace('_', ' ') to "$state · 경고 횟수 ${light.getInt("warningCount")}회"
                }
            }
            else -> listOf("관측값" to "표시를 지원하지 않는 항목")
        }
    }.getOrElse { listOf("관측값" to if (raw.startsWith("TEST:")) "테스트용 샘플 데이터" else "표시할 수 없는 형식") }
}

private fun JSONObject.measurement(): String {
    if (isNull("rawValue")) return "관측값 없음"
    val number = java.text.NumberFormat.getNumberInstance(Locale.KOREA).apply { maximumFractionDigits = 6 }
        .format(getDouble("rawValue"))
    val unit = if (isNull("unit")) "" else optString("unit")
    return if (unit.isBlank()) "$number (단위 미확인)" else "$number $unit"
}

private fun readableVehicleValue(value: Any?): String = when(value) {
    null, JSONObject.NULL -> "관측값 없음"
    is Boolean -> if(value) "예" else "아니요"
    is Number -> "$value (SDK 원본 값 · 단위 미확인)"
    is JSONArray -> if(value.length() == 0) "보고된 항목 없음" else (0 until value.length()).joinToString("\n") { readableVehicleValue(value.get(it)) }
    is JSONObject -> value.keys().asSequence().joinToString(" · ") { "$it: ${readableVehicleValue(value.get(it))}" }
    else -> when(value.toString()) { "ON" -> "켜짐"; "OFF" -> "꺼짐"; "UNKNOWN" -> "알 수 없음"; else -> value.toString().replace('_', ' ') }
}
