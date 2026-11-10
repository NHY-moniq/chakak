package ai.chakak.ui.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ActionFeedbackTest {
    private fun outcome(kind: ActionKind, result: ActionResult) = ActionOutcome(kind, "r", result)

    @Test fun rejectedCaptureDoesNotClaimSaved() {
        val message = outcome(ActionKind.Capture, ActionResult.Rejected).message()
        assertTrue(message.isError)
        assertFalse(message.text.contains("저장했습니다"))
        assertFalse(message.text.contains("남겼습니다"))
    }

    @Test fun onlyDoneIsNotError() {
        ActionKind.entries.forEach { kind ->
            ActionResult.entries.forEach { result ->
                assertEquals("$kind/$result", result != ActionResult.Done, outcome(kind, result).message().isError)
            }
        }
    }

    @Test fun deleteFailureSaysRecordKept() {
        assertTrue(outcome(ActionKind.Delete, ActionResult.Failed).message().text.contains("그대로 남아"))
    }

    @Test fun failedLookupMatchesKindAndRecord() {
        val state = ActionState(outcome = outcome(ActionKind.Delete, ActionResult.Failed))
        assertTrue(state.failed(ActionKind.Delete, "r"))
        assertFalse(state.failed(ActionKind.Delete, "other"))
        assertFalse(state.failed(ActionKind.Edit, "r"))
    }

    @Test fun runningLookup() {
        val state = ActionState(running = ActionKind.Edit, runningRecordId = "r")
        assertTrue(state.busy)
        assertTrue(state.isRunning(ActionKind.Edit, "r"))
        assertFalse(state.isRunning(ActionKind.Edit, "other"))
        assertFalse(state.isRunning(ActionKind.Delete))
    }
}
