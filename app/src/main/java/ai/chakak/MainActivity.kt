package ai.chakak

import android.os.Bundle
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ai.chakak.core.*
import ai.chakak.ui.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import java.util.UUID

class LibraryModel(val app: ChakakApp) : ViewModel() {
    val busy = MutableStateFlow(false)
    val message = MutableStateFlow<String?>(null)
    val loaded = MutableStateFlow(false)
    val records = app.store.records.onEach { loaded.value = true }.catch {
        message.value = "기록을 읽지 못했습니다. 앱을 다시 열어 주세요."
        loaded.value = true
        emit(emptyList())
    }
    private fun mutate(successMessage: String = "저장했습니다.", block: suspend () -> Unit) {
        if(busy.value) return
        busy.value = true
        message.value = null
        viewModelScope.launch {
            try {
                app.ready.await()
                if(!app.platform.touchAllowed.value) { message.value = "주차 상태를 확인해 주세요."; return@launch }
                block()
                message.value = successMessage
            } catch (e: CancellationException) { throw e }
            catch (_: Exception) { message.value = "처리하지 못했습니다. 기록을 확인한 뒤 다시 시도해 주세요." }
            finally { busy.value = false }
        }
    }
    fun capture() = mutate { app.engine.capture(UUID.randomUUID().toString(), Category.UNCLASSIFIED) }
    fun edit(id: String, title: String, category: Category, memo: String) = mutate { app.store.edit(id, title, category, memo) }
    fun delete(id: String) = mutate("삭제했습니다.") { app.store.delete(id) }
    fun deleteMany(ids: List<String>) = mutate("선택한 기록을 삭제했습니다.") { app.store.deleteMany(ids) }

}
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
        super.onCreate(savedInstanceState)
        val model = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST") override fun <T : ViewModel> create(modelClass: Class<T>): T = LibraryModel(app) as T
        })[LibraryModel::class.java]
        setContent {
            val rows = model.records.collectAsStateWithLifecycle(emptyList()).value
            val allowed = app.platform.touchAllowed.collectAsStateWithLifecycle().value
            val busy = model.busy.collectAsStateWithLifecycle().value
            val message = model.message.collectAsStateWithLifecycle().value
            val loaded = model.loaded.collectAsStateWithLifecycle().value
            ChakakTheme { LibraryScreen(rows, allowed, app.platform.source == Source.MOCK, model::capture, model::edit, model::delete,
                busy = busy, message = message, loaded = loaded, deleteMany = model::deleteMany,
                needsLocationPermission = needsLocationPermission.value,
                requestLocationPermission = { if(app.platform.touchAllowed.value) requestLocation.launch(missingPermissions()) }) }
        }
    }
}
