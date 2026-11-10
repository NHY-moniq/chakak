package ai.chakak.ui.model

import androidx.compose.runtime.Immutable
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale

/*
 * 저장된 관측값 문자열 → 구조화 값. 형식은 real `PlatformFactory`가 만드는 JSON이다.
 * - location: {"latitude":37.52,"longitude":126.93,"confidence":0.8}
 * - odometer: {"rawValue":12480.5,"unit":null}
 * - telltales: [{"type":"TPMS","state":"ON","warningCount":1}, ...]
 * - tires: [{"area":"LEFT_FRONT","rawValue":33.0,"unit":null}, ...] (값 없는 바퀴는 rawValue null)
 * 형식이 다르면(Mock "TEST: … sample" 등) null을 돌려주고, 화면은 원문을 그대로 보여준다. 숫자를 지어내지 않는다.
 */

/** 위치. [confidence]는 SDK 신뢰도이며 정확도(m)와 같지 않다(spec 14). */
@Immutable
data class LocationReading(val latitude: Double, val longitude: Double, val confidence: Double?)

/** 측정값과 단위. 단위는 SDK가 줄 때만 있다(현재 real은 null). */
@Immutable
data class Measurement(val value: Double, val unit: String?)

/** 경고등 한 개 */
@Immutable
data class TelltaleReading(val type: String, val state: String, val warningCount: Int?) {
    /** 켜짐 또는 깜빡임 */
    val lit: Boolean get() = state == STATE_ON || state == STATE_BLINK
    val blinking: Boolean get() = state == STATE_BLINK

    companion object {
        const val STATE_ON = "ON"
        const val STATE_BLINK = "BLINK"
    }
}

/** 바퀴 위치. SDK WheelValue 이름과 같다 */
enum class Wheel(val sdkName: String) {
    FrontLeft("LEFT_FRONT"), FrontRight("RIGHT_FRONT"), RearLeft("LEFT_REAR"), RearRight("RIGHT_REAR"),
}

/** 바퀴별 값. 목록에 없던 바퀴는 [readings]에 키가 없고, 값 없는 바퀴는 null이다. */
@Immutable
data class TireReadings(val readings: Map<Wheel, Measurement?>) {
    fun has(wheel: Wheel): Boolean = wheel in readings
    fun valueOf(wheel: Wheel): Measurement? = readings[wheel]
}

fun parseLocation(raw: String?): LocationReading? = parseObject(raw) { json ->
    val lat = json.optFiniteDouble("latitude") ?: return@parseObject null
    val lng = json.optFiniteDouble("longitude") ?: return@parseObject null
    if (lat !in -90.0..90.0 || lng !in -180.0..180.0) return@parseObject null
    LocationReading(lat, lng, json.optFiniteDouble("confidence"))
}

fun parseOdometer(raw: String?): Measurement? = parseObject(raw) { json ->
    json.optFiniteDouble("rawValue")?.let { Measurement(it, json.optUnit()) }
}

fun parseTelltales(raw: String?): List<TelltaleReading>? = parseArray(raw) { array ->
    (0 until array.length()).map { index ->
        val item = array.optJSONObject(index) ?: return@parseArray null
        val type = item.optString("type").takeIf { it.isNotBlank() } ?: return@parseArray null
        val state = item.optString("state").takeIf { it.isNotBlank() } ?: return@parseArray null
        TelltaleReading(type, state, if (item.has("warningCount") && !item.isNull("warningCount")) item.optInt("warningCount") else null)
    }
}

/**
 * "LEFT_FRONT, RIGHT_FRONT"처럼 한 영역이 여러 바퀴를 덮으면 각 바퀴에 같은 값을 넣는다. 모르는 영역은 무시한다.
 * 같은 바퀴가 여러 영역에 나오면 null이 아닌 첫 값을 쓴다.
 */
fun parseTires(raw: String?): TireReadings? = parseArray(raw) { array ->
    val entries = (0 until array.length()).flatMap { index ->
        val item = array.optJSONObject(index) ?: return@parseArray null
        val wheels = item.optString("area").split(',').mapNotNull { name -> Wheel.entries.firstOrNull { it.sdkName == name.trim() } }
        val value = item.optFiniteDouble("rawValue")?.let { Measurement(it, item.optUnit()) }
        wheels.map { it to value }
    }
    // 같은 바퀴가 여러 번 나오면 값이 있는 쪽을 쓴다(뒤의 null이 앞의 값을 덮지 않게)
    val merged = entries.groupBy({ it.first }, { it.second }).mapValues { (_, values) -> values.firstOrNull { it != null } }
    if (merged.isEmpty()) null else TireReadings(merged)
}

/** 숫자 표시. 소수는 한 자리까지(반올림), 천 단위 구분은 로캘을 따른다. */
fun formatNumber(value: Double, locale: Locale): String =
    NumberFormat.getNumberInstance(locale).apply { maximumFractionDigits = 1 }.format(value)

/** "37.5219, 126.9399" — 소수 넷째 자리(약 10m). 로캘과 관계없이 점을 쓴다 */
fun LocationReading.coordinatesText(): String = "%.4f, %.4f".format(Locale.ROOT, latitude, longitude)

/** 값 + 단위(있을 때만) */
fun Measurement.text(locale: Locale): String = unit?.let { "${formatNumber(value, locale)} $it" } ?: formatNumber(value, locale)

private inline fun <T> parseObject(raw: String?, read: (JSONObject) -> T?): T? {
    val text = raw?.trim()?.takeIf { it.startsWith("{") } ?: return null
    return try { read(JSONObject(text)) } catch (_: JSONException) { null }
}

private inline fun <T> parseArray(raw: String?, read: (JSONArray) -> T?): T? {
    val text = raw?.trim()?.takeIf { it.startsWith("[") } ?: return null
    return try { read(JSONArray(text)) } catch (_: JSONException) { null }
}

private fun JSONObject.optFiniteDouble(name: String): Double? =
    if (!has(name) || isNull(name)) null else optDouble(name).takeIf { it.isFinite() }

private fun JSONObject.optUnit(): String? = if (!has("unit") || isNull("unit")) null else optString("unit").takeIf { it.isNotBlank() }
