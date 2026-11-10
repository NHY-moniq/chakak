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
    val Navigate: ImageVector by lazy { lineIcon("Navigate", "M12 3l7 17l-7-4l-7 4z") }

    val Info: ImageVector by lazy { lineIcon("Info", "$CIRCLE_9 M12 11v6M12 7.5v.5") }
    /** 삼각 경고 */
    val Caution: ImageVector by lazy { lineIcon("Caution", "M12 4l9 16H3z M12 10v4M12 17v.5") }
    /** 메모(사각 안 줄) */
    val Mic: ImageVector by lazy { lineIcon("Mic", "M9 5a3 3 0 0 1 6 0v6a3 3 0 0 1 -6 0z M5 11a7 7 0 0 0 14 0 M12 18v3 M9 21h6") }

    val Memo: ImageVector by lazy { lineIcon("Memo", "M5 4h14v16H5z M8 9h8M8 13h8M8 17h5") }


    val Temperature by lazy { lineIcon("Temperature", "M10 14V5a2 2 0 0 1 4 0v9a4 4 0 1 1 -4 0 M12 9v8 M17 6h3 M17 10h3") }
    val Cooling by lazy { lineIcon("Cooling", "M12 3v18 M4 7l16 10 M4 17L20 7 M9 4l3 3 3-3 M9 20l3-3 3 3") }
    val Recirculate by lazy { lineIcon("Recirculate", "M4 10a8 8 0 0 1 14-4l2 3 M20 4v5h-5 M20 14a8 8 0 0 1 -14 4l-2-3 M4 20v-5h5") }
    val Battery by lazy { lineIcon("Battery", "M9 3h6 M7 5h10v16H7z M12 8v10 M9 13h6") }
    val Fuel by lazy { lineIcon("Fuel", "M4 21V4h10v17 M3 21h12 M7 7h4v4H7z M14 12h3v6a2 2 0 0 0 4 0V9l-4-4") }
    val Lamp by lazy { lineIcon("Lamp", "M9 17h6v4H9z M9 17v-2a6 6 0 1 1 6 0v2 M2 8H1 M22 8h1 M4 2l2 2 M20 2l-2 2") }
    val Window by lazy { lineIcon("Window", "M4 20V9l5-5h11v16z M5 14h14 M11 5v8") }
    val Wiper by lazy { lineIcon("Wiper", "M3 16l3-11h12l3 11z M8 16l8-8 M12 20h7") }
    val Gauge by lazy { lineIcon("Gauge", "M4 19a10 10 0 1 1 16 0 M6 17h12 M12 15l4-7 M5 10l2 1 M12 4v3 M19 10l-2 1") }
    val Seat by lazy { lineIcon("Seat", "M9 3h6v4H9z M7 9h10v7H7z M6 16h12v4H6z M8 20v2 M16 20v2") }

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
