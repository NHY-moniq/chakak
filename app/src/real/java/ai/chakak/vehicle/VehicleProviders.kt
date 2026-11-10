package ai.chakak.vehicle

import android.content.Context
import ai.chakak.core.*
import ai.pleos.playground.vehicle.api.model.area.*

// Explicit read-only bindings checked against Vehicle SDK 2.3.2.
internal fun vehicleProviders(context: Context): List<CaptureProvider> {
    val session = VehicleSession(context)
    return listOf(
        vehicleProvider(context, session, "vehicle.getCurrentBrakePedalStatus", "pleos.car.permission.CAR_DRIVE") { vehicle ->
            val domain = vehicle.getBrake()
            val capability = capability { ok, error -> domain.checkBrakePedalStatusCapabilities(ok, error) }
            readScalar(capability) { ok, error -> domain.getCurrentBrakePedalStatus(ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getDoorLockState", "pleos.car.permission.CAR_DOORS") { vehicle ->
            val domain = vehicle.getDoor()
            val capability = areaCapability<DoorArea> { ok, error ->
                domain.checkDoorLockCapabilities({ config, supported -> ok(config, supported) }, error)
            }
            readAreas(capability, { area -> area.values.joinToString(", ") { it.name } }) { area, ok, error -> domain.getDoorLockState(area, ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getCruiseControlState", "pleos.car.permission.CAR_DRIVE") { vehicle ->
            val domain = vehicle.getDrivingMode()
            val capability = capability { ok, error -> domain.checkCruiseControlStateCapabilities(ok, error) }
            readScalar(capability) { ok, error -> domain.getCruiseControlState(ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getCurrentDrivingModeState", "pleos.car.permission.CAR_DRIVE") { vehicle ->
            val domain = vehicle.getDrivingMode()
            val capability = capability { ok, error -> domain.checkDrivingModeStateCapabilities(ok, error) }
            readScalar(capability) { ok, error -> domain.getCurrentDrivingModeState(ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getCurrentEvEfficiency", "pleos.car.permission.CAR_ENERGY") { vehicle ->
            val domain = vehicle.getEvBattery()
            val capability = capability { ok, error -> domain.checkCurrentEvEfficiencyCapabilities(ok, error) }
            readScalar(capability) { ok, error -> domain.getCurrentEvEfficiency(ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getEvBatteryStateOfCharge", "pleos.car.permission.CAR_ENERGY") { vehicle ->
            val domain = vehicle.getEvBattery()
            val capability = capability { ok, error -> domain.checkEvBatteryStateOfChargeCapabilities(ok, error) }
            readScalar(capability) { ok, error -> domain.getEvBatteryStateOfCharge(ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getFuelPercent", "pleos.car.permission.CAR_ENERGY") { vehicle ->
            val domain = vehicle.getFuel()
            val capability = capability { ok, error -> domain.checkFuelPercentCapabilities(ok, error) }
            readScalar(capability) { ok, error -> domain.getFuelPercent(ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getHvacAcState", "pleos.car.permission.CAR_CLIMATE") { vehicle ->
            val domain = vehicle.getHVAC()
            val capability = areaCapability<SeatArea> { ok, error ->
                domain.checkHvacAcCapabilities({ config, supported -> ok(config, supported) }, error)
            }
            readAreas(capability, { area -> area.values.joinToString(", ") { it.name } }) { area, ok, error -> domain.getHvacAcState(area, ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getHvacRecirculationState", "pleos.car.permission.CAR_CLIMATE") { vehicle ->
            val domain = vehicle.getHVAC()
            val capability = areaCapability<SeatArea> { ok, error ->
                domain.checkHvacRecirculationCapabilities({ config, supported -> ok(config, supported) }, error)
            }
            readAreas(capability, { area -> area.values.joinToString(", ") { it.name } }) { area, ok, error -> domain.getHvacRecirculationState(area, ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getHvacTemperature", "pleos.car.permission.CAR_CLIMATE") { vehicle ->
            val domain = vehicle.getHVAC()
            val capability = areaCapability<SeatArea> { ok, error ->
                domain.checkHvacTemperatureCapabilities({ config, supported, _ -> ok(config, supported.keys.toList()) }, error)
            }
            readAreas(capability, { area -> area.values.joinToString(", ") { it.name } }) { area, ok, error -> domain.getHvacTemperature(area, ai.pleos.playground.vehicle.constant.unit.TemperatureUnit.CELSIUS, ok, error) }.withUnit("°C")
        },
        vehicleProvider(context, session, "vehicle.getHvacTemperatureCurrent", "pleos.car.permission.CAR_CLIMATE") { vehicle ->
            val domain = vehicle.getHVAC()
            val capability = areaCapability<SeatArea> { ok, error ->
                domain.checkHvacTemperatureCurrentCapabilities({ config, supported -> ok(config, supported) }, error)
            }
            readAreas(capability, { area -> area.values.joinToString(", ") { it.name } }) { area, ok, error -> domain.getHvacTemperatureCurrent(area, ai.pleos.playground.vehicle.constant.unit.TemperatureUnit.CELSIUS, ok, error) }.withUnit("°C")
        },
        vehicleProvider(context, session, "vehicle.getCurrentFrontFogLightState", "pleos.car.permission.CAR_LIGHTS") { vehicle ->
            val domain = vehicle.getLight()
            val capability = capability { ok, error -> domain.checkFrontFogLightStateCapabilities({ config, _ -> ok(config) }, error) }
            readScalar(capability) { ok, error -> domain.getCurrentFrontFogLightState(ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getCurrentHighBeamLightState", "pleos.car.permission.CAR_LIGHTS") { vehicle ->
            val domain = vehicle.getLight()
            val capability = capability { ok, error -> domain.checkHighBeamLightStateCapabilities({ config, _ -> ok(config) }, error) }
            readScalar(capability) { ok, error -> domain.getCurrentHighBeamLightState(ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getCurrentLowBeamLightState", "pleos.car.permission.CAR_LIGHTS") { vehicle ->
            val domain = vehicle.getLight()
            val capability = capability { ok, error -> domain.checkLowBeamLightStateCapabilities({ config, _ -> ok(config) }, error) }
            readScalar(capability) { ok, error -> domain.getCurrentLowBeamLightState(ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getCurrentRearFogLightState", "pleos.car.permission.CAR_LIGHTS") { vehicle ->
            val domain = vehicle.getLight()
            val capability = capability { ok, error -> domain.checkRearFogLightStateCapabilities({ config, _ -> ok(config) }, error) }
            readScalar(capability) { ok, error -> domain.getCurrentRearFogLightState(ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getCurrentTailLampLightState", "pleos.car.permission.CAR_LIGHTS") { vehicle ->
            val domain = vehicle.getLight()
            val capability = capability { ok, error -> domain.checkTailLampLightStateCapabilities({ config, _ -> ok(config) }, error) }
            readScalar(capability) { ok, error -> domain.getCurrentTailLampLightState(ok, error) }
        },
        vehicleProvider(context, session, "odometer", "pleos.car.permission.CAR_DRIVE") { vehicle ->
            val domain = vehicle.getOdometer()
            val capability = capability { ok, error -> domain.checkCurrentDistanceDrivenCapabilities(ok, error) }
            readScalar(capability) { ok, error -> domain.getCurrentDistanceDriven(ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getBrakeFluidLevelState", "pleos.car.permission.CAR_SAFETY") { vehicle ->
            val domain = vehicle.getSafety()
            val capability = capability { ok, error -> domain.checkBrakeFluidLevelStateCapabilities({ config, _ -> ok(config) }, error) }
            readScalar(capability) { ok, error -> domain.getBrakeFluidLevelState(ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getEngineOilLevelState", "pleos.car.permission.CAR_SAFETY") { vehicle ->
            val domain = vehicle.getSafety()
            val capability = capability { ok, error -> domain.checkEngineOilLevelStateCapabilities({ config, _ -> ok(config) }, error) }
            readScalar(capability) { ok, error -> domain.getEngineOilLevelState(ok, error) }
        },
        vehicleProvider(context, session, "telltales", "pleos.car.permission.PRIVATE_CAR_SAFETY") { vehicle ->
            val domain = vehicle.getSafety()
            val capability = capability { ok, error -> domain.checkTelltalesCapabilities(ok, error) }
            readScalar(capability) { ok, error -> domain.getTelltalesInfo(ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getTransmissionOilLevelState", "pleos.car.permission.CAR_SAFETY") { vehicle ->
            val domain = vehicle.getSafety()
            val capability = capability { ok, error -> domain.checkTransmissionOilLevelStateCapabilities({ config, _ -> ok(config) }, error) }
            readScalar(capability) { ok, error -> domain.getTransmissionOilLevelState(ok, error) }
        },
        vehicleProvider(context, session, "tires", "pleos.car.permission.CAR_SAFETY") { vehicle ->
            val domain = vehicle.getTire()
            val capability = areaCapability<WheelArea> { ok, error ->
                domain.checkTirePressureCapabilities({ config, supported -> ok(config, supported) }) { failure ->
                    android.util.Log.w("ChakakTire", "Tire capability failed: ${failure.javaClass.simpleName}: ${failure.message}")
                    error(failure)
                }
            }
            readAreas(capability, { area -> area.values.joinToString(", ") { it.name } }) { area, ok, error -> domain.getTirePressure(area, ok) { failure ->
                android.util.Log.w("ChakakTire", "Tire pressure read failed: ${failure.javaClass.simpleName}: ${failure.message}")
                error(failure)
            } }.withUnit("kPa")
        },
        vehicleProvider(context, session, "vehicle.getCurrentWindowLockState", "pleos.car.permission.CAR_WINDOWS") { vehicle ->
            val domain = vehicle.getWindow()
            val capability = areaCapability<WindowArea> { ok, error ->
                domain.checkWindowLockCapabilities({ config, supported -> ok(config, supported) }, error)
            }
            readAreas(capability, { area -> area.values.joinToString(", ") { it.name } }) { area, ok, error -> domain.getCurrentWindowLockState(area, ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getCurrentWindowPosition", "pleos.car.permission.CAR_WINDOWS") { vehicle ->
            val domain = vehicle.getWindow()
            val capability = areaCapability<WindowArea> { ok, error ->
                domain.checkWindowPositionCapabilities({ config, supported -> ok(config, supported.keys.toList()) }, error)
            }
            readAreas(capability, { area -> area.values.joinToString(", ") { it.name } }) { area, ok, error -> domain.getCurrentWindowPosition(area, ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getCurrentWiperState", "pleos.car.permission.CAR_WINDOWS") { vehicle ->
            val domain = vehicle.getWiper()
            val capability = areaCapability<WindowArea> { ok, error ->
                domain.checkWiperStateCapabilities({ config, supported -> ok(config, supported.keys.toList()) }, error)
            }
            readAreas(capability, { area -> area.values.joinToString(", ") { it.name } }) { area, ok, error -> domain.getCurrentWiperState(area, ok, error) }
        },
        vehicleProvider(context, session, "vehicle.getCurrentWiperSwitch", "pleos.car.permission.CAR_WINDOWS") { vehicle ->
            val domain = vehicle.getWiper()
            val capability = areaCapability<WindowArea> { ok, error ->
                domain.checkWiperSwitchCapabilities({ config, supported -> ok(config, supported.keys.toList()) }, error)
            }
            readAreas(capability, { area -> area.values.joinToString(", ") { it.name } }) { area, ok, error -> domain.getCurrentWiperSwitch(area, ok, error) }
        },
    )
}
