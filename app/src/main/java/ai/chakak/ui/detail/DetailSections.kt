package ai.chakak.ui.detail

import ai.chakak.core.Observation
import ai.chakak.core.Record
import ai.chakak.core.Status
import ai.chakak.ui.component.InfoNote
import ai.chakak.ui.component.PanelTone
import ai.chakak.ui.component.SectionHeader
import ai.chakak.ui.component.SectionPanel
import ai.chakak.ui.component.StatusBadge
import ai.chakak.ui.model.captureSummary
import ai.chakak.ui.model.displayTitle
import ai.chakak.ui.model.formatDateTime
import ai.chakak.ui.model.label
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics

const val COMMAND_TIME_LABEL = "명령 수신 시각"
const val OBSERVED_TIME_LABEL = "관측 시각"
const val PLACE_LOCATION_NOTICE = "기록 당시 차량 위치이며, 주변 시설의 정확한 위치와 다를 수 있습니다."
const val MEMO_HEADER = "운전자 메모"

/**
 * 상세 머리(spec 9.7의 CaptureSummary): 이름 · 분류 · 명령 수신 시각 · 수집 요약 배지.
 * 모델 타입 [ai.chakak.ui.model.CaptureSummary]와 이름이 겹치지 않도록 Section을 붙였다.
 */
@Composable
fun CaptureSummarySection(record: Record, modifier: Modifier = Modifier) {
    val colors = ChaKakTheme.colors
    Column(modifier, verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight)) {
        Text(
            record.displayTitle(),
            style = ChaKakTheme.typography.titleLarge,
            color = colors.textPrimary,
            modifier = Modifier.semantics { heading() },
        )
        Text("분류 · ${record.category.label()}", style = ChaKakTheme.typography.bodySmall, color = colors.textSecondary)
        LabeledTime(COMMAND_TIME_LABEL, record.createdAt)
        StatusBadge(record.captureSummary())
    }
}

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
 * 저장 위치(spec 9.3). 값·상태 배지·관측 시각과 함께, 차량 위치이며 시설 위치와 다를 수 있다는
 * 안내를 항상 붙인다. 장소 기록([emphasized])은 surface/high 면과 강조 테두리, 큰 값 글꼴을 쓴다.
 * [observation]이 null이면 위치 항목을 수집하지 않은 기록이다.
 */
@Composable
fun LocationSection(observation: Observation?, emphasized: Boolean, modifier: Modifier = Modifier) {
    val colors = ChaKakTheme.colors
    SectionPanel(
        modifier = modifier,
        tone = if (emphasized) PanelTone.High else PanelTone.Basic,
        borderColor = if (emphasized) colors.informativePositive else null,
    ) {
        SectionHeader("저장 위치", Icons.Filled.Place, iconTint = if (emphasized) colors.informativePositive else null)
        if (observation == null) {
            Text("위치 항목을 수집하지 않은 기록입니다.", style = ChaKakTheme.typography.bodySmall, color = colors.textSecondary)
        } else {
            LocationValue(observation, emphasized)
        }
        InfoNote(PLACE_LOCATION_NOTICE, Icons.Filled.Info)
    }
}

@Composable
private fun LocationValue(observation: Observation, emphasized: Boolean) {
    observation.displayValue()?.let {
        Text(
            it,
            style = if (emphasized) ChaKakTheme.typography.titleSmall else ChaKakTheme.typography.bodyMedium,
            color = ChaKakTheme.colors.textPrimary,
        )
    }
    StatusBadge(observation.status)
    observation.observedAt?.let { LabeledTime(OBSERVED_TIME_LABEL, it) }
}

/**
 * 표시할 값. 값이 없으면 null을 돌려주고 사유는 상태 배지 하나로만 보여준다(중복 문구 금지).
 * OK인데 값이 비어 있는 경우만 따로 알린다.
 */
fun Observation.displayValue(): String? = when {
    value != null -> value
    status == Status.OK -> "값이 비어 있습니다"
    else -> null
}

/** 운전자 메모(spec 9.4). 차량 데이터와 구분되도록 surface/high 면과 사람 아이콘을 쓴다. */
@Composable
fun MemoSection(memo: String, modifier: Modifier = Modifier) {
    val colors = ChaKakTheme.colors
    SectionPanel(modifier = modifier, tone = PanelTone.High) {
        SectionHeader(MEMO_HEADER, Icons.Filled.Person)
        if (memo.isBlank()) {
            Text("메모가 없습니다. ‘편집’에서 추가할 수 있습니다.", style = ChaKakTheme.typography.bodySmall, color = colors.textTertiary)
        } else {
            Text(memo, style = ChaKakTheme.typography.bodyLarge, color = colors.textPrimary)
        }
    }
}
