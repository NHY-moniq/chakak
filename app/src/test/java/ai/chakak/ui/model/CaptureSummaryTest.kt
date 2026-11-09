package ai.chakak.ui.model

import ai.chakak.core.Category
import ai.chakak.core.Observation
import ai.chakak.core.Record
import ai.chakak.core.Source
import ai.chakak.core.Status
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CaptureSummaryTest {
    private fun record(collecting: Boolean, vararg statuses: Status) = Record(
        id = "r", createdAt = 0, category = Category.UNCLASSIFIED, source = Source.MOCK, collecting = collecting,
        observations = statuses.mapIndexed { i, s -> Observation("k$i", s) },
    )

    @Test fun collectingWinsOverEverything() {
        assertEquals(CaptureSummary.Collecting, record(true).captureSummary())
        assertEquals(CaptureSummary.Collecting, record(true, Status.OK, Status.INTERRUPTED).captureSummary())
    }

    @Test fun zeroObservationsIsNotComplete() {
        val summary = record(false).captureSummary()
        assertEquals(CaptureSummary.NoObservations, summary)
        assertNotEquals(CaptureSummary.Complete(0), summary)
    }

    @Test fun anyInterruptedIsInterrupted() {
        assertEquals(CaptureSummary.Interrupted(1, 2), record(false, Status.OK, Status.INTERRUPTED).captureSummary())
        assertEquals(CaptureSummary.Interrupted(0, 1), record(false, Status.INTERRUPTED).captureSummary())
    }

    @Test fun interruptedLabelShowsSuccessCount() {
        val summary = record(false, Status.OK, Status.OK, Status.INTERRUPTED, Status.TIMEOUT).captureSummary()
        assertEquals(CaptureSummary.Interrupted(2, 4), summary)
        assertEquals("수집 중단 · 2/4 수집", summary.label())
    }

    @Test fun allOkIsComplete() {
        assertEquals(CaptureSummary.Complete(3), record(false, Status.OK, Status.OK, Status.OK).captureSummary())
    }

    @Test fun someOkIsPartial() {
        assertEquals(CaptureSummary.Partial(1, 3), record(false, Status.OK, Status.DENIED, Status.TIMEOUT).captureSummary())
    }

    @Test fun allFailedIsNoneCollected() {
        val failed = arrayOf(Status.UNAVAILABLE, Status.UNSUPPORTED, Status.DENIED, Status.TIMEOUT, Status.ERROR)
        assertEquals(CaptureSummary.NoneCollected(5), record(false, *failed).captureSummary())
    }

    @Test fun leftoverPendingAfterFinishCountsAsMissing() {
        assertEquals(CaptureSummary.Partial(1, 2), record(false, Status.OK, Status.PENDING).captureSummary())
        assertEquals(CaptureSummary.NoneCollected(1), record(false, Status.PENDING).captureSummary())
    }

    @Test fun summaryLabelsAreDistinctAndShowCounts() {
        val summaries = listOf(
            CaptureSummary.Collecting, CaptureSummary.NoObservations, CaptureSummary.Interrupted(1, 2),
            CaptureSummary.Complete(2), CaptureSummary.Partial(1, 2), CaptureSummary.NoneCollected(2),
        )
        assertEquals(summaries.size, summaries.map { it.label() }.toSet().size)
        assertTrue(CaptureSummary.Partial(1, 2).label().contains("1/2"))
        assertEquals("수집 완료", CaptureSummary.Complete(2).label())
    }

    @Test fun statusLabelsAreDistinct() {
        assertEquals(Status.entries.size, Status.entries.map { it.label() }.toSet().size)
    }

    @Test fun categoryLabelsFollowSpec() {
        assertEquals("차량 상태", Category.VEHICLE_STATUS.label())
        assertEquals("장소", Category.PLACE.label())
        assertEquals("미분류", Category.UNCLASSIFIED.label())
    }

    @Test fun observationKeyLabelsKeepUnknownKeys() {
        assertEquals("위치", observationKeyLabel("location"))
        assertEquals("누적 주행 거리", observationKeyLabel("odometer"))
        assertEquals("경고등 상태", observationKeyLabel("telltales"))
        assertEquals("타이어 상태", observationKeyLabel("tires"))
        assertEquals("fuel", observationKeyLabel("fuel"))
    }

    @Test fun tonesNeverMarkMissingDataAsSuccess() {
        assertEquals(StatusTone.Success, CaptureSummary.Complete(1).tone())
        assertEquals(StatusTone.Success, Status.OK.tone())
        listOf(CaptureSummary.Collecting, CaptureSummary.NoObservations, CaptureSummary.Interrupted(1, 2),
            CaptureSummary.Partial(1, 2), CaptureSummary.NoneCollected(1))
            .forEach { assertNotEquals(StatusTone.Success, it.tone()) }
        Status.entries.filter { it != Status.OK }.forEach { assertNotEquals(StatusTone.Success, it.tone()) }
    }

    @Test fun collectionFailuresUseFailureTone() {
        listOf(Status.TIMEOUT, Status.ERROR, Status.INTERRUPTED).forEach { assertEquals(StatusTone.Failure, it.tone()) }
        listOf(Status.UNAVAILABLE, Status.UNSUPPORTED, Status.DENIED).forEach { assertEquals(StatusTone.Neutral, it.tone()) }
        assertEquals(StatusTone.Failure, CaptureSummary.NoneCollected(1).tone())
        assertEquals(StatusTone.Notice, CaptureSummary.Partial(1, 2).tone())
    }
}
