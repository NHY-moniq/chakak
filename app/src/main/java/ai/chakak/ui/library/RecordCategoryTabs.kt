package ai.chakak.ui.library

import ai.chakak.core.Category
import ai.chakak.ui.model.label
import ai.chakak.ui.theme.ChaKakColors
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakShapes
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import ai.chakak.ui.theme.JournalDimens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

/** 보관함 필터 순서. 전체(null) → 장소 → 차량 상태 → 미분류 */
private val filterOptions: List<Category?> = listOf(null, Category.PLACE, Category.VEHICLE_STATUS, Category.UNCLASSIFIED)
/** 편집 화면 분류 선택 순서 */
private val categoryOptions: List<Category> = listOf(Category.PLACE, Category.VEHICLE_STATUS, Category.UNCLASSIFIED)

const val ALL_FILTER_LABEL = "전체"
/** 이 글꼴 배율보다 크면 개수를 이름 아래 줄에 따로 둔다 */
private const val TAB_TWO_LINE_FONT_SCALE = 1.3f
/** 줄바꿈 없는 공백. 이름과 개수 사이에 써서 개수만 다음 줄로 떨어지지 않게 한다 */
private const val NO_BREAK_SPACE = '\u00A0'

/** 탭 라벨: 이름과(알면) 개수 */
@Immutable
data class TabLabel(val name: String, val count: Int? = null) {
    /** 한 줄 표시 "장소 7"(개수 앞은 줄바꿈 없는 공백) */
    val singleLine: String get() = count?.let { "$name$NO_BREAK_SPACE$it" } ?: name
}

/**
 * 보관함 필터 탭(spec 9.6 Tabs). null은 전체.
 * [counts]가 있으면 "장소 7"처럼 현재 기록 수를 붙인다(목록을 읽기 전에는 이름만).
 */
@Composable
fun RecordCategoryFilterTabs(
    selected: Category?,
    onSelect: (Category?) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    counts: Map<Category?, Int>? = null,
) {
    SegmentedTabs(filterOptions, selected, { filterLabel(it, counts) }, onSelect, modifier, enabled)
}

/** "전체 12", "장소 7". 개수를 모르면 이름만 */
fun filterLabel(option: Category?, counts: Map<Category?, Int>?): TabLabel =
    TabLabel(option?.label() ?: ALL_FILTER_LABEL, counts?.get(option))

/** 편집 화면 분류 선택. 필터 탭과 같은 컴포넌트를 쓴다. */
@Composable
fun RecordCategoryTabs(selected: Category, onSelect: (Category) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    SegmentedTabs(categoryOptions, selected, { TabLabel(it.label()) }, onSelect, modifier, enabled)
}

/**
 * Pleos Tabs(Segmented): 높이 100, 반경 32, 내부 8.
 * 배경은 controller/tab_background에 해당하는 토큰이 아직 없어 surface/high를 쓴다.
 * TBD: Pleos 원본 미확인 — controller/tab_background 값 미추출.
 * 선택 세그먼트는 색 외에 surface/basic 면 + 그림자 + accentDeep 2dp 테두리 + Bold 라벨로 구분한다(시안 A).
 * Dark에서는 그림자가 거의 보이지 않으므로 테두리가 색 외의 단서가 된다.
 */
@Composable
private fun <T> SegmentedTabs(
    options: List<T>,
    selected: T,
    label: (T) -> TabLabel,
    onSelect: (T) -> Unit,
    modifier: Modifier,
    enabled: Boolean,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = ChaKakDimens.tabsHeight)
            .height(IntrinsicSize.Min)
            .background(ChaKakTheme.colors.surfaceHigh, ChaKakShapes.tabs)
            .padding(ChaKakDimens.tabsInnerPadding)
            .selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight),
    ) {
        options.forEach { option ->
            TabSegment(label(option), option == selected, enabled) { onSelect(option) }
        }
    }
}

/** 누르는 동안은 surface/accent 면으로 바뀐다(장식 애니메이션 없음). */
@Composable
private fun RowScope.TabSegment(label: TabLabel, selected: Boolean, enabled: Boolean, onClick: () -> Unit) {
    val colors = ChaKakTheme.colors
    // 바깥 반경 32에서 내부 패딩 8을 뺀 반경
    val shape = ChaKakShapes.buttonMedium
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .then(if (selected) Modifier.shadow(JournalDimens.selectedTabElevation, shape) else Modifier)
            .clip(shape)
            .segmentSurface(colors, selected, pressed && enabled, shape)
            .then(if (selected) Modifier.border(ChaKakDimens.dividerThick, colors.brand.accentDeep, shape) else Modifier)
            .selectable(
                selected = selected,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interaction,
                indication = null,
                onClick = onClick,
            )
            .semantics { contentDescription = label.singleLine }
            .padding(horizontal = ChaKakSpacing.tight),
        contentAlignment = Alignment.Center,
    ) {
        val color = when {
            !enabled -> colors.textQuaternary
            selected -> colors.textPrimary
            else -> colors.textSecondary
        }
        TabLabelText(label, selected, color)
    }
}

/**
 * 한 줄 "장소 7"이 칸에 들어가면 한 줄로, 넘치거나 글꼴 배율이 [TAB_TWO_LINE_FONT_SCALE]보다 크면
 * 이름(줄바꿈 허용) 아래에 개수를 한 단계 작은 글꼴로 둔다. 개수만 홀로 다음 줄로 떨어지지 않는다.
 * 부모 행이 IntrinsicSize를 쓰므로 BoxWithConstraints 대신 직접 잰다.
 */
@Composable
private fun TabLabelText(label: TabLabel, selected: Boolean, color: Color) {
    val typography = ChaKakTheme.typography
    val nameStyle = if (selected) typography.bodySmallStrong else typography.bodySmall
    val countStyle = if (selected) typography.labelMediumStrong else typography.labelMedium
    val forceTwoLines = LocalDensity.current.fontScale > TAB_TWO_LINE_FONT_SCALE
    Layout(
        modifier = Modifier.clearAndSetSemantics {},
        content = {
            Text(label.singleLine, style = nameStyle, color = color, maxLines = 1, softWrap = false)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(label.name, style = nameStyle, color = color, textAlign = TextAlign.Center)
                label.count?.let { Text(it.toString(), style = countStyle, color = color, textAlign = TextAlign.Center) }
            }
        },
    ) { measurables, constraints ->
        val loose = constraints.copy(minWidth = 0, minHeight = 0)
        val single = if (label.count != null && !forceTwoLines) measurables[0].measure(Constraints()) else null
        val chosen = single?.takeIf { it.width <= constraints.maxWidth } ?: measurables[1].measure(loose)
        layout(chosen.width, chosen.height) { chosen.place(0, 0) }
    }
}

/** 선택: surface/basic. 누름: surface/accent. 둘 다 대비 테스트를 거친 면 토큰이다. */
private fun Modifier.segmentSurface(colors: ChaKakColors, selected: Boolean, pressed: Boolean, shape: Shape): Modifier {
    val fill = when {
        pressed -> colors.surfaceAccent
        selected -> colors.surfaceBasic
        else -> null
    }
    return fill?.let { background(it, shape) } ?: this
}

private val previewCounts: Map<Category?, Int> =
    mapOf(null to 12, Category.PLACE to 7, Category.VEHICLE_STATUS to 4, Category.UNCLASSIFIED to 1)

@Composable
private fun TabsGallery() {
    Surface(color = ChaKakTheme.colors.backgroundPrimary) {
        Column(Modifier.padding(ChaKakSpacing.screen), verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
            RecordCategoryFilterTabs(selected = null, onSelect = {}, counts = previewCounts)
            RecordCategoryFilterTabs(selected = Category.VEHICLE_STATUS, onSelect = {}, counts = previewCounts)
            RecordCategoryTabs(selected = Category.PLACE, onSelect = {})
        }
    }
}

@Preview(name = "Tabs L portrait Light", widthDp = 628)
@Composable
private fun TabsLightPreview() = ChaKakTheme(darkTheme = false) { TabsGallery() }

@Preview(name = "Tabs L portrait Dark fontScale 1.5", widthDp = 628, fontScale = 1.5f)
@Composable
private fun TabsDarkPreview() = ChaKakTheme(darkTheme = true) { TabsGallery() }
