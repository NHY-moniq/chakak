package ai.chakak.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/**
 * 시안 A "저널 타임라인" 치수. 출처: `docs/design/mockups/Main.dc.html`, `VehicleDetail.dc.html` (px = dp 1:1).
 * Pleos 버튼·탭 값과 같은 것은 [ChaKakDimens]를 그대로 쓰고, 여기에는 시안에서 새로 정한 값만 둔다.
 * 차칵 브랜드(시안 A), Pleos 토큰 아님.
 */
object JournalDimens {
    /** 앱 머리 로고 타일 */
    val headerMark = 72.dp
    /** 로고 타일 안 셔터 마크 */
    val headerMarkGlyph = 40.dp

    /** 타임라인 시각 열 폭(오른쪽 정렬) */
    val timeColumnWidth = 112.dp
    /** 시각 열 위 여백. 카드 첫 줄과 높이를 맞춘다 */
    val timeColumnTopPadding = 28.dp
    /** 시각 열 · 레일 · 카드 사이 간격 */
    val timelineGap = 20.dp
    /** 세로 레일 두께 */
    val rail = 4.dp

    /** 기록 카드 안쪽 여백 */
    val cardPadding = 24.dp
    /** 아이콘 타일·타이어 값 타일 반경 */
    val tileRadius = 20.dp
    /** 카드 분류 아이콘 타일 */
    val categoryTile = 64.dp
    /** 분류 아이콘 타일 안 글리프 */
    val categoryGlyph = 36.dp
    /** 카드 제목·메타 줄 간격 */
    val cardTextGap = 4.dp

    /** 두 열에서 목록 열 최대 폭 */
    val listPaneMaxWidth = 720.dp
    /** 상세 면 안쪽 여백(넓은 창). 좁은 창은 [ChaKakSpacing.panel] */
    val detailPadding = 48.dp
    /** 상세 안 큰 묶음 사이 간격 */
    val detailGap = 32.dp
    /** 이 폭보다 좁은 상세는 타이어 패널을 세로로 쌓고 큰 숫자를 줄인다 */
    val detailCompactWidth = 700.dp

    /** 분류 칩 높이 */
    val chipHeight = 48.dp
    /** 분류 칩 좌우 여백 */
    val chipPaddingHorizontal = 20.dp
    /** 분류 칩 아이콘 */
    val chipGlyph = 28.dp

    /** 위치 패널 큰 아이콘 타일 */
    val locationTile = 96.dp
    /** 위치 타일 안 글리프 */
    val locationGlyph = 52.dp

    /** 경고등 칩 높이(터치 영역 최소값과 같음) */
    val telltaleChipHeight = 64.dp

    /** 타이어 그림 영역 */
    val tireDiagramWidth = 180.dp
    val tireDiagramHeight = 240.dp
    /** 차체(위에서 본 모습) */
    val carBodyWidth = 90.dp
    val carBodyHeight = 208.dp
    val carBodyRadius = 36.dp
    val carBodyBorder = 3.dp
    /** 바퀴 */
    val wheelWidth = 22.dp
    val wheelHeight = 52.dp
    val wheelRadius = 8.dp
    /** 그림 가장자리에서 바퀴까지 */
    val wheelInsetHorizontal = 18.dp
    val wheelInsetVertical = 40.dp
    /** 값 없음 점선: 선 길이와 틈 */
    val dashOn = 8.dp
    val dashOff = 6.dp
    /** 타이어 값 타일 여백 */
    val tireTilePaddingHorizontal = 20.dp
    val tireTilePaddingVertical = 16.dp

    /** 선택 탭 그림자 높이 */
    val selectedTabElevation = 2.dp
}

/** 시안 A 형태 */
object JournalShapes {
    /** 기록 카드·요약 타일 24 (= Button Medium) */
    val card: CornerBasedShape = ChaKakShapes.buttonMedium
    /** 분류 아이콘 타일·타이어 값 타일 20 */
    val tile: CornerBasedShape = RoundedCornerShape(JournalDimens.tileRadius)
    /** 위치 타일 28 */
    val locationTile: CornerBasedShape = RoundedCornerShape(28.dp)
    /** 앱 머리 로고 24 */
    val headerMark: CornerBasedShape = ChaKakShapes.buttonMedium
    /** 레일 끝 */
    val rail: CornerBasedShape = RoundedCornerShape(2.dp)
    /** 상세 면(넓은 창) 48 = Popup */
    val detailSurface: CornerBasedShape = ChaKakShapes.popup
    /** 상세 면(좁은 창) 32 */
    val detailSurfaceCompact: CornerBasedShape = ChaKakShapes.buttonLarge
    /** 타이어 그림 차체 */
    val carBody: CornerBasedShape = RoundedCornerShape(JournalDimens.carBodyRadius)
    /** 타이어 그림 바퀴 */
    val wheel: CornerBasedShape = RoundedCornerShape(JournalDimens.wheelRadius)
    /** 위치 패널 32 */
    val panel: CornerBasedShape = ChaKakShapes.buttonLarge
}

/** 두 열 폭 비율 상한: 목록은 남은 폭의 절반을 넘지 않는다 */
const val LIST_PANE_MAX_FRACTION = 0.5f

/** 이 글꼴 배율보다 크면 차량 상세의 큰 숫자 칸과 경고등 칸을 나란히 두지 않고 세로로 쌓는다 */
const val STACK_FONT_SCALE = 1.2f
