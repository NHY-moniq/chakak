package ai.chakak.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

/**
 * 시안 A 선 아이콘(24 그리드, 선 2). 출처: `docs/design/mockups` 시안 HTML의 SVG 경로.
 * Material core 세트에 없는 모양만 둔다. 차칵 브랜드(시안 A), Pleos 아이콘 아님.
 * TBD: Pleos 아이콘 세트 — 공식 아이콘 확보 시 교체.
 */
object ChaKakIcons {
    /** 원 두 개: 캡처 버튼 셔터 */
    val Shutter: ImageVector by lazy { lineIcon("Shutter", CIRCLE_9 + CIRCLE_4) }
    /** 원 + 조리개 날: 앱 머리 로고 */
    val ShutterMark: ImageVector by lazy { lineIcon("ShutterMark", "$CIRCLE_9 M12 3l3 8M21 12l-8 3M12 21l-3-8M3 12l8-3") }
    /** 차(옆모습): 차량 상태 분류 */
    val Car: ImageVector by lazy {
        lineIcon("Car", "M4 15l1.5-5a2 2 0 0 1 1.9-1.4h9.2a2 2 0 0 1 1.9 1.4L20 15v3H4z" + dot(7.5f, 15.5f) + dot(16.5f, 15.5f))
    }
    /** 위치 핀 */
    val Pin: ImageVector by lazy { lineIcon("Pin", "M12 21s-7-6.2-7-11a7 7 0 0 1 14 0c0 4.8-7 11-7 11z M9.5 10a2.5 2.5 0 1 0 5 0a2.5 2.5 0 1 0 -5 0") }
    /** 원 안 i: 안내 */
    val Info: ImageVector by lazy { lineIcon("Info", "$CIRCLE_9 M12 11v6M12 7.5v.5") }
    /** 삼각 경고 */
    val Caution: ImageVector by lazy { lineIcon("Caution", "M12 4l9 16H3z M12 10v4M12 17v.5") }
    /** 메모(사각 안 줄) */
    val Memo: ImageVector by lazy { lineIcon("Memo", "M5 4h14v16H5z M8 9h8M8 13h8M8 17h5") }

    private const val CIRCLE_9 = "M3 12a9 9 0 1 0 18 0a9 9 0 1 0 -18 0"
    private const val CIRCLE_4 = " M8 12a4 4 0 1 0 8 0a4 4 0 1 0 -8 0"
    private const val GRID = 24f
    private const val STROKE = 2f

    private fun dot(x: Float, y: Float) = " M${x - 1} ${y}a1 1 0 1 0 2 0a1 1 0 1 0 -2 0"

    private fun lineIcon(name: String, pathData: String): ImageVector =
        ImageVector.Builder(name = name, defaultWidth = 24.dp, defaultHeight = 24.dp, viewportWidth = GRID, viewportHeight = GRID)
            .addPath(
                pathData = PathParser().parsePathString(pathData).toNodes(),
                fill = null,
                stroke = SolidColor(Color.Black),
                strokeLineWidth = STROKE,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
            )
            .build()
}
