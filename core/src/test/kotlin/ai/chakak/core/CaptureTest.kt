package ai.chakak.core

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Test

private class MemoryStore : RecordStore {
    override val records = MutableStateFlow<List<Record>>(emptyList())
    private val seen = mutableSetOf<String>()
    override suspend fun begin(id: String, at: Long, category: Category, source: Source, keys: List<String>): Boolean {
        if(!seen.add(id)) return false
        records.value += Record(id, at, category, source, observations = keys.map { Observation(it, Status.PENDING) }); return true
    }
    override suspend fun observe(id: String, observation: Observation) { records.value = records.value.map { r -> if(r.id != id || !r.collecting) r else r.copy(observations = r.observations.map { if(it.key == observation.key && it.status == Status.PENDING) observation else it }) } }
    override suspend fun finish(id: String, pendingStatus: Status) { records.value = records.value.map { r -> if(r.id != id) r else r.copy(collecting = false, observations = r.observations.map { if(it.status == Status.PENDING) it.copy(status = pendingStatus) else it }) } }
    override suspend fun recover() { records.value.filter { it.collecting }.forEach { finish(it.id, Status.INTERRUPTED) } }
    override suspend fun edit(id: String, title: String, category: Category, memo: String) {}
    override suspend fun delete(id: String) { records.value = records.value.filterNot { it.id == id } }
    override suspend fun issueMemoToken(id: String, token: String, expiresAt: Long) {}
    override suspend fun addMemo(id: String, token: String, now: Long, memo: String) = false
    override suspend fun addPendingMemo(now: Long, memo: String) = false
}
@OptIn(ExperimentalCoroutinesApi::class)
class CaptureTest {
    private fun provider(key: String, block: suspend () -> Observation) = object : CaptureProvider { override val key = key; override suspend fun read() = block() }
    @Test fun baseIsDurableBeforeProvidersAndTimeoutPreservesSuccess() = runTest {
        val store = MemoryStore()
        val engine = CaptureEngine(store, listOf(
            provider("fast") { assertEquals("id", store.records.value.single().id); Observation("fast", Status.OK, "42") },
            provider("slow") { delay(6000); Observation("slow", Status.OK, "late") }
        ), Source.MOCK, clock = { testScheduler.currentTime })
        engine.capture("id", Category.PLACE)
        val record = store.records.value.single()
        assertEquals(5000, testScheduler.currentTime)
        assertFalse(record.collecting)
        assertEquals(listOf(Status.OK, Status.TIMEOUT), record.observations.map { it.status })
    }
    @Test fun cancellationFinalizesAsInterrupted() = runTest {
        val store = MemoryStore()
        val engine = CaptureEngine(store, listOf(provider("slow") { delay(6000); Observation("slow", Status.OK) }), Source.REAL)
        val job = launch { engine.capture("id", Category.UNCLASSIFIED) }
        runCurrent(); job.cancelAndJoin()
        assertEquals(Status.INTERRUPTED, store.records.value.single().observations.single().status)
    }
    @Test fun deletionAndDuplicateCannotResurrectRecord() = runTest {
        val store = MemoryStore()
        var calls = 0
        val engine = CaptureEngine(store, listOf(provider("value") { calls++; delay(100); Observation("value", Status.OK, "123") }), Source.MOCK)
        val job = launch { engine.capture("id", Category.UNCLASSIFIED) }
        runCurrent(); store.delete("id"); job.join()
        assertFalse(engine.capture("id", Category.UNCLASSIFIED)); assertEquals(1, calls); assertTrue(store.records.value.isEmpty())
    }
    @Test fun deniedDoesNotBecomeZeroOrSuccess() = runTest {
        val store = MemoryStore()
        CaptureEngine(store, listOf(provider("value") { throw SecurityException() }), Source.REAL).capture("id", Category.VEHICLE_STATUS)
        val field = store.records.value.single().observations.single()
        assertEquals(Status.DENIED, field.status); assertNull(field.value)
    }
    @Test fun restrictionChangeInterruptsAndRetainsBase() = runTest {
        val store = MemoryStore()
        val allowed = MutableStateFlow(true)
        val engine = CaptureEngine(store, listOf(provider("slow") { delay(6000); Observation("slow", Status.OK) }), Source.REAL, allowed)
        val job = launch { engine.capture("id", Category.PLACE) }
        runCurrent(); allowed.value = false; job.join()
        assertEquals(Status.INTERRUPTED, store.records.value.single().observations.single().status)
        assertFalse(engine.capture("second", Category.PLACE))
        assertEquals(1, store.records.value.size)
    }

    @Test fun approvedDrivingVoiceCapturePersistsWithoutTouchPermission() = runTest {
        val access = captureAccess(DrivingState.DRIVE, true, false, true)
        assertFalse(access.touch)
        val store = MemoryStore()
        val gate = MutableStateFlow(access.voice)
        val engine = CaptureEngine(store, listOf(provider("value") { Observation("value", Status.OK, "sample") }), Source.REAL, gate)
        assertTrue(engine.capture("voice", Category.VEHICLE_STATUS))
        assertEquals(Status.OK, store.records.value.single().observations.single().status)
    }
    @Test fun lostVehicleStateInterruptsVoiceCaptureWithoutDeletingBase() = runTest {
        val store = MemoryStore()
        val gate = MutableStateFlow(captureAccess(DrivingState.DRIVE, true, false, true).voice)
        val engine = CaptureEngine(store, listOf(provider("slow") { delay(6000); Observation("slow", Status.OK) }), Source.REAL, gate)
        val job = launch { engine.capture("voice", Category.PLACE) }
        runCurrent()
        gate.value = captureAccess(DrivingState.UNKNOWN, false, false, true).voice
        job.join()
        assertEquals(Status.INTERRUPTED, store.records.value.single().observations.single().status)
    }

}
