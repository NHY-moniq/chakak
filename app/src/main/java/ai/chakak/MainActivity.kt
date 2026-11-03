package ai.chakak

import android.os.Bundle
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
    private fun mutate(block: suspend () -> Unit) {
        if(busy.value) return
        busy.value = true
        message.value = null
        viewModelScope.launch {
            try {
                app.ready.await()
                if(!app.platform.touchAllowed.value) { message.value = "주차 상태를 확인해 주세요."; return@launch }
                block()
                message.value = "저장했습니다."
            } catch (e: CancellationException) { throw e }
            catch (_: Exception) { message.value = "처리하지 못했습니다. 기록을 확인한 뒤 다시 시도해 주세요." }
            finally { busy.value = false }
        }
    }
    fun capture() = mutate { app.engine.capture(UUID.randomUUID().toString(), Category.UNCLASSIFIED) }
    fun edit(id: String, title: String, category: Category, memo: String) = mutate { app.store.edit(id, title, category, memo) }
    fun delete(id: String) = mutate { app.store.delete(id) }

}
class MainActivity : ComponentActivity() {
    private val app get() = application as ChakakApp
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
                busy = busy, message = message, loaded = loaded) }
        }
    }
}
