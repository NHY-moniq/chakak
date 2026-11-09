package ai.chakak.ui.model

import ai.chakak.core.Category
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver

/**
 * 보관함 화면 이동 상태(spec 8 경로: /records → /records/{id} → /records/{id}/edit).
 * 회전·창 크기 변경 후에도 선택·필터·편집·삭제 확인을 복원한다. 갱신은 모두 copy로 한다.
 */
@Immutable
data class LibraryNavigation(
    val selectedId: String? = null,
    val editing: Boolean = false,
    val confirmingDelete: Boolean = false,
    val filter: Category? = null,
) {
    /** 목록 최상위가 아니면 뒤로 동작을 앱이 처리한다 */
    val canGoBack: Boolean get() = selectedId != null

    fun select(id: String): LibraryNavigation = copy(selectedId = id, editing = false, confirmingDelete = false)
    /**
     * 필터를 바꾼다. 선택한 기록([selectedCategory])이 새 필터 목록에 없으면 선택·편집·삭제 확인을 닫아
     * 두 열에서 목록에 없는 기록의 상세가 남지 않게 한다. 편집 초안은 상위 저장 영역에 남는다.
     */
    fun withFilter(value: Category?, selectedCategory: Category?): LibraryNavigation {
        val hidden = selectedId != null && value != null && selectedCategory != value
        return if (hidden) LibraryNavigation(filter = value) else copy(filter = value)
    }
    fun startEdit(): LibraryNavigation = if (selectedId == null) this else copy(editing = true, confirmingDelete = false)
    fun stopEdit(): LibraryNavigation = copy(editing = false)
    fun openDelete(): LibraryNavigation = if (selectedId == null) this else copy(confirmingDelete = true)
    fun closeDelete(): LibraryNavigation = copy(confirmingDelete = false)

    /** 편집 → 상세 → 목록 순서로 한 단계 돌아간다 */
    fun back(): LibraryNavigation = when {
        confirmingDelete -> closeDelete()
        editing -> stopEdit()
        else -> copy(selectedId = null)
    }

    /** 선택한 기록이 사라졌으면(다른 경로로 삭제) 목록으로 돌아간다 */
    fun reconcile(existingIds: Set<String>): LibraryNavigation =
        if (selectedId != null && selectedId !in existingIds) LibraryNavigation(filter = filter) else this

    /** 저장·삭제가 끝난 뒤의 이동. 실패하면 화면을 유지해 원인을 보여준다. */
    fun after(outcome: ActionOutcome): LibraryNavigation = when {
        !outcome.succeeded || outcome.recordId == null || outcome.recordId != selectedId -> this
        outcome.kind == ActionKind.Edit -> stopEdit()
        outcome.kind == ActionKind.Delete -> LibraryNavigation(filter = filter)
        else -> this
    }

    companion object {
        val Saver: Saver<LibraryNavigation, Any> = listSaver<LibraryNavigation, Any?>(
            save = { listOf(it.selectedId, it.editing, it.confirmingDelete, it.filter?.name) },
            restore = {
                LibraryNavigation(
                    selectedId = it[0] as String?,
                    editing = it[1] as Boolean,
                    confirmingDelete = it[2] as Boolean,
                    filter = (it[3] as String?)?.let(Category::valueOf),
                )
            },
        )
    }
}

/** 화면이 처리한 뒤 비워도 되는 결과인지. 편집·삭제 실패는 해당 화면에 남겨 둔다. */
fun ActionOutcome.consumedImmediately(): Boolean = succeeded || kind == ActionKind.Capture || result == ActionResult.Restricted
