package ai.chakak.ui.model

import ai.chakak.core.Category
import ai.chakak.core.Record
import ai.chakak.core.Source
import androidx.compose.runtime.saveable.SaverScope
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EditDraftTest {
    private val record = Record("r", 0, Category.UNCLASSIFIED, Source.MOCK, title = "t", memo = "m")

    @Test fun limitsLengthOnInput() {
        val draft = EditDraft.from(record)
        assertEquals(MAX_TITLE_LENGTH, draft.withTitle("a".repeat(MAX_TITLE_LENGTH + 5)).title.length)
        assertEquals(MAX_MEMO_LENGTH, draft.withMemo("a".repeat(MAX_MEMO_LENGTH + 5)).memo.length)
    }

    @Test fun detectsChanges() {
        val draft = EditDraft.from(record)
        assertFalse(draft.differsFrom(record))
        assertTrue(draft.withCategory(Category.PLACE).differsFrom(record))
    }

    @Test fun saverRoundTrip() {
        val draft = EditDraft("제목", Category.VEHICLE_STATUS, "메모")
        val saved = with(EditDraft.Saver) { SaverScope { true }.save(draft) }!!
        assertEquals(draft, EditDraft.Saver.restore(saved))
    }

    @Test fun dictationAppendsToExistingMemoAndRespectsLimit() {
        val draft = EditDraft("", Category.PLACE, "")
        assertEquals("오른쪽에 카페", draft.withDictation("", " 오른쪽에 카페 ").memo)
        assertEquals("입구는 뒤쪽 오른쪽에 카페", draft.withDictation("입구는 뒤쪽 ", "오른쪽에 카페").memo)
        assertEquals("입구는 뒤쪽", draft.withDictation("입구는 뒤쪽", "  ").memo)
        assertEquals(MAX_MEMO_LENGTH, draft.withDictation("가".repeat(MAX_MEMO_LENGTH - 1), "나다라").memo.length)
    }
}
