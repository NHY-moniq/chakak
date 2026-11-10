package ai.chakak.ui.library

import ai.chakak.ui.component.ButtonSize
import ai.chakak.ui.component.ButtonVariant
import ai.chakak.ui.component.ChaKakButton
import ai.chakak.ui.component.InfoNote
import ai.chakak.ui.theme.ChaKakIcons
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import ai.chakak.ui.theme.JournalDimens
import ai.chakak.ui.theme.JournalShapes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics

const val APP_TITLE = "차칵"
const val APP_SUBTITLE = "운전 중 남긴 순간들"
const val CAPTURE_LABEL = "지금 차칵 기록"
const val CAPTURE_LOADING_LABEL = "기록 중…"

/** 앱 머리(시안 A): 셔터 마크 타일 + "차칵" + 부제. 목록과 함께 스크롤된다. */
@Composable
fun AppHeader(modifier: Modifier = Modifier) {
    val colors = ChaKakTheme.colors
    Row(
        modifier = modifier.semantics(mergeDescendants = true) { heading() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(JournalDimens.timelineGap),
    ) {
        Box(
            Modifier.size(JournalDimens.headerMark).background(colors.brand.accent, JournalShapes.headerMark),
            contentAlignment = Alignment.Center,
        ) {
            Icon(ChaKakIcons.ShutterMark, contentDescription = null, tint = colors.brand.onAccent, modifier = Modifier.size(JournalDimens.headerMarkGlyph))
        }
        Column {
            Text(APP_TITLE, style = ChaKakTheme.typography.titleLargeStrong, color = colors.textPrimary)
            Text(APP_SUBTITLE, style = ChaKakTheme.typography.labelMedium, color = colors.textTertiary)
        }
    }
}

/**
 * "지금 차칵 기록"(Button Large, accent 채움). 기록 중이면 진행 표시와 함께 다시 누를 수 없고,
 * 비활성이면 이유를 버튼 아래에 글로 보여준다(색만으로 비활성 표시 금지).
 */
@Composable
fun CaptureButton(capture: CaptureControl, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight)) {
        ChaKakButton(
            text = CAPTURE_LABEL,
            onClick = capture.onCapture,
            modifier = Modifier.fillMaxWidth(),
            variant = ButtonVariant.Accent,
            size = ButtonSize.Large,
            icon = ChaKakIcons.Shutter,
            enabled = capture.enabled,
            loading = capture.loading,
            loadingText = CAPTURE_LOADING_LABEL,
        )
        val reason = capture.disabledReason
        if (!capture.enabled && !capture.loading && reason != null) InfoNote(reason, Icons.Filled.Info)
    }
}
