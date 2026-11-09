package ai.chakak.ui.app

import ai.chakak.core.Category
import ai.chakak.core.Record
import ai.chakak.ui.component.MessageBar
import ai.chakak.ui.model.ActionKind
import ai.chakak.ui.model.ActionState
import ai.chakak.ui.model.LibraryNavigation
import ai.chakak.ui.model.LibraryUiState
import ai.chakak.ui.model.consumedImmediately
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/** 화면에서 ViewModel로 가는 동작 묶음 */
@Stable
class LibraryCallbacks(
    val capture: () -> Unit = {},
    val edit: (id: String, title: String, category: Category, memo: String) -> Unit = { _, _, _, _ -> },
    val delete: (String) -> Unit = {},
    val retry: () -> Unit = {},
    val consumeOutcome: () -> Unit = {},
)

/**
 * 앱 윈도 골격(spec 9.7): 배경 · insets(시스템 바·IME) · 주행 제한 게이트 · 결과 안내 · 창 폭별 배치.
 * 폭 < [ChaKakDimens.twoPaneMinWidth]이면 목록과 상세·편집을 각각 전체 화면으로,
 * 이상이면 목록 + 상세를 나란히 보여준다. 이동 상태는 [LibraryNavigation]으로 저장·복원한다.
 */
@Composable
fun AppWindowScaffold(
    state: LibraryUiState,
    actions: ActionState,
    allowed: Boolean,
    mock: Boolean,
    callbacks: LibraryCallbacks,
    modifier: Modifier = Modifier,
    initialNavigation: LibraryNavigation = LibraryNavigation(),
) {
    var nav by rememberSaveable(stateSaver = LibraryNavigation.Saver) { mutableStateOf(initialNavigation) }
    val drafts = rememberSaveableStateHolder()

    LaunchedEffect(actions.outcome) {
        val outcome = actions.outcome ?: return@LaunchedEffect
        nav = nav.after(outcome)
        if (outcome.succeeded && outcome.kind != ActionKind.Capture) outcome.recordId?.let(drafts::removeState)
        if (outcome.consumedImmediately()) callbacks.consumeOutcome()
    }
    LaunchedEffect(state) {
        if (state is LibraryUiState.Content) nav = nav.reconcile(state.records.mapTo(HashSet()) { it.id })
    }
    // 편집을 어떤 경로로든 벗어나면(시스템 뒤로, 다른 기록 선택) 남은 저장 실패 결과를 비워
    // 다시 편집을 열 때 오래된 오류가 보이지 않게 한다.
    LaunchedEffect(nav.editing, nav.selectedId) {
        val outcome = actions.outcome
        if (!nav.editing && outcome != null && outcome.kind == ActionKind.Edit && !outcome.succeeded) {
            callbacks.consumeOutcome()
        }
    }
    // 저장·삭제 중에는 화면의 "취소"처럼 시스템 뒤로도 막는다.
    val writing = actions.isRunning(ActionKind.Edit) || actions.isRunning(ActionKind.Delete)
    BackHandler(enabled = allowed && nav.canGoBack && !writing) { nav = nav.back() }

    val host = LibraryHost(
        state = state, actions = actions, nav = nav, mock = mock, callbacks = callbacks,
        drafts = drafts, onNavigate = { nav = it },
    )
    BoxWithConstraints(modifier.fillMaxSize().background(ChaKakTheme.colors.backgroundPrimary)) {
        val twoPane = maxWidth >= ChaKakDimens.twoPaneMinWidth
        Column(
            modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(ChaKakSpacing.screen),
            verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item),
        ) {
            RestrictionGate(allowed, mock, Modifier.fillMaxSize()) {
                actions.message?.let { MessageBar(it) }
                val paneModifier = Modifier.weight(1f).fillMaxWidth()
                if (twoPane) TwoPaneLayout(host, paneModifier) else SinglePaneLayout(host, paneModifier)
                host.DeleteDialogIfOpen()
            }
        }
    }
}

/** 선택된 기록. 목록이 아직 없거나 사라졌으면 null */
internal fun LibraryUiState.find(id: String?): Record? =
    if (id == null || this !is LibraryUiState.Content) null else records.firstOrNull { it.id == id }
