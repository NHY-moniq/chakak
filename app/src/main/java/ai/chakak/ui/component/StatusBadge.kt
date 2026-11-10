package ai.chakak.ui.component

import ai.chakak.core.Status
import ai.chakak.ui.model.CaptureSummary
import ai.chakak.ui.model.StatusTone
import ai.chakak.ui.model.label
import ai.chakak.ui.model.tone
import ai.chakak.ui.theme.ChaKakColors
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakShapes
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview

/** 기록 요약 배지: 아이콘 + 텍스트 + 색. 목록 행과 상세 요약에서 같이 쓴다. */
@Composable
fun StatusBadge(summary: CaptureSummary, modifier: Modifier = Modifier) {
    StatusBadgeContent(summary.icon(), summary.label(), summary.tone(), modifier)
}

/** 항목별 수집 상태 배지. [StatusBadge]와 같은 모양을 쓴다. */
@Composable
fun StatusBadge(status: Status, modifier: Modifier = Modifier) {
    StatusBadgeContent(status.icon(), status.label(), status.tone(), modifier)
}

/**
 * 색은 아이콘 원에만 쓰고, 텍스트는 text primary로 둔다(regulation red·informative active는
 * Light에서 텍스트 대비 미달, foundations 2.3). 글리프 색은 원 색 위 대비가 높은 쪽을 고른다.
 */
@Composable
private fun StatusBadgeContent(icon: ImageVector, label: String, tone: StatusTone, modifier: Modifier) {
    val colors = ChaKakTheme.colors
    val fill = colors.toneFill(tone)
    Row(
        modifier = modifier.semantics(mergeDescendants = true) {},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline),
    ) {
        Box(
            modifier = Modifier.size(ChaKakDimens.badgeIcon).background(fill, ChaKakShapes.pill),
            contentAlignment = Alignment.Center,
        ) {
            // TBD: Pleos 아이콘 세트 — Material 기본 아이콘 임시 사용
            Icon(icon, contentDescription = null, tint = colors.contentOn(fill), modifier = Modifier.size(ChaKakDimens.badgeGlyph))
        }
        Text(label, style = ChaKakTheme.typography.bodySmallStrong, color = colors.textPrimary)
    }
}

/** 상태 단계 → Pleos 색. Neutral은 강조색 대신 surface/accent를 쓴다. */
private fun ChaKakColors.toneFill(tone: StatusTone): Color = when (tone) {
    StatusTone.Success -> informativeActive
    StatusTone.Progress, StatusTone.Notice -> informativePositive
    StatusTone.Failure -> regulationRed
    StatusTone.Neutral -> surfaceAccent
}

/** 같은 색 단계 안에서도 아이콘 모양으로 구분한다(색만으로 구분 금지). */
private fun CaptureSummary.icon(): ImageVector = when (this) {
    CaptureSummary.Collecting -> Icons.Filled.Refresh
    CaptureSummary.NoObservations -> Icons.Filled.Info
    is CaptureSummary.Interrupted -> Icons.Filled.Clear
    is CaptureSummary.Complete -> Icons.Filled.CheckCircle
    is CaptureSummary.Partial -> Icons.Filled.Info
    is CaptureSummary.NoneCollected -> Icons.Filled.Warning
}

private fun Status.icon(): ImageVector = when (this) {
    Status.PENDING -> Icons.Filled.Refresh
    Status.OK -> Icons.Filled.CheckCircle
    Status.UNAVAILABLE -> Icons.Filled.Info
    Status.UNSUPPORTED -> Icons.Filled.Clear
    Status.DENIED -> Icons.Filled.Lock
    Status.TIMEOUT, Status.ERROR -> Icons.Filled.Warning
    Status.INTERRUPTED -> Icons.Filled.Clear
}

private val previewSummaries = listOf(
    CaptureSummary.Collecting,
    CaptureSummary.Complete(4),
    CaptureSummary.Partial(2, 4),
    CaptureSummary.NoneCollected(4),
    CaptureSummary.Interrupted(1, 4),
    CaptureSummary.NoObservations,
)

@Composable
private fun BadgeGallery() {
    Surface {
        Column(Modifier.padding(ChaKakSpacing.screen), verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.item)) {
            previewSummaries.forEach { StatusBadge(it) }
            Status.entries.forEach { StatusBadge(it) }
        }
    }
}

@Preview(name = "Badges Light", widthDp = 600)
@Composable
private fun BadgesLightPreview() = ChaKakTheme(darkTheme = false) { BadgeGallery() }

@Preview(name = "Badges Dark", widthDp = 600)
@Composable
private fun BadgesDarkPreview() = ChaKakTheme(darkTheme = true) { BadgeGallery() }

@Preview(name = "Badges fontScale 1.5", widthDp = 600, fontScale = 1.5f)
@Composable
private fun BadgesLargeFontPreview() = ChaKakTheme(darkTheme = false) { BadgeGallery() }
