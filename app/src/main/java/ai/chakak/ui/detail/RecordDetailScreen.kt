package ai.chakak.ui.detail

import ai.chakak.core.Category
import ai.chakak.core.Record
import ai.chakak.core.Source
import ai.chakak.ui.component.ButtonSize
import ai.chakak.ui.component.ButtonVariant
import ai.chakak.ui.component.ChaKakButton
import ai.chakak.ui.component.MockBanner
import ai.chakak.ui.component.ScreenTopBar
import ai.chakak.ui.theme.ChaKakSpacing
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/** 위치 관측 항목 키(Platform providers와 같은 값) */
const val LOCATION_KEY = "location"

/**
 * 기록 상세(읽기 전용, spec 9.3·9.4). 순서:
 * 테스트 데이터 배너 → 요약 → 저장 위치 → 차량 상태 → 운전자 메모 → 편집·삭제.
 * 동작 버튼은 스크롤과 관계없이 하단에 고정한다.
 * [onBack]이 null이면(두 열) 목록 버튼을 숨긴다.
 */
@Composable
fun RecordDetailScreen(
    record: Record,
    onBack: (() -> Unit)?,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    actionsEnabled: Boolean,
    modifier: Modifier = Modifier,
    showMockBanner: Boolean = true,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
        ScreenTopBar(title = "기록 상세", backLabel = "목록", onBack = onBack)
        DetailBody(record, showMockBanner, Modifier.weight(1f).fillMaxWidth())
        DetailActions(onEdit, onDelete, actionsEnabled)
    }
}

@Composable
private fun DetailBody(record: Record, showMockBanner: Boolean, modifier: Modifier) {
    val location = record.observations.firstOrNull { it.key == LOCATION_KEY }
    val isPlace = record.category == Category.PLACE
    val showLocation = location != null || isPlace
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()).padding(bottom = ChaKakSpacing.item),
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.section),
    ) {
        if (showMockBanner && record.source == Source.MOCK) MockBanner()
        CaptureSummarySection(record)
        if (showLocation) LocationSection(location, emphasized = isPlace)
        VehicleObservationList(record.observations.filter { !showLocation || it.key != LOCATION_KEY })
        MemoSection(record.memo)
    }
}

@Composable
private fun DetailActions(onEdit: () -> Unit, onDelete: () -> Unit, enabled: Boolean) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
        ChaKakButton(
            text = "삭제",
            onClick = onDelete,
            modifier = Modifier.weight(1f),
            variant = ButtonVariant.Destructive,
            size = ButtonSize.Medium,
            icon = Icons.Filled.Delete,
            enabled = enabled,
        )
        ChaKakButton(
            text = "편집",
            onClick = onEdit,
            modifier = Modifier.weight(1f),
            variant = ButtonVariant.Filled,
            size = ButtonSize.Medium,
            icon = Icons.Filled.Edit,
            enabled = enabled,
        )
    }
}
