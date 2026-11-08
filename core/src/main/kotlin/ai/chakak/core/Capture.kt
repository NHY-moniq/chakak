package ai.chakak.core

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

enum class Category { UNCLASSIFIED, PLACE, VEHICLE_STATUS }
enum class Source { REAL, MOCK }
enum class Status { PENDING, OK, UNAVAILABLE, UNSUPPORTED, DENIED, TIMEOUT, ERROR, INTERRUPTED }
data class Observation(val key: String, val status: Status, val value: String? = null, val observedAt: Long? = null)
data class Record(val id: String, val createdAt: Long, val category: Category, val source: Source,
    val title: String = "", val memo: String = "", val collecting: Boolean = true, val observations: List<Observation> = emptyList())
interface RecordStore {
    val records: Flow<List<Record>>
    /** Returns false for a previously seen request, including deleted requests. */
    suspend fun begin(id: String, at: Long, category: Category, source: Source, keys: List<String>): Boolean
    suspend fun observe(id: String, observation: Observation)
    suspend fun finish(id: String, pendingStatus: Status)
    suspend fun recover()
    suspend fun edit(id: String, title: String, category: Category, memo: String)
    suspend fun delete(id: String)
    /** Replaces any outstanding memo token, so at most one record awaits a voice memo at a time. */
    suspend fun issueMemoToken(id: String, token: String, expiresAt: Long)
    suspend fun addMemo(id: String, token: String, now: Long, memo: String): Boolean
    /** Attaches the memo to the single record still awaiting one; Gleo does not pass callback data into the next action. */
    suspend fun addPendingMemo(now: Long, memo: String): Boolean
}
interface CaptureProvider { val key: String; suspend fun read(): Observation }
class CaptureEngine(private val store: RecordStore, private val providers: List<CaptureProvider>,
    private val source: Source, private val allowed: StateFlow<Boolean>? = null, private val clock: () -> Long = System::currentTimeMillis) {
    suspend fun capture(id: String, category: Category, receivedAt: Long = clock()): Boolean {
        if(allowed?.value == false) return false
        require(id.isNotBlank() && id.length <= 128)
        if (!store.begin(id, receivedAt, category, source, providers.map { it.key })) return false
        var unfinished = Status.TIMEOUT
        try {
            val remaining = (5000L - (clock() - receivedAt).coerceAtLeast(0)).coerceAtLeast(0)
            withTimeoutOrNull(remaining) {
                coroutineScope {
                    val monitor = allowed?.let { gate -> launch { gate.first { !it }; throw RestrictionChanged() } }
                    providers.map { provider -> launch {
                        val value = try { provider.read() }
                        catch (e: CancellationException) { throw e }
                        catch (_: SecurityException) { Observation(provider.key, Status.DENIED) }
                        catch (_: Exception) { Observation(provider.key, Status.ERROR) }
                        store.observe(id, value.copy(key = provider.key))
                    } }.joinAll()
                    monitor?.cancel()
                }
            }
        } catch (_: RestrictionChanged) {
            unfinished = Status.INTERRUPTED
        } catch (e: CancellationException) {
            unfinished = Status.INTERRUPTED
            throw e
        } catch (e: Exception) {
            unfinished = Status.ERROR
            throw e
        } finally {
            withContext(NonCancellable) { store.finish(id, unfinished) }
        }
        return true
    }
}

private class RestrictionChanged : RuntimeException()
