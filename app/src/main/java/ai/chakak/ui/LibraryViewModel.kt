package ai.chakak.ui

import ai.chakak.ChakakApp
import ai.chakak.core.Category
import ai.chakak.ui.model.ActionKind
import ai.chakak.ui.model.ActionOutcome
import ai.chakak.ui.model.ActionResult
import ai.chakak.ui.model.ActionState
import ai.chakak.ui.model.LibraryUiState
import ai.chakak.ui.model.MESSAGE_VISIBLE_MILLIS
import ai.chakak.ui.model.message
import ai.chakak.ui.model.toLibraryUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * 보관함 화면 상태. 저장·수집 동작은 기존 엔진·저장소 호출을 그대로 쓰고,
 * 여기서는 조회 상태와 결과 안내만 만든다.
 */
class LibraryViewModel(private val app: ChakakApp) : ViewModel() {
    private val reloads = MutableStateFlow(0)

    /**
     * Lazily: 화면이 백그라운드에서 돌아올 때 다시 구독하며 Loading으로 깜빡이지 않도록
     * ViewModel 수명 동안 구독을 유지한다. 다시 시도하면 새로 구독하며 Loading부터 시작한다.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<LibraryUiState> = reloads
        .flatMapLatest { app.store.records.toLibraryUiState() }
        .stateIn(viewModelScope, SharingStarted.Lazily, LibraryUiState.Loading)

    private val actionState = MutableStateFlow(ActionState())
    val actions: StateFlow<ActionState> = actionState.asStateFlow()
    private var clearMessageJob: Job? = null

    fun retry() = reloads.update { it + 1 }

    fun capture() = launchAction(ActionKind.Capture, null) {
        val started = app.engine.capture(UUID.randomUUID().toString(), Category.UNCLASSIFIED)
        if (started) ActionResult.Done else ActionResult.Rejected
    }

    fun edit(id: String, title: String, category: Category, memo: String) = launchAction(ActionKind.Edit, id) {
        app.store.edit(id, title, category, memo)
        ActionResult.Done
    }

    fun delete(id: String) = launchAction(ActionKind.Delete, id) {
        app.store.delete(id)
        ActionResult.Done
    }

    fun deleteMany(ids: List<String>) = launchAction(ActionKind.Delete, null) {
        app.store.deleteMany(ids)
        ActionResult.Done
    }

    /** 화면이 결과(편집 종료·팝업 닫기)를 반영한 뒤 호출한다. */
    fun consumeOutcome() = actionState.update { it.copy(outcome = null) }

    private fun launchAction(kind: ActionKind, recordId: String?, block: suspend () -> ActionResult) {
        if (actionState.value.busy) return
        actionState.update { ActionState(running = kind, runningRecordId = recordId) }
        viewModelScope.launch {
            val result = execute(block)
            val outcome = ActionOutcome(kind, recordId, result)
            actionState.update { it.copy(running = null, runningRecordId = null, outcome = outcome, message = outcome.message()) }
            scheduleMessageClear()
        }
    }

    private suspend fun execute(block: suspend () -> ActionResult): ActionResult = try {
        app.ready.await()
        if (!app.platform.touchAllowed.value) ActionResult.Restricted else block()
    } catch (e: CancellationException) {
        throw e
    } catch (_: Exception) {
        ActionResult.Failed
    }

    private fun scheduleMessageClear() {
        clearMessageJob?.cancel()
        clearMessageJob = viewModelScope.launch {
            delay(MESSAGE_VISIBLE_MILLIS)
            actionState.update { it.copy(message = null) }
        }
    }

    class Factory(private val app: ChakakApp) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = LibraryViewModel(app) as T
    }
}
