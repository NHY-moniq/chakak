package ai.chakak.ui.detail

import ai.chakak.core.Category
import ai.chakak.core.Record
import ai.chakak.core.Source
import ai.chakak.ui.component.MockBanner
import ai.chakak.ui.component.ScreenTopBar
import ai.chakak.ui.model.guidanceTarget
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import ai.chakak.ui.theme.JournalDimens
import ai.chakak.ui.theme.JournalShapes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

const val DETAIL_TITLE = "기록 상세"
const val BACK_TO_LIST = "목록"

/**
 * 기록 상세(읽기 전용, spec 9.3·9.4, 시안 A). 상세 면 안에서:
 * 분류 칩 + 편집·삭제 → 테스트 데이터 배너 → 분류별 본문(장소·미분류: [PlaceDetailBody], 차량 상태: [VehicleDetailBody]).
 * [onBack]이 null이면(두 열) 상단 "목록" 줄을 숨긴다.
 * [guidance]가 있고 기록에 좌표가 있으면 위치 카드를 누를 때 목적지 안내 확인 팝업을 띄운다.
 * 폭이 [JournalDimens.detailCompactWidth]보다 좁으면 여백·큰 숫자를 줄이고 타이어 패널·두 칸 묶음을 세로로 쌓는다.
 */
@Composable
fun RecordDetailScreen(
    record: Record,
    onBack: (() -> Unit)?,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    actionsEnabled: Boolean,
    modifier: Modifier = Modifier,
    showMockBanner: Boolean = true,
    guidance: GuidanceControl? = null,
) {
    // 좌표가 있는 기록만 안내할 수 있다. 팝업은 기록별로 열리고, 요청이 끝나면(성공·실패 모두) 닫고 결과를 비운다.
    val target = remember(record) { record.guidanceTarget() }
    var confirming by rememberSaveable(record.id) { mutableStateOf(false) }
    val running = guidance?.running == true
    LaunchedEffect(guidance?.finished) {
        if (guidance?.finished == true) { confirming = false; guidance.onFinished() }
    }
    val onGuide: (() -> Unit)? = if (guidance != null && target != null && guidance.enabled) ({ confirming = true }) else null
    if (confirming && guidance != null && target != null) {
        GuidanceDialog(target, running, onConfirm = { guidance.onStart(target) }, onDismiss = { confirming = false })
    }
    Column(modifier, verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
        if (onBack != null) ScreenTopBar(title = DETAIL_TITLE, backLabel = BACK_TO_LIST, onBack = onBack)
        BoxWithConstraints(Modifier.weight(1f).fillMaxWidth()) {
            val compact = maxWidth < JournalDimens.detailCompactWidth
            DetailSurface(compact, record.id) {
                DetailTopRow(record.category, onEdit, onDelete, actionsEnabled)
                if (showMockBanner && record.source == Source.MOCK) MockBanner()
                if (record.category == Category.VEHICLE_STATUS) {
                    VehicleDetailBody(record, compact, onGuide)
                } else {
                    PlaceDetailBody(record, compact, onGuide)
                }
            }
        }
    }
}

/**
 * 상세 면: background/secondary(Light 흰색, Dark basic_900). 안쪽 위치·타이어 패널은 background/primary라
 * 두 테마 모두 면이 구분된다. 내용은 면 안에서 스크롤한다.
 */
@Composable
private fun DetailSurface(compact: Boolean, recordId: String, content: @Composable ColumnScope.() -> Unit) {
    // 다른 기록을 열면 맨 위부터 보여준다(이전 기록의 스크롤 위치를 물려받지 않는다).
    val scroll = key(recordId) { rememberScrollState() }
    val shape = if (compact) JournalShapes.detailSurfaceCompact else JournalShapes.detailSurface
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape)
            .background(ChaKakTheme.colors.backgroundSecondary, shape)
            .verticalScroll(scroll)
            .padding(if (compact) ChaKakSpacing.panel else JournalDimens.detailPadding),
        verticalArrangement = Arrangement.spacedBy(JournalDimens.detailGap),
        content = content,
    )
}
