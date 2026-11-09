package ai.chakak

import android.os.Bundle
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
import ai.chakak.ui.theme.ChaKakTheme

class MainActivity : ComponentActivity() {
    private val app get() = application as ChakakApp
    override fun onStart() { super.onStart(); app.platform.start() }
    override fun onStop() { app.platform.stop(); super.onStop() }
    override fun onCreate(savedInstanceState: Bundle?) {
        // 시스템 바·IME insets를 Compose가 직접 처리한다(AppWindowScaffold의 safeDrawing).
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val model = ViewModelProvider(this, LibraryViewModel.Factory(app))[LibraryViewModel::class.java]
        val callbacks = LibraryCallbacks(
            capture = model::capture, edit = model::edit, delete = model::delete,
            retry = model::retry, consumeOutcome = model::consumeOutcome,
        )
        val mock = app.platform.source == Source.MOCK
        setContent {
            val state by model.uiState.collectAsStateWithLifecycle()
            val actions by model.actions.collectAsStateWithLifecycle()
            val allowed by app.platform.touchAllowed.collectAsStateWithLifecycle()
            ChaKakTheme { AppWindowScaffold(state, actions, allowed, mock, callbacks) }
        }
    }
}
