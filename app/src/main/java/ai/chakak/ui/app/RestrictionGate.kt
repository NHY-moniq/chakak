package ai.chakak.ui.app

import ai.chakak.ui.component.InfoNote
import ai.chakak.ui.component.MockBanner
import ai.chakak.ui.component.PanelTone
import ai.chakak.ui.component.SectionPanel
import ai.chakak.ui.component.StateIcon
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign

const val RESTRICTION_TITLE = "주차 후 이용할 수 있습니다"
const val RESTRICTION_ACTIONS = "제한되는 기능: 현재 상태 기록 · 기록 목록 · 필터 · 상세 · 편집 · 삭제"
const val RESTRICTION_REASON = "주행 중이거나 주차 상태를 확인할 수 없어 기록을 열지 않습니다."
const val RESTRICTION_KEEP = "저장된 기록은 그대로 유지됩니다."

/**
 * 주행 제한 게이트(spec 10 허용 매트릭스). 주행 중·상태 미확인이면 [content] 대신 안내를 보여준다.
 * 창 insets는 상위 [AppWindowScaffold]가 이미 적용한다.
 */
@Composable
fun RestrictionGate(allowed: Boolean, mock: Boolean, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    if (allowed) {
        content()
    } else {
        RestrictionNotice(mock, modifier)
    }
}

@Composable
fun RestrictionNotice(mock: Boolean, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier, contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier
                .widthIn(max = ChaKakDimens.popupWidth)
                .fillMaxWidth()
                .heightIn(min = maxHeight)
                .verticalScroll(rememberScrollState())
                .semantics(mergeDescendants = true) { liveRegion = LiveRegionMode.Polite },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.section, Alignment.CenterVertically),
        ) {
            if (mock) MockBanner()
            StateIcon(Icons.Filled.Lock, tint = ChaKakTheme.colors.iconSecondary)
            Text(
                RESTRICTION_TITLE,
                style = ChaKakTheme.typography.headlineSmall,
                color = ChaKakTheme.colors.textPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.semantics { heading() },
            )
            SectionPanel(tone = PanelTone.High) {
                InfoNote(RESTRICTION_ACTIONS, Icons.Filled.Lock)
                InfoNote(RESTRICTION_REASON, Icons.Filled.Info)
                InfoNote(RESTRICTION_KEEP, Icons.Filled.CheckCircle)
            }
        }
    }
}
