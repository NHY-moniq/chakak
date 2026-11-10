package ai.chakak.ui.detail

import ai.chakak.core.Observation
import ai.chakak.core.Status
import ai.chakak.ui.component.StatusBadge
import ai.chakak.ui.component.dashedBorder
import ai.chakak.ui.model.isLegacyTireThreshold
import ai.chakak.ui.model.currentTimelineClock
import ai.chakak.ui.model.TIRES_TITLE
import ai.chakak.ui.model.Wheel
import ai.chakak.ui.model.WheelDisplay
import ai.chakak.ui.model.label
import ai.chakak.ui.model.tireWheels
import ai.chakak.ui.model.tiresContentDescription
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import ai.chakak.ui.theme.JournalDimens
import ai.chakak.ui.theme.JournalShapes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

/**
 * 타이어 패널(시안 A'): 위에서 본 차 그림 + 바퀴별 값 타일 2×2.
 * 값이 없는 바퀴는 그림에서 점선 바퀴, 타일에서 점선 테두리 + 상태 배지(아이콘 + 글)로 보여준다.
 * [compact]면 그림과 타일을 세로로 쌓는다. 형식을 모르는 값은 원문을 그대로 보여준다.
 * [observed]는 이 항목의 관측 시각 줄이다(타일 아래).
 */
@Composable
fun TirePanel(observation: Observation?, compact: Boolean, modifier: Modifier = Modifier, observed: String? = null) {
    val locale = currentTimelineClock().locale
    val wheels = remember(observation, locale) {
        observation?.let { tireWheels(it, locale) } ?: Wheel.entries.map {
            WheelDisplay(it, null, if (observation == null) Status.UNAVAILABLE else Status.ERROR)
        }
    }
    val panel = modifier.fillMaxWidth().background(ChaKakTheme.colors.backgroundPrimary, JournalShapes.panel).padding(ChaKakSpacing.panel)
    val content: @Composable (Modifier) -> Unit = { m ->
        Column(m, verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
            SectionLabel(TIRES_TITLE)
            WheelGrid(wheels)
            if (observation?.isLegacyTireThreshold() == true) {
                Text("이전 기록에는 저압 경고 기준값이 저장되었습니다. 실제 공기압은 새로 기록해 주세요.",
                    style = ChaKakTheme.typography.labelMedium, color = ChaKakTheme.colors.textTertiary)
            }
            if (observation?.isLegacyTireThreshold() != true && observation?.let { ai.chakak.ui.model.parseTires(it.value) }?.readings?.values?.any { it != null && it.unit == null } == true) {
                Text(ai.chakak.ui.model.UNIT_NOT_PROVIDED, style = ChaKakTheme.typography.labelMedium,
                    color = ChaKakTheme.colors.textTertiary)
            }
            ObservedTime(observed)
        }
    }
    if (compact) {
        Column(panel, verticalArrangement = Arrangement.spacedBy(JournalDimens.detailGap), horizontalAlignment = Alignment.CenterHorizontally) {
            TireDiagram(wheels)
            content(Modifier.fillMaxWidth())
        }
    } else {
        Row(panel, horizontalArrangement = Arrangement.spacedBy(JournalDimens.detailGap), verticalAlignment = Alignment.CenterVertically) {
            TireDiagram(wheels)
            content(Modifier.weight(1f))
        }
    }
}

/** 차체와 네 바퀴. 접근성 서비스에는 네 값을 한 문장으로 읽어 준다 */
@Composable
private fun TireDiagram(wheels: List<WheelDisplay>) {
    val colors = ChaKakTheme.colors
    val description = tiresContentDescription(wheels)
    Box(
        Modifier
            .size(JournalDimens.tireDiagramWidth, JournalDimens.tireDiagramHeight)
            .clearAndSetSemantics { contentDescription = description },
    ) {
        Box(
            Modifier
                .align(Alignment.Center)
                .size(JournalDimens.carBodyWidth, JournalDimens.carBodyHeight)
                .background(colors.backgroundSecondary, JournalShapes.carBody)
                .border(JournalDimens.carBodyBorder, colors.dividerTertiary, JournalShapes.carBody),
        )
        wheels.forEach { WheelShape(it) }
    }
}

@Composable
private fun BoxScope.WheelShape(display: WheelDisplay) {
    val colors = ChaKakTheme.colors
    val front = display.wheel == Wheel.FrontLeft || display.wheel == Wheel.FrontRight
    val left = display.wheel == Wheel.FrontLeft || display.wheel == Wheel.RearLeft
    val alignment = when {
        front && left -> Alignment.TopStart
        front -> Alignment.TopEnd
        left -> Alignment.BottomStart
        else -> Alignment.BottomEnd
    }
    val base = Modifier
        .align(alignment)
        .padding(horizontal = JournalDimens.wheelInsetHorizontal, vertical = JournalDimens.wheelInsetVertical)
        .size(JournalDimens.wheelWidth, JournalDimens.wheelHeight)
    val drawn = if (display.missing) {
        base.dashedBorder(ChaKakDimens.dividerThick, colors.iconTertiary, JournalDimens.wheelRadius)
    } else {
        base.background(colors.iconSecondary, JournalShapes.wheel)
    }
    Box(drawn)
}

/** 2×2: 앞 왼쪽 · 앞 오른쪽 / 뒤 왼쪽 · 뒤 오른쪽 */
@Composable
private fun WheelGrid(wheels: List<WheelDisplay>) {
    Column(verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline)) {
        wheels.chunked(2).forEach { pair ->
            Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline)) {
                pair.forEach { WheelTile(it, Modifier.weight(1f).fillMaxHeight()) }
            }
        }
    }
}

@Composable
private fun WheelTile(display: WheelDisplay, modifier: Modifier) {
    val colors = ChaKakTheme.colors
    val surface = if (display.missing) {
        Modifier.dashedBorder(ChaKakDimens.dividerThick, colors.iconTertiary, JournalDimens.tileRadius)
    } else {
        Modifier.background(colors.backgroundSecondary, JournalShapes.tile)
    }
    Column(
        modifier = modifier
            .then(surface)
            .padding(horizontal = JournalDimens.tireTilePaddingHorizontal, vertical = JournalDimens.tireTilePaddingVertical)
            .semantics(mergeDescendants = true) {},
        verticalArrangement = Arrangement.spacedBy(JournalDimens.cardTextGap),
    ) {
        Text(display.wheel.label(), style = ChaKakTheme.typography.labelMedium, color = colors.textTertiary)
        display.value?.let { Text(it, style = ChaKakTheme.typography.titleSmall, color = colors.textPrimary) }
        display.status?.let { StatusBadge(it) }
    }
}
