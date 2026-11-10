package ai.chakak.ui.component

import ai.chakak.ui.model.UserMessage
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakShapes
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign

/** 패널 톤. [Basic]은 surface/basic(차량 데이터), [High]는 surface/high(운전자 입력·강조) */
enum class PanelTone { Basic, High }

/** 상세 화면의 섹션 묶음. 반경은 Button Medium 24를 차용한다. */
@Composable
fun SectionPanel(
    modifier: Modifier = Modifier,
    tone: PanelTone = PanelTone.Basic,
    borderColor: Color? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = ChaKakTheme.colors
    val shape = ChaKakShapes.buttonMedium
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(if (tone == PanelTone.High) colors.surfaceHigh else colors.surfaceBasic, shape)
            .border(ChaKakDimens.dividerThick, borderColor ?: colors.dividerSecondary, shape)
            .padding(ChaKakSpacing.panel),
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item),
        content = content,
    )
}

/** 섹션 머리: 아이콘 + 제목. 접근성 heading으로 표시한다. */
@Composable
fun SectionHeader(title: String, icon: ImageVector, modifier: Modifier = Modifier, iconTint: Color? = null) {
    Row(
        modifier = modifier.semantics(mergeDescendants = true) { heading() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline),
    ) {
        // TBD: Pleos 아이콘 세트 — Material 기본 아이콘 임시 사용
        Icon(icon, contentDescription = null, tint = iconTint ?: ChaKakTheme.colors.iconSecondary, modifier = Modifier.size(ChaKakDimens.iconInButtonSmall))
        Text(title, style = ChaKakTheme.typography.bodyMedium, color = ChaKakTheme.colors.textPrimary)
    }
}

/** 한 줄 안내: 아이콘 + 짧은 문장. 큰 글꼴에서는 줄바꿈하고 자르지 않는다. */
@Composable
fun InfoNote(text: String, icon: ImageVector, modifier: Modifier = Modifier, iconTint: Color? = null) {
    Row(
        modifier = modifier.semantics(mergeDescendants = true) {},
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline),
    ) {
        Icon(icon, contentDescription = null, tint = iconTint ?: ChaKakTheme.colors.iconTertiary, modifier = Modifier.size(ChaKakDimens.badgeIcon))
        Text(text, style = ChaKakTheme.typography.bodySmall, color = ChaKakTheme.colors.textSecondary)
    }
}

/** 큰 아이콘 + 제목 + 설명 + 선택 동작. 빈 목록·오류·로딩·제한 안내 공통. */
@Composable
fun StatePlaceholder(
    title: String,
    body: String,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    action: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .widthIn(max = ChaKakDimens.popupWidth)
            .padding(ChaKakSpacing.screen),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item),
    ) {
        icon()
        Text(
            title,
            style = ChaKakTheme.typography.titleSmall,
            color = ChaKakTheme.colors.textPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.semantics { heading() },
        )
        Text(body, style = ChaKakTheme.typography.bodySmall, color = ChaKakTheme.colors.textSecondary, textAlign = TextAlign.Center)
        action?.invoke()
    }
}

/** [StatePlaceholder]용 큰 아이콘 */
@Composable
fun StateIcon(icon: ImageVector, tint: Color? = null) {
    // TBD: Pleos 아이콘 세트 — Material 기본 아이콘 임시 사용
    Icon(icon, contentDescription = null, tint = tint ?: ChaKakTheme.colors.iconTertiary, modifier = Modifier.size(ChaKakDimens.stateIcon))
}

/**
 * 동작 결과 안내(Toast 대용, spec 9.6). 실패는 경고 아이콘, 완료는 체크 아이콘으로 구분하고
 * 접근성 서비스에 알린다. 실패 원인은 해당 화면(편집·삭제 팝업)에도 남는다.
 */
@Composable
fun MessageBar(message: UserMessage, modifier: Modifier = Modifier) {
    val colors = ChaKakTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.surfaceHigh, ChaKakShapes.buttonMedium)
            .border(ChaKakDimens.dividerThick, if (message.isError) colors.regulationRed else colors.dividerTertiary, ChaKakShapes.buttonMedium)
            .padding(horizontal = ChaKakDimens.buttonPaddingHorizontalMedium, vertical = ChaKakSpacing.item)
            .semantics(mergeDescendants = true) { liveRegion = LiveRegionMode.Polite },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline),
    ) {
        Icon(
            if (message.isError) Icons.Filled.Warning else Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = if (message.isError) colors.regulationRed else colors.informativePositive,
            modifier = Modifier.size(ChaKakDimens.badgeIcon),
        )
        Text(message.text, style = ChaKakTheme.typography.bodySmallStrong, color = colors.textPrimary)
    }
}

/**
 * 하위 화면 상단(Page Navigation 대응): 뒤로 동작 버튼 + 화면 제목.
 * [onBack]이 null이면(두 열의 상세) 버튼을 숨긴다.
 */
@Composable
fun ScreenTopBar(title: String, backLabel: String, onBack: (() -> Unit)?, modifier: Modifier = Modifier, backEnabled: Boolean = true) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.item),
    ) {
        if (onBack != null) {
            ChaKakButton(
                text = backLabel,
                onClick = onBack,
                size = ButtonSize.Medium,
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                enabled = backEnabled,
            )
        }
        Text(
            title,
            style = ChaKakTheme.typography.titleSmall,
            color = ChaKakTheme.colors.textSecondary,
            modifier = Modifier.semantics { heading() },
        )
    }
}
