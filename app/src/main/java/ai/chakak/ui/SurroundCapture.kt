package ai.chakak.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ai.chakak.core.Observation
import ai.chakak.core.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

// Future SDK adapter contract: persist a local image, then save {"uri":"content://..."}
// under this observation key. No camera API or fabricated capture is invoked by this UI.
internal const val SURROUND_CAPTURE_KEY = "surroundCapture"

private suspend fun loadCapture(context: Context, value: String?): Bitmap? = withContext(Dispatchers.IO) {
    runCatching {
        val uri = Uri.parse(JSONObject(value ?: "{}").getString("uri"))
        require(uri.scheme == "content" || uri.scheme == "file")
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        context.contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, bounds) }
        require(bounds.outWidth > 0 && bounds.outHeight > 0)
        val options = BitmapFactory.Options()
        while (bounds.outWidth / options.inSampleSize > 2048 || bounds.outHeight / options.inSampleSize > 2048) {
            options.inSampleSize *= 2
        }
        context.contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, options) }
    }.getOrNull()
}

@Composable internal fun SurroundCaptureCard(observation: Observation?) {
    val context = LocalContext.current.applicationContext
    var loading by remember(observation) { mutableStateOf(observation?.status == Status.OK) }
    val bitmap by produceState<Bitmap?>(null, observation) {
        value = null
        loading = observation?.status == Status.OK
        if (observation?.status == Status.OK) value = loadCapture(context, observation.value)
        loading = false
    }
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("서라운드뷰 캡처", style = MaterialTheme.typography.titleMedium)
            val image = bitmap
            if (image != null) {
                Image(image.asImageBitmap(), contentDescription = "이 기록에 저장된 서라운드뷰 이미지",
                    modifier = Modifier.fillMaxWidth().heightIn(max = 360.dp), contentScale = ContentScale.Fit)
                observation?.observedAt?.let {
                    Text("캡처 시각: ${formatRecordTime(it)}", style = MaterialTheme.typography.bodySmall)
                }
            } else {
                val message = when {
                    loading -> "이미지를 불러오는 중입니다…"
                    observation == null -> "저장된 캡처 이미지가 없습니다."
                    observation.status == Status.PENDING -> "이미지를 캡처하는 중입니다…"
                    observation.status == Status.DENIED -> "이미지 캡처 권한이 없습니다."
                    observation.status == Status.UNSUPPORTED -> "서라운드뷰 캡처를 지원하지 않습니다."
                    observation.status == Status.OK -> "저장된 이미지를 불러올 수 없습니다."
                    else -> "캡처 이미지를 저장하지 못했습니다."
                }
                Text(message)
            }
        }
    }
}
