package ai.chakak.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import ai.chakak.core.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = android.app.Application::class)
class RoomRecordStoreTest {
    private lateinit var db: ChakakDatabase
    private lateinit var store: RoomRecordStore
    @Before fun setup() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext<Context>(), ChakakDatabase::class.java).allowMainThreadQueries().build()
        store = RoomRecordStore(db)
    }
    @After fun close() { db.close() }
    @Test fun transactionsPreserveEditsAndRejectLateWritesAfterDeletion() = runTest {
        assertTrue(store.begin("id", 1, Category.PLACE, Source.REAL, listOf("location")))
        store.edit("id", "title", Category.PLACE, "memo")
        store.observe("id", Observation("location", Status.OK, "data"))
        store.finish("id", Status.TIMEOUT)
        assertEquals("memo", store.records.first().single().memo)
        store.delete("id")
        store.observe("id", Observation("location", Status.OK, "late"))
        assertFalse(store.begin("id", 2, Category.PLACE, Source.REAL, emptyList()))
        assertTrue(store.records.first().isEmpty())
    }
    @Test fun memoIsCorrelatedSingleUseAndExpiring() = runTest {
        store.begin("id", 1, Category.PLACE, Source.REAL, emptyList())
        store.issueMemoToken("id", "token", 100)
        assertFalse(store.addMemo("other", "token", 2, "wrong"))
        assertFalse(store.addMemo("id", "wrong", 2, "wrong"))
        assertTrue(store.addMemo("id", "token", 2, "saved"))
        assertFalse(store.addMemo("id", "token", 3, "replay"))
        store.issueMemoToken("id", "next", 100)
        assertFalse(store.addMemo("id", "next", 100, "expired"))
        assertEquals("saved", store.records.first().single().memo)
    }
    @Test fun pendingMemoTargetsOnlyTheLatestUnexpiredCapture() = runTest {
        store.begin("old", 1, Category.PLACE, Source.REAL, emptyList())
        store.begin("new", 2, Category.PLACE, Source.REAL, emptyList())
        store.issueMemoToken("old", "t1", 100)
        store.issueMemoToken("new", "t2", 100)
        assertFalse(store.addMemo("old", "t1", 5, "stale token"))
        assertFalse(store.addPendingMemo(100, "expired"))
        assertTrue(store.addPendingMemo(5, "park on the right"))
        assertFalse(store.addPendingMemo(6, "second memo"))
        val memos = store.records.first().associate { it.id to it.memo }
        assertEquals("park on the right", memos["new"])
        assertEquals("", memos["old"])
    }
    @Test fun parkedEditCancelsPendingMemo() = runTest {
        store.begin("id", 1, Category.PLACE, Source.REAL, emptyList())
        store.issueMemoToken("id", "token", 100)
        store.edit("id", "edited", Category.PLACE, "parked memo")
        assertFalse(store.addPendingMemo(2, "late voice memo"))
        assertEquals("parked memo", store.records.first().single().memo)
    }
    @Test fun startupRecoveryPreservesCompletedObservations() = runTest {
        store.begin("id", 1, Category.PLACE, Source.REAL, listOf("location", "tire"))
        store.observe("id", Observation("location", Status.OK, "observed"))
        store.recover()
        val record = store.records.first().single()
        assertFalse(record.collecting)
        assertEquals(setOf(Status.OK, Status.INTERRUPTED), record.observations.map { it.status }.toSet())
    }
    @Test fun parkedEditInvalidatesOutstandingVoiceMemo() = runTest {
        store.begin("id", 1, Category.PLACE, Source.REAL, emptyList())
        store.issueMemoToken("id", "token", 100)
        store.edit("id", "edited", Category.PLACE, "parked memo")
        assertFalse(store.addMemo("id", "token", 2, "late voice memo"))
        assertEquals("parked memo", store.records.first().single().memo)
    }

}
