package ai.chakak.ui.detail

import ai.chakak.platform.GuidanceTarget
import ai.chakak.ui.component.ButtonSize
import ai.chakak.ui.component.ButtonVariant
import ai.chakak.ui.component.ChaKakButton
import ai.chakak.ui.component.ChaKakPopup
import ai.chakak.ui.component.ChaKakPopupContent
import ai.chakak.ui.component.PopupActions
import ai.chakak.ui.component.InfoNote
import ai.chakak.ui.component.PanelTone
import ai.chakak.ui.component.SectionPanel
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakIcons
import ai.chakak.ui.theme.ChaKakShapes
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.util.Locale

const val GUIDANCE_TITLE = "이 장소로 안내할까요?"
const val GUIDANCE_HINT = "눌러서 이 위치로 안내"
const val GUIDANCE_REPLACES_ROUTE = "안내 중인 경로가 있으면 이 목적지로 바뀝니다."

/**
 * 위치 카드의 안내 동작. [running]이면 팝업의 두 버튼을 잠그고, [enabled]가 false면 카드를 누를 수 없다.
 * [finished]는 이 기록의 안내 요청이 방금 끝났다는 뜻이고, 화면이 팝업을 닫은 뒤 [onFinished]로 비운다.
 */
@Immutable
class GuidanceControl(
    val enabled: Boolean,
    val running: Boolean,
    val finished: Boolean,
    val onStart: (GuidanceTarget) -> Unit,
    val onFinished: () -> Unit,
)

/**
 * 목적지 안내 확인(Pleos Popup Medium, 삭제 확인과 같은 틀). 요청 중에는 닫히지 않는다.
 * 결과는 상위의 안내 줄이 알리고, 성공하면 내비게이션 화면이 앞으로 온다.
 */
@Composable
fun GuidanceDialog(target: GuidanceTarget, running: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    ChaKakPopup(GUIDANCE_TITLE, running, onDismiss) { GuidanceBody(target, running, onConfirm, onDismiss) }
}

/** 팝업 본문. Preview에서도 직접 쓴다. */
@Composable
fun GuidanceDialogContent(target: GuidanceTarget, running: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    ChaKakPopupContent(GUIDANCE_TITLE, modifier) { GuidanceBody(target, running, onConfirm, onDismiss) }
}

@Composable
private fun GuidanceBody(target: GuidanceTarget, running: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    val colors = ChaKakTheme.colors
    SectionPanel(tone = PanelTone.High) {
        Text(target.name, style = ChaKakTheme.typography.bodyMedium, color = colors.textPrimary, maxLines = 2, overflow = TextOverflow.Ellipsis)
        target.address?.let { Text(it, style = ChaKakTheme.typography.bodySmall, color = colors.textSecondary) }
        Text(
            String.format(Locale.US, "%.5f, %.5f", target.latitude, target.longitude),
            style = ChaKakTheme.typography.labelMedium,
            color = colors.textTertiary,
        )
    }
    InfoNote(GUIDANCE_REPLACES_ROUTE, ChaKakIcons.Caution)
    PopupActions(running, onDismiss) { modifier ->
        ChaKakButton(
            "안내 시작", onConfirm, modifier, ButtonVariant.Accent, ButtonSize.Medium,
            icon = ChaKakIcons.Navigate, loading = running, loadingText = "요청 중…",
        )
    }
}
