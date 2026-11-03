package ai.chakak.core

/** UNKNOWN includes disconnected services, unreadable gear and missing UX restriction data. */
enum class DrivingState { UNKNOWN, PARKED, DRIVE, REVERSE, OTHER }
data class CaptureAccess(val touch: Boolean, val voice: Boolean)
fun captureAccess(state: DrivingState, uxKnown: Boolean, unrestricted: Boolean,
                  inMotionVoiceApproved: Boolean): CaptureAccess {
    val touch = uxKnown && state == DrivingState.PARKED && unrestricted
    // This feature gate is resolved from deployment approval or debug-emulator opt-in. It never unlocks UI.
    val voice = touch || (uxKnown && state == DrivingState.DRIVE && inMotionVoiceApproved)
    return CaptureAccess(touch, voice)
}

/** Development opt-in is effective only on a debug emulator, never on a production vehicle. */
fun inMotionVoiceEnabled(approved: Boolean, emulatorOptIn: Boolean, debugBuild: Boolean, emulator: Boolean): Boolean =
    approved || (emulatorOptIn && debugBuild && emulator)
