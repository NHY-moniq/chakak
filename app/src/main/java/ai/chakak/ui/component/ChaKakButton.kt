package ai.chakak.ui.component

import ai.chakak.ui.theme.ChaKakColors
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakShapes
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import ai.chakak.ui.theme.ChaKakTypography
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

/**
 * Pleos Basic Button 변형.
 * - [Filled]: button/filled — 주요 동작(현재 상태 기록·편집·저장)
 * - [Basic]: button/basic — 보조 동작(목록·취소)
 * - [Destructive]: button/basic + regulation/red 테두리·아이콘. 라벨은 text primary로 대비 유지
 */
enum class ButtonVariant { Filled, Basic, Destructive }

/** Pleos 버튼 크기(foundations 5). 높이는 최소값이며 큰 글꼴에서는 늘어난다. */
enum class ButtonSize { Large, Medium, Small }

/**
 * 차칵 버튼. M3 pill 모양 대신 Pleos 반경·높이·패딩을 쓴다.
 * [loading]이면 진행 표시와 [loadingText]를 보이고 다시 누를 수 없다(중복 실행 방지).
 * 누름 피드백은 pressed 색으로만 주고 장식 애니메이션은 쓰지 않는다.
 */
@Composable
fun ChaKakButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Basic,
    size: ButtonSize = ButtonSize.Medium,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
) {
    val metrics = size.metrics()
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val palette = ChaKakTheme.colors.buttonPalette(variant, enabled || loading, pressed)
    Row(
        modifier = modifier
            .heightIn(min = metrics.height)
            .clip(metrics.shape)
            .background(palette.container, metrics.shape)
            .then(palette.border?.let { Modifier.border(ChaKakDimens.dividerThick, it, metrics.shape) } ?: Modifier)
            .clickable(interaction, indication = null, enabled = enabled && !loading, role = Role.Button, onClick = onClick)
            .semantics { if (loading) stateDescription = PROCESSING_STATE }
            .padding(metrics.padding),
        horizontalArrangement = Arrangement.spacedBy(metrics.gap, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ButtonLeading(icon, loading, metrics.iconSize, palette)
        Text(
            text = if (loading) loadingText ?: text else text,
            style = ChaKakTheme.typography.buttonLabel(size),
            color = palette.content,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ButtonLeading(icon: ImageVector?, loading: Boolean, iconSize: Dp, palette: ButtonPalette) {
    when {
        loading -> CircularProgressIndicator(
            modifier = Modifier.size(iconSize),
            color = palette.content,
            trackColor = Color.Transparent,
            strokeWidth = ChaKakDimens.lineLevel4,
        )
        // TBD: Pleos 아이콘 세트 — Material 기본 아이콘 임시 사용
        icon != null -> Icon(icon, contentDescription = null, tint = palette.icon, modifier = Modifier.size(iconSize))
    }
}

private const val PROCESSING_STATE = "처리 중"

private class ButtonMetrics(
    val height: Dp,
    val shape: CornerBasedShape,
    val padding: PaddingValues,
    val gap: Dp,
    val iconSize: Dp,
)

private fun ButtonSize.metrics(): ButtonMetrics = when (this) {
    ButtonSize.Large -> ButtonMetrics(
        ChaKakDimens.buttonHeightLarge, ChaKakShapes.buttonLarge,
        PaddingValues(ChaKakDimens.buttonPaddingHorizontalLarge, ChaKakDimens.buttonPaddingVerticalLarge),
        ChaKakDimens.buttonGapLarge, ChaKakDimens.iconInButtonLarge,
    )
    ButtonSize.Medium -> ButtonMetrics(
        ChaKakDimens.buttonHeightMedium, ChaKakShapes.buttonMedium,
        PaddingValues(ChaKakDimens.buttonPaddingHorizontalMedium, ChaKakDimens.buttonPaddingVerticalMedium),
        ChaKakDimens.buttonGapMedium, ChaKakDimens.iconInButtonMedium,
    )
    ButtonSize.Small -> ButtonMetrics(
        ChaKakDimens.buttonHeightSmall, ChaKakShapes.buttonSmall,
        PaddingValues(ChaKakDimens.buttonPaddingHorizontalSmall, ChaKakDimens.buttonPaddingVerticalSmall),
        ChaKakDimens.buttonGapSmall, ChaKakDimens.iconInButtonSmall,
    )
}

/** 라벨: Large 32 Bold(Title Small), Medium 30 Bold, Small 26 Bold (foundations 5) */
private fun ChaKakTypography.buttonLabel(size: ButtonSize): TextStyle = when (size) {
    ButtonSize.Large -> titleSmall
    ButtonSize.Medium -> bodyLargeStrong
    ButtonSize.Small -> bodySmallStrong
}

private class ButtonPalette(val container: Color, val content: Color, val icon: Color, val border: Color?)

private fun ChaKakColors.buttonPalette(variant: ButtonVariant, active: Boolean, pressed: Boolean): ButtonPalette =
    when (variant) {
        ButtonVariant.Filled -> {
            val container = when {
                !active -> buttonFilledDisabled
                pressed -> buttonFilledPressed
                else -> buttonFilledEnabled
            }
            val content = if (active) textOnFilled else textQuaternary
            ButtonPalette(container, content, content, border = null)
        }
        ButtonVariant.Basic, ButtonVariant.Destructive -> basicPalette(variant, active, pressed)
    }

private fun ChaKakColors.basicPalette(variant: ButtonVariant, active: Boolean, pressed: Boolean): ButtonPalette {
    val container = when {
        !active -> buttonBasicDisabled
        pressed -> buttonBasicPressed
        else -> buttonBasicEnabled
    }
    val content = if (active) textPrimary else textQuaternary
    val destructive = variant == ButtonVariant.Destructive && active
    return ButtonPalette(
        container = container,
        content = content,
        icon = if (destructive) regulationRed else content,
        border = when {
            destructive -> regulationRed
            !active -> dividerSecondary
            else -> null
        },
    )
}

@Composable
private fun ButtonGallery() {
    Surface(color = ChaKakTheme.colors.backgroundPrimary) {
        Column(Modifier.padding(ChaKakSpacing.screen), verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
            ChaKakButton("현재 상태 기록", {}, variant = ButtonVariant.Filled, size = ButtonSize.Large)
            ChaKakButton("현재 상태 기록", {}, variant = ButtonVariant.Filled, size = ButtonSize.Large, loading = true, loadingText = "기록 중…")
            ChaKakButton("편집", {}, variant = ButtonVariant.Filled, icon = Icons.Filled.Edit)
            ChaKakButton("삭제", {}, variant = ButtonVariant.Destructive, icon = Icons.Filled.Delete)
            ChaKakButton("취소", {}, enabled = false)
            ChaKakButton("목록", {}, size = ButtonSize.Small)
        }
    }
}

@Preview(name = "Buttons Light", widthDp = 840)
@Composable
private fun ButtonsLightPreview() = ChaKakTheme(darkTheme = false) { ButtonGallery() }

@Preview(name = "Buttons Dark fontScale 1.5", widthDp = 840, fontScale = 1.5f)
@Composable
private fun ButtonsDarkPreview() = ChaKakTheme(darkTheme = true) { ButtonGallery() }
