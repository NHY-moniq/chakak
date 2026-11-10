package ai.chakak.ui.model

import ai.chakak.core.Category
import ai.chakak.core.Record
import ai.chakak.core.Source
import org.junit.Assert.assertEquals
import org.junit.Test

class DisplayTitleTest {
    private fun record(title: String, category: Category = Category.UNCLASSIFIED) =
        Record(id = "r", createdAt = 0, category = category, source = Source.MOCK, title = title)

    @Test fun blankTitleUsesUntitledLabelNotCategory() {
        Category.entries.forEach { category ->
            assertEquals(UNTITLED_RECORD, record("", category).displayTitle())
        }
    }

    @Test fun whitespaceOnlyTitleIsTreatedAsBlank() {
        assertEquals(UNTITLED_RECORD, record("   ").displayTitle())
    }

    @Test fun userTitleIsShownAsIs() {
        assertEquals("한강 전망 카페", record("한강 전망 카페", Category.PLACE).displayTitle())
    }
}
