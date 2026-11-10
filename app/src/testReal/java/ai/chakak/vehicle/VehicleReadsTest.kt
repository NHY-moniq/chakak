package ai.chakak.vehicle

import android.content.ContextWrapper
import ai.chakak.core.Status
import ai.chakak.ui.displayLines
import ai.pleos.playground.vehicle.api.model.capability.*
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = android.app.Application::class)
class VehicleReadsTest {
    @Test fun catalogContainsOnlyEnabledReadings() {
        val providers = vehicleProviders(ContextWrapper(null))
        assertEquals(26, providers.size)
        assertEquals(26, providers.map { it.key }.distinct().size)
        assertFalse(providers.any { it.key == "vehicle.getVINInfo" })
        assertFalse(providers.any { it.key == "vehicle.getCruiseControlTargetSpeed" })
        assertFalse(providers.any { it.key == "vehicle.getCurrentSteeringWheelAngle" })
        assertFalse(providers.any { it.key == "vehicle.getCurrentTemperatureDisplayUnit" })
        assertFalse(providers.any { it.key == "vehicle.getTripSinceRefillDrivingDistance" })
        assertFalse(providers.any { it.key == "vehicle.getTripSinceRefillDrivingTime" })
        assertFalse(providers.any { it.key == "vehicle.getCRNInfo" })
        assertFalse(providers.any { "Seat" in it.key || "Ambient" in it.key || "FuelEconomy" in it.key || "SmartGlass" in it.key })
        assertTrue(providers.any { it.key == "vehicle.getFuelPercent" })
        assertTrue(providers.any { it.key == "vehicle.getCurrentEvEfficiency" })
        assertTrue(providers.any { it.key == "vehicle.getDoorLockState" })
        assertTrue(providers.any { it.key == "tires" })
        assertFalse(providers.any { it.key.startsWith("vehicle.getTrip") })
        assertTrue(providers.any { it.key == "odometer" })
        assertFalse(providers.any { "TurnSignal" in it.key || "HeatState" in it.key })
        val observations = providers.map { ai.chakak.core.Observation(it.key, Status.PENDING) }
        val groups = ai.chakak.ui.groupObservations(observations)
        assertFalse(groups.any { it.title == "이전 수집·기타 항목" })
        assertEquals(26, groups.flatMap { it.observations }.map { it.key }.distinct().size)
        assertEquals(setOf("vehicle.getHvacAcState", "vehicle.getHvacTemperature", "vehicle.getHvacTemperatureCurrent", "vehicle.getHvacRecirculationState"),
            groups.single { it.title == "공조" }.observations.map { it.key }.toSet())
        val lights = groups.single { it.title == "외부 조명" }.observations
        assertEquals(setOf("vehicle.getCurrentFrontFogLightState", "vehicle.getCurrentHighBeamLightState",
            "vehicle.getCurrentLowBeamLightState", "vehicle.getCurrentRearFogLightState", "vehicle.getCurrentTailLampLightState"), lights.map { it.key }.toSet())
        assertFalse(providers.any { it.key.endsWith("LightSwitch") })
        val battery = groups.single { it.title == "배터리" }.observations
        val fuel = groups.single { it.title == "연료" }.observations
        assertEquals(listOf("vehicle.getEvBatteryStateOfCharge", "vehicle.getCurrentEvEfficiency"), battery.map { it.key })
        assertEquals(listOf("vehicle.getFuelPercent", "vehicle.getCurrentEvEfficiency"), fuel.map { it.key })
        assertSame(battery.last(), fuel.last())
    }
    @Test fun missingNaviPermissionKeepsCoordinateFallbackAvailable() = runTest {
        val context = object : ContextWrapper(null) {
            override fun checkSelfPermission(permission: String): Int = android.content.pm.PackageManager.PERMISSION_DENIED
        }
        val result = readNaviLocation(context)
        assertNull(result.observation)
        assertTrue(result.reason.contains("권한"))
    }
    @Test fun temperatureUnitsAndClimateSwitchesAreReadable() = runTest {
        val temperature = readScalar(CapabilityConfig(AccessType.READ)) { ok, _ -> ok(22.5) }
            .copy(key = "vehicle.getHvacTemperature").withUnit("°C")
        assertEquals(listOf("전체" to "22.5 °C"), temperature.displayLines())
        val circulation = readScalar(CapabilityConfig(AccessType.READ)) { ok, _ -> ok(false) }
            .copy(key = "vehicle.getHvacRecirculationState")
        assertEquals(listOf("전체" to "OFF (꺼짐)"), circulation.displayLines())
    }
    @Test fun writeOnlyCapabilitiesDoNotCallGetter() = runTest {
        val result = readScalar(CapabilityConfig(AccessType.WRITE)) { _, _ -> fail("Getter must not run") }
        assertEquals(Status.UNSUPPORTED, result.status)
    }
    @Test fun partialAreaFailureKeepsSuccessfulValues() = runTest {
        val result = readAreas(CapabilityConfig(AccessType.READ) to listOf("left", "right"), { it }) { area, ok, error ->
            if(area == "left") ok(false) else error(SecurityException())
        }
        assertEquals(Status.UNAVAILABLE, result.status)
        val rows = JSONObject(result.value!!).getJSONArray("entries")
        assertEquals("OK", rows.getJSONObject(0).getString("status"))
        assertFalse(rows.getJSONObject(0).getBoolean("value"))
        assertEquals("DENIED", rows.getJSONObject(1).getString("status"))
    }
    @Test fun missingReplyTimesOutWithoutDiscardingOtherAreas() = runTest {
        val result = readAreas(CapabilityConfig(AccessType.READ) to listOf("left", "right"), { it }) { area, ok, _ ->
            if(area == "left") ok(0)
        }
        val rows = JSONObject(result.value!!).getJSONArray("entries")
        assertEquals(0, rows.getJSONObject(0).getInt("value"))
        assertEquals("TIMEOUT", rows.getJSONObject(1).getString("status"))
    }
    @Test fun nullAndDuplicateCallbacksRemainSafe() = runTest {
        val result = readScalar(CapabilityConfig(AccessType.READ)) { ok, error ->
            ok(null); ok(99); error(IllegalStateException())
        }
        assertEquals(Status.UNAVAILABLE, result.status)
    }

    @Test fun unsupportedSdkPropertyIsNotReportedAsCollectionError() = runTest {
        val observation = readScalar(CapabilityConfig(AccessType.READ)) { _, error ->
            error(IllegalStateException("ERROR_NOT_SUPPORTED_PROPERTY"))
        }
        assertEquals(Status.UNSUPPORTED, observation.status)
    }
}
