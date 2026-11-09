package ai.chakak.ui.library

import ai.chakak.core.Category
import ai.chakak.ui.model.label
import ai.chakak.ui.theme.ChaKakColors
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

/** 보관함 필터 순서. 전체(null) → 장소 → 차량 상태 → 미분류 */
private val filterOptions: List<Category?> = listOf(null, Category.PLACE, Category.VEHICLE_STATUS, Category.UNCLASSIFIED)
/** 편집 화면 분류 선택 순서 */
private val categoryOptions: List<Category> = listOf(Category.PLACE, Category.VEHICLE_STATUS, Category.UNCLASSIFIED)

const val ALL_FILTER_LABEL = "전체"

/** 보관함 필터 탭(spec 9.6 Tabs). null은 전체. */
@Composable
fun RecordCategoryFilterTabs(selected: Category?, onSelect: (Category?) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    SegmentedTabs(filterOptions, selected, { it?.label() ?: ALL_FILTER_LABEL }, onSelect, modifier, enabled)
}

/** 편집 화면 분류 선택. 필터 탭과 같은 컴포넌트를 쓴다. */
@Composable
fun RecordCategoryTabs(selected: Category, onSelect: (Category) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    SegmentedTabs(categoryOptions, selected, { it.label() }, onSelect, modifier, enabled)
}

/**
 * Pleos Tabs(Segmented): 높이 100, 반경 32, 내부 8.
 * 배경은 controller/tab_background에 해당하는 토큰이 아직 없어 surface/high를 쓴다.
 * TBD: Pleos 원본 미확인 — controller/tab_background 값 미추출.
 * 선택 세그먼트는 색 외에 surface/basic 면 + 테두리 + Bold 라벨로 구분한다.
 */
@Composable
private fun <T> SegmentedTabs(
    options: List<T>,
    selected: T,
    label: (T) -> String,
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
private fun RowScope.TabSegment(text: String, selected: Boolean, enabled: Boolean, onClick: () -> Unit) {
    val colors = ChaKakTheme.colors
    // 바깥 반경 32에서 내부 패딩 8을 뺀 반경
    val shape = ChaKakShapes.buttonMedium
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clip(shape)
            .segmentSurface(colors, selected, pressed && enabled, shape)
            .selectable(
                selected = selected,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interaction,
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = ChaKakSpacing.tight),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = if (selected) ChaKakTheme.typography.bodySmallStrong else ChaKakTheme.typography.bodySmall,
            color = when {
                !enabled -> colors.textQuaternary
                selected -> colors.textPrimary
                else -> colors.textSecondary
            },
            textAlign = TextAlign.Center,
        )
    }
}

/** 선택: surface/basic + 테두리. 누름: surface/accent. 둘 다 대비 테스트를 거친 면 토큰이다. */
private fun Modifier.segmentSurface(colors: ChaKakColors, selected: Boolean, pressed: Boolean, shape: Shape): Modifier {
    val fill = when {
        pressed -> colors.surfaceAccent
        selected -> colors.surfaceBasic
        else -> null
    }
    val filled = fill?.let { background(it, shape) } ?: this
    return if (selected) filled.border(ChaKakDimens.dividerThick, colors.dividerTertiary, shape) else filled
}

@Composable
private fun TabsGallery() {
    Surface(color = ChaKakTheme.colors.backgroundPrimary) {
        Column(Modifier.padding(ChaKakSpacing.screen), verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
            RecordCategoryFilterTabs(selected = null, onSelect = {})
            RecordCategoryFilterTabs(selected = Category.VEHICLE_STATUS, onSelect = {})
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
