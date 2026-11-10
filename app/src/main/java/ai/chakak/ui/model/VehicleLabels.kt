package ai.chakak.ui.model

/** 바퀴 이름 */
fun Wheel.label(): String = when (this) {
    Wheel.FrontLeft -> "앞 왼쪽"
    Wheel.FrontRight -> "앞 오른쪽"
    Wheel.RearLeft -> "뒤 왼쪽"
    Wheel.RearRight -> "뒤 오른쪽"
}

const val BLINKING_SUFFIX = " (깜빡임)"

/** 경고등 이름. 번역이 없는 종류는 SDK 이름을 그대로 보여준다(숨기지 않음) */
fun telltaleLabel(reading: TelltaleReading): String =
    (telltaleNames[reading.type] ?: reading.type) + if (reading.blinking) BLINKING_SUFFIX else ""

/** SDK TelltaleType 중 자주 보이는 항목. 출처: Vehicle 2.3.2 `TelltaleType` 상수 이름 */
private val telltaleNames: Map<String, String> = mapOf(
    "PARKING_BRAKE" to "주차 브레이크",
    "ELECTRONIC_PARKING_BRAKE" to "전자식 주차 브레이크",
    "SEAT_BELT" to "안전벨트",
    "DOOR_OPEN" to "문 열림",
    "TRUNK_OPEN" to "트렁크 열림",
    "ABS" to "ABS",
    "MDPS" to "전동식 조향",
    "TPMS" to "타이어 공기압",
    "LOW_VOLT_BATTERY" to "저전압 배터리",
    "ESC" to "차체 자세 제어",
    "ESC_OFF" to "차체 자세 제어 꺼짐",
    "AIRBAG" to "에어백",
    "MASTER_WARNING" to "종합 경고",
    "ENGINE_OIL_PRESSURE" to "엔진 오일 압력",
    "ENGINE_TEMPERATURE" to "엔진 온도",
    "ENGINE" to "엔진",
    "EV_SYSTEM" to "EV 시스템",
    "LOW_EV_BATTERY" to "EV 배터리 부족",
    "LOW_FUEL" to "연료 부족",
    "AUTO_HOLD" to "오토 홀드",
    "HEADLAMP_LOW" to "전조등(하향)",
    "HEADLAMP_HIGH" to "전조등(상향)",
    "AUTO_HIGH_BEAM" to "자동 상향등",
    "TURN_SIGNAL_LEFT" to "왼쪽 방향지시등",
    "TURN_SIGNAL_RIGHT" to "오른쪽 방향지시등",
    "ICY_ROAD" to "노면 결빙 주의",
)
