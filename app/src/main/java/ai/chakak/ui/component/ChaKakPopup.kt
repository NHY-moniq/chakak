package ai.chakak.ui.component

import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakShapes
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * 확인 팝업의 공통 틀(spec 9.6 System Popup, Pleos Popup Medium). 삭제·일괄 삭제·목적지 안내가 함께 쓴다.
 * [busy]인 동안에는 뒤로·바깥 터치로 닫히지 않는다. 닫기는 상위가 결정한다.
 */
@Composable
fun ChaKakPopup(title: String, busy: Boolean, onDismiss: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Dialog(
        onDismissRequest = { if (!busy) onDismiss() },
        properties = DialogProperties(dismissOnBackPress = !busy, dismissOnClickOutside = !busy, usePlatformDefaultWidth = false),
    ) {
        ChaKakPopupContent(title, Modifier.padding(ChaKakSpacing.screen), content)
    }
}

/**
 * 팝업 본문. 폭 808(창이 좁으면 창 폭), 반경 48, 패딩 48, gap 40, background/popup,
 * 테두리 divider/primary 2 (foundations 5). Preview에서도 직접 쓴다.
 */
@Composable
fun ChaKakPopupContent(title: String, modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    val colors = ChaKakTheme.colors
    Column(
        modifier = modifier
            .widthIn(max = ChaKakDimens.popupWidth)
            .fillMaxWidth()
            .background(colors.backgroundPopup, ChaKakShapes.popup)
            .border(ChaKakDimens.dividerThick, colors.dividerPrimary, ChaKakShapes.popup)
            .verticalScroll(rememberScrollState())
            .padding(ChaKakDimens.popupPadding)
            .semantics { paneTitle = title },
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.section),
    ) {
        Text(title, style = ChaKakTheme.typography.titleMedium, color = colors.textPrimary, modifier = Modifier.semantics { heading() })
        content()
    }
}

/** 팝업 아래 두 버튼: 왼쪽 취소(Basic), 오른쪽 확정. 확정 버튼은 호출 측이 만든다. */
@Composable
fun PopupActions(busy: Boolean, onDismiss: () -> Unit, confirm: @Composable (Modifier) -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
        ChaKakButton("취소", onDismiss, Modifier.weight(1f), ButtonVariant.Basic, ButtonSize.Medium, enabled = !busy)
        confirm(Modifier.weight(1f))
    }
}

/**
 * 글자만 있는 보조 동작(펼치기·상세 보기 등). M3 TextButton 대신 쓴다.
 * [expanded]가 있으면 펼침 화살표와 상태 설명을 붙인다(색만으로 알리지 않음).
 */
@Composable
fun TextAction(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, expanded: Boolean? = null, enabled: Boolean = true) {
    val colors = ChaKakTheme.colors
    val tint = if (enabled) colors.brand.accentDeep else colors.textTertiary
    Row(
        modifier = modifier
            .clip(ChaKakShapes.buttonSmall)
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            .heightIn(min = ChaKakDimens.iconGrid)
            .padding(horizontal = ChaKakSpacing.tight)
            .semantics { if (expanded != null) stateDescription = if (expanded) "펼쳐짐" else "접힘" },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight),
    ) {
        Text(text, style = ChaKakTheme.typography.labelMediumStrong, color = tint)
        if (expanded != null) {
            Icon(
                if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null, tint = tint, modifier = Modifier.size(ChaKakDimens.badgeGlyph),
            )
        }
    }
}
