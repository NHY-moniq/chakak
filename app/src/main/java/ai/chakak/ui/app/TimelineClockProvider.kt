package ai.chakak.ui.app

import ai.chakak.ui.model.LocalTimelineClock
import ai.chakak.ui.model.TimelineClock
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

/** 날짜가 바뀌었을 수 있는 시스템 방송 */
private val clockChangeActions = listOf(Intent.ACTION_DATE_CHANGED, Intent.ACTION_TIME_CHANGED, Intent.ACTION_TIMEZONE_CHANGED)

/**
 * 앱 루트에서 [LocalTimelineClock]을 제공한다. 자정(날짜 변경)·시각 변경·시간대 변경 방송을 받으면
 * 새 [TimelineClock]으로 바꿔 오늘/어제 머리와 시각 표시가 다시 그려지게 한다.
 * 같은 날이면 값이 같아 다시 그리지 않는다. 화면을 벗어나면(dispose) 수신기를 해제한다.
 */
@Composable
fun ProvideTimelineClock(content: @Composable () -> Unit) {
    val clock = rememberTimelineClock()
    CompositionLocalProvider(LocalTimelineClock provides clock, content = content)
}

@Composable
private fun rememberTimelineClock(): TimelineClock {
    val context = LocalContext.current
    var clock by remember { mutableStateOf(TimelineClock.system()) }
    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                clock = TimelineClock.system()
            }
        }
        val filter = IntentFilter().apply { clockChangeActions.forEach(::addAction) }
        // 시스템 방송만 받으므로 외부 앱에 열지 않는다
        ContextCompat.registerReceiver(context, receiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED)
        // 등록 전(백그라운드 등)에 날짜가 바뀌었을 수 있어 한 번 다시 읽는다
        clock = TimelineClock.system()
        onDispose { context.unregisterReceiver(receiver) }
    }
    return clock
}
