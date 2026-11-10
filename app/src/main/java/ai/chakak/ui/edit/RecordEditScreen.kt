package ai.chakak.ui.edit

import ai.chakak.core.Record
import ai.chakak.core.Source
import ai.chakak.ui.component.ButtonSize
import ai.chakak.ui.component.ButtonVariant
import ai.chakak.ui.component.ChaKakButton
import ai.chakak.ui.component.InfoNote
import ai.chakak.ui.component.MockBanner
import ai.chakak.ui.component.ScreenTopBar
import ai.chakak.ui.library.RecordCategoryTabs
import ai.chakak.ui.model.EditDraft
import ai.chakak.ui.model.MAX_MEMO_LENGTH
import ai.chakak.ui.model.MAX_TITLE_LENGTH
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakShapes
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * 편집 초안을 기록 id별로 보관하는 진입점. 회전 후에도 초안이 남는다.
 * 기록 간 초안 분리는 상위의 `SaveableStateHolder`가 맡는다.
 */
@Composable
fun RecordEditRoute(
    record: Record,
    saving: Boolean,
    saveFailed: Boolean,
    enabled: Boolean,
    onSave: (EditDraft) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    showMockBanner: Boolean = true,
) {
    var draft by rememberSaveable(record.id, stateSaver = EditDraft.Saver) { mutableStateOf(EditDraft.from(record)) }
    RecordEditScreen(record, draft, { draft = it }, saving, saveFailed, enabled, { onSave(draft) }, onCancel, modifier, showMockBanner)
}

/**
 * 기록 편집(spec 9.5): 제목 · 분류 · 메모 · 저장. 저장 중에는 입력과 버튼을 잠그고,
 * 실패하면 초안을 유지한 채 원인을 표시한다. IME가 열려도 저장 버튼이 보이도록 하단에 고정한다.
 */
@Composable
fun RecordEditScreen(
    record: Record,
    draft: EditDraft,
    onDraftChange: (EditDraft) -> Unit,
    saving: Boolean,
    saveFailed: Boolean,
    enabled: Boolean,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    showMockBanner: Boolean = true,
) {
    val editable = enabled && !saving
    Column(modifier.imePadding(), verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
        ScreenTopBar(title = "기록 편집", backLabel = "취소", onBack = onCancel, backEnabled = !saving)
        Column(
            modifier = Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()).padding(bottom = ChaKakSpacing.item),
            verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.section),
        ) {
            if (showMockBanner && record.source == Source.MOCK) MockBanner()
            EditFields(draft, onDraftChange, editable)
            if (saveFailed) SaveError()
        }
        ChaKakButton(
            text = "변경 저장",
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            variant = ButtonVariant.Filled,
            size = ButtonSize.Large,
            icon = Icons.Filled.Check,
            enabled = editable && draft.differsFrom(record),
            loading = saving,
            loadingText = "저장 중…",
        )
    }
}

@Composable
private fun EditFields(draft: EditDraft, onDraftChange: (EditDraft) -> Unit, editable: Boolean) {
    EditTextField(
        value = draft.title,
        onValueChange = { onDraftChange(draft.withTitle(it)) },
        label = "제목",
        maxLength = MAX_TITLE_LENGTH,
        enabled = editable,
        singleLine = true,
    )
    Column(verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight)) {
        Text("분류", style = ChaKakTheme.typography.bodySmallStrong, color = ChaKakTheme.colors.textSecondary)
        RecordCategoryTabs(selected = draft.category, onSelect = { onDraftChange(draft.withCategory(it)) }, enabled = editable)
    }
    EditTextField(
        value = draft.memo,
        onValueChange = { onDraftChange(draft.withMemo(it)) },
        label = "운전자 메모",
        maxLength = MAX_MEMO_LENGTH,
        enabled = editable,
        singleLine = false,
    )
}

@Composable
private fun EditTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxLength: Int,
    enabled: Boolean,
    singleLine: Boolean,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth().heightIn(min = ChaKakDimens.fieldHeightSmall),
        enabled = enabled,
        label = { Text(label) },
        supportingText = { Text("${value.length}/$maxLength") },
        singleLine = singleLine,
        minLines = if (singleLine) 1 else MEMO_MIN_LINES,
        textStyle = ChaKakTheme.typography.bodyLarge,
        shape = ChaKakShapes.buttonSmall,
        colors = pleosFieldColors(),
    )
}

/** 테두리 divider/secondary 2(foundations 5 Dropdown·Field), 포커스는 informative/positive */
@Composable
private fun pleosFieldColors(): TextFieldColors {
    val colors = ChaKakTheme.colors
    return OutlinedTextFieldDefaults.colors(
        focusedTextColor = colors.textPrimary,
        unfocusedTextColor = colors.textPrimary,
        disabledTextColor = colors.textTertiary,
        focusedBorderColor = colors.informativePositive,
        unfocusedBorderColor = colors.dividerTertiary,
        disabledBorderColor = colors.dividerSecondary,
        focusedLabelColor = colors.textSecondary,
        unfocusedLabelColor = colors.textSecondary,
        disabledLabelColor = colors.textTertiary,
        cursorColor = colors.textPrimary,
        focusedSupportingTextColor = colors.textTertiary,
        unfocusedSupportingTextColor = colors.textTertiary,
        disabledSupportingTextColor = colors.textTertiary,
        focusedContainerColor = colors.surfaceBasic,
        unfocusedContainerColor = colors.surfaceBasic,
        disabledContainerColor = colors.surfaceHigh,
    )
}

@Composable
private fun SaveError() {
    InfoNote(
        "저장하지 못했습니다. 입력한 내용은 남아 있으니 다시 시도해 주세요.",
        Icons.Filled.Warning,
        iconTint = ChaKakTheme.colors.regulationRed,
    )
}

private const val MEMO_MIN_LINES = 3
