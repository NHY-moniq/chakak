package ai.chakak.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DefaultTypography = ChaKakTypography()
private val LightMaterialColors by lazy { ChaKakColors.Light.toMaterialColorScheme() }
private val DarkMaterialColors by lazy { ChaKakColors.Dark.toMaterialColorScheme() }
private val DefaultMaterialTypography by lazy { DefaultTypography.toMaterialTypography() }

val LocalChaKakColors = staticCompositionLocalOf { ChaKakColors.Light }
val LocalChaKakTypography = staticCompositionLocalOf { DefaultTypography }

/** 화면 코드의 토큰 진입점. `ChaKakTheme.colors.textPrimary`처럼 쓴다. */
object ChaKakTheme {
    val colors: ChaKakColors
        @Composable @ReadOnlyComposable get() = LocalChaKakColors.current
    val typography: ChaKakTypography
        @Composable @ReadOnlyComposable get() = LocalChaKakTypography.current
}

/**
 * Pleos 토큰을 제공하고, 남아 있는 Material3 위젯도 같은 토큰을 쓰도록 M3 테마에 매핑한다.
 * 브랜드 테마(Hyundai·Kia·Genesis)는 MVP 범위 밖이다(foundations 8.4).
 */
@Composable
fun ChaKakTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) ChaKakColors.Dark else ChaKakColors.Light
    CompositionLocalProvider(LocalChaKakColors provides colors, LocalChaKakTypography provides DefaultTypography) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkMaterialColors else LightMaterialColors,
            typography = DefaultMaterialTypography,
            shapes = pleosMaterialShapes,
            content = content,
        )
    }
}

/**
 * M3 슬롯 → Pleos 토큰. M3 보라색이 남지 않도록 모든 슬롯을 채운다.
 * - surfaceContainerHigh는 M3 AlertDialog 컨테이너이므로 background/popup을 쓴다.
 * - onError는 text/light/primary다. Light에서 흰 글자/regulation red 대비가 3.9라
 *   error 채움 위 본문 텍스트는 쓰지 않는다(errorContainer 쪽은 text primary).
 */
internal fun ChaKakColors.toMaterialColorScheme(): ColorScheme {
    val base = if (isDark) darkColorScheme() else lightColorScheme()
    return base.copy(
        primary = buttonFilledEnabled, onPrimary = textOnFilled,
        primaryContainer = surfaceAccent, onPrimaryContainer = textPrimary,
        inversePrimary = informativePositive,
        secondary = buttonFilledEnabled, onSecondary = textOnFilled,
        secondaryContainer = surfaceAccent, onSecondaryContainer = textPrimary,
        tertiary = informativePositive, onTertiary = contentOn(informativePositive),
        tertiaryContainer = surfaceHigh, onTertiaryContainer = textPrimary,
        background = backgroundPrimary, onBackground = textPrimary,
        surface = surfaceBasic, onSurface = textPrimary,
        surfaceVariant = surfaceHigh, onSurfaceVariant = textSecondary,
        surfaceTint = Color.Transparent,
        inverseSurface = textPrimary, inverseOnSurface = backgroundSecondary,
        error = regulationRed, onError = textOnFilled,
        errorContainer = surfaceHigh, onErrorContainer = textPrimary,
        outline = dividerTertiary, outlineVariant = dividerSecondary,
        // TBD: Pleos 원본 미확인 — 팝업 dim 토큰 미확정, M3 scrim 동작(검정+알파) 유지
        scrim = Color.Black,
        surfaceBright = surfaceBasic, surfaceDim = backgroundPrimary,
        surfaceContainerLowest = backgroundSecondary,
        surfaceContainerLow = surfaceHigh,
        surfaceContainer = surfaceHigh,
        surfaceContainerHigh = backgroundPopup,
        surfaceContainerHighest = surfaceHigh,
    )
}

/** M3 display는 Pleos에 없으므로 Headline으로, labelLarge(버튼·칩 라벨)는 Button Medium 라벨(Body Large Bold)로. */
internal fun ChaKakTypography.toMaterialTypography(): Typography = Typography(
    displayLarge = headlineLarge, displayMedium = headlineMedium, displaySmall = headlineSmall,
    headlineLarge = headlineLarge, headlineMedium = headlineMedium, headlineSmall = headlineSmall,
    titleLarge = titleLarge, titleMedium = titleMedium, titleSmall = titleSmall,
    bodyLarge = bodyLarge, bodyMedium = bodyMedium, bodySmall = bodySmall,
    labelLarge = bodyLargeStrong, labelMedium = labelMedium, labelSmall = labelSmall,
)

/** M3 형태 단계 → Pleos 반경: 12 / 16 / 24 / 32 / 48. */
private val pleosMaterialShapes = Shapes(
    extraSmall = ChaKakShapes.extraSmall,
    small = ChaKakShapes.buttonSmall,
    medium = ChaKakShapes.buttonMedium,
    large = ChaKakShapes.buttonLarge,
    extraLarge = ChaKakShapes.popup,
)
