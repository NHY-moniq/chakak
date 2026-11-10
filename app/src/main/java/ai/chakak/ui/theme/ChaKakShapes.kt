package ai.chakak.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CornerBasedShape

/** Pleos 반경으로 만든 형태. 출처: `design-foundations.md` 4·5장 [Figma]. */
object ChaKakShapes {
    /** radius level4 = 12 */
    val extraSmall: CornerBasedShape = RoundedCornerShape(ChaKakDimens.radiusLevel4)
    /** Button Small 16 (= radius level5) */
    val buttonSmall: CornerBasedShape = RoundedCornerShape(ChaKakDimens.buttonRadiusSmall)
    /** Button Medium 24 */
    val buttonMedium: CornerBasedShape = RoundedCornerShape(ChaKakDimens.buttonRadiusMedium)
    /** Button Large 32, Tabs 32, Dropdown 32 */
    val buttonLarge: CornerBasedShape = RoundedCornerShape(ChaKakDimens.buttonRadiusLarge)
    /** Tabs 32 */
    val tabs: CornerBasedShape = RoundedCornerShape(ChaKakDimens.tabsRadius)
    /** Popup 48, 알림 카드 48 */
    val popup: CornerBasedShape = RoundedCornerShape(ChaKakDimens.popupRadius)
    /** 배지 아이콘 원. 차칵 결정값 */
    val pill: CornerBasedShape = RoundedCornerShape(percent = 50)
}
