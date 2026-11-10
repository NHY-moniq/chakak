package ai.chakak.vehicle

import android.content.Context
import android.content.pm.PackageManager
import ai.chakak.core.*
import ai.pleos.playground.vehicle.Vehicle
import ai.pleos.playground.vehicle.api.model.capability.*
import ai.pleos.playground.vehicle.api.model.telltale.Telltales
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/** Share a connection across concurrent reads, including concurrent voice/touch captures. */
internal class VehicleSession(private val context: Context) {
    private val lock = Mutex()
    private var instance: Vehicle? = null
    private var readers = 0
    suspend fun acquire(): Vehicle = lock.withLock {
        val sdk = instance ?: Vehicle(context).also {
            try { it.initialize() } catch (error: Exception) { runCatching { it.release() }; throw error }
            instance = it
        }
        readers++
        sdk
    }
    suspend fun release() = lock.withLock {
        readers--
        if(readers == 0) {
            val sdk = instance
            instance = null
            runCatching { sdk?.release() }
        }
    }
}

internal fun vehicleProvider(context: Context, session: VehicleSession, field: String, permission: String,
    read: suspend (Vehicle) -> Observation) = object : CaptureProvider {
    override val key = field
    override suspend fun read(): Observation {
        if(context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) return Observation(key, Status.DENIED)
        val vehicle = session.acquire()
        try { return read(vehicle).copy(key = key) }
        finally { withContext(NonCancellable) { session.release() } }
    }
}

internal suspend fun <T> awaitValue(register: ((T) -> Unit, (Exception) -> Unit) -> Unit): T = suspendCancellableCoroutine { c ->
    val completed = AtomicBoolean(false)
    c.invokeOnCancellation { completed.set(true) }
    try {
        register({ value -> if(completed.compareAndSet(false, true)) c.resume(value) },
            { error -> if(completed.compareAndSet(false, true)) c.resumeWithException(error) })
    } catch(error: Exception) { if(completed.compareAndSet(false, true)) c.resumeWithException(error) }
}
internal suspend fun capability(register: ((CapabilityConfig) -> Unit, (Exception) -> Unit) -> Unit) = awaitValue(register)
internal suspend fun <A> areaCapability(register: ((CapabilityConfig, List<A>) -> Unit, (Exception) -> Unit) -> Unit): Pair<CapabilityConfig, List<A>> =
    awaitValue { ok, error -> register({ config, areas -> ok(config to areas) }, error) }
private fun CapabilityConfig.readable() = accessType == AccessType.READ || accessType == AccessType.READ_WRITE

internal suspend fun readScalar(config: CapabilityConfig, read: ((Any?) -> Unit, (Exception) -> Unit) -> Unit): Observation {
    if(!config.readable()) return Observation("", Status.UNSUPPORTED)
    val row = readEntry("값", read)
    return result(listOf(row))
}
internal suspend fun <A> readAreas(capability: Pair<CapabilityConfig, List<A>>, label: (A) -> String,
    read: (A, (Any?) -> Unit, (Exception) -> Unit) -> Unit): Observation {
    if(!capability.first.readable() || capability.second.isEmpty()) return Observation("", Status.UNSUPPORTED)
    val rows = coroutineScope { capability.second.distinct().map { area -> async {
        readEntry(label(area)) { ok, error -> read(area, ok, error) }
    } }.awaitAll() }
    return result(rows)
}
private suspend fun readEntry(area: String, read: ((Any?) -> Unit, (Exception) -> Unit) -> Unit): JSONObject {
    val row = JSONObject().put("area", area)
    try {
        val completed = withTimeoutOrNull(1500) {
            val value = awaitValue(read)
            row.put("status", if(value == null) Status.UNAVAILABLE.name else Status.OK.name)
                .put("value", jsonValue(value))
            true
        }
        if(completed == null) row.put("status", Status.TIMEOUT.name)
    } catch(error: CancellationException) { throw error }
    catch(_: SecurityException) { row.put("status", Status.DENIED.name) }
    catch(_: UnsupportedOperationException) { row.put("status", Status.UNSUPPORTED.name) }
    catch(_: Exception) { row.put("status", Status.ERROR.name) }
    return row
}
private fun result(rows: List<JSONObject>): Observation {
    val statuses = rows.map { Status.valueOf(it.getString("status")) }.distinct()
    val status = if(statuses.size == 1) statuses.single() else Status.UNAVAILABLE
    return Observation("", status, JSONObject().put("entries", JSONArray(rows)).toString())
}
internal fun jsonValue(value: Any?): Any = when(value) {
    null -> JSONObject.NULL
    is String, is Boolean, is Number -> value
    is Enum<*> -> value.name
    is Iterable<*> -> JSONArray(value.map { jsonValue(it) })
    is Array<*> -> JSONArray(value.map { jsonValue(it) })
    is SmartGlassConfig -> JSONObject().put("구역 수", value.zoneCount).put("레벨 수", value.levelCount)
    is Telltales -> JSONArray(value.telltales.map { JSONObject().put("종류", it.type.name).put("상태", it.state.name).put("경고 횟수", it.warningCount) })
    else -> error("Unsupported SDK value type")
}

internal fun Observation.withUnit(unit: String): Observation {
    val raw = value ?: return this
    val json = JSONObject(raw)
    val entries = json.getJSONArray("entries")
    for(index in 0 until entries.length()) entries.getJSONObject(index).put("unit", unit)
    return copy(value = json.toString())
}
