package ai.chakak.ui.detail

import ai.chakak.core.Record
import ai.chakak.ui.component.IconTile
import ai.chakak.ui.component.InfoNote
import ai.chakak.ui.component.StatusBadge
import ai.chakak.ui.model.currentTimelineClock
import ai.chakak.ui.model.LocationDisplay
import ai.chakak.ui.model.ObservationKeys
import ai.chakak.ui.model.locationDisplay
import ai.chakak.ui.model.observation
import ai.chakak.ui.theme.ChaKakDimens
import ai.chakak.ui.theme.ChaKakIcons
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import ai.chakak.ui.theme.JournalDimens
import ai.chakak.ui.theme.JournalShapes
import ai.chakak.ui.theme.Tint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.produceState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import ai.chakak.ui.coordinates
import ai.chakak.ui.resolveAddress
import ai.chakak.ui.AddressResult
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle

const val VEHICLE_LOCATION_LABEL = "기록 당시 차량 위치"
const val SHORT_LOCATION_LABEL = "기록 당시 위치"

/** 기록의 위치·주소 항목을 패널 내용으로 바꾼다 */
@Composable
fun rememberLocationDisplay(record: Record): LocationDisplay {
    val clock = currentTimelineClock()
    val location = record.observation(ObservationKeys.LOCATION)
    val context = LocalContext.current.applicationContext
    val coordinates = remember(location) { location?.coordinates() }
    val resolved by produceState<AddressResult?>(null, location) {
        value = null
        if (coordinates != null) value = resolveAddress(context, coordinates, roadOnly = true)
    }
    val display = locationDisplay(location, record.observation(ObservationKeys.ADDRESS), record.createdAt, clock)
    return when {
        resolved?.address != null -> display.copy(headline = resolved?.address, headlineStatus = null,
            addressShown = true, notice = ai.chakak.ui.model.ADDRESS_NOTICE)
        coordinates != null && resolved == null -> display.copy(
            headline = if (display.addressShown) display.headline else "도로명 주소를 조회하고 있어요",
            details = display.details + "도로명 주소 조회 중")
        else -> display
    }
}

/**
 * 장소 상세 위치 패널(시안 A). 큰 accent 타일 + "기록 당시 차량 위치" + 주소(또는 좌표) 큰 줄 + 작은 줄 + 안내.
 * [emphasized]가 false(미분류)면 강조 면 대신 테두리 패널과 무채색 타일, 한 단계 작은 글꼴을 쓴다.
 * [onGuide]가 있으면 패널 전체가 버튼이 되고 "눌러서 이 위치로 안내" 줄을 보여준다(색만으로 알리지 않음).
 */
@Composable
fun LocationPanel(display: LocationDisplay, emphasized: Boolean, modifier: Modifier = Modifier, onGuide: (() -> Unit)? = null) {
    val colors = ChaKakTheme.colors
    val shape = if (emphasized) JournalShapes.panel else JournalShapes.card
    val surface = if (emphasized) {
        Modifier.background(colors.backgroundPrimary, shape).padding(ChaKakSpacing.panel)
    } else {
        Modifier.border(ChaKakDimens.dividerThick, colors.dividerSecondary, shape).padding(JournalDimens.cardPadding)
    }
    Row(
        modifier = modifier.fillMaxWidth().guideClickable(shape, onGuide).then(surface),
        horizontalArrangement = Arrangement.spacedBy(if (emphasized) JournalDimens.detailGap else JournalDimens.timelineGap),
    ) {
        if (emphasized) {
            IconTile(ChaKakIcons.Pin, Tint(colors.brand.accent, colors.brand.onAccent), JournalDimens.locationTile, JournalDimens.locationGlyph)
        } else {
            IconTile(ChaKakIcons.Pin, colors.brand.neutral, JournalDimens.categoryTile, JournalDimens.categoryGlyph)
        }
        LocationLines(
            display = display,
            label = VEHICLE_LOCATION_LABEL,
            headlineStyle = if (emphasized) ChaKakTheme.typography.titleLargeStrong else ChaKakTheme.typography.titleSmall,
            modifier = Modifier.weight(1f),
            guidable = onGuide != null,
        )
    }
}

/** 차량 상태 상세 하단의 작은 위치 상자. 같은 판정을 작은 글꼴로 보여준다 */
@Composable
fun LocationBox(display: LocationDisplay, modifier: Modifier = Modifier, onGuide: (() -> Unit)? = null) {
    val colors = ChaKakTheme.colors
    Column(
        modifier = modifier
            .guideClickable(JournalShapes.card, onGuide)
            .border(ChaKakDimens.dividerThick, colors.dividerSecondary, JournalShapes.card)
            .padding(JournalDimens.cardPadding),
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight)) {
            Icon(ChaKakIcons.Pin, contentDescription = null, tint = colors.iconSecondary, modifier = Modifier.size(JournalDimens.chipGlyph))
            SectionLabel(SHORT_LOCATION_LABEL)
        }
        LocationLines(display, label = null, headlineStyle = ChaKakTheme.typography.bodyMedium, guidable = onGuide != null)
    }
}

/** 라벨 · 큰 줄(또는 상태 배지) · 작은 줄들 · 안내 */
@Composable
private fun LocationLines(display: LocationDisplay, label: String?, headlineStyle: TextStyle, modifier: Modifier = Modifier, guidable: Boolean = false) {
    val colors = ChaKakTheme.colors
    // 안내 가능한 카드는 바깥 버튼이 내용을 한 노드로 읽으므로 여기서 따로 묶지 않는다.
    Column(if (guidable) modifier else modifier.semantics(mergeDescendants = true) {}, verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight)) {
        label?.let { SectionLabel(it) }
        display.headline?.let { Text(it, style = headlineStyle, color = colors.textPrimary) }
        display.headlineStatus?.let { StatusBadge(it) }
        display.details.forEach { Text(it, style = ChaKakTheme.typography.labelMedium, color = colors.textTertiary) }
        if (guidable) InfoNote(GUIDANCE_HINT, ChaKakIcons.Navigate, iconTint = colors.brand.accentDeep)
    }
}

/** 안내 가능한 위치 카드만 버튼으로 만든다. 누르면 확인 팝업이 먼저 뜬다. */
private fun Modifier.guideClickable(shape: Shape, onGuide: (() -> Unit)?): Modifier =
    if (onGuide == null) this else clip(shape).clickable(role = Role.Button, onClickLabel = GUIDANCE_HINT, onClick = onGuide)
