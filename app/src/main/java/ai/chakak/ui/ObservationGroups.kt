package ai.chakak.ui

import ai.chakak.core.Observation

internal data class ObservationGroup(val title: String, val observations: List<Observation>)

private val groupOrder = listOf("위치", "주행·제동", "배터리", "연료", "공조", "외부 조명",
    "경고·오일", "문·창문", "타이어", "와이퍼", "이전 수집·기타 항목")
private val fieldGroups = mapOf(
    "location" to "위치",
    "vehicle.getCurrentBrakePedalStatus" to "주행·제동",
    "vehicle.getDoorLockState" to "문·창문",
    "vehicle.getCruiseControlState" to "주행·제동",
    "vehicle.getCurrentDrivingModeState" to "주행·제동",
    "vehicle.getCurrentEvEfficiency" to "배터리",
    "vehicle.getEvBatteryStateOfCharge" to "배터리",
    "vehicle.getFuelPercent" to "연료",
    "vehicle.getHvacAcState" to "공조",
    "vehicle.getHvacRecirculationState" to "공조",
    "vehicle.getHvacTemperature" to "공조",
    "vehicle.getHvacTemperatureCurrent" to "공조",
    "vehicle.getCurrentFrontFogLightState" to "외부 조명",
    "vehicle.getCurrentHighBeamLightState" to "외부 조명",
    "vehicle.getCurrentLowBeamLightState" to "외부 조명",
    "vehicle.getCurrentRearFogLightState" to "외부 조명",
    "vehicle.getCurrentTailLampLightState" to "외부 조명",
    "odometer" to "주행·제동",
    "vehicle.getBrakeFluidLevelState" to "경고·오일",
    "vehicle.getEngineOilLevelState" to "경고·오일",
    "telltales" to "경고·오일",
    "vehicle.getTransmissionOilLevelState" to "경고·오일",
    "tires" to "타이어",
    "vehicle.getCurrentWindowLockState" to "문·창문",
    "vehicle.getCurrentWindowPosition" to "문·창문",
    "vehicle.getCurrentWiperState" to "와이퍼",
    "vehicle.getCurrentWiperSwitch" to "와이퍼",
)

internal fun groupObservations(observations: List<Observation>): List<ObservationGroup> {
    val grouped = observations.groupBy { fieldGroups[it.key] ?: "이전 수집·기타 항목" }.toMutableMap()
    // Display the same saved EV efficiency in both sections, without a second SDK read.
    val efficiency = observations.find { it.key == "vehicle.getCurrentEvEfficiency" }
    if(efficiency != null && grouped.containsKey("연료")) {
        grouped["연료"] = grouped.getValue("연료") + efficiency
    }
    val fieldOrder = (listOf("location") + vehicleFieldLabels.keys).withIndex().associate { it.value to it.index }
    return groupOrder.mapNotNull { title -> grouped[title]?.let { rows ->
        val ordered = when(title) {
            "배터리", "연료" -> rows.sortedBy { if(it.key == "vehicle.getCurrentEvEfficiency") 1 else 0 }
            else -> rows.sortedWith(compareBy<Observation> { fieldOrder[it.key] ?: Int.MAX_VALUE }.thenBy { it.key })
        }
        ObservationGroup(title, ordered)
    } }
}
