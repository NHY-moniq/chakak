package ai.chakak.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/** 분류 아이콘 타일·칩 색. [fill] 위에 [content](아이콘·라벨)를 올린다. */
@Immutable
data class Tint(val fill: Color, val content: Color)

/**
 * 차칵 브랜드(시안 A "저널 타임라인") 색. 출처: `docs/design/mockups/Main.dc.html`, `VehicleDetail.dc.html`.
 * 차칵 브랜드(시안 A), Pleos 토큰 아님 — Pleos 의미 색([ChaKakColors])과 섞어 쓰되 대체하지 않는다.
 * Dark 값은 시안에 없어 대비(텍스트 4.5:1, 큰 아이콘 3:1)를 맞춰 차칵이 정했다. `ChaKakColorsContrastTest`에서 검증한다.
 */
@Immutable
data class ChaKakBrandColors(
    /** accent — 캡처 버튼·로고 타일·위치 타일 채움. 위 글자·아이콘은 [onAccent] */
    val accent: Color,
    /** accent 누름 상태 */
    val accentPressed: Color,
    /** accent 위 글자·아이콘(두 테마 모두 basic_900) */
    val onAccent: Color,
    /** accentDeep — 타임라인 레일, 선택 테두리, 강조 시각, 밝은 면 위 아이콘 */
    val accentDeep: Color,
    /** 장소 분류 */
    val place: Tint,
    /** 차량 상태 분류 */
    val vehicle: Tint,
    /** 미분류 */
    val neutral: Tint,
    /** 켜져 있던 경고등 칩. 글자는 text primary, 경고 아이콘만 [Tint.content] */
    val caution: Tint,
) {
    companion object {
        // 차칵 브랜드(시안 A), Pleos 토큰 아님
        private val Ink = Color(0xFF131417)
        private val Accent = Color(0xFF2CC5B4)

        val Light = ChaKakBrandColors(
            accent = Accent,
            accentPressed = Color(0xFF23A396),
            onAccent = Ink,
            accentDeep = Color(0xFF0F766E),
            place = Tint(fill = Color(0xFFCCF3EE), content = Color(0xFF0F766E)),
            vehicle = Tint(fill = Color(0xFFDCE8FF), content = Color(0xFF0050CC)),
            neutral = Tint(fill = Color(0xFFEDEEF2), content = Ink.copy(alpha = 0.84f)),
            caution = Tint(fill = Color(0xFFFFF1DB), content = Color(0xFFB45309)),
        )

        val Dark = ChaKakBrandColors(
            accent = Accent,
            accentPressed = Color(0xFF23A396),
            onAccent = Ink,
            accentDeep = Accent,
            place = Tint(fill = Color(0xFF12423D), content = Color(0xFF5EEAD4)),
            vehicle = Tint(fill = Color(0xFF1A2C4D), content = Color(0xFF8AB4FF)),
            neutral = Tint(fill = Color(0xFF313236), content = Color.White.copy(alpha = 0.84f)),
            caution = Tint(fill = Color(0xFF3D2A0E), content = Color(0xFFFBBF24)),
        )
    }
}
