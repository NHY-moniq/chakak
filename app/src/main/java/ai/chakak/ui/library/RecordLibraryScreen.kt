package ai.chakak.ui.library

import ai.chakak.core.Category
import ai.chakak.core.Record
import ai.chakak.ui.component.ButtonSize
import ai.chakak.ui.component.ButtonVariant
import ai.chakak.ui.component.ChaKakButton
import ai.chakak.ui.component.InfoNote
import ai.chakak.ui.component.MockBanner
import ai.chakak.ui.model.LibraryUiState
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import kotlinx.coroutines.launch

const val LIBRARY_TITLE = "Chakak · 기록 보관함"
/** 628dp 창에서 한 줄에 들어가도록 줄인 공유 차량 안내 */
const val SHARED_VEHICLE_NOTE = "공유 차량: 다른 탑승자도 기록을 볼 수 있음"

/**
 * 보관함 상단 입력. 캡처 버튼 상태는 상위에서 정한다.
 * [disabledReason]이 있으면 비활성 버튼 아래에 이유를 글로 보여준다.
 */
data class CaptureControl(
    val enabled: Boolean,
    val loading: Boolean,
    val onCapture: () -> Unit,
    val disabledReason: String? = null,
)

private const val KEY_TITLE = "header:title"
private const val KEY_NOTE = "header:shared-note"
private const val KEY_MOCK = "header:mock"
private const val KEY_CAPTURE = "header:capture"
private const val KEY_TABS = "header:tabs"
private const val KEY_STATE = "body:state"

/**
 * 기록 보관함(spec 9.2): 제목 · 공유 차량 안내 · 테스트 데이터 배너 · 현재 상태 기록 ·
 * 분류 탭 · 목록(로딩/빈 목록/조회 오류/정상). 상태는 모두 파라미터로 받는다.
 *
 * 머리 영역과 목록을 한 [LazyColumn]에 두어 낮은 창(628×855)에서도 목록 행이 화면 대부분을 쓴다.
 * 분류 탭은 sticky header로 고정해 어느 위치에서든 필터를 바꿀 수 있다.
 * "현재 상태 기록"은 탭 바로 위 항목이라 목록 맨 위에서는 항상 보이고, 스크롤한 뒤에는 한 번 올리면 닿는다.
 */
@Composable
fun RecordLibraryScreen(
    state: LibraryUiState,
    filter: Category?,
    onFilterChange: (Category?) -> Unit,
    selectedId: String?,
    onSelect: (String) -> Unit,
    mock: Boolean,
    capture: CaptureControl,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
) {
    val visible = remember(state, filter) { (state as? LibraryUiState.Content)?.records?.filterBy(filter).orEmpty() }
    val scope = rememberCoroutineScope()
    // 탭 위 머리 항목 수 = 탭의 목록 위치
    val tabsIndex = if (mock) 4 else 3
    val changeFilter: (Category?) -> Unit = { value ->
        onFilterChange(value)
        // 탭 아래까지 내려가 있었다면 새 목록의 처음부터 보여준다. 위쪽에 있으면 위치를 유지한다.
        if (listState.firstVisibleItemIndex > tabsIndex) scope.launch { listState.scrollToItem(tabsIndex) }
    }
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(bottom = ChaKakSpacing.screen),
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item),
    ) {
        libraryHeader(mock, capture)
        filterTabs(filter, changeFilter, enabled = state is LibraryUiState.Content)
        libraryBody(state, filter, visible, selectedId, onSelect, onRetry)
    }
}

private fun LazyListScope.libraryHeader(mock: Boolean, capture: CaptureControl) {
    item(key = KEY_TITLE) {
        Text(
            LIBRARY_TITLE,
            style = ChaKakTheme.typography.titleLarge,
            color = ChaKakTheme.colors.textPrimary,
            modifier = Modifier.semantics { heading() },
        )
    }
    item(key = KEY_NOTE) { InfoNote(SHARED_VEHICLE_NOTE, Icons.Filled.Info) }
    if (mock) item(key = KEY_MOCK) { MockBanner() }
    item(key = KEY_CAPTURE) { CaptureButton(capture) }
}

@Composable
private fun CaptureButton(capture: CaptureControl) {
    Column(verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight)) {
        ChaKakButton(
            text = "현재 상태 기록",
            onClick = capture.onCapture,
            modifier = Modifier.fillMaxWidth(),
            variant = ButtonVariant.Filled,
            size = ButtonSize.Large,
            icon = Icons.Filled.AddCircle,
            enabled = capture.enabled,
            loading = capture.loading,
            loadingText = "기록 중…",
        )
        val reason = capture.disabledReason
        if (!capture.enabled && !capture.loading && reason != null) InfoNote(reason, Icons.Filled.Info)
    }
}

/** 분류 탭. 목록이 스크롤돼도 위에 남는다. 뒤 행이 비치지 않게 화면 배경을 깐다. */
@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.filterTabs(filter: Category?, onFilterChange: (Category?) -> Unit, enabled: Boolean) {
    stickyHeader(key = KEY_TABS) {
        RecordCategoryFilterTabs(
            selected = filter,
            onSelect = onFilterChange,
            enabled = enabled,
            modifier = Modifier
                .background(ChaKakTheme.colors.backgroundPrimary)
                .padding(vertical = ChaKakSpacing.tight),
        )
    }
}

private fun LazyListScope.libraryBody(
    state: LibraryUiState,
    filter: Category?,
    visible: List<Record>,
    selectedId: String?,
    onSelect: (String) -> Unit,
    onRetry: () -> Unit,
) {
    when (state) {
        LibraryUiState.Loading -> item(key = KEY_STATE) { RecordListLoading() }
        is LibraryUiState.Error -> item(key = KEY_STATE) { RecordListError(state.message, onRetry) }
        is LibraryUiState.Content -> when {
            state.records.isEmpty() -> item(key = KEY_STATE) { RecordListEmpty() }
            visible.isEmpty() && filter != null -> item(key = KEY_STATE) { RecordListFilteredEmpty(filter) }
            else -> recordItems(visible, selectedId, onSelect)
        }
    }
}

private fun List<Record>.filterBy(filter: Category?): List<Record> =
    if (filter == null) this else filter { it.category == filter }
