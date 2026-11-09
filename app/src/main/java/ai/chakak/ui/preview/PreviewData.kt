package ai.chakak.ui.preview

import ai.chakak.core.Category
import ai.chakak.core.Observation
import ai.chakak.core.Record
import ai.chakak.core.Source
import ai.chakak.core.Status
import ai.chakak.ui.model.LibraryUiState

/**
 * Preview 전용 표본. 모두 Mock 출처이며 값에 "TEST" 접두어를 붙여 실제 관측값으로 오인하지 않게 한다.
 * 샘플 사진은 쓰지 않는다.
 */
object PreviewData {
    /** 2025-11-09 14:00 KST 무렵. 표시 확인용 고정 시각 */
    private const val BASE_TIME = 1_762_664_400_000L
    private const val MINUTE = 60_000L

    private fun at(minutesAgo: Long, seconds: Long = 0): Long = BASE_TIME - minutesAgo * MINUTE + seconds * 1_000L

    private fun obs(key: String, status: Status, value: String? = null, minutesAgo: Long) =
        Observation(key, status, value, if (status == Status.PENDING) null else at(minutesAgo, seconds = 2))

    val placeOk = Record(
        id = "preview-place", createdAt = at(10), category = Category.PLACE, source = Source.MOCK,
        title = "TEST 해안도로 전망 지점", memo = "TEST 노을이 잘 보이는 곳. 다음에 다시 오기", collecting = false,
        observations = listOf(
            obs("location", Status.OK, "TEST: 37.5665, 126.9780 (오차 ±12m)", 10),
            obs("odometer", Status.OK, "TEST: 12,345 km", 10),
            obs("telltales", Status.OK, "TEST: 켜진 경고등 없음", 10),
            obs("tires", Status.OK, "TEST: 앞 33 · 33 / 뒤 32 · 32 psi", 10),
        ),
    )

    val vehiclePartial = Record(
        id = "preview-vehicle", createdAt = at(45), category = Category.VEHICLE_STATUS, source = Source.MOCK,
        title = "TEST 방지턱 이후 소음", memo = "TEST 방지턱을 넘은 뒤부터 이상한 소리가 났어", collecting = false,
        observations = listOf(
            obs("location", Status.DENIED, minutesAgo = 45),
            obs("odometer", Status.OK, "TEST: 12,301 km", 45),
            obs("telltales", Status.TIMEOUT, minutesAgo = 45),
            obs("tires", Status.OK, "TEST: 앞 33 · 31 / 뒤 32 · 32 psi", 45),
        ),
    )

    val interrupted = Record(
        id = "preview-interrupted", createdAt = at(120), category = Category.VEHICLE_STATUS, source = Source.MOCK,
        title = "TEST 주행 시작으로 중단", collecting = false,
        observations = listOf(
            obs("location", Status.OK, "TEST: 37.4979, 127.0276", 120),
            obs("odometer", Status.INTERRUPTED, minutesAgo = 120),
            obs("telltales", Status.INTERRUPTED, minutesAgo = 120),
            obs("tires", Status.OK, "TEST: 앞 33 · 33 / 뒤 32 · 32 psi", 120),
        ),
    )

    val collecting = Record(
        id = "preview-collecting", createdAt = at(0), category = Category.PLACE, source = Source.MOCK,
        observations = listOf("location", "odometer", "telltales", "tires").map { obs(it, Status.PENDING, minutesAgo = 0) },
    )

    val noneCollected = Record(
        id = "preview-none", createdAt = at(300), category = Category.UNCLASSIFIED, source = Source.MOCK, collecting = false,
        observations = listOf(
            obs("location", Status.UNSUPPORTED, minutesAgo = 300),
            obs("odometer", Status.ERROR, minutesAgo = 300),
            obs("telltales", Status.UNSUPPORTED, minutesAgo = 300),
            obs("tires", Status.UNAVAILABLE, minutesAgo = 300),
        ),
    )

    val unclassifiedMemo = Record(
        id = "preview-unclassified", createdAt = at(1_440), category = Category.UNCLASSIFIED, source = Source.MOCK,
        memo = "TEST 주차장 B2 기둥 C-14 근처", collecting = false,
        observations = listOf(
            obs("location", Status.OK, "TEST: 37.5512, 126.9882", 1_440),
            obs("odometer", Status.OK, "TEST: 12,100 km", 1_440),
        ),
    )

    val all: List<Record> = listOf(collecting, placeOk, vehiclePartial, interrupted, noneCollected, unclassifiedMemo)

    val content: LibraryUiState = LibraryUiState.Content(all)
    val empty: LibraryUiState = LibraryUiState.Content(emptyList())
    /** 장소 기록이 없는 목록(필터 결과 없음 확인용) */
    val withoutPlaces: LibraryUiState = LibraryUiState.Content(all.filter { it.category != Category.PLACE })
}
