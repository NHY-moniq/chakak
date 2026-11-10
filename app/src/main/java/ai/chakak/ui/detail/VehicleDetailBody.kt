package ai.chakak.ui.detail

import ai.chakak.core.Observation
import ai.chakak.core.Record
import ai.chakak.core.Status
import ai.chakak.ui.component.StatusBadge
import ai.chakak.ui.model.currentTimelineClock
import ai.chakak.ui.model.Measurement
import ai.chakak.ui.model.NO_LIT_TELLTALES
import ai.chakak.ui.model.OBSERVATION_NOTE
import ai.chakak.ui.model.ObservationKeys
import ai.chakak.ui.model.formatNumber
import ai.chakak.ui.model.UNIT_NOT_PROVIDED
import ai.chakak.ui.model.observation
import ai.chakak.ui.model.observedLine
import ai.chakak.ui.model.otherObservations
import ai.chakak.ui.model.parseOdometer
import ai.chakak.ui.model.parseTelltales
import ai.chakak.ui.model.telltaleLabel
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakIcons
import ai.chakak.ui.theme.ChaKakShapes
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import ai.chakak.ui.theme.JournalDimens
import ai.chakak.ui.theme.JournalShapes
import ai.chakak.ui.theme.STACK_FONT_SCALE
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle

const val ODOMETER_LABEL = "누적 주행 거리"
const val LIT_TELLTALES_LABEL = "켜져 있던 경고등"

/**
 * 차량 상태 상세 본문(시안 A'): 머리 → 관측 안내(진단 아님) → 누적 주행 거리 큰 숫자 + 경고등 →
 * 타이어 패널 → 메모 + 위치 상자 → 정해진 영역이 없는 항목. 수집 개수는 머리의 상태 배지가 보여준다.
 * 각 묶음 아래에 항목별 관측 시각을 둬 명령 수신 시각과 구분한다(spec 5.4).
 * 글꼴 배율이 [STACK_FONT_SCALE]보다 크면 큰 숫자 칸과 경고등 칸을 세로로 쌓아 숫자 칸 폭을 넓힌다.
 */
@Composable
fun VehicleDetailBody(record: Record, compact: Boolean) {
    val clock = currentTimelineClock()
    val observedOf: (Observation?) -> String? = { it?.observedLine(record.createdAt, clock) }
    val odometer = record.observation(ObservationKeys.ODOMETER)
    val telltales = record.observation(ObservationKeys.TELLTALES)
    val tires = record.observation(ObservationKeys.TIRES)
    VehicleHeading(record)
    ObservationNote(OBSERVATION_NOTE)
    AdaptiveRow(compact || LocalDensity.current.fontScale > STACK_FONT_SCALE) { itemModifier ->
        OdometerBlock(odometer, observedOf(odometer), compact, itemModifier)
        TelltaleBlock(telltales, observedOf(telltales), itemModifier)
    }
    TirePanel(tires, compact, observed = observedOf(tires))
    AdaptiveRow(compact) { itemModifier ->
        Column(
            itemModifier.border(ChaKakDimens.dividerThick, ChaKakTheme.colors.dividerSecondary, JournalShapes.card)
                .padding(JournalDimens.cardPadding),
            verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight),
        ) {
            SectionLabel(MEMO_HEADER)
            MemoText(record.memo, large = false)
        }
        LocationBox(rememberLocationDisplay(record), itemModifier)
    }
    val others = record.otherObservations(ObservationKeys.vehicleKeys + ObservationKeys.locationKeys)
    if (others.isNotEmpty()) VehicleObservationList(others)
}

/** 넓으면 같은 높이 두 칸, 좁으면 세로로 쌓는다 */
@Composable
private fun AdaptiveRow(compact: Boolean, content: @Composable (Modifier) -> Unit) {
    if (compact) {
        Column(verticalArrangement = Arrangement.spacedBy(JournalDimens.detailGap)) { content(Modifier.fillMaxWidth()) }
    } else {
        Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(JournalDimens.detailGap)) {
            content(Modifier.weight(1f).fillMaxHeight())
        }
    }
}

@Composable
private fun ObservationNote(text: String) {
    val colors = ChaKakTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.backgroundPrimary, JournalShapes.card)
            .padding(horizontal = JournalDimens.cardPadding, vertical = JournalDimens.timelineGap)
            .semantics(mergeDescendants = true) {},
        horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(ChaKakIcons.Info, contentDescription = null, tint = colors.iconSecondary, modifier = Modifier.size(JournalDimens.chipGlyph))
        Text(text, style = ChaKakTheme.typography.labelMedium, color = colors.textSecondary)
    }
}

/** 큰 숫자 + 단위. 단위를 받지 못했으면 단위를 지어내지 않고 "단위 정보 없음"을 작게 붙인다 */
@Composable
private fun OdometerBlock(observation: Observation?, observed: String?, compact: Boolean, modifier: Modifier) {
    Column(modifier.semantics(mergeDescendants = true) {}, verticalArrangement = Arrangement.spacedBy(JournalDimens.cardTextGap)) {
        Text(ODOMETER_LABEL, style = ChaKakTheme.typography.labelMedium, color = ChaKakTheme.colors.textTertiary)
        val reading = observation?.takeIf { it.status == Status.OK }?.let { parseOdometer(it.value) }
        when {
            observation == null -> PlainValue(NOT_COLLECTED_ITEM)
            reading != null -> OdometerNumber(reading, compact)
            observation.status == Status.OK -> PlainValue(observation.displayValue().orEmpty())
            else -> StatusBadge(observation.status)
        }
        ObservedTime(observed)
    }
}

/**
 * 큰 숫자는 실제 칸 폭을 재서 한 줄에 들어가는 가장 큰 단계(112 → 80 → 60 → 40)를 고른다.
 * 줄바꿈·줄임표 없이 한 줄로만 그려 자릿수가 갈리거나 잘리지 않게 한다. [compact]면 80부터 시작한다.
 */
@Composable
private fun OdometerNumber(reading: Measurement, compact: Boolean) {
    val typography = ChaKakTheme.typography
    val colors = ChaKakTheme.colors
    val number = formatNumber(reading.value, currentTimelineClock().locale)
    val unitStyle = typography.titleMedium.toSpanStyle().copy(color = colors.textSecondary)
    val text = remember(number, reading.unit, unitStyle) {
        buildAnnotatedString {
            append(number)
            reading.unit?.let { withStyle(unitStyle) { append(" $it") } }
        }
    }
    val steps = if (compact) typography.dashboardNumberSteps.drop(1) else typography.dashboardNumberSteps
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val style = rememberFittingStyle(text, steps, constraints.maxWidth)
        Text(text, style = style, color = colors.textPrimary, softWrap = false, maxLines = 1)
    }
    if (reading.unit == null) Text(UNIT_NOT_PROVIDED, style = typography.labelMedium, color = colors.textTertiary)
}

/** [steps] 중 [maxWidthPx] 안에 한 줄로 들어가는 첫 스타일. 모두 넘치면 가장 작은 단계 */
@Composable
private fun rememberFittingStyle(text: AnnotatedString, steps: List<TextStyle>, maxWidthPx: Int): TextStyle {
    val measurer = rememberTextMeasurer()
    return remember(text, steps, maxWidthPx, measurer) {
        steps.firstOrNull { measurer.measure(text, it, softWrap = false, maxLines = 1).size.width <= maxWidthPx } ?: steps.last()
    }
}

@Composable
private fun PlainValue(text: String) {
    Text(text, style = ChaKakTheme.typography.titleSmall, color = ChaKakTheme.colors.textPrimary)
}

/** 켜져 있던 경고등 칩. 없으면 "켜진 항목 없음", 조회 실패면 상태 배지, 형식을 모르면 원문 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TelltaleBlock(observation: Observation?, observed: String?, modifier: Modifier) {
    Column(modifier.semantics(mergeDescendants = true) {}, verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline)) {
        Text(LIT_TELLTALES_LABEL, style = ChaKakTheme.typography.labelMedium, color = ChaKakTheme.colors.textTertiary)
        val lit = observation?.takeIf { it.status == Status.OK }?.let { parseTelltales(it.value) }?.filter { it.lit }
        when {
            observation == null -> PlainValue(NOT_COLLECTED_ITEM)
            lit == null && observation.status == Status.OK -> PlainValue(observation.displayValue().orEmpty())
            lit == null -> StatusBadge(observation.status)
            lit.isEmpty() -> PlainValue(NO_LIT_TELLTALES)
            else -> FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline),
                verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline),
            ) { lit.forEach { TelltaleChip(telltaleLabel(it)) } }
        }
        ObservedTime(observed)
    }
}

@Composable
private fun TelltaleChip(text: String) {
    val tint = ChaKakTheme.colors.brand.caution
    Row(
        modifier = Modifier
            .heightIn(min = JournalDimens.telltaleChipHeight)
            .background(tint.fill, ChaKakShapes.pill)
            .padding(horizontal = JournalDimens.cardPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline),
    ) {
        Icon(ChaKakIcons.Caution, contentDescription = null, tint = tint.content, modifier = Modifier.size(ChaKakDimens.iconInButtonSmall))
        Text(text, style = ChaKakTheme.typography.bodySmallStrong, color = ChaKakTheme.colors.textPrimary)
    }
}
