package ai.chakak.ui.theme

import org.junit.Assert.assertTrue
import org.junit.Test

/** foundations 2.3: 의미 있는 텍스트·아이콘은 4.5:1 이상. */
class ChaKakColorsContrastTest {
    private val themes = listOf(ChaKakColors.Light, ChaKakColors.Dark)

    private fun assertContrast(name: String, ratio: Float) =
        assertTrue("$name contrast $ratio < 4.5", ratio >= 4.5f)

    @Test fun readableTextOnEveryBackground() = themes.forEach { c ->
        val backgrounds = mapOf(
            "backgroundPrimary" to c.backgroundPrimary, "backgroundSecondary" to c.backgroundSecondary,
            "backgroundPopup" to c.backgroundPopup, "surfaceBasic" to c.surfaceBasic,
            "surfaceHigh" to c.surfaceHigh, "surfaceAccent" to c.surfaceAccent,
        )
        val texts = mapOf("primary" to c.textPrimary, "secondary" to c.textSecondary, "tertiary" to c.textTertiary)
        backgrounds.forEach { (bgName, bg) ->
            texts.forEach { (textName, text) -> assertContrast("dark=${c.isDark} $textName/$bgName", contrastRatio(text, bg)) }
        }
    }

    @Test fun onFilledButtonText() = themes.forEach { c ->
        assertContrast("dark=${c.isDark} onFilled", contrastRatio(c.textOnFilled, c.buttonFilledEnabled))
    }

    @Test fun badgeGlyphOnToneFill() = themes.forEach { c ->
        listOf(c.informativeActive, c.informativePositive, c.regulationRed, c.surfaceAccent).forEach { fill ->
            assertContrast("dark=${c.isDark} glyph on $fill", contrastRatio(c.contentOn(fill), fill))
        }
    }

    @Test fun quaternaryIsKnownToFail() = themes.forEach { c ->
        assertTrue(contrastRatio(c.textQuaternary, c.backgroundPrimary) < 4.5f)
    }
}
