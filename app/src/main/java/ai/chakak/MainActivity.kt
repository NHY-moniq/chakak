package ai.chakak

import android.os.Bundle
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ai.chakak.core.Source
import ai.chakak.ui.LibraryViewModel
import ai.chakak.ui.app.AppWindowScaffold
import ai.chakak.ui.app.LibraryCallbacks
import ai.chakak.ui.app.ProvideTimelineClock
import ai.chakak.ui.theme.ChaKakTheme

class MainActivity : ComponentActivity() {
    private val app get() = application as ChakakApp
    private val capturePermissions = arrayOf(
        "CAR_CLIMATE", "CAR_DOORS", "CAR_DRIVE", "CAR_ENERGY", "CAR_LIGHTS",
        "CAR_SAFETY", "CAR_WINDOWS", "PRIVATE_CAR_SAFETY", "NAVI_ROUTE", "FUSED_LOCATION"
    ).map { "pleos.car.permission.$it" }.toTypedArray()
    private fun missingPermissions() = capturePermissions.filter { checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }.toTypedArray()
    private val needsLocationPermission = mutableStateOf(false)
    private val requestLocation = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        refreshLocationPermission()
        android.widget.Toast.makeText(this, if(!needsLocationPermission.value) "기록 권한을 허용했습니다. 새로 기록해 주세요."
            else "일부 권한이 허용되지 않았습니다. 허용된 항목만 수집합니다.", android.widget.Toast.LENGTH_LONG).show()
    }
    private fun refreshLocationPermission() {
        needsLocationPermission.value = app.platform.source == Source.REAL && missingPermissions().isNotEmpty()
    }
    override fun onResume() { super.onResume(); refreshLocationPermission() }
    override fun onStart() { super.onStart(); app.platform.start() }
    override fun onStop() { app.platform.stop(); super.onStop() }
    override fun onCreate(savedInstanceState: Bundle?) {
        // 시스템 바·IME insets를 Compose가 직접 처리한다(AppWindowScaffold의 safeDrawing).
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val model = ViewModelProvider(this, LibraryViewModel.Factory(app))[LibraryViewModel::class.java]
        val callbacks = LibraryCallbacks(
            capture = model::capture, edit = model::edit, delete = model::delete,
            deleteMany = model::deleteMany,
            retry = model::retry, consumeOutcome = model::consumeOutcome,
        )
        val mock = app.platform.source == Source.MOCK
        setContent {
            val state by model.uiState.collectAsStateWithLifecycle()
            val actions by model.actions.collectAsStateWithLifecycle()
            val allowed by app.platform.touchAllowed.collectAsStateWithLifecycle()
            ChaKakTheme { ProvideTimelineClock { AppWindowScaffold(state, actions, allowed, mock, callbacks,
                needsCapturePermission = needsLocationPermission.value,
                requestCapturePermission = { if (app.platform.touchAllowed.value) requestLocation.launch(missingPermissions()) }) } }
        }
    }
}
