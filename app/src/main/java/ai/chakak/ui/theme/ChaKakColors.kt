package ai.chakak.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance

/**
 * Pleos Connect 의미 색상 토큰.
 *
 * 출처: `docs/pleos/design-foundations.md` 2.2 [Figma] — `Pleos_Connect_design_assets_215.fig` 변수.
 * 각 프로퍼티 KDoc의 이름이 Figma 토큰 이름이다. 화면 코드는 HEX 대신 이 토큰만 쓴다.
 *
 * 대비(2.3): text primary/secondary/tertiary는 배경·표면 위에서 4.5:1 이상이다.
 * [textQuaternary]는 4.5:1 미달이므로 의미 있는 정보에 쓰지 않는다.
 * [regulationRed]·[informativeActive]는 Light에서 텍스트 대비가 부족하므로 아이콘·점·배경에만 쓴다.
 * Pleos에는 error·warning·success 토큰이 없다. regulation·informative를 상태에 빌려 쓰는 것은 차칵의 선택이다.
 */
@Immutable
data class ChaKakColors(
    /** background/primary — 앱 배경 */
    val backgroundPrimary: Color,
    /** background/secondary — 보조 배경 */
    val backgroundSecondary: Color,
    /** background/popup — 시스템 팝업(삭제 확인) */
    val backgroundPopup: Color,
    /** surface/basic — 기록 카드, Page Navigation */
    val surfaceBasic: Color,
    /** surface/high — 강조 패널 */
    val surfaceHigh: Color,
    /** surface/accent — 선택된 행 */
    val surfaceAccent: Color,
    /** text/dark/primary — 본문·제목 */
    val textPrimary: Color,
    /** text/dark/secondary — 보조 본문 */
    val textSecondary: Color,
    /** text/dark/tertiary — 시각·메타 정보 */
    val textTertiary: Color,
    /** text/dark/quaternary — 비활성 라벨 전용(대비 미달) */
    val textQuaternary: Color,
    /** text/light/primary — filled 버튼 위 글자 */
    val textOnFilled: Color,
    /** icon/dark/primary */
    val iconPrimary: Color,
    /** icon/dark/secondary */
    val iconSecondary: Color,
    /** icon/dark/tertiary */
    val iconTertiary: Color,
    /** icon/dark/quaternary — 비활성 아이콘 전용 */
    val iconQuaternary: Color,
    /** divider/primary */
    val dividerPrimary: Color,
    /** divider/secondary — 버튼·필드 테두리 */
    val dividerSecondary: Color,
    /** divider/tertiary — Page Navigation 하단선 */
    val dividerTertiary: Color,
    /** button/basic/enabled — 보조 버튼 */
    val buttonBasicEnabled: Color,
    /** button/basic/pressed */
    val buttonBasicPressed: Color,
    /** button/basic/disabled — divider/secondary 테두리와 함께 사용 */
    val buttonBasicDisabled: Color,
    /** button/filled/enabled — 주요 버튼 */
    val buttonFilledEnabled: Color,
    /** button/filled/pressed */
    val buttonFilledPressed: Color,
    /** button/filled/disabled */
    val buttonFilledDisabled: Color,
    /** regulation/red — 오류·삭제. Light 본문 텍스트 사용 금지 */
    val regulationRed: Color,
    /** informative/positive (= regulation/blue) — 강조·선택·진행 */
    val informativePositive: Color,
    /** informative/active — 완료. Light 텍스트 사용 금지 */
    val informativeActive: Color,
    val isDark: Boolean,
) {
    /** [fill] 위에 올릴 글리프 색. textPrimary와 textOnFilled 중 대비가 높은 쪽을 고른다. */
    fun contentOn(fill: Color): Color =
        if (contrastRatio(textPrimary, fill) >= contrastRatio(textOnFilled, fill)) textPrimary else textOnFilled

    companion object {
        /** basic_900 (Light) — text/dark 계열의 기준색 */
        private val Ink = Color(0xFF131417)
        /** basic_00 (Light) — text/light 계열의 기준색 */
        private val White = Color(0xFFFFFFFF)

        val Light = ChaKakColors(
            backgroundPrimary = Color(0xFFF7F8FA),
            backgroundSecondary = White,
            backgroundPopup = White,
            surfaceBasic = White,
            surfaceHigh = Color(0xFFEDEEF2),
            surfaceAccent = Color(0xFFE0E1E6),
            textPrimary = Ink,
            textSecondary = Ink.copy(alpha = 0.84f),
            textTertiary = Ink.copy(alpha = 0.64f),
            textQuaternary = Ink.copy(alpha = 0.32f),
            textOnFilled = White,
            iconPrimary = Ink,
            iconSecondary = Ink.copy(alpha = 0.84f),
            iconTertiary = Ink.copy(alpha = 0.64f),
            iconQuaternary = Ink.copy(alpha = 0.32f),
            dividerPrimary = Ink.copy(alpha = 0.05f),
            dividerSecondary = Ink.copy(alpha = 0.10f),
            dividerTertiary = Ink.copy(alpha = 0.20f),
            buttonBasicEnabled = Ink.copy(alpha = 0.05f),
            buttonBasicPressed = Ink.copy(alpha = 0.10f),
            buttonBasicDisabled = White.copy(alpha = 0.20f),
            buttonFilledEnabled = Color(0xFF44464E),
            buttonFilledPressed = Color(0xFF313236),
            buttonFilledDisabled = Ink.copy(alpha = 0.05f),
            regulationRed = Color(0xFFF62E24),
            informativePositive = Color(0xFF0064FF),
            informativeActive = Color(0xFF02C265),
            isDark = false,
        )

        val Dark = ChaKakColors(
            backgroundPrimary = Color(0xFF1D1E21),
            backgroundSecondary = Ink,
            backgroundPopup = Color(0xFF313236),
            surfaceBasic = Color(0xFF1D1E21),
            surfaceHigh = Color(0xFF313236),
            surfaceAccent = Color(0xFF44464E),
            textPrimary = White,
            textSecondary = White.copy(alpha = 0.84f),
            textTertiary = White.copy(alpha = 0.64f),
            textQuaternary = White.copy(alpha = 0.32f),
            textOnFilled = Ink,
            iconPrimary = White,
            iconSecondary = White.copy(alpha = 0.84f),
            iconTertiary = White.copy(alpha = 0.64f),
            iconQuaternary = White.copy(alpha = 0.32f),
            dividerPrimary = White.copy(alpha = 0.05f),
            dividerSecondary = White.copy(alpha = 0.10f),
            dividerTertiary = White.copy(alpha = 0.20f),
            buttonBasicEnabled = White.copy(alpha = 0.05f),
            buttonBasicPressed = White.copy(alpha = 0.10f),
            buttonBasicDisabled = Ink.copy(alpha = 0.20f),
            buttonFilledEnabled = Color(0xFFE0E1E6),
            buttonFilledPressed = Color(0xFFC7C9CE),
            buttonFilledDisabled = White.copy(alpha = 0.05f),
            regulationRed = Color(0xFFFF4339),
            informativePositive = Color(0xFF0082FF),
            informativeActive = Color(0xFF00E074),
            isDark = true,
        )
    }
}

/** WCAG 2.x 대비. 반투명 [foreground]는 [background] 위에 합성한 뒤 계산한다. */
fun contrastRatio(foreground: Color, background: Color): Float {
    val fg = foreground.compositeOver(background).luminance()
    val bg = background.luminance()
    val lighter = maxOf(fg, bg)
    val darker = minOf(fg, bg)
    return (lighter + 0.05f) / (darker + 0.05f)
}
