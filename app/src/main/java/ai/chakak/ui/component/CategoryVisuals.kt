package ai.chakak.ui.component

import ai.chakak.core.Category
import ai.chakak.ui.model.label
import ai.chakak.ui.theme.ChaKakBrandColors
import ai.chakak.ui.theme.ChaKakIcons
import ai.chakak.ui.theme.ChaKakShapes
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import ai.chakak.ui.theme.JournalDimens
import ai.chakak.ui.theme.JournalShapes
import ai.chakak.ui.theme.Tint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp

/** 분류별 아이콘. 색 외에 모양으로도 구분한다 */
fun Category.icon(): ImageVector = when (this) {
    Category.PLACE -> ChaKakIcons.Pin
    Category.VEHICLE_STATUS -> ChaKakIcons.Car
    Category.UNCLASSIFIED -> Icons.Filled.Info
}

/** 분류별 색(시안 A). 미분류는 무채색 */
fun ChaKakBrandColors.tintOf(category: Category): Tint = when (category) {
    Category.PLACE -> place
    Category.VEHICLE_STATUS -> vehicle
    Category.UNCLASSIFIED -> neutral
}

/** 분류 아이콘 타일(64). 분류 이름은 옆 메타 줄이 말하므로 장식으로 둔다 */
@Composable
fun CategoryIconTile(category: Category, modifier: Modifier = Modifier) {
    IconTile(category.icon(), ChaKakTheme.colors.brand.tintOf(category), JournalDimens.categoryTile, JournalDimens.categoryGlyph, modifier)
}

/** 채운 사각 타일 안 아이콘 */
@Composable
fun IconTile(icon: ImageVector, tint: Tint, size: Dp, glyph: Dp, modifier: Modifier = Modifier) {
    Box(modifier.size(size).background(tint.fill, JournalShapes.tile), contentAlignment = Alignment.Center) {
        Icon(icon, contentDescription = null, tint = tint.content, modifier = Modifier.size(glyph))
    }
}

/** 분류 칩: 아이콘 + 분류 이름(색만으로 구분하지 않음) */
@Composable
fun CategoryChip(category: Category, modifier: Modifier = Modifier) {
    val tint = ChaKakTheme.colors.brand.tintOf(category)
    Row(
        modifier = modifier
            .heightIn(min = JournalDimens.chipHeight)
            .background(tint.fill, ChaKakShapes.pill)
            .padding(horizontal = JournalDimens.chipPaddingHorizontal)
            .semantics(mergeDescendants = true) {},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight),
    ) {
        Icon(category.icon(), contentDescription = null, tint = tint.content, modifier = Modifier.size(JournalDimens.chipGlyph))
        Text(category.label(), style = ChaKakTheme.typography.labelMediumStrong, color = tint.content)
    }
}
