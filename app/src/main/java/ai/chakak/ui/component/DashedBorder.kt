package ai.chakak.ui.component

import ai.chakak.ui.theme.JournalDimens
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp

/** 값 없음 표시용 점선 테두리. 실선(값 있음)과 모양으로 구분한다. 선·점선 효과는 크기가 바뀔 때만 다시 만든다 */
fun Modifier.dashedBorder(width: Dp, color: Color, radius: Dp): Modifier = drawWithCache {
    val strokeWidth = width.toPx()
    val inset = strokeWidth / 2
    val stroke = Stroke(
        width = strokeWidth,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(JournalDimens.dashOn.toPx(), JournalDimens.dashOff.toPx())),
    )
    val topLeft = Offset(inset, inset)
    val rectSize = Size(size.width - strokeWidth, size.height - strokeWidth)
    val corner = CornerRadius(radius.toPx())
    onDrawBehind { drawRoundRect(color = color, topLeft = topLeft, size = rectSize, cornerRadius = corner, style = stroke) }
}
