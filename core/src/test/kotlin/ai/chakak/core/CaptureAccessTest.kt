package ai.chakak.core

import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.*
import org.junit.Test

class CaptureAccessTest {
    @Test fun approvedDrivingAllowsVoiceButNeverTouch() {
        assertEquals(CaptureAccess(false, true), captureAccess(DrivingState.DRIVE, true, false, true))
    }
    @Test fun unapprovedDrivingCannotCapture() {
        assertEquals(CaptureAccess(false, false), captureAccess(DrivingState.DRIVE, true, false, false))
    }
    @Test fun unknownAndReverseFailClosedEvenWithApproval() {
        for(state in listOf(DrivingState.UNKNOWN, DrivingState.REVERSE, DrivingState.OTHER))
            assertEquals(CaptureAccess(false, false), captureAccess(state, true, true, true))
        assertEquals(CaptureAccess(false, false), captureAccess(DrivingState.DRIVE, false, false, true))
    }
    @Test fun parkedRequiresUnrestrictedUiUntilFeaturePolicySaysOtherwise() {
        assertEquals(CaptureAccess(true, true), captureAccess(DrivingState.PARKED, true, true, false))
        assertEquals(CaptureAccess(false, false), captureAccess(DrivingState.PARKED, true, false, true))
    }
    @Test fun emulatorOptInIsRestrictedToEmulatorHardware() {
        assertTrue(inMotionVoiceEnabled(false, true, true))
        assertFalse(inMotionVoiceEnabled(false, true, false))
        assertFalse(inMotionVoiceEnabled(false, false, true))
        assertTrue(inMotionVoiceEnabled(true, false, false))
        val enabled = inMotionVoiceEnabled(false, true, true)
        assertEquals(CaptureAccess(false, true), captureAccess(DrivingState.DRIVE, true, false, enabled))
    }

}
