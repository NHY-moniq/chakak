package ai.chakak.ui.model

import ai.chakak.core.Observation
import ai.chakak.core.Status
import androidx.compose.runtime.Immutable
import java.util.Locale

const val TIRES_TITLE = "타이어 공기압"

/** 바퀴 한 개 표시: 값([value]) 또는 값이 없는 이유([status]) 중 하나 */
@Immutable
data class WheelDisplay(val wheel: Wheel, val value: String?, val status: Status?) {
    val missing: Boolean get() = value == null
}

/**
 * 타이어 항목 → 네 바퀴(앞 왼쪽, 앞 오른쪽, 뒤 왼쪽, 뒤 오른쪽 순).
 * - 값을 읽을 수 있으면 바퀴별 값. 값이 null인 바퀴는 UNAVAILABLE, 목록에 없던 바퀴는 UNSUPPORTED.
 * - 값이 없고 항목이 실패면 네 바퀴 모두 항목 상태.
 * - 항목은 OK인데 형식을 모르면 null(화면이 원문을 보여준다).
 */
fun tireWheels(observation: Observation, locale: Locale): List<WheelDisplay>? {
    val parsed = parseTires(observation.value)
    if (parsed != null) return Wheel.entries.map { wheel -> wheelOf(wheel, parsed, locale) }
    if (observation.status == Status.OK) return null
    return Wheel.entries.map { WheelDisplay(it, null, observation.status) }
}

private fun wheelOf(wheel: Wheel, tires: TireReadings, locale: Locale): WheelDisplay = when {
    !tires.has(wheel) -> WheelDisplay(wheel, null, Status.UNSUPPORTED)
    else -> tires.valueOf(wheel)?.let { WheelDisplay(wheel, it.text(locale), null) }
        ?: WheelDisplay(wheel, null, Status.UNAVAILABLE)
}

/** 그림의 접근성 설명. "타이어 공기압: 앞 왼쪽 33, …, 뒤 오른쪽 응답 시간 초과" */
fun tiresContentDescription(wheels: List<WheelDisplay>): String =
    "$TIRES_TITLE: " + wheels.joinToString(", ") { "${it.wheel.label()} ${it.value ?: it.status?.label().orEmpty()}" }
