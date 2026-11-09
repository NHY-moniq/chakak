package ai.chakak.ui.theme

import androidx.compose.ui.unit.dp

/**
 * Pleos 컴포넌트 치수. 출처: `design-foundations.md` 4·5장 [Figma, connect-s px = dp 1:1, 7.1].
 * 화면 코드는 dp 리터럴 대신 이 값을 쓴다.
 */
object ChaKakDimens {
    /** Button Large 높이 */
    val buttonHeightLarge = 100.dp
    /** Button Medium 높이 */
    val buttonHeightMedium = 80.dp
    /** Button Small 높이 — 차칵 터치 영역 최소값(64)과 같음 */
    val buttonHeightSmall = 64.dp

    /** Button Large 반경 */
    val buttonRadiusLarge = 32.dp
    /** Button Medium 반경 */
    val buttonRadiusMedium = 24.dp
    /** Button Small 반경 */
    val buttonRadiusSmall = 16.dp

    /** Button Large 좌우 패딩 */
    val buttonPaddingHorizontalLarge = 40.dp
    /** Button Medium 좌우 패딩 */
    val buttonPaddingHorizontalMedium = 32.dp
    /** Button Small 좌우 패딩 */
    val buttonPaddingHorizontalSmall = 24.dp

    /** Button Large 세로 패딩 */
    val buttonPaddingVerticalLarge = 16.dp
    /** Button Medium 세로 패딩 */
    val buttonPaddingVerticalMedium = 16.dp
    /** Button Small 세로 패딩 */
    val buttonPaddingVerticalSmall = 8.dp

    /** Button Large 아이콘-라벨 간격 */
    val buttonGapLarge = 16.dp
    /** Button Medium 아이콘-라벨 간격 */
    val buttonGapMedium = 14.dp
    /** Button Small 아이콘-라벨 간격 */
    val buttonGapSmall = 12.dp

    /** Button Large 안 아이콘 */
    val iconInButtonLarge = 52.dp
    /** Button Medium 안 아이콘 */
    val iconInButtonMedium = 40.dp
    /** Button Small 안 아이콘 */
    val iconInButtonSmall = 32.dp

    /** Tabs(Segmented) 높이 */
    val tabsHeight = 100.dp
    /** Tabs 반경 */
    val tabsRadius = 32.dp
    /** Tabs 내부 패딩 */
    val tabsInnerPadding = 8.dp

    /** Popup Medium 폭 */
    val popupWidth = 808.dp
    /** Popup 반경 */
    val popupRadius = 48.dp
    /** Popup 패딩 */
    val popupPadding = 48.dp

    /** Text Field Small 높이 */
    val fieldHeightSmall = 80.dp
    /** Text Field Medium 높이 */
    val fieldHeightMedium = 100.dp

    /** line level1 — 구분선 */
    val dividerThin = 1.dp
    /** 버튼·필드 테두리 두께 */
    val dividerThick = 2.dp
    /** line level4 — 진행 표시 선 두께 */
    val lineLevel4 = 4.dp
    /**
     * 선택된 목록 행 테두리. 기본 테두리(2)보다 두껍게 해 색 외의 단서를 준다. line level4 값 차용.
     * TBD: Pleos 원본 미확인 — 목록 선택 상태 규격 없음.
     */
    val selectedBorder = 4.dp

    /** radius level4 */
    val radiusLevel4 = 12.dp
    /** radius level5 */
    val radiusLevel5 = 16.dp

    /**
     * 두 열 전환 기준 폭. foundations 7.1: 앱 Portrait 폭 628~840dp, Landscape 1256~1700dp이므로
     * S-17 Portrait(840)가 두 열로 바뀌지 않도록 그 사이 값을 쓴다. 차칵 결정값.
     */
    val twoPaneMinWidth = 1000.dp

    /**
     * 상태 배지 아이콘 원 지름. Button Small 아이콘(32)을 차용.
     * TBD: Pleos 원본 미확인 — 배지 전용 컴포넌트 없음.
     */
    val badgeIcon = 32.dp
    /** 배지 원 안의 글리프. TBD: Pleos 원본 미확인 — 48 그리드의 1/2 차용 */
    val badgeGlyph = 24.dp

    /** 기본 아이콘 그리드 48 (foundations 6). 섹션 머리 아이콘에 쓴다 */
    val iconGrid = 48.dp
    /**
     * 빈 목록·오류·제한 안내의 큰 아이콘. foundations 6의 96 아트보드 차용.
     * TBD: Pleos 원본 미확인 — 빈 상태 일러스트 규격 없음.
     */
    val stateIcon = 96.dp
}

/** 화면·패널 여백과 간격. */
object ChaKakSpacing {
    /**
     * 앱 윈도 가장자리 여백. Page Navigation 상하 패딩 40과 Button Large 좌우 패딩 40을 차용했다.
     * Popup 패딩 48은 팝업 전용이라 쓰지 않았다. TBD: Pleos 원본 미확인 — 화면 여백 공식값 없음.
     */
    val screen = 40.dp
    /** 섹션 사이 간격. Popup gap 40 차용. TBD: Pleos 원본 미확인 */
    val section = 40.dp
    /** 묶음 안 요소 간격. Button Large gap 16 차용. TBD: Pleos 원본 미확인 */
    val item = 16.dp
    /** 아이콘과 라벨 간격. Button Small gap 12 차용 */
    val inline = 12.dp
    /** 촘촘한 간격. Tabs 내부 패딩 8 차용 */
    val tight = 8.dp
    /** 패널(카드) 안쪽 여백. Button Medium 좌우 패딩 32 차용. TBD: Pleos 원본 미확인 */
    val panel = 32.dp
    /** 두 열 사이 간격. 화면 여백과 같은 40. TBD: Pleos 원본 미확인 */
    val pane = 40.dp
}
