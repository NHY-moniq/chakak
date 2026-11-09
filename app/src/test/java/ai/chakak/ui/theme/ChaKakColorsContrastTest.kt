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

    private fun assertLargeIcon(name: String, ratio: Float) =
        assertTrue("$name contrast $ratio < 3.0", ratio >= 3.0f)

    // 차칵 브랜드(시안 A) 색 쌍

    @Test fun textOnAccentFill() = themes.forEach { c ->
        val b = c.brand
        assertContrast("dark=${c.isDark} onAccent/accent", contrastRatio(b.onAccent, b.accent))
        assertContrast("dark=${c.isDark} onAccent/accentPressed", contrastRatio(b.onAccent, b.accentPressed))
    }

    @Test fun accentDeepAsTextOnDetailSurfaces() = themes.forEach { c ->
        listOf(c.backgroundPrimary, c.backgroundSecondary, c.surfaceBasic).forEach { bg ->
            assertContrast("dark=${c.isDark} accentDeep/$bg", contrastRatio(c.brand.accentDeep, bg))
        }
    }

    @Test fun selectedBorderAndRailAreVisible() = themes.forEach { c ->
        // surfaceHigh: 선택 탭 테두리가 놓이는 탭 바탕
        listOf(c.backgroundPrimary, c.surfaceBasic, c.surfaceHigh).forEach { bg ->
            assertLargeIcon("dark=${c.isDark} accentDeep border/$bg", contrastRatio(c.brand.accentDeep, bg))
        }
    }

    @Test fun categoryTintLabelsAndIcons() = themes.forEach { c ->
        val b = c.brand
        mapOf("place" to b.place, "vehicle" to b.vehicle, "neutral" to b.neutral, "caution" to b.caution).forEach { (name, tint) ->
            assertContrast("dark=${c.isDark} $name content/fill", contrastRatio(tint.content, tint.fill))
        }
    }

    @Test fun telltaleChipTextAndIcon() = themes.forEach { c ->
        assertContrast("dark=${c.isDark} text/caution", contrastRatio(c.textPrimary, c.brand.caution.fill))
        assertLargeIcon("dark=${c.isDark} caution icon", contrastRatio(c.brand.caution.content, c.brand.caution.fill))
    }

    @Test fun iconOnLocationTile() = themes.forEach { c ->
        assertLargeIcon("dark=${c.isDark} onAccent icon", contrastRatio(c.brand.onAccent, c.brand.accent))
    }

    @Test fun destructiveIconOnDetailSurface() = themes.forEach { c ->
        assertLargeIcon("dark=${c.isDark} regulationRed/backgroundSecondary", contrastRatio(c.regulationRed, c.backgroundSecondary))
    }

    @Test fun quaternaryIsKnownToFail() = themes.forEach { c ->
        assertTrue(contrastRatio(c.textQuaternary, c.backgroundPrimary) < 4.5f)
    }
}
