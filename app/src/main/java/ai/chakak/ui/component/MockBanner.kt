package ai.chakak.ui.component

import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakShapes
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview

const val MOCK_BANNER_TEXT = "테스트 데이터 · 실제 차량 관측값이 아닙니다"
const val MOCK_TAG_TEXT = "테스트 데이터"

/** Mock 빌드 화면 상단 안내. 아이콘 + 텍스트로 표시하고 색에만 의존하지 않는다. */
@Composable
fun MockBanner(modifier: Modifier = Modifier) {
    val colors = ChaKakTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.surfaceHigh, ChaKakShapes.buttonMedium)
            .border(ChaKakDimens.dividerThick, colors.dividerTertiary, ChaKakShapes.buttonMedium)
            .padding(horizontal = ChaKakDimens.buttonPaddingHorizontalMedium, vertical = ChaKakSpacing.item)
            .semantics(mergeDescendants = true) {},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.item),
    ) {
        MockIcon()
        Text(MOCK_BANNER_TEXT, style = ChaKakTheme.typography.bodyMedium, color = colors.textPrimary)
    }
}

/** 목록 행용 짧은 표시. 배너와 같은 아이콘을 쓴다. */
@Composable
fun MockTag(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.semantics(mergeDescendants = true) {},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline),
    ) {
        MockIcon()
        Text(MOCK_TAG_TEXT, style = ChaKakTheme.typography.bodySmallStrong, color = ChaKakTheme.colors.textPrimary)
    }
}

@Composable
private fun MockIcon() {
    val colors = ChaKakTheme.colors
    val fill = colors.informativePositive
    Box(Modifier.size(ChaKakDimens.badgeIcon).background(fill, ChaKakShapes.pill), contentAlignment = Alignment.Center) {
        // TBD: Pleos 아이콘 세트 — Material 기본 아이콘 임시 사용
        Icon(Icons.Filled.Build, contentDescription = null, tint = colors.contentOn(fill), modifier = Modifier.size(ChaKakDimens.badgeGlyph))
    }
}

@Composable
private fun MockGallery() {
    Surface {
        Column(Modifier.padding(ChaKakSpacing.screen), verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
            MockBanner()
            MockTag()
        }
    }
}

@Preview(name = "Mock Light", widthDp = 600)
@Composable
private fun MockLightPreview() = ChaKakTheme(darkTheme = false) { MockGallery() }

@Preview(name = "Mock Dark", widthDp = 1200)
@Composable
private fun MockDarkPreview() = ChaKakTheme(darkTheme = true) { MockGallery() }

@Preview(name = "Mock fontScale 1.5", widthDp = 600, fontScale = 1.5f)
@Composable
private fun MockLargeFontPreview() = ChaKakTheme(darkTheme = false) { MockGallery() }
