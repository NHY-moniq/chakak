package ai.chakak.ui.detail

import ai.chakak.core.Observation
import ai.chakak.core.Status
import ai.chakak.ui.component.TextAction
import ai.chakak.ui.displayLines
import ai.chakak.ui.model.*
import ai.chakak.ui.theme.*
import ai.chakak.ui.vehicleFieldLabels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics

private fun fieldIcon(key: String): ImageVector = when {
    "Temperature" in key -> ChaKakIcons.Temperature
    "AcState" in key -> ChaKakIcons.Cooling
    "Recirculation" in key -> ChaKakIcons.Recirculate
    "Battery" in key || "Ev" in key -> ChaKakIcons.Battery
    "Fuel" in key -> ChaKakIcons.Fuel
    "Light" in key || "Lamp" in key -> ChaKakIcons.Lamp
    "Lock" in key -> Icons.Filled.Lock
    "Window" in key -> ChaKakIcons.Window
    "Wiper" in key -> ChaKakIcons.Wiper
    "Brake" in key || "Cruise" in key || "Driving" in key -> ChaKakIcons.Gauge
    else -> ChaKakIcons.Car
}

@Composable
internal fun VehicleObservationTiles(observation: Observation) {
    val colors = ChaKakTheme.colors
    val cells = remember(observation) { observation.visualCells() }
    var details by rememberSaveable(observation.key) { mutableStateOf(false) }
    Column(
        Modifier.fillMaxWidth().background(colors.backgroundPrimary, JournalShapes.card).padding(JournalDimens.cardPadding),
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline)) {
            Icon(fieldIcon(observation.key), null, tint = colors.brand.accentDeep, modifier = Modifier.size(ChaKakDimens.badgeIcon))
            Text(vehicleFieldLabels[observation.key] ?: observationKeyLabel(observation.key),
                style = ChaKakTheme.typography.bodySmallStrong, color = colors.textPrimary, modifier = Modifier.weight(1f))
            // Successful values are already visible in the tiles; no repeated "관측값 있음".
            if (observation.status != Status.OK) {
                Icon(ChaKakIcons.Info, observation.status.shortLabel(), tint = colors.iconSecondary, modifier = Modifier.size(ChaKakDimens.badgeGlyph))
            }
        }
        val seats = cells.filter { it.area.startsWith("ROW_") }
        if (seats.isNotEmpty()) {
            Column(
                Modifier.fillMaxWidth().border(ChaKakDimens.dividerThin, colors.dividerSecondary, JournalShapes.carBody)
                    .padding(ChaKakSpacing.item),
                verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline),
            ) {
                Icon(ChaKakIcons.Car, "차량 앞쪽", tint = colors.iconTertiary,
                    modifier = Modifier.size(JournalDimens.chipGlyph).align(Alignment.CenterHorizontally))
                seatRows.filter { row -> seats.any { it.area in row } }.forEach { row ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight)) {
                        row.forEach { area ->
                            val cell = seats.find { it.area == area }
                            if (cell == null) Spacer(Modifier.weight(1f))
                            else StateTile(cell, ChaKakIcons.Seat, Modifier.weight(1f))
                        }
                    }
                }
            }
        }
        val other = cells.filterNot { it in seats }
        other.chunked(2).forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(ChaKakSpacing.inline)) {
                row.forEach { StateTile(it, fieldIcon(observation.key), Modifier.weight(1f)) }
                if (row.size == 1 && other.size > 1) Spacer(Modifier.weight(1f))
            }
        }
        if (cells.isEmpty()) {
            StateTile(VehicleCell("값", "전체", if (observation.status == Status.OK) "상세 값" else observation.status.shortLabel(),
                observation.status, null), fieldIcon(observation.key), Modifier.fillMaxWidth())
        }
        TextAction(if (details) "상세 접기" else "상세 보기", { details = !details }, expanded = details)
        if (details) {
            observation.displayLines().forEach { (label, value) ->
                Text("$label: $value", style = ChaKakTheme.typography.bodySmall, color = colors.textSecondary)
            }
            observation.observedAt?.let { LabeledTime(OBSERVED_TIME_LABEL, it) }
        }
    }
}

@Composable
private fun StateTile(cell: VehicleCell, icon: ImageVector, modifier: Modifier) {
    val colors = ChaKakTheme.colors
    val active = cell.status == Status.OK && cell.active == true
    Column(
        modifier.background(if (active) colors.brand.place.fill else colors.surfaceBasic, JournalShapes.tile)
            .border(ChaKakDimens.dividerThin, if (active) colors.brand.accentDeep else colors.dividerSecondary, JournalShapes.tile)
            .padding(ChaKakSpacing.inline).semantics(mergeDescendants = true) {},
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(ChaKakSpacing.tight),
    ) {
        Icon(if (cell.status == Status.OK) icon else ChaKakIcons.Info, null,
            tint = if (active) colors.brand.accentDeep else colors.iconSecondary, modifier = Modifier.size(ChaKakDimens.badgeIcon))
        if (cell.area != "값") Text(cell.label, style = ChaKakTheme.typography.labelMedium, color = colors.textTertiary)
        Text(cell.value, style = ChaKakTheme.typography.bodySmallStrong, color = colors.textPrimary)
    }
}
