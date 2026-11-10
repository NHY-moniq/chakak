package ai.chakak.ui.detail

import ai.chakak.core.Category
import ai.chakak.core.Observation
import ai.chakak.core.Record
import ai.chakak.core.Status
import ai.chakak.ui.component.ButtonSize
import ai.chakak.ui.component.ButtonVariant
import ai.chakak.ui.component.CategoryChip
import ai.chakak.ui.component.ChaKakButton
import ai.chakak.ui.model.EMPTY_VALUE
import ai.chakak.ui.model.currentTimelineClock
import ai.chakak.ui.model.displayTitle
import ai.chakak.ui.model.formatClockTime
import ai.chakak.ui.model.formatDateTime
import ai.chakak.ui.model.commandReceivedText
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics

const val COMMAND_TIME_LABEL = "명령 수신 시각"
const val OBSERVED_TIME_LABEL = "관측 시각"
const val MEMO_HEADER = "운전자 메모"
const val NO_MEMO = "메모가 없습니다. ‘편집’에서 추가할 수 있습니다."
const val NOT_COLLECTED_ITEM = "수집하지 않은 항목"
const val EDIT_LABEL = "편집"
const val DELETE_LABEL = "삭제"

/** "라벨 · 시각" 메타 정보. Label Medium, text tertiary */
@Composable
fun LabeledTime(label: String, millis: Long, modifier: Modifier = Modifier) {
    Text(
        "$label · ${formatDateTime(millis)}",
        style = ChaKakTheme.typography.labelMedium,
        color = ChaKakTheme.colors.textTertiary,
        modifier = modifier,
    )
}

/**
 * 표시할 값. 값이 없으면 null을 돌려주고 사유는 상태 배지 하나로만 보여준다(중복 문구 금지).
 * OK인데 값이 비어 있는 경우만 따로 알린다.
 */
fun Observation.displayValue(): String? = when {
    value != null -> value
    status == Status.OK -> EMPTY_VALUE
    else -> null
}

/**
 * 상세 맨 위 줄: 분류 칩 + 편집(외곽선) · 삭제(빨간 테두리). 둘 다 높이 64(Button Small)이며
 * 아이콘과 함께 글자 라벨을 보인다. 큰 글꼴에서는 다음 줄로 넘어간다.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailTopRow(category: Category, onEdit: () -> Unit, onDelete: () -> Unit, enabled: Boolean) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item),
    ) {
        CategoryChip(category, Modifier.align(Alignment.CenterVertically))
        FlowRow(Modifier.align(Alignment.CenterVertically), horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
            ChaKakButton(EDIT_LABEL, onEdit, variant = ButtonVariant.Outlined, size = ButtonSize.Small, icon = Icons.Filled.Edit, enabled = enabled)
            ChaKakButton(DELETE_LABEL, onDelete, variant = ButtonVariant.Destructive, size = ButtonSize.Small, icon = Icons.Filled.Delete, enabled = enabled)
        }
    }
}

/** 장소·미분류 머리: 큰 시각(accentDeep) · 이름 · "명령 수신 날짜 시각" */
@Composable
fun PlaceHeading(record: Record) {
    val clock = currentTimelineClock()
    Column(verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight)) {
        Text(
            formatClockTime(record.createdAt, clock),
            style = ChaKakTheme.typography.headlineLarge,
            color = ChaKakTheme.colors.brand.accentDeep,
        )
        RecordTitle(record)
        MetaLine(commandReceivedText(record.createdAt, clock))
    }
}

/** 차량 상태 머리: "명령 수신 날짜 시각" · 이름 */
@Composable
fun VehicleHeading(record: Record) {
    val clock = currentTimelineClock()
    Column(verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight)) {
        MetaLine(commandReceivedText(record.createdAt, clock))
        RecordTitle(record)
    }
}

@Composable
private fun RecordTitle(record: Record) {
    Text(
        record.displayTitle(),
        style = ChaKakTheme.typography.headlineSmall,
        color = ChaKakTheme.colors.textPrimary,
        modifier = Modifier.semantics { heading() },
    )
}

@Composable
private fun MetaLine(text: String) {
    Text(text, style = ChaKakTheme.typography.labelMedium, color = ChaKakTheme.colors.textTertiary)
}

/** 항목별 관측 시각 줄("관측 오전 9:34"). null이면 그리지 않는다. Label Medium, text tertiary */
@Composable
fun ObservedTime(text: String?, modifier: Modifier = Modifier) {
    text?.let { Text(it, style = ChaKakTheme.typography.labelMedium, color = ChaKakTheme.colors.textTertiary, modifier = modifier) }
}

/** 굵은 작은 라벨(섹션 이름). 접근성 heading */
@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = ChaKakTheme.typography.labelMediumStrong,
        color = ChaKakTheme.colors.textSecondary,
        modifier = modifier.semantics { heading() },
    )
}

/** 운전자 메모(spec 9.4): 라벨 + 큰 SemiBold 본문. 비어 있으면 편집 안내 */
@Composable
fun MemoBlock(memo: String, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline)) {
        SectionLabel(MEMO_HEADER)
        MemoText(memo, large = true)
    }
}

@Composable
fun MemoText(memo: String, large: Boolean) {
    val colors = ChaKakTheme.colors
    if (memo.isBlank()) {
        Text(NO_MEMO, style = ChaKakTheme.typography.bodySmall, color = colors.textTertiary)
    } else {
        val style = if (large) ChaKakTheme.typography.memoLarge else ChaKakTheme.typography.memoSmall
        Text(memo, style = style, color = colors.textPrimary)
    }
}
