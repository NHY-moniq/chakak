package ai.chakak.ui.model

import ai.chakak.core.Category
import ai.chakak.core.Record
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver

const val MAX_TITLE_LENGTH = 120
const val MAX_MEMO_LENGTH = 2000

/** 편집 화면 초안. 길이 제한은 입력 시점에 적용한다. */
@Immutable
data class EditDraft(val title: String, val category: Category, val memo: String) {
    fun withTitle(value: String): EditDraft = copy(title = value.take(MAX_TITLE_LENGTH))
    fun withMemo(value: String): EditDraft = copy(memo = value.take(MAX_MEMO_LENGTH))
    fun withCategory(value: Category): EditDraft = copy(category = value)
    fun differsFrom(record: Record): Boolean =
        title != record.title || category != record.category || memo != record.memo

    companion object {
        fun from(record: Record): EditDraft = EditDraft(record.title, record.category, record.memo)

        /** 회전·프로세스 재생성 후 복원용 */
        val Saver: Saver<EditDraft, Any> = listSaver<EditDraft, String>(
            save = { listOf(it.title, it.category.name, it.memo) },
            restore = { EditDraft(it[0], Category.valueOf(it[1]), it[2]) },
        )
    }
}
