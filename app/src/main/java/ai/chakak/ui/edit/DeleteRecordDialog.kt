package ai.chakak.ui.edit

import ai.chakak.core.Record
import ai.chakak.core.Source
import ai.chakak.ui.component.ButtonSize
import ai.chakak.ui.component.ButtonVariant
import ai.chakak.ui.component.ChaKakButton
import ai.chakak.ui.component.InfoNote
import ai.chakak.ui.component.MockTag
import ai.chakak.ui.component.PanelTone
import ai.chakak.ui.component.SectionPanel
import ai.chakak.ui.detail.COMMAND_TIME_LABEL
import ai.chakak.ui.detail.LabeledTime
import ai.chakak.ui.model.displayTitle
import ai.chakak.ui.model.label
import ai.chakak.ui.theme.ChaKakDimens
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

const val DELETE_IRREVERSIBLE_NOTICE = "삭제한 기록은 복구할 수 없습니다."
const val DELETE_FAILED_NOTICE = "삭제하지 못했습니다. 기록은 그대로 남아 있습니다. 다시 시도해 주세요."
private const val DELETE_TITLE = "기록을 삭제할까요?"

/**
 * 삭제 확인(spec 9.6 System Popup, Pleos Popup Medium). 삭제 중에는 닫히지 않고(뒤로·바깥 터치 무시)
 * 두 버튼을 잠근다. 실패하면 팝업을 유지하고 원인을 보여준다. 성공 후 닫기는 상위가 결정한다.
 */
@Composable
fun DeleteRecordDialog(record: Record, deleting: Boolean, failed: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = { if (!deleting) onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = !deleting,
            dismissOnClickOutside = !deleting,
            usePlatformDefaultWidth = false,
        ),
    ) {
        DeleteRecordDialogContent(record, deleting, failed, onConfirm, onDismiss, Modifier.padding(ChaKakSpacing.screen))
    }
}

/**
 * 팝업 본문. 폭 808(창이 좁으면 창 폭), 반경 48, 패딩 48, gap 40, background/popup,
 * 테두리 divider/primary 2 (foundations 5). Preview에서도 직접 쓴다.
 */
@Composable
fun DeleteRecordDialogContent(
    record: Record,
    deleting: Boolean,
    failed: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ChaKakTheme.colors
    Column(
        modifier = modifier
            .widthIn(max = ChaKakDimens.popupWidth)
            .fillMaxWidth()
            .background(colors.backgroundPopup, ChaKakShapes.popup)
            .border(ChaKakDimens.dividerThick, colors.dividerPrimary, ChaKakShapes.popup)
            .verticalScroll(rememberScrollState())
            .padding(ChaKakDimens.popupPadding)
            .semantics { paneTitle = DELETE_TITLE },
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.section),
    ) {
        Text(DELETE_TITLE, style = ChaKakTheme.typography.titleMedium, color = colors.textPrimary, modifier = Modifier.semantics { heading() })
        DeleteTargetSummary(record)
        InfoNote(DELETE_IRREVERSIBLE_NOTICE, Icons.Filled.Warning, iconTint = colors.regulationRed)
        if (failed) {
            InfoNote(
                DELETE_FAILED_NOTICE,
                Icons.Filled.Warning,
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Assertive },
                iconTint = colors.regulationRed,
            )
        }
        DeleteActions(deleting, onConfirm, onDismiss)
    }
}

@Composable
private fun DeleteTargetSummary(record: Record) {
    SectionPanel(tone = PanelTone.High) {
        Text(
            record.displayTitle(),
            style = ChaKakTheme.typography.bodyMedium,
            color = ChaKakTheme.colors.textPrimary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Text("분류 · ${record.category.label()}", style = ChaKakTheme.typography.bodySmall, color = ChaKakTheme.colors.textSecondary)
        LabeledTime(COMMAND_TIME_LABEL, record.createdAt)
        if (record.source == Source.MOCK) MockTag()
    }
}

@Composable
private fun DeleteActions(deleting: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
        ChaKakButton(
            text = "취소",
            onClick = onDismiss,
            modifier = Modifier.weight(1f),
            variant = ButtonVariant.Basic,
            size = ButtonSize.Medium,
            enabled = !deleting,
        )
        ChaKakButton(
            text = "삭제",
            onClick = onConfirm,
            modifier = Modifier.weight(1f),
            variant = ButtonVariant.Destructive,
            size = ButtonSize.Medium,
            icon = Icons.Filled.Delete,
            loading = deleting,
            loadingText = "삭제 중…",
        )
    }
}
