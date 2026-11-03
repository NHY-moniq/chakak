package ai.chakak.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ai.chakak.core.*
import java.text.DateFormat
import java.util.Date

@Composable fun ChakakTheme(dark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if(dark) darkColorScheme(background = Color(0xFF131417), surface = Color(0xFF1D1E21), surfaceVariant = Color(0xFF313236))
        else lightColorScheme(background = Color.White, surface = Color(0xFFF7F8FA), surfaceVariant = Color(0xFFEDEEF2))
    MaterialTheme(colorScheme = colors, content = content)
}
@OptIn(ExperimentalLayoutApi::class)
@Composable fun LibraryScreen(records: List<Record>, allowed: Boolean, mock: Boolean,
    capture: () -> Unit = {}, edit: (String, String, Category, String) -> Unit = {_,_,_,_->}, delete: (String) -> Unit = {}, initialSelection: String? = null, busy: Boolean = false, message: String? = null, loaded: Boolean = true) {
    var selectedId by rememberSaveable { mutableStateOf(initialSelection) }
    val drafts = rememberSaveableStateHolder()
    var filter by rememberSaveable { mutableStateOf<Category?>(null) }
    Surface(Modifier.fillMaxSize()) {
        if(!allowed) {
            Column(Modifier.padding(24.dp)) { Text("주차 후 이용할 수 있습니다", style = MaterialTheme.typography.headlineSmall)
                Text("주차 상태와 차량 화면 제한을 확인할 수 없는 경우에도 기록을 열지 않습니다.") }
        } else Column(Modifier.fillMaxSize().safeDrawingPadding().imePadding().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Chakak · 기록 보관함", style = MaterialTheme.typography.headlineMedium)
            Text("위치와 차량 상태를 이 차량에 저장합니다. 공유 차량에서는 다른 탑승자가 기록을 볼 수 있습니다.")
            if(mock) Text("테스트 데이터 · 실제 차량 관측값이 아닙니다", color = MaterialTheme.colorScheme.error)
            Button(onClick = capture, enabled = !busy && loaded, modifier = Modifier.heightIn(min = 64.dp)) { Text(if(busy) "처리 중…" else "현재 상태 기록") }
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = filter == null, onClick = { filter = null }, label = { Text("전체") })
                Category.entries.forEach { category -> FilterChip(selected = filter == category, onClick = { filter = category }, label = { Text(category.label()) }) }
            }
            if(message != null) Text(message)
            if(!loaded) CircularProgressIndicator()
            val selected = records.find { it.id == selectedId }
            BoxWithConstraints(Modifier.weight(1f)) {
                val wide = maxWidth >= 840.dp
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    if(wide || selected == null) LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        if(loaded && records.isEmpty()) item { Text("아직 기록이 없습니다. 사진 없이 위치와 차량 상태를 기록합니다.") }
                        items(records.filter { filter == null || it.category == filter }, key = { it.id }) { record ->
                            Card(onClick = { selectedId = record.id }, modifier = Modifier.fillMaxWidth()) {
                                Column(Modifier.padding(16.dp)) {
                                    Text(record.title.ifBlank { record.category.label() }, style = MaterialTheme.typography.titleLarge)
                                    if(record.source == Source.MOCK) Text("테스트 데이터")
                                    Text(DateFormat.getDateTimeInstance().format(Date(record.createdAt)))
                                    Text(if(record.collecting) "수집 중" else if(record.observations.all { it.status == Status.OK }) "수집 완료" else "일부 정보 없음")
                                }
                            }
                        }
                    }
                    if(selected != null) Box(Modifier.weight(1f)) {
                        drafts.SaveableStateProvider(selected.id) {
                            RecordDetail(selected, Modifier.fillMaxSize(), { selectedId = null }, edit) { delete(selected.id) }
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable private fun RecordDetail(record: Record, modifier: Modifier, back: () -> Unit,
    edit: (String, String, Category, String) -> Unit, delete: () -> Unit) {
    var title by rememberSaveable(record.id) { mutableStateOf(record.title) }
    var memo by rememberSaveable(record.id) { mutableStateOf(record.memo) }
    var category by rememberSaveable(record.id) { mutableStateOf(record.category) }
    var confirm by rememberSaveable(record.id) { mutableStateOf(false) }
    LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { TextButton(onClick = back) { Text("목록") } }
        item { Text("${record.source} · ${DateFormat.getDateTimeInstance().format(Date(record.createdAt))}") }
        item { OutlinedTextField(value = title, onValueChange = { title = it.take(120) }, label = { Text("제목") }, modifier = Modifier.fillMaxWidth()) }
        item { FlowRow { Category.entries.forEach { c -> FilterChip(selected = c == category, onClick = { category = c }, label = { Text(c.label()) }) } } }
        item { OutlinedTextField(value = memo, onValueChange = { memo = it.take(2000) }, label = { Text("메모") }, modifier = Modifier.fillMaxWidth(), minLines = 3) }
        item { Button(onClick = { edit(record.id, title, category, memo) }) { Text("변경 저장") } }
        items(record.observations, key = { it.key }) { observation ->
            Text("${observation.key.fieldLabel()}: ${observation.status.label()}\n${observation.value ?: "관측값 없음"}")
            observation.observedAt?.let { Text("관측 시각: ${DateFormat.getDateTimeInstance().format(Date(it))}") }
        }
        item { Text("수집 시점의 관측값입니다. 진단 결과나 실시간 차량 상태가 아닙니다.") }
        item { OutlinedButton(onClick = { confirm = true }) { Text("기록 삭제") } }
    }
    if(confirm) AlertDialog(onDismissRequest = { confirm = false }, title = { Text("기록을 삭제할까요?") },
        text = { Text("삭제한 기록은 복구할 수 없습니다.") },
        confirmButton = { TextButton(onClick = { confirm = false; delete() }) { Text("삭제") } }, dismissButton = { TextButton(onClick = { confirm = false }) { Text("취소") } })
}
private fun Category.label() = when(this) { Category.VEHICLE_STATUS -> "차량"; Category.PLACE -> "장소"; Category.UNCLASSIFIED -> "미분류" }
@Preview(widthDp = 600, heightDp = 900) @Composable private fun PortraitPreview() { ChakakTheme(false) { LibraryScreen(emptyList(), true, true) } }
@Preview(widthDp = 1200, heightDp = 700) @Composable private fun WideNightPreview() { ChakakTheme(true) { LibraryScreen(listOf(Record("preview", 0, Category.PLACE, Source.MOCK)), true, true) } }
@Preview(widthDp = 600, fontScale = 1.5f) @Composable private fun RestrictedPreview() { ChakakTheme { LibraryScreen(emptyList(), false, false) } }

@Preview(widthDp = 600, heightDp = 500, fontScale = 1.3f) @Composable private fun CompactEditorPreview() {
    ChakakTheme { LibraryScreen(listOf(Record("detail", 0, Category.VEHICLE_STATUS, Source.MOCK, memo = "메모 작성", collecting = false,
        observations = listOf(Observation("location", Status.DENIED), Observation("tires", Status.TIMEOUT)))), true, true, initialSelection = "detail") }
}

private fun String.fieldLabel() = when(this) { "location" -> "위치"; "odometer" -> "누적 주행 거리"; "telltales" -> "경고등 상태"; "tires" -> "타이어 상태"; else -> this }
private fun Status.label() = when(this) {
    Status.PENDING -> "수집 중"
    Status.OK -> "관측값 있음"
    Status.UNAVAILABLE -> "유효한 관측값 없음"
    Status.UNSUPPORTED -> "지원하지 않음"
    Status.DENIED -> "권한 없음"
    Status.TIMEOUT -> "응답 시간 초과"
    Status.ERROR -> "수집 오류"
    Status.INTERRUPTED -> "수집 중단"
}
