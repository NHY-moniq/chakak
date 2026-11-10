package ai.chakak.ui.detail

import ai.chakak.core.Record
import ai.chakak.core.Status
import ai.chakak.ui.SURROUND_CAPTURE_KEY
import ai.chakak.ui.model.*
import ai.chakak.ui.theme.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/** 장소 기록의 보조 정보: 위치 요약 상자와 같은 작은 테두리 카드. */
@Composable
internal fun CompactVehicleCard(record: Record) {
    val observations = record.observations.filter {
        it.key !in ObservationKeys.locationKeys && it.key != SURROUND_CAPTURE_KEY
    }
    if (observations.isEmpty()) return
    var expanded by rememberSaveable(record.id) { mutableStateOf(false) }
    val colors = ChaKakTheme.colors
    val clock = currentTimelineClock()
    Column(
        Modifier.fillMaxWidth()
            .border(ChaKakDimens.dividerThick, colors.dividerSecondary, JournalShapes.card)
            .padding(JournalDimens.cardPadding),
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight)) {
            Icon(ChaKakIcons.Car, contentDescription = null, tint = colors.iconSecondary,
                modifier = Modifier.size(JournalDimens.chipGlyph))
            SectionLabel(OTHER_OBSERVATIONS_LABEL)
        }
        val odometer = record.observation(ObservationKeys.ODOMETER)
        val distance = odometer?.takeIf { it.status == Status.OK }?.let { parseOdometer(it.value) }
        val lights = record.observation(ObservationKeys.TELLTALES)
        val warnings = lights?.takeIf { it.status == Status.OK }?.let { parseTelltales(it.value) }
        val lines = listOfNotNull(
            odometer?.let { "누적 주행 거리 · " + (distance?.let {
                it.text(clock.locale) + if (it.unit == null) " ($UNIT_NOT_PROVIDED)" else ""
            } ?: if (it.status == Status.OK) "값을 표시할 수 없습니다" else it.status.label()) },
            lights?.let { "경고등 · " + (warnings?.filter { it.lit }?.let {
                if (it.isEmpty()) NO_LIT_TELLTALES else it.joinToString(", ", transform = ::telltaleLabel)
            } ?: if (it.status == Status.OK) "값을 표시할 수 없습니다" else it.status.label()) },
        )
        lines.forEach { Text(it, style = ChaKakTheme.typography.bodySmall, color = colors.textSecondary) }
        TextButton(onClick = { expanded = !expanded }) { Text(if (expanded) "차량 정보 접기" else "차량 정보 자세히 보기") }
        if (expanded) {
            VehicleStatusOverview(record, compact = true)
            val others = observations.filter { it.key !in ObservationKeys.vehicleKeys }
            if (others.isNotEmpty()) VehicleObservationList(others)
        }
    }
}
