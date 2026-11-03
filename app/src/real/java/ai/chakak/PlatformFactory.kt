package ai.chakak

import android.content.Context
import android.content.pm.PackageManager
import android.car.Car
import android.car.VehiclePropertyIds
import android.car.VehicleGear
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.car.drivingstate.CarUxRestrictionsManager
import ai.chakak.core.*
import ai.chakak.platform.Platform
import ai.pleos.playground.vehicle.Vehicle
import ai.pleos.playground.vehicle.api.model.capability.CapabilityConfig
import ai.pleos.playground.vehicle.api.model.capability.AccessType
import ai.pleos.playground.location.fused.FusedLocation
import ai.pleos.playground.location.fused.listener.FusedLocationListener
import ai.pleos.playground.location.fused.model.LocationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import org.json.JSONObject
import org.json.JSONArray

private suspend fun <T> callback(register: ((T) -> Unit, (Exception) -> Unit) -> Unit): T = suspendCancellableCoroutine { c ->
    val terminal = java.util.concurrent.atomic.AtomicBoolean(false)
    c.invokeOnCancellation { terminal.set(true) }
    register({ value -> if(terminal.compareAndSet(false, true)) c.resume(value) },
        { error -> if(terminal.compareAndSet(false, true)) c.resumeWithException(error) })
}
private fun CapabilityConfig.readable() = accessType == AccessType.READ || accessType == AccessType.READ_WRITE
class RealPlatform(private val context: Context) : Platform {
    override val source = Source.REAL
    override val touchAllowed = MutableStateFlow(false)
    private var car: Car? = null
    private var ux: CarUxRestrictionsManager? = null
    private var properties: CarPropertyManager? = null
    private var parked = false
    private var unrestricted = false
    private var active = false
    private var clients = 0
    private fun update() { touchAllowed.value = active && parked && unrestricted }
    private val listener = object : CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(value: CarPropertyValue<*>) {
            if(value.propertyId == VehiclePropertyIds.GEAR_SELECTION) {
                parked = value.status == CarPropertyValue.STATUS_AVAILABLE && value.value == VehicleGear.GEAR_PARK
                update()
            }
        }
        override fun onErrorEvent(propId: Int, zone: Int) { parked = false; update() }
    }
    override fun start() {
        clients++
        if(active) return
        active = true
        try {
            car = Car.createCar(context, null, Car.CAR_WAIT_TIMEOUT_DO_NOT_WAIT) { connected, ready ->
                if(!ready || !active) { parked = false; unrestricted = false; update() }
                else try {
                    ux = connected.getCarManager(Car.CAR_UX_RESTRICTION_SERVICE) as CarUxRestrictionsManager
                    properties = connected.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
                    ux!!.registerListener { restrictions -> unrestricted = !restrictions.isRequiresDistractionOptimization; update() }
                    unrestricted = !ux!!.currentCarUxRestrictions.isRequiresDistractionOptimization
                    val registered = properties!!.registerCallback(listener, VehiclePropertyIds.GEAR_SELECTION, CarPropertyManager.SENSOR_RATE_ONCHANGE)
                    val value = properties!!.getProperty<Int>(VehiclePropertyIds.GEAR_SELECTION, 0)
                    parked = registered && value.status == CarPropertyValue.STATUS_AVAILABLE && value.value == VehicleGear.GEAR_PARK
                    update()
                } catch (_: Exception) { parked = false; unrestricted = false; update() }
            }
        } catch (_: Exception) { touchAllowed.value = false }
    }
    override fun stop() {
        clients = (clients - 1).coerceAtLeast(0)
        if(clients > 0) return
        active = false; touchAllowed.value = false; parked = false; unrestricted = false
        runCatching { ux?.unregisterListener() }; runCatching { properties?.unregisterCallback(listener) }; runCatching { car?.disconnect() }
        ux = null; properties = null; car = null
    }
    private fun vehicleProvider(field: String, permission: String, read: suspend (Vehicle) -> Observation) = object : CaptureProvider {
        override val key = field
        override suspend fun read(): Observation {
            if(context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) return Observation(key, Status.DENIED)
            val vehicle = Vehicle(context)
            try { vehicle.initialize(); return read(vehicle) } finally { vehicle.release() }
        }
    }
    override val providers: List<CaptureProvider> = listOf(
        vehicleProvider("odometer", "pleos.car.permission.CAR_DRIVE") { vehicle ->
            val capability = callback<CapabilityConfig> { ok, error -> vehicle.getOdometer().checkCurrentDistanceDrivenCapabilities(ok, error) }
            if(!capability.readable()) Observation("odometer", Status.UNSUPPORTED)
            else {
                val value = callback<Float?> { ok, error -> vehicle.getOdometer().getCurrentDistanceDriven(ok, error) }
                Observation("odometer", if(value == null) Status.UNAVAILABLE else Status.OK,
                    value?.let { JSONObject().put("rawValue", it).put("unit", JSONObject.NULL).toString() })
            }
        },
        vehicleProvider("telltales", "pleos.car.permission.PRIVATE_CAR_SAFETY") { vehicle ->
            val capability = callback<CapabilityConfig> { ok, error -> vehicle.getSafety().checkTelltalesCapabilities(ok, error) }
            if(!capability.readable()) Observation("telltales", Status.UNSUPPORTED)
            else {
                val value = callback<ai.pleos.playground.vehicle.api.model.telltale.Telltales?> { ok, error -> vehicle.getSafety().getTelltalesInfo(ok, error) }
                Observation("telltales", if(value == null) Status.UNAVAILABLE else Status.OK,
                    value?.let { JSONArray(it.telltales.map { item -> JSONObject().put("type", item.type.name).put("state", item.state.name).put("warningCount", item.warningCount) }).toString() })
            }
        },
        vehicleProvider("tires", "pleos.car.permission.CAR_SAFETY") { vehicle ->
            val (capability, areas) = callback<Pair<CapabilityConfig, List<ai.pleos.playground.vehicle.api.model.area.WheelArea>>> { ok, error ->
                vehicle.getTire().checkLowTirePressureCapabilities({ config, supported -> ok(config to supported) }, error)
            }
            if(!capability.readable() || areas.isEmpty()) Observation("tires", Status.UNSUPPORTED)
            else {
                val values = JSONArray()
                var missing = false
                for(area in areas) {
                    val value = callback<Float?> { ok, error -> vehicle.getTire().getLowTirePressure(area, ok, error) }
                    if(value == null) missing = true
                    values.put(JSONObject().put("area", area.values.joinToString { it.name }).put("rawValue", value ?: JSONObject.NULL).put("unit", JSONObject.NULL))
                }
                Observation("tires", if(missing) Status.UNAVAILABLE else Status.OK, values.toString())
            }
        },
        object : CaptureProvider {
            override val key = "location"
            override suspend fun read(): Observation {
                val sdk = FusedLocation(context)
                try {
                    if(!sdk.isSupported()) return Observation(key, Status.UNSUPPORTED)
                    if(context.checkSelfPermission("pleos.car.permission.FUSED_LOCATION") != PackageManager.PERMISSION_GRANTED) return Observation(key, Status.DENIED)
                    sdk.initialize()
                    val location = callback<LocationData?> { ok, error -> sdk.getLastKnownLocation(object : FusedLocationListener {
                        override fun onChanged(data: LocationData?) { ok(data) }
                        override fun onError(exception: Exception) { error(exception) }
                    }) }
                    if(location == null) return Observation(key, Status.UNAVAILABLE)
                    // Product policy: last-known observations older than 60s are unavailable, never silently current.
                    val age = System.currentTimeMillis() - location.timestampMillis
                    if(age !in 0..60_000) return Observation(key, Status.UNAVAILABLE, observedAt = location.timestampMillis)
                    return Observation(key, Status.OK, JSONObject().put("latitude", location.latitude).put("longitude", location.longitude)
                        .put("confidence", location.confidence).toString(), location.timestampMillis)
                } finally { sdk.release() }
            }
        }
    )
}
private class UnavailablePlatform : Platform {
    override val source = Source.REAL
    override val touchAllowed = MutableStateFlow(false)
    override val providers = emptyList<CaptureProvider>()
    override fun start() {}
    override fun stop() {}
}
fun createPlatform(context: Context): Platform = if(context.packageManager.hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE)) RealPlatform(context) else UnavailablePlatform()
