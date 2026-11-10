package ai.chakak.ui.theme

import ai.chakak.R
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

/**
 * Asta Sans 가변 폰트(wght 300~800). `res/font/asta_sans_variable.ttf`,
 * 출처 google/fonts `ofl/astasans/AstaSans[wght].ttf`, 라이선스 SIL OFL 1.1 (`res/raw/asta_sans_ofl.txt`).
 * Pleos 스타일이 쓰는 굵기(Regular 400, Bold 700, ExtraBold 800)와 중간 굵기를 축 값으로 고정한다.
 * 리소스 가변 폰트 축 지정 API가 Compose 1.7에서 실험적이라 opt-in 한다(minSdk 28 ≥ 26 요구 충족).
 */
@OptIn(ExperimentalTextApi::class)
val AstaSans: FontFamily = FontFamily(
    listOf(FontWeight.Normal, FontWeight.Medium, FontWeight.SemiBold, FontWeight.Bold, FontWeight.ExtraBold).map { weight ->
        Font(
            resId = R.font.asta_sans_variable,
            weight = weight,
            variationSettings = FontVariation.Settings(FontVariation.weight(weight.weight)),
        )
    },
)

private fun pleosStyle(weight: FontWeight, size: Int, lineHeight: Int, tracking: Float = 0f): TextStyle = TextStyle(
    fontFamily = AstaSans,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = lineHeight.sp,
    letterSpacing = tracking.em,
    // 한국어가 음절 중간에서 줄바꿈되지 않도록 어절 단위로 끊는다(API 33+ 적용, 이하에서는 기본 동작).
    lineBreak = LineBreak.Paragraph.copy(wordBreak = LineBreak.WordBreak.Phrase),
)

/**
 * Pleos 타입 스케일. 출처: `design-foundations.md` 3장 [Figma 텍스트 스타일 = Web 타입 스케일],
 * connect-s 기준 px = sp 1:1 (7.1). 각 레벨의 기본 굵기는 Figma 첫 번째 굵기이며,
 * 같은 레벨의 다른 굵기는 `*Strong`/`*Regular` 변형으로 둔다. letterSpacing은 0.
 * TBD: connect-l·connect-w·waterfall 폼팩터 스케일은 미적용(connect-s 값 고정).
 */
@Immutable
data class ChaKakTypography(
    /** Headline Large — ExtraBold 60/80 */
    val headlineLarge: TextStyle = pleosStyle(FontWeight.ExtraBold, 60, 80),
    /** Headline Medium — ExtraBold 56/72 */
    val headlineMedium: TextStyle = pleosStyle(FontWeight.ExtraBold, 56, 72),
    /** Headline Small — ExtraBold 48/64 */
    val headlineSmall: TextStyle = pleosStyle(FontWeight.ExtraBold, 48, 64),
    /** Title Large — Bold 40/52 (ExtraBold·Regular 변형도 존재) */
    val titleLarge: TextStyle = pleosStyle(FontWeight.Bold, 40, 52),
    /** Title Medium — Bold 36/44 (ExtraBold·Regular 변형도 존재) */
    val titleMedium: TextStyle = pleosStyle(FontWeight.Bold, 36, 44),
    /** Title Small — Bold 32/40. Button Large 라벨과 같은 크기 */
    val titleSmall: TextStyle = pleosStyle(FontWeight.Bold, 32, 40),
    /** Body Large Normal — Regular 30/38 */
    val bodyLarge: TextStyle = pleosStyle(FontWeight.Normal, 30, 38),
    /** Body Large Strong — Bold 30/38. Button Medium 라벨 */
    val bodyLargeStrong: TextStyle = pleosStyle(FontWeight.Bold, 30, 38),
    /** Body Medium Strong — Bold 28/36 (Figma에 Bold만 존재) */
    val bodyMedium: TextStyle = pleosStyle(FontWeight.Bold, 28, 36),
    /** Body Small Normal — Regular 26/34 */
    val bodySmall: TextStyle = pleosStyle(FontWeight.Normal, 26, 34),
    /** Body Small Strong — Bold 26/34. Button Small 라벨 */
    val bodySmallStrong: TextStyle = pleosStyle(FontWeight.Bold, 26, 34),
    /** Label Medium — Regular 24/28 */
    val labelMedium: TextStyle = pleosStyle(FontWeight.Normal, 24, 28),
    /** Label Small — Regular 20/24 (최소 12sp 이상 충족) */
    val labelSmall: TextStyle = pleosStyle(FontWeight.Normal, 20, 24),
    /** Title Large ExtraBold 변형 40/52 — 주소 큰 줄 */
    val titleLargeStrong: TextStyle = pleosStyle(FontWeight.ExtraBold, 40, 52),
    /** Title Medium SemiBold 36/48 — 상세 운전자 메모. 시안 A 값. TBD: Pleos 원본 미확인(36의 행간 44와 다름) */
    val memoLarge: TextStyle = pleosStyle(FontWeight.SemiBold, 36, 48),
    /** Body Medium SemiBold 28/38 — 작은 메모 상자. 시안 A 값. TBD: Pleos 원본 미확인 */
    val memoSmall: TextStyle = pleosStyle(FontWeight.SemiBold, 28, 38),
    /** Label Medium Bold 24/28 — 날짜 머리·섹션 라벨·칩. TBD: Pleos 원본 미확인(Label에 Bold 변형 미추출) */
    val labelMediumStrong: TextStyle = pleosStyle(FontWeight.Bold, 24, 28),
    /**
     * 누적 주행 거리 큰 숫자 ExtraBold 112/120, 자간 -3%. Pleos Driving/Speed 숫자 스타일을 본뜬 시안 A 값.
     * TBD: Pleos 원본 미확인 — driving view 숫자 스타일 미추출.
     */
    val dashboardNumber: TextStyle = pleosStyle(FontWeight.ExtraBold, 112, 120, tracking = -0.03f),
    /** 폭 700dp 미만에서 쓰는 [dashboardNumber] 축소형 80/88. TBD: Pleos 원본 미확인 */
    val dashboardNumberCompact: TextStyle = pleosStyle(FontWeight.ExtraBold, 80, 88, tracking = -0.03f),
    /** 칸이 좁거나 글꼴이 클 때 쓰는 [dashboardNumber] 2단계 축소형 60/68. TBD: Pleos 원본 미확인 */
    val dashboardNumberSmall: TextStyle = pleosStyle(FontWeight.ExtraBold, 60, 68, tracking = -0.03f),
) {
    /**
     * 큰 숫자가 한 줄에 들어갈 때까지 줄여 가는 순서: 112 → 80 → 60 → Title Large 40.
     * 숫자를 줄임표로 자르거나 자릿수 중간에서 줄바꿈하지 않기 위해 쓴다.
     */
    val dashboardNumberSteps: List<TextStyle> = listOf(dashboardNumber, dashboardNumberCompact, dashboardNumberSmall, titleLarge)
}
