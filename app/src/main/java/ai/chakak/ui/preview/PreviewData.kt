package ai.chakak.ui.preview

import ai.chakak.core.Category
import ai.chakak.core.Observation
import ai.chakak.core.Record
import ai.chakak.core.Source
import ai.chakak.core.Status
import ai.chakak.ui.model.LibraryUiState
import ai.chakak.ui.model.TimelineClock
import java.time.Instant
import java.time.ZoneId
import java.util.Locale

/**
 * Preview 전용 표본. 모두 Mock 출처이고 이름·메모·주소에 "TEST"를 붙여 실제 기록으로 오인하지 않게 한다.
 * 관측값은 real `PlatformFactory`가 저장하는 JSON 형식을 따라 구조화 표시(좌표·큰 숫자·타이어)를 확인한다.
 * 숫자는 화면 확인용 임의 값이며 앱 코드에는 들어가지 않는다. 샘플 사진은 쓰지 않는다.
 */
object PreviewData {
    /** 2025-11-09(일) 14:00 KST. 표시 확인용 고정 시각 */
    private const val BASE_TIME = 1_762_664_400_000L
    private const val MINUTE = 60_000L
    private const val DAY_MINUTES = 1_440L

    /** 오늘/어제 판정 기준. 서울 시간대·한국어로 고정한다 */
    private val zone: ZoneId = ZoneId.of("Asia/Seoul")
    val clock = TimelineClock(Instant.ofEpochMilli(BASE_TIME).atZone(zone).toLocalDate(), zone, Locale.KOREA)

    private fun at(minutesAgo: Long, seconds: Long = 0): Long = BASE_TIME - minutesAgo * MINUTE + seconds * 1_000L

    private fun obs(key: String, status: Status, value: String? = null, minutesAgo: Long) =
        Observation(key, status, value, if (status == Status.PENDING) null else at(minutesAgo, seconds = 2))

    private fun locationJson(lat: Double, lng: Double) = """{"latitude":$lat,"longitude":$lng,"confidence":0.82}"""
    private fun odometerJson(value: Double, unit: String? = null) =
        """{"rawValue":$value,"unit":${unit?.let { "\"$it\"" } ?: "null"}}"""
    private fun tiresJson(fl: Double?, fr: Double?, rl: Double?, rr: Double?) = listOf(
        "LEFT_FRONT" to fl, "RIGHT_FRONT" to fr, "LEFT_REAR" to rl, "RIGHT_REAR" to rr,
    ).joinToString(",", "[", "]") { (area, v) -> """{"area":"$area","rawValue":${v ?: "null"},"unit":null}""" }
    private const val NO_TELLTALES = """[{"type":"TPMS","state":"OFF","warningCount":0}]"""
    private const val TPMS_ON = """[{"type":"TPMS","state":"ON","warningCount":1},{"type":"HEADLAMP_LOW","state":"OFF","warningCount":0}]"""

    val placeOk = Record(
        id = "preview-place", createdAt = at(10), category = Category.PLACE, source = Source.MOCK,
        title = "TEST 강변 전망 카페 근처", memo = "TEST 오른쪽에 공원 발견. 다음 주말에 다시 와보기.", collecting = false,
        observations = listOf(
            obs("location", Status.OK, locationJson(37.52191, 126.93994), 10),
            obs("address", Status.OK, "TEST 서울 영등포구 강변로 1 부근", 10),
            obs("odometer", Status.OK, odometerJson(12_480.0), 10),
            obs("telltales", Status.OK, NO_TELLTALES, 10),
            obs("tires", Status.OK, tiresJson(33.0, 33.0, 32.0, 34.0), 10),
        ),
    )

    /** 부분 저장: 위치 권한 없음, 뒤 오른쪽 타이어 값 없음, 경고등 하나 켜짐 */
    val vehiclePartial = Record(
        id = "preview-vehicle", createdAt = at(45), category = Category.VEHICLE_STATUS, source = Source.MOCK,
        title = "TEST 방지턱 이후 소음", memo = "TEST 방지턱을 넘은 뒤부터 오른쪽 뒤에서 덜컹거리는 소리가 났어요.", collecting = false,
        observations = listOf(
            obs("location", Status.DENIED, minutesAgo = 45),
            obs("odometer", Status.OK, odometerJson(12_301.0), 45),
            obs("telltales", Status.OK, TPMS_ON, 45),
            obs("tires", Status.UNAVAILABLE, tiresJson(33.0, 33.0, 32.0, null), 45),
        ),
    )

    val interrupted = Record(
        id = "preview-interrupted", createdAt = at(120), category = Category.VEHICLE_STATUS, source = Source.MOCK,
        title = "TEST 주행 시작으로 중단", collecting = false,
        observations = listOf(
            obs("location", Status.OK, locationJson(37.4979, 127.0276), 120),
            obs("odometer", Status.INTERRUPTED, minutesAgo = 120),
            obs("telltales", Status.INTERRUPTED, minutesAgo = 120),
            obs("tires", Status.OK, tiresJson(33.0, 33.0, 32.0, 32.0), 120),
        ),
    )

    val collecting = Record(
        id = "preview-collecting", createdAt = at(0), category = Category.PLACE, source = Source.MOCK,
        observations = listOf("location", "odometer", "telltales", "tires").map { obs(it, Status.PENDING, minutesAgo = 0) },
    )

    /** 어제: 타이어 항목 전체 시간 초과 */
    val vehicleTimeout = Record(
        id = "preview-vehicle-timeout", createdAt = at(DAY_MINUTES + 60), category = Category.VEHICLE_STATUS, source = Source.MOCK,
        title = "TEST 경고등 깜빡임", collecting = false,
        observations = listOf(
            obs("location", Status.OK, locationJson(37.4980, 127.0276), DAY_MINUTES + 60),
            obs("odometer", Status.OK, odometerJson(12_250.0), DAY_MINUTES + 60),
            obs("telltales", Status.OK, """[{"type":"MASTER_WARNING","state":"BLINK","warningCount":1}]""", DAY_MINUTES + 60),
            obs("tires", Status.TIMEOUT, minutesAgo = DAY_MINUTES + 60),
        ),
    )

    val noneCollected = Record(
        id = "preview-none", createdAt = at(DAY_MINUTES + 120), category = Category.UNCLASSIFIED, source = Source.MOCK, collecting = false,
        observations = listOf(
            obs("location", Status.UNSUPPORTED, minutesAgo = DAY_MINUTES + 120),
            obs("odometer", Status.ERROR, minutesAgo = DAY_MINUTES + 120),
            obs("telltales", Status.UNSUPPORTED, minutesAgo = DAY_MINUTES + 120),
            obs("tires", Status.UNAVAILABLE, minutesAgo = DAY_MINUTES + 120),
        ),
    )

    val unclassifiedMemo = Record(
        id = "preview-unclassified", createdAt = at(DAY_MINUTES + 300), category = Category.UNCLASSIFIED, source = Source.MOCK,
        memo = "TEST 주차장 B2 기둥 C-14 근처", collecting = false,
        observations = listOf(
            obs("location", Status.OK, locationJson(37.5512, 126.9882), DAY_MINUTES + 300),
            obs("odometer", Status.OK, "TEST: odometer sample", DAY_MINUTES + 300),
        ),
    )

    /** 3일 전: 주소 항목이 없는 장소(현재 플랫폼과 같음) */
    val placeNoAddress = Record(
        id = "preview-place-no-address", createdAt = at(3 * DAY_MINUTES + 200), category = Category.PLACE, source = Source.MOCK,
        title = "TEST 노을 보이던 해안도로", collecting = false,
        observations = listOf(
            obs("location", Status.OK, locationJson(37.7519, 128.8761), 3 * DAY_MINUTES + 200),
            obs("odometer", Status.OK, odometerJson(11_902.5, "km"), 3 * DAY_MINUTES + 200),
            obs("telltales", Status.TIMEOUT, minutesAgo = 3 * DAY_MINUTES + 200),
            obs("tires", Status.DENIED, minutesAgo = 3 * DAY_MINUTES + 200),
        ),
    )

    /** 3일 전: 주소 조회 실패 */
    val placeAddressFailed = Record(
        id = "preview-place-address-failed", createdAt = at(3 * DAY_MINUTES + 400), category = Category.PLACE, source = Source.MOCK,
        title = "TEST 시장 입구", collecting = false,
        observations = listOf(
            obs("location", Status.OK, locationJson(37.5700, 126.9996), 3 * DAY_MINUTES + 400),
            obs("address", Status.TIMEOUT, minutesAgo = 3 * DAY_MINUTES + 400),
        ),
    )

    val all: List<Record> = listOf(
        collecting, placeOk, vehiclePartial, interrupted,
        vehicleTimeout, noneCollected, unclassifiedMemo,
        placeNoAddress, placeAddressFailed,
    )

    val content: LibraryUiState = LibraryUiState.Content(all)
    val empty: LibraryUiState = LibraryUiState.Content(emptyList())
    /** 장소 기록이 없는 목록(필터 결과 없음 확인용) */
    val withoutPlaces: LibraryUiState = LibraryUiState.Content(all.filter { it.category != Category.PLACE })
}
