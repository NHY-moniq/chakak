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
import ai.pleos.playground.location.fused.FusedLocation
import ai.pleos.playground.location.fused.listener.FusedLocationListener
import ai.pleos.playground.location.fused.model.LocationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import org.json.JSONObject

private suspend fun <T> callback(register: ((T) -> Unit, (Exception) -> Unit) -> Unit): T = suspendCancellableCoroutine { c ->
    val terminal = java.util.concurrent.atomic.AtomicBoolean(false)
    c.invokeOnCancellation { terminal.set(true) }
    register({ value -> if(terminal.compareAndSet(false, true)) c.resume(value) },
        { error -> if(terminal.compareAndSet(false, true)) c.resumeWithException(error) })
}
class RealPlatform(private val context: Context) : Platform {
    override val source = Source.REAL
    override val touchAllowed = MutableStateFlow(false)
    private var car: Car? = null
    private var ux: CarUxRestrictionsManager? = null
    private var properties: CarPropertyManager? = null
    override val voiceAllowed = MutableStateFlow(false)
    private var drivingState = DrivingState.UNKNOWN
    private var uxKnown = false
    private var unrestricted = false
    private var active = false
    private var clients = 0
    private fun update() {
        val access = captureAccess(if(active) drivingState else DrivingState.UNKNOWN, active && uxKnown,
            unrestricted, inMotionVoiceEnabled(
                approved = context.resources.getBoolean(R.bool.gleo_in_motion_approved),
                emulatorOptIn = context.resources.getBoolean(R.bool.gleo_emulator_in_motion_enabled),
                debugBuild = BuildConfig.DEBUG,
                emulator = android.os.Build.HARDWARE == "ranchu"))
        touchAllowed.value = access.touch
        voiceAllowed.value = access.voice
    }
    private fun readGear(value: CarPropertyValue<*>): DrivingState {
        if(value.status != CarPropertyValue.STATUS_AVAILABLE) return DrivingState.UNKNOWN
        return when(value.value) {
            VehicleGear.GEAR_PARK -> DrivingState.PARKED
            VehicleGear.GEAR_DRIVE -> DrivingState.DRIVE
            VehicleGear.GEAR_REVERSE -> DrivingState.REVERSE
            else -> DrivingState.OTHER
        }
    }
    private val listener = object : CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(value: CarPropertyValue<*>) {
            if(value.propertyId == VehiclePropertyIds.GEAR_SELECTION) {
                drivingState = readGear(value)
                update()
            }
        }
        override fun onErrorEvent(propId: Int, zone: Int) { drivingState = DrivingState.UNKNOWN; update() }
    }
    override fun start() {
        clients++
        if(active) return
        active = true
        try {
            car = Car.createCar(context, null, Car.CAR_WAIT_TIMEOUT_DO_NOT_WAIT) { connected, ready ->
                if(!ready || !active) { drivingState = DrivingState.UNKNOWN; unrestricted = false; uxKnown = false; update() }
                else try {
                    ux = connected.getCarManager(Car.CAR_UX_RESTRICTION_SERVICE) as CarUxRestrictionsManager
                    properties = connected.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
                    ux!!.registerListener { restrictions -> uxKnown = true; unrestricted = !restrictions.isRequiresDistractionOptimization; update() }
                    unrestricted = !ux!!.currentCarUxRestrictions.isRequiresDistractionOptimization
                    uxKnown = true
                    val registered = properties!!.registerCallback(listener, VehiclePropertyIds.GEAR_SELECTION, CarPropertyManager.SENSOR_RATE_ONCHANGE)
                    val value = properties!!.getProperty<Int>(VehiclePropertyIds.GEAR_SELECTION, 0)
                    drivingState = if(registered) readGear(value) else DrivingState.UNKNOWN
                    update()
                } catch (_: Exception) { drivingState = DrivingState.UNKNOWN; unrestricted = false; uxKnown = false; update() }
            }
        } catch (_: Exception) { drivingState = DrivingState.UNKNOWN; uxKnown = false; update() }
    }
    override fun stop() {
        clients = (clients - 1).coerceAtLeast(0)
        if(clients > 0) return
        active = false; drivingState = DrivingState.UNKNOWN; unrestricted = false; uxKnown = false; update()
        runCatching { ux?.unregisterListener() }; runCatching { properties?.unregisterCallback(listener) }; runCatching { car?.disconnect() }
        ux = null; properties = null; car = null
    }
    override val providers: List<CaptureProvider> = ai.chakak.vehicle.vehicleProviders(context) + listOf(
        object : CaptureProvider {
            override val key = "location"
            override suspend fun read(): Observation {
                val navi = ai.chakak.vehicle.readNaviLocation(context)
                navi.observation?.let { return it }
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
                        .put("confidence", location.confidence).put("addressMessage", navi.reason).toString(), location.timestampMillis)
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
