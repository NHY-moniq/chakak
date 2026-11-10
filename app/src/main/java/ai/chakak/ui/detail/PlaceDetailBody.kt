package ai.chakak.ui.detail

import ai.chakak.core.Category
import ai.chakak.core.Record
import ai.chakak.ui.SURROUND_CAPTURE_KEY
import ai.chakak.ui.SurroundCaptureCard
import ai.chakak.ui.model.ObservationKeys
import androidx.compose.runtime.Composable

const val OTHER_OBSERVATIONS_LABEL = "함께 기록된 차량 정보"

/** 장소·미분류도 차량 상세와 같은 시각화로 저장된 차량 상태를 보여준다. */
@Composable
fun PlaceDetailBody(record: Record, compact: Boolean) {
    PlaceHeading(record)
    LocationPanel(rememberLocationDisplay(record), emphasized = record.category == Category.PLACE)
    SurroundCaptureCard()
    val place = record.category == Category.PLACE
    MemoBlock(record.memo)
    if (place) {
        CompactVehicleCard(record)
        return
    }
    if (record.observations.any { it.key in ObservationKeys.vehicleKeys }) {
        SectionLabel(OTHER_OBSERVATIONS_LABEL)
        VehicleStatusOverview(record, compact)
    }
    val others = record.observations.filter {
        it.key !in ObservationKeys.vehicleKeys + ObservationKeys.locationKeys + SURROUND_CAPTURE_KEY
    }
    if (others.isNotEmpty()) VehicleObservationList(others)
}
