package ai.chakak.ui.library

import ai.chakak.core.Record
import ai.chakak.core.Source
import ai.chakak.ui.component.CategoryIconTile
import ai.chakak.ui.component.MockTag
import ai.chakak.ui.component.StatusBadge
import ai.chakak.ui.model.DayGroup
import ai.chakak.ui.model.currentTimelineClock
import ai.chakak.ui.model.captureSummary
import ai.chakak.ui.model.displayTitle
import ai.chakak.ui.model.formatClockTime
import ai.chakak.ui.model.rowMetaLine
import ai.chakak.ui.model.showsStatusBadge
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import ai.chakak.ui.theme.JournalDimens
import ai.chakak.ui.theme.JournalShapes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

private const val RECORD_CONTENT_TYPE = "record"
private const val DAY_CONTENT_TYPE = "day"

/** 날짜별 타임라인(시안 A). 날짜 머리 아래에 그날 기록을 저장소 순서대로 붙인다. */
fun LazyListScope.timelineItems(groups: List<DayGroup>, selectedId: String?, onSelect: (String) -> Unit, checked: List<String> = emptyList(), onCheck: ((String) -> Unit)? = null, enabled: Boolean = true) {
    groups.forEach { group ->
        item(key = "day:${group.date}", contentType = DAY_CONTENT_TYPE) { DayHeader(group.header) }
        items(group.records, key = { it.id }, contentType = { RECORD_CONTENT_TYPE }) { record ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (onCheck != null) androidx.compose.material3.Checkbox(
                    checked = record.id in checked, onCheckedChange = { onCheck(record.id) }, enabled = enabled,
                    modifier = Modifier.semantics { contentDescription = "${record.displayTitle()} 선택" },
                )
                RecordRow(record, selected = record.id == selectedId, onClick = { onSelect(record.id) }, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun DayHeader(text: String) {
    Text(
        text,
        style = ChaKakTheme.typography.labelMediumStrong,
        color = ChaKakTheme.colors.textSecondary,
        modifier = Modifier.padding(top = ChaKakSpacing.tight).semantics { heading() },
    )
}

/**
 * 타임라인 한 행: 시각 열 · 세로 레일 · 기록 카드.
 * 선택은 색 외에 레일 강조 + 카드 테두리 두께(2 → 4)로 구분한다. 누르는 동안 카드 면이 surface/high로 바뀐다.
 * 행 전체가 눌림 영역이다(시각 열 포함).
 */
@Composable
fun RecordRow(record: Record, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val clock = currentTimelineClock()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .selectable(selected = selected, interactionSource = interaction, indication = null, role = Role.Button, onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(JournalDimens.timelineGap),
    ) {
        Text(
            formatClockTime(record.createdAt, clock),
            style = ChaKakTheme.typography.labelMedium,
            color = ChaKakTheme.colors.textTertiary,
            textAlign = TextAlign.End,
            modifier = Modifier.width(JournalDimens.timeColumnWidth).padding(top = JournalDimens.timeColumnTopPadding),
        )
        Rail(selected)
        RecordCard(record, selected, pressed, Modifier.weight(1f))
    }
}

@Composable
private fun Rail(selected: Boolean) {
    val colors = ChaKakTheme.colors
    Box(
        Modifier
            .width(JournalDimens.rail)
            .fillMaxHeight()
            .background(if (selected) colors.brand.accentDeep else colors.dividerSecondary, JournalShapes.rail),
    )
}

@Composable
private fun RecordCard(record: Record, selected: Boolean, pressed: Boolean, modifier: Modifier) {
    val colors = ChaKakTheme.colors
    val shape = JournalShapes.card
    Row(
        modifier = modifier
            .background(if (pressed) colors.surfaceHigh else colors.surfaceBasic, shape)
            .border(
                if (selected) ChaKakDimens.selectedBorder else ChaKakDimens.dividerThick,
                if (selected) colors.brand.accentDeep else colors.dividerSecondary,
                shape,
            )
            .padding(JournalDimens.cardPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(JournalDimens.timelineGap),
    ) {
        CategoryIconTile(record.category)
        CardText(record, Modifier.weight(1f))
    }
}

/** 제목 · 메타 한 줄 · (수집 완료가 아니면) 상태 배지 · (Mock이면) 테스트 데이터 표시 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CardText(record: Record, modifier: Modifier) {
    val colors = ChaKakTheme.colors
    val meta = remember(record) { record.rowMetaLine() }
    val summary = remember(record) { record.captureSummary().takeIf { record.showsStatusBadge() } }
    Column(modifier, verticalArrangement = Arrangement.spacedBy(JournalDimens.cardTextGap)) {
        Text(
            record.displayTitle(),
            style = ChaKakTheme.typography.bodyLargeStrong,
            color = colors.textPrimary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            meta,
            style = ChaKakTheme.typography.labelMedium,
            color = colors.textSecondary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        val mock = record.source == Source.MOCK
        if (summary != null || mock) {
            FlowRow(
                modifier = Modifier.padding(top = ChaKakSpacing.tight),
                horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.item),
                verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight),
            ) {
                summary?.let { StatusBadge(it) }
                if (mock) MockTag()
            }
        }
    }
}
