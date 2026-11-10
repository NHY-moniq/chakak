package ai.chakak.ui.model

import ai.chakak.core.Category
import androidx.compose.runtime.saveable.SaverScope
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LibraryNavigationTest {
    private val detail = LibraryNavigation(filter = Category.PLACE).select("a")

    @Test fun backGoesEditThenDetailThenList() {
        val edit = detail.startEdit()
        assertTrue(edit.editing)
        val back1 = edit.back()
        assertEquals(detail, back1)
        val back2 = back1.back()
        assertNull(back2.selectedId)
        assertEquals(Category.PLACE, back2.filter)
        assertFalse(back2.canGoBack)
    }

    @Test fun backClosesDeleteConfirmFirst() {
        assertEquals(detail, detail.openDelete().back())
    }

    @Test fun cannotEditOrDeleteWithoutSelection() {
        val root = LibraryNavigation()
        assertEquals(root, root.startEdit())
        assertEquals(root, root.openDelete())
    }

    @Test fun successfulDeleteReturnsToListKeepingFilter() {
        val after = detail.openDelete().after(ActionOutcome(ActionKind.Delete, "a", ActionResult.Done))
        assertEquals(LibraryNavigation(filter = Category.PLACE), after)
    }

    @Test fun failedDeleteKeepsDialogOpen() {
        val confirming = detail.openDelete()
        assertEquals(confirming, confirming.after(ActionOutcome(ActionKind.Delete, "a", ActionResult.Failed)))
    }

    @Test fun successfulEditReturnsToDetailOnlyForSameRecord() {
        val editing = detail.startEdit()
        assertFalse(editing.after(ActionOutcome(ActionKind.Edit, "a", ActionResult.Done)).editing)
        assertTrue(editing.after(ActionOutcome(ActionKind.Edit, "b", ActionResult.Done)).editing)
        assertTrue(editing.after(ActionOutcome(ActionKind.Edit, "a", ActionResult.Failed)).editing)
    }

    @Test fun missingRecordReturnsToList() {
        assertEquals(detail, detail.reconcile(setOf("a")))
        assertNull(detail.startEdit().reconcile(setOf("b")).selectedId)
    }

    @Test fun filterHidingSelectionClearsSelectionEditAndDelete() {
        val editing = detail.startEdit()
        assertEquals(LibraryNavigation(filter = Category.VEHICLE_STATUS), editing.withFilter(Category.VEHICLE_STATUS, Category.PLACE))
        val deleting = detail.openDelete()
        assertEquals(LibraryNavigation(filter = Category.UNCLASSIFIED), deleting.withFilter(Category.UNCLASSIFIED, Category.PLACE))
    }

    @Test fun filterKeepingSelectionKeepsDetail() {
        val editing = detail.startEdit()
        assertEquals(editing.copy(filter = null), editing.withFilter(null, Category.PLACE))
        val vehicle = LibraryNavigation().select("v")
        assertEquals(vehicle.copy(filter = Category.VEHICLE_STATUS), vehicle.withFilter(Category.VEHICLE_STATUS, Category.VEHICLE_STATUS))
    }

    @Test fun filterWithoutSelectionOnlyChangesFilter() {
        assertEquals(LibraryNavigation(filter = Category.PLACE), LibraryNavigation().withFilter(Category.PLACE, null))
    }

    @Test fun saverRoundTrip() {
        val scope = SaverScope { true }
        val original = detail.startEdit()
        val saved = with(LibraryNavigation.Saver) { scope.save(original) }!!
        assertEquals(original, LibraryNavigation.Saver.restore(saved))
        val root = LibraryNavigation()
        assertEquals(root, LibraryNavigation.Saver.restore(with(LibraryNavigation.Saver) { scope.save(root) }!!))
    }

    @Test fun captureAndSuccessAreConsumedButEditFailureStays() {
        assertTrue(ActionOutcome(ActionKind.Capture, null, ActionResult.Failed).consumedImmediately())
        assertTrue(ActionOutcome(ActionKind.Edit, "a", ActionResult.Done).consumedImmediately())
        assertFalse(ActionOutcome(ActionKind.Edit, "a", ActionResult.Failed).consumedImmediately())
        assertFalse(ActionOutcome(ActionKind.Delete, "a", ActionResult.Failed).consumedImmediately())
    }

    @Test fun guidanceOutcomeStaysUntilTheDetailScreenClosesItsPopup() {
        assertFalse(ActionOutcome(ActionKind.Navigate, "a", ActionResult.Done).consumedImmediately())
        assertFalse(ActionOutcome(ActionKind.Navigate, "a", ActionResult.Failed).consumedImmediately())
        assertTrue(ActionOutcome(ActionKind.Navigate, "a", ActionResult.Restricted).consumedImmediately())
        val detail = LibraryNavigation(selectedId = "a")
        assertEquals(detail, detail.after(ActionOutcome(ActionKind.Navigate, "a", ActionResult.Done)))
    }
}
