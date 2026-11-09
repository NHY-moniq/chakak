package ai.chakak.ui.detail

import ai.chakak.core.Category
import ai.chakak.core.Record
import ai.chakak.ui.component.StatusBadge
import ai.chakak.ui.model.currentTimelineClock
import ai.chakak.ui.model.ObservationSummary
import ai.chakak.ui.model.summaryTiles
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import ai.chakak.ui.theme.JournalDimens
import ai.chakak.ui.theme.JournalShapes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics

const val OTHER_OBSERVATIONS_LABEL = "함께 기록된 차량 정보"
private const val WIDE_SUMMARY_COLUMNS = 3

/**
 * 장소·미분류 상세 본문(시안 A Main): 머리 → 위치 패널 → 운전자 메모 → 나머지 관측 요약.
 * 미분류([Category.UNCLASSIFIED])는 같은 배치에 위치 패널 강조만 뺀다.
 */
@Composable
fun PlaceDetailBody(record: Record, compact: Boolean) {
    val clock = currentTimelineClock()
    val tiles = remember(record, clock) { record.summaryTiles(clock) }
    PlaceHeading(record)
    LocationPanel(rememberLocationDisplay(record), emphasized = record.category == Category.PLACE)
    MemoBlock(record.memo)
    if (tiles.isNotEmpty()) {
        Column(verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline)) {
            SectionLabel(OTHER_OBSERVATIONS_LABEL)
            SummaryGrid(tiles, columns = if (compact) 1 else WIDE_SUMMARY_COLUMNS)
        }
    }
}

/** 요약 타일 격자. 마지막 줄이 모자라면 빈 칸으로 폭을 맞춘다 */
@Composable
fun SummaryGrid(tiles: List<ObservationSummary>, columns: Int, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
        tiles.chunked(columns).forEach { row ->
            Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
                row.forEach { SummaryTile(it, Modifier.weight(1f).fillMaxHeight()) }
                repeat(columns - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

/** 라벨 + 값(Title Small) · 보조 줄(단위 정보 없음) · 상태 배지 · 관측 시각. 값과 상태가 함께 있을 수 있다(일부 바퀴만 값) */
@Composable
private fun SummaryTile(tile: ObservationSummary, modifier: Modifier) {
    val colors = ChaKakTheme.colors
    Column(
        modifier = modifier
            .border(ChaKakDimens.dividerThick, colors.dividerSecondary, JournalShapes.card)
            .padding(JournalDimens.cardPadding)
            .semantics(mergeDescendants = true) {},
        verticalArrangement = Arrangement.spacedBy(JournalDimens.cardTextGap),
    ) {
        Text(tile.label, style = ChaKakTheme.typography.labelMedium, color = colors.textTertiary)
        tile.value?.let { Text(it, style = ChaKakTheme.typography.titleSmall, color = colors.textPrimary) }
        tile.note?.let { Text(it, style = ChaKakTheme.typography.labelMedium, color = colors.textTertiary) }
        tile.status?.let { StatusBadge(it, Modifier.padding(top = JournalDimens.cardTextGap)) }
        ObservedTime(tile.observed)
    }
}
