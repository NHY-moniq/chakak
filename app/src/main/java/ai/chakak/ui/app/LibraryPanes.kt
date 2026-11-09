package ai.chakak.ui.app

import ai.chakak.core.Record
import ai.chakak.ui.detail.RecordDetailScreen
import ai.chakak.ui.edit.DeleteRecordDialog
import ai.chakak.ui.edit.RecordEditRoute
import ai.chakak.ui.library.CaptureControl
import ai.chakak.ui.library.NoSelectionPlaceholder
import ai.chakak.ui.library.RecordLibraryScreen
import ai.chakak.ui.model.ActionKind
import ai.chakak.ui.model.ActionState
import ai.chakak.ui.model.LibraryNavigation
import ai.chakak.ui.model.LibraryUiState
import ai.chakak.ui.theme.ChaKakSpacing
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.ui.Modifier

/** 목록 스크롤 상태의 저장 키. 기록 id(UUID)와 겹치지 않는 이름을 쓴다. */
private const val LIBRARY_LIST_STATE_KEY = "chakak:library-list"
const val CAPTURE_WAIT_ACTION = "다른 작업이 끝나면 기록할 수 있습니다."
const val CAPTURE_WAIT_LOADING = "기록 목록을 불러온 뒤 기록할 수 있습니다."

/**
 * 한 번의 구성에서 쓰는 화면 상태와 이동 동작. 상태는 읽기만 하고,
 * 이동은 [onNavigate]로 새 [LibraryNavigation]을 올려 보낸다.
 */
@Stable
internal class LibraryHost(
    val state: LibraryUiState,
    val actions: ActionState,
    val nav: LibraryNavigation,
    val mock: Boolean,
    val callbacks: LibraryCallbacks,
    val drafts: SaveableStateHolder,
    val onNavigate: (LibraryNavigation) -> Unit,
) {
    val selected: Record? get() = state.find(nav.selectedId)

    /**
     * 목록 화면. 한 열에서 상세로 갔다 돌아와도 스크롤 위치가 남도록 목록 상태를 저장 영역에 둔다.
     */
    @Composable
    fun LibraryPane(modifier: Modifier) {
        drafts.SaveableStateProvider(LIBRARY_LIST_STATE_KEY) {
            RecordLibraryScreen(
                state = state,
                filter = nav.filter,
                onFilterChange = { onNavigate(nav.withFilter(it, selected?.category)) },
                selectedId = nav.selectedId,
                onSelect = { onNavigate(nav.select(it)) },
                mock = mock,
                capture = captureControl(),
                onRetry = callbacks.retry,
                modifier = modifier,
            )
        }
    }

    /** 다른 동작이 실행 중이거나 목록을 읽는 중이면 이유를 함께 보여준다(색만으로 비활성 표시 금지). */
    private fun captureControl(): CaptureControl {
        val capturing = actions.isRunning(ActionKind.Capture)
        val reason = when {
            capturing -> null
            actions.busy -> CAPTURE_WAIT_ACTION
            state is LibraryUiState.Loading -> CAPTURE_WAIT_LOADING
            else -> null
        }
        return CaptureControl(
            enabled = state !is LibraryUiState.Loading && !actions.busy,
            loading = capturing,
            onCapture = callbacks.capture,
            disabledReason = reason,
        )
    }

    /** 상세 또는 편집. 편집 초안은 기록 id별 저장 영역에 둔다(기록을 바꿔도 초안 유지). */
    @Composable
    fun RecordPane(record: Record, showBack: Boolean, modifier: Modifier) {
        // 두 열(showBack=false)에서 앱이 Mock이면 목록 쪽에 이미 배너가 보이므로 반복하지 않는다.
        val showMockBanner = showBack || !mock
        if (nav.editing) {
            drafts.SaveableStateProvider(record.id) { EditPane(record, showMockBanner, modifier) }
        } else {
            RecordDetailScreen(
                record = record,
                onBack = if (showBack) ({ onNavigate(nav.back()) }) else null,
                onEdit = { onNavigate(nav.startEdit()) },
                onDelete = { onNavigate(nav.openDelete()) },
                actionsEnabled = !actions.busy,
                modifier = modifier,
                showMockBanner = showMockBanner,
            )
        }
    }

    @Composable
    private fun EditPane(record: Record, showMockBanner: Boolean, modifier: Modifier) {
        val saving = actions.isRunning(ActionKind.Edit, record.id)
        val failed = actions.failed(ActionKind.Edit, record.id)
        RecordEditRoute(
            record = record,
            saving = saving,
            saveFailed = failed,
            enabled = !actions.busy || saving,
            onSave = { draft -> callbacks.edit(record.id, draft.title, draft.category, draft.memo) },
            onCancel = {
                // 명시적 취소는 초안을 버린다. 시스템 뒤로는 초안을 남긴다.
                drafts.removeState(record.id)
                if (failed) callbacks.consumeOutcome()
                onNavigate(nav.stopEdit())
            },
            modifier = modifier,
            showMockBanner = showMockBanner,
        )
    }

    @Composable
    fun DeleteDialogIfOpen() {
        val record = selected
        if (record == null || !nav.confirmingDelete) return
        val failed = actions.failed(ActionKind.Delete, record.id)
        DeleteRecordDialog(
            record = record,
            deleting = actions.isRunning(ActionKind.Delete, record.id),
            failed = failed,
            onConfirm = { callbacks.delete(record.id) },
            onDismiss = {
                if (failed) callbacks.consumeOutcome()
                onNavigate(nav.closeDelete())
            },
        )
    }
}

/** 좁은 창: 목록 / 상세 / 편집 중 하나만 전체 화면으로 */
@Composable
internal fun SinglePaneLayout(host: LibraryHost, modifier: Modifier) {
    val record = host.selected
    if (record == null) host.LibraryPane(modifier) else host.RecordPane(record, showBack = true, modifier = modifier)
}

/** 넓은 창: 목록 + 선택한 기록 */
@Composable
internal fun TwoPaneLayout(host: LibraryHost, modifier: Modifier) {
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.pane)) {
        host.LibraryPane(Modifier.weight(1f).fillMaxHeight())
        val record = host.selected
        val detailModifier = Modifier.weight(1f).fillMaxHeight()
        if (record == null) NoSelectionPlaceholder(detailModifier) else host.RecordPane(record, showBack = false, modifier = detailModifier)
    }
}
