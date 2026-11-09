package ai.chakak.ui.detail

import ai.chakak.core.Observation
import ai.chakak.ui.component.InfoNote
import ai.chakak.ui.component.SectionHeader
import ai.chakak.ui.component.SectionPanel
import ai.chakak.ui.component.StatusBadge
import ai.chakak.ui.model.observationKeyLabel
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics

const val OBSERVATION_NOTICE = "수집 시점의 관측값입니다. 진단 결과나 실시간 차량 상태가 아닙니다."

/**
 * 차량 상태 항목 목록(spec 9.4). 미지원·조회 실패도 숨기지 않고 항목별 상태로 보여준다.
 * 위치·주소·전용 영역이 있는 항목은 호출 측이 제외해서 넘긴다(정해진 영역이 없는 항목만).
 */
@Composable
fun VehicleObservationList(observations: List<Observation>, modifier: Modifier = Modifier) {
    SectionPanel(modifier) {
        SectionHeader("차량 상태", Icons.Filled.Build)
        InfoNote(OBSERVATION_NOTICE, Icons.Filled.Info)
        if (observations.isEmpty()) {
            Text("수집한 차량 상태 항목이 없습니다.", style = ChaKakTheme.typography.bodySmall, color = ChaKakTheme.colors.textSecondary)
        }
        observations.forEach { observation ->
            HorizontalDivider(thickness = ChaKakDimens.dividerThin, color = ChaKakTheme.colors.dividerSecondary)
            ObservationRow(observation)
        }
    }
}

/** 항목 한 줄: 이름과 상태 배지, 값(있을 때만), 관측 시각. 사유는 배지에만 쓴다. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ObservationRow(observation: Observation, modifier: Modifier = Modifier) {
    val colors = ChaKakTheme.colors
    Column(
        modifier = modifier.fillMaxWidth().semantics(mergeDescendants = true) {},
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight),
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight),
        ) {
            Text(observationKeyLabel(observation.key), style = ChaKakTheme.typography.bodySmallStrong, color = colors.textSecondary)
            StatusBadge(observation.status)
        }
        observation.displayValue()?.let {
            Text(it, style = ChaKakTheme.typography.bodyMedium, color = colors.textPrimary)
        }
        observation.observedAt?.let { LabeledTime(OBSERVED_TIME_LABEL, it) }
    }
}
