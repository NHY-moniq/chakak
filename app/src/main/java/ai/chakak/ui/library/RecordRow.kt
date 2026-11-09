package ai.chakak.ui.library

import ai.chakak.core.Record
import ai.chakak.core.Source
import ai.chakak.ui.component.MockTag
import ai.chakak.ui.component.StatusBadge
import ai.chakak.ui.model.captureSummary
import ai.chakak.ui.model.displayTitle
import ai.chakak.ui.model.formatDateTime
import ai.chakak.ui.model.label
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakShapes
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp

/** 시간순 기록 목록 항목(spec 9.2). 정렬은 저장소 순서를 따르고, 보관함의 머리 영역과 같은 목록에 붙는다. */
fun LazyListScope.recordItems(records: List<Record>, selectedId: String?, onSelect: (String) -> Unit) {
    items(records, key = { it.id }, contentType = { RECORD_CONTENT_TYPE }) { record ->
        RecordRow(record, selected = record.id == selectedId, onClick = { onSelect(record.id) })
    }
}

private const val RECORD_CONTENT_TYPE = "record"

/**
 * 기록 한 행: 이름(비어 있으면 "이름 없는 기록") · 분류와 명령 수신 시각 · 메모 요약 · 수집 요약 배지 · 테스트 데이터 표시.
 * 선택 행은 색 외에도 두꺼운 informative/positive 테두리와 오른쪽 화살표 표시로 구분한다.
 * 누르는 동안은 surface/high 면으로 바뀐다(장식 애니메이션 없음).
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecordRow(record: Record, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val colors = ChaKakTheme.colors
    val shape = ChaKakShapes.buttonMedium
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(rowFill(selected, pressed), shape)
            .border(rowBorderWidth(selected), if (selected) colors.informativePositive else colors.dividerSecondary, shape)
            .selectable(selected = selected, interactionSource = interaction, indication = null, role = Role.Button, onClick = onClick)
            .padding(ChaKakSpacing.panel),
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight),
    ) {
        RowTitle(record, selected)
        Text(
            "${record.category.label()} · ${formatDateTime(record.createdAt)}",
            style = ChaKakTheme.typography.labelMedium,
            color = colors.textTertiary,
        )
        if (record.memo.isNotBlank()) {
            Text(record.memo, style = ChaKakTheme.typography.bodySmall, color = colors.textSecondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.item),
            verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight),
        ) {
            StatusBadge(record.captureSummary())
            if (record.source == Source.MOCK) MockTag()
        }
    }
}

/** 면 색. 대비 테스트를 거친 surface 토큰만 쓴다(텍스트 4.5:1 유지). */
@Composable
private fun rowFill(selected: Boolean, pressed: Boolean): Color = when {
    pressed -> ChaKakTheme.colors.surfaceHigh
    selected -> ChaKakTheme.colors.surfaceAccent
    else -> ChaKakTheme.colors.surfaceBasic
}

private fun rowBorderWidth(selected: Boolean): Dp = if (selected) ChaKakDimens.selectedBorder else ChaKakDimens.dividerThick

/** 제목 + 선택 표시. 선택 표시는 배지와 같은 원 안 글리프라 Dark에서도 글리프 대비가 유지된다. */
@Composable
private fun RowTitle(record: Record, selected: Boolean) {
    val colors = ChaKakTheme.colors
    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline)) {
        Text(
            record.displayTitle(),
            style = ChaKakTheme.typography.titleSmall,
            color = colors.textPrimary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        if (selected) {
            val fill = colors.informativePositive
            Box(Modifier.size(ChaKakDimens.badgeIcon).background(fill, ChaKakShapes.pill), contentAlignment = Alignment.Center) {
                // TBD: Pleos 아이콘 세트 — Material 기본 아이콘 임시 사용
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = colors.contentOn(fill),
                    modifier = Modifier.size(ChaKakDimens.badgeGlyph),
                )
            }
        }
    }
}
