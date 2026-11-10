package ai.chakak.ui

import ai.chakak.R
import ai.chakak.ui.component.IconTile
import ai.chakak.ui.detail.SectionLabel
import ai.chakak.ui.theme.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

internal const val SURROUND_CAPTURE_KEY = "surroundCapture"

/** 사용자 지정 예시 이미지. 상세 패널과 같은 저널 색상·모서리·간격을 사용한다. */
@Composable
internal fun SurroundCaptureCard() {
    val colors = ChaKakTheme.colors
    Column(
        Modifier.fillMaxWidth()
            .background(colors.backgroundPrimary, JournalShapes.panel)
            .border(ChaKakDimens.dividerThick, colors.dividerSecondary, JournalShapes.panel)
            .padding(JournalDimens.cardPadding),
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline)) {
            IconTile(ChaKakIcons.Shutter, colors.brand.neutral, JournalDimens.chipHeight, JournalDimens.chipGlyph)
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight)) {
                SectionLabel("서라운드뷰 캡처")
                Text("차량 주변 보기", style = ChaKakTheme.typography.labelMedium, color = colors.textTertiary)
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight)) {
                Text("예시 이미지", style = ChaKakTheme.typography.labelMedium, color = colors.textSecondary)
                Text("협의가 필요한 기능입니다.", style = ChaKakTheme.typography.labelSmall,
                    color = colors.textTertiary, textAlign = TextAlign.End)
            }
        }
        Image(
            painter = painterResource(R.drawable.surroundview),
            contentDescription = "서라운드뷰 예시 이미지",
            modifier = Modifier.fillMaxWidth().aspectRatio(1672f / 941f).clip(JournalShapes.tile),
            contentScale = ContentScale.Fit,
        )
    }
}
