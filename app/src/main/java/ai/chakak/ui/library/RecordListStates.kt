package ai.chakak.ui.library

import ai.chakak.core.Category
import ai.chakak.ui.component.ButtonSize
import ai.chakak.ui.component.ButtonVariant
import ai.chakak.ui.component.ChaKakButton
import ai.chakak.ui.component.StateIcon
import ai.chakak.ui.component.StatePlaceholder
import ai.chakak.ui.model.label
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics

/** 조회 중. 시간 제한이 지나면 상위 상태가 오류로 바뀐다(무한 로딩 없음). */
@Composable
fun RecordListLoading(modifier: Modifier = Modifier) {
    ListState(modifier) {
        StatePlaceholder(
            title = "기록을 불러오는 중입니다",
            body = "이 차량에 저장된 기록을 읽고 있습니다.",
            icon = {
                CircularProgressIndicator(
                    modifier = Modifier.size(ChaKakDimens.stateIcon),
                    color = ChaKakTheme.colors.informativePositive,
                    trackColor = ChaKakTheme.colors.surfaceHigh,
                    strokeWidth = ChaKakDimens.lineLevel4,
                )
            },
        )
    }
}

/** 조회 오류. 빈 목록과 다르게 표시하고 다시 시도할 수 있게 한다. */
@Composable
fun RecordListError(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    ListState(modifier.semantics { liveRegion = LiveRegionMode.Polite }) {
        StatePlaceholder(
            title = "기록을 불러오지 못했습니다",
            body = "$message 저장된 기록은 삭제되지 않았습니다.",
            icon = { StateIcon(Icons.Filled.Warning, tint = ChaKakTheme.colors.regulationRed) },
            action = {
                ChaKakButton("다시 시도", onRetry, variant = ButtonVariant.Basic, size = ButtonSize.Medium, icon = Icons.Filled.Refresh)
            },
        )
    }
}

/** 저장된 기록이 하나도 없음 */
@Composable
fun RecordListEmpty(modifier: Modifier = Modifier) {
    ListState(modifier) {
        StatePlaceholder(
            title = "아직 기록이 없습니다",
            body = "사진 없이 위치와 차량 상태를 기록합니다. 주차 중에는 ‘현재 상태 기록’으로 남길 수 있습니다.",
            icon = { StateIcon(Icons.AutoMirrored.Filled.List) },
        )
    }
}

/** 기록은 있지만 선택한 분류에 해당하는 것이 없음 */
@Composable
fun RecordListFilteredEmpty(category: Category, modifier: Modifier = Modifier) {
    ListState(modifier) {
        StatePlaceholder(
            title = "${category.label()} 기록이 없습니다",
            body = "‘전체’ 탭에서 다른 기록을 확인할 수 있습니다.",
            icon = { StateIcon(Icons.Filled.Search) },
        )
    }
}

/** 두 열에서 아직 기록을 고르지 않았을 때 오른쪽 영역 */
@Composable
fun NoSelectionPlaceholder(modifier: Modifier = Modifier) {
    CenteredState(modifier) {
        StatePlaceholder(
            title = "기록을 선택하세요",
            body = "왼쪽 목록에서 기록을 고르면 위치·차량 상태·메모를 확인할 수 있습니다.",
            icon = { StateIcon(Icons.Filled.Info) },
        )
    }
}

/**
 * 목록 자리 상태(로딩·오류·빈 목록). 머리 영역과 같은 스크롤 목록 안의 한 항목이라
 * 남은 높이를 채우지 않고 탭 바로 아래에 가로 가운데로 둔다.
 */
@Composable
private fun ListState(modifier: Modifier, content: @Composable () -> Unit) {
    Box(modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) { content() }
}

/** 가운데 정렬하되, 높이가 모자라면(큰 글꼴·낮은 창) 스크롤한다. */
@Composable
private fun CenteredState(modifier: Modifier, content: @Composable () -> Unit) {
    BoxWithConstraints(modifier) {
        Column(
            modifier = Modifier.fillMaxWidth().heightIn(min = maxHeight).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) { content() }
    }
}
