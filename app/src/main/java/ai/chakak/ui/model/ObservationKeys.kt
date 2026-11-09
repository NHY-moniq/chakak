package ai.chakak.ui.model

import ai.chakak.core.Observation
import ai.chakak.core.Record

/** 관측 항목 키(Platform providers와 같은 값). [ADDRESS]는 아직 제공자가 없는 선택 항목이다. */
object ObservationKeys {
    const val LOCATION = "location"
    const val ADDRESS = "address"
    const val ODOMETER = "odometer"
    const val TELLTALES = "telltales"
    const val TIRES = "tires"

    /** 위치 패널에서 보여주는 키 */
    val locationKeys: Set<String> = setOf(LOCATION, ADDRESS)
    /** 차량 상태 상세가 전용 영역으로 보여주는 키 */
    val vehicleKeys: Set<String> = setOf(ODOMETER, TELLTALES, TIRES)
}

fun Record.observation(key: String): Observation? = observations.firstOrNull { it.key == key }

/** 정해진 영역이 없는 항목. 숨기지 않고 일반 목록으로 보여준다 */
fun Record.otherObservations(excluded: Set<String>): List<Observation> = observations.filter { it.key !in excluded }
