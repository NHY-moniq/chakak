package ai.chakak.ui.model

import ai.chakak.core.Record
import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

/** 보관함 조회 상태(spec 9.2). 빈 목록은 [Content]의 빈 리스트이며 오류와 구분한다. */
sealed interface LibraryUiState {
    data object Loading : LibraryUiState
    @Immutable data class Error(val message: String) : LibraryUiState
    @Immutable data class Content(val records: List<Record>) : LibraryUiState
}

/** 첫 목록이 이 시간 안에 오지 않으면 오류로 바꾼다. 무한 로딩 금지(spec 9.7). */
const val LOAD_TIMEOUT_MILLIS = 10_000L
const val READ_ERROR_MESSAGE = "기록을 읽지 못했습니다. 다시 시도해 주세요."
const val LOAD_TIMEOUT_MESSAGE = "기록을 불러오는 데 시간이 오래 걸립니다. 다시 시도해 주세요."

/**
 * 기록 흐름을 화면 상태로 바꾼다. [LibraryUiState.Loading]으로 시작하고,
 * 첫 목록 전에 [timeoutMillis]가 지나면 [LibraryUiState.Error]를 낸다(늦게 온 목록은 그대로 반영).
 * 읽기 예외는 빈 목록이 아니라 오류로 표시한다.
 */
fun Flow<List<Record>>.toLibraryUiState(timeoutMillis: Long = LOAD_TIMEOUT_MILLIS): Flow<LibraryUiState> = channelFlow {
    send(LibraryUiState.Loading)
    val watchdog = launch {
        delay(timeoutMillis)
        send(LibraryUiState.Error(LOAD_TIMEOUT_MESSAGE))
    }
    try {
        collect { records ->
            watchdog.cancel()
            send(LibraryUiState.Content(records))
        }
    } catch (e: CancellationException) {
        throw e
    } catch (_: Exception) {
        watchdog.cancel()
        send(LibraryUiState.Error(READ_ERROR_MESSAGE))
    }
}
