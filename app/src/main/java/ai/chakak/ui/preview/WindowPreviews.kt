package ai.chakak.ui.preview

import ai.chakak.core.Category
import ai.chakak.ui.app.AppWindowScaffold
import ai.chakak.ui.app.LibraryCallbacks
import ai.chakak.ui.edit.DeleteRecordDialogContent
import ai.chakak.ui.model.ActionKind
import ai.chakak.ui.model.ActionOutcome
import ai.chakak.ui.model.ActionResult
import ai.chakak.ui.model.ActionState
import ai.chakak.ui.model.LOAD_TIMEOUT_MESSAGE
import ai.chakak.ui.model.LibraryNavigation
import ai.chakak.ui.model.LibraryUiState
import ai.chakak.ui.model.LocalTimelineClock
import ai.chakak.ui.model.message
import ai.chakak.ui.theme.ChaKakSpacing
import ai.chakak.ui.theme.ChaKakTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/*
 * 실제 앱 윈도 크기(foundations 7.1)로 본 화면 Preview.
 * S-17 Portrait 840×1184 · L Portrait 628×855 · S-17 Landscape 1700×1184 · WF Landscape 1256×800.
 * Light/Dark, 글꼴 1.0/1.5, 상태(목록·빈 목록·조회 오류·로딩·제한·상세·편집·삭제)를 나눠 담았다.
 */

private const val SCRIM_ALPHA = 0.32f

@Composable
private fun AppPreview(
    dark: Boolean,
    state: LibraryUiState = PreviewData.content,
    allowed: Boolean = true,
    nav: LibraryNavigation = LibraryNavigation(),
    actions: ActionState = ActionState(),
) = ChaKakTheme(darkTheme = dark) {
    CompositionLocalProvider(LocalTimelineClock provides PreviewData.clock) {
        AppWindowScaffold(state, actions, allowed, mock = true, callbacks = LibraryCallbacks(), initialNavigation = nav)
    }
}

private val captureDone = ActionState(message = ActionOutcome(ActionKind.Capture, null, ActionResult.Done).message())
private val editFailed = ActionOutcome(ActionKind.Edit, PreviewData.vehiclePartial.id, ActionResult.Failed)
private fun detailOf(id: String) = LibraryNavigation(selectedId = id)
private fun editOf(id: String) = LibraryNavigation(selectedId = id, editing = true)

@Preview(name = "01 타임라인(오늘·어제·11월 6일) · S-17 portrait · Light", widthDp = 840, heightDp = 1184)
@Composable private fun ListPortraitLight() = AppPreview(dark = false, actions = captureDone)

@Preview(name = "02 목록 · L portrait · Dark · font 1.5", widthDp = 628, heightDp = 855, fontScale = 1.5f)
@Composable private fun ListSmallDarkLargeFont() = AppPreview(dark = true)

@Preview(name = "03 목록+장소 상세(주소 있음) · S-17 landscape · Light · 기록 중", widthDp = 1700, heightDp = 1184)
@Composable private fun TwoPanePlaceLight() =
    AppPreview(dark = false, nav = detailOf(PreviewData.placeOk.id), actions = ActionState(running = ActionKind.Capture))

@Preview(name = "04 목록+차량 상태 부분 저장 · WF landscape · Dark · font 1.5", widthDp = 1256, heightDp = 800, fontScale = 1.5f)
@Composable private fun TwoPanePartialDarkLargeFont() = AppPreview(dark = true, nav = detailOf(PreviewData.vehiclePartial.id))

@Preview(name = "05 빈 목록 · L portrait · Light", widthDp = 628, heightDp = 855)
@Composable private fun EmptyLight() = AppPreview(dark = false, state = PreviewData.empty)

@Preview(name = "06 필터 결과 없음 · S-17 portrait · Dark", widthDp = 840, heightDp = 1184)
@Composable private fun FilteredEmptyDark() =
    AppPreview(dark = true, state = PreviewData.withoutPlaces, nav = LibraryNavigation(filter = Category.PLACE))

@Preview(name = "07 조회 오류 · WF landscape · Light · font 1.5", widthDp = 1256, heightDp = 800, fontScale = 1.5f)
@Composable private fun ErrorLightLargeFont() = AppPreview(dark = false, state = LibraryUiState.Error(LOAD_TIMEOUT_MESSAGE))

@Preview(name = "08 로딩 · S-17 portrait · Dark", widthDp = 840, heightDp = 1184)
@Composable private fun LoadingDark() = AppPreview(dark = true, state = LibraryUiState.Loading)

@Preview(name = "09 주행 제한 · S-17 portrait · Light · font 1.5", widthDp = 840, heightDp = 1184, fontScale = 1.5f)
@Composable private fun RestrictedLightLargeFont() = AppPreview(dark = false, allowed = false)

@Preview(name = "10 주행 제한 · WF landscape · Dark", widthDp = 1256, heightDp = 800)
@Composable private fun RestrictedDark() = AppPreview(dark = true, allowed = false)

@Preview(name = "11 장소 상세(주소 없음) · L portrait · Light", widthDp = 628, heightDp = 855)
@Composable private fun PlaceDetailLight() = AppPreview(dark = false, nav = detailOf(PreviewData.placeNoAddress.id))

@Preview(name = "12 부분 저장 상세(권한 없음·시간 초과) · S-17 portrait · Dark · font 1.5", widthDp = 840, heightDp = 1184, fontScale = 1.5f)
@Composable private fun PartialDetailDarkLargeFont() = AppPreview(dark = true, nav = detailOf(PreviewData.vehiclePartial.id))

@Preview(name = "13 편집 · L portrait · Light · font 1.5", widthDp = 628, heightDp = 855, fontScale = 1.5f)
@Composable private fun EditLightLargeFont() = AppPreview(dark = false, nav = editOf(PreviewData.unclassifiedMemo.id))

@Preview(name = "14 편집 저장 실패 · S-17 landscape · Dark", widthDp = 1700, heightDp = 1184)
@Composable private fun EditFailedDark() = AppPreview(
    dark = true,
    nav = editOf(PreviewData.vehiclePartial.id),
    actions = ActionState(outcome = editFailed, message = editFailed.message()),
)

@Preview(name = "15 목록 · L portrait · Light · font 1.0", widthDp = 628, heightDp = 855)
@Composable private fun ListSmallLight() = AppPreview(dark = false)

@Preview(name = "16 다른 작업 중(기록 버튼 비활성 이유) · L portrait · Dark", widthDp = 628, heightDp = 855)
@Composable private fun CaptureBlockedDark() = AppPreview(
    dark = true,
    actions = ActionState(running = ActionKind.Delete, runningRecordId = PreviewData.vehiclePartial.id),
)

@Preview(name = "17 목록+장소 상세(주소 있음) · S-17 landscape · Dark", widthDp = 1700, heightDp = 1184)
@Composable private fun TwoPanePlaceDark() = AppPreview(dark = true, nav = detailOf(PreviewData.placeOk.id))

@Preview(name = "18 목록+차량 상태(타이어 1개 값 없음·위치 권한 없음) · S-17 landscape · Light", widthDp = 1700, heightDp = 1184)
@Composable private fun TwoPaneVehicleLight() = AppPreview(dark = false, nav = detailOf(PreviewData.vehiclePartial.id))

@Preview(name = "19 목록+차량 상태(타이어 시간 초과) · S-17 landscape · Dark", widthDp = 1700, heightDp = 1184)
@Composable private fun TwoPaneVehicleTimeoutDark() = AppPreview(dark = true, nav = detailOf(PreviewData.vehicleTimeout.id))

@Preview(name = "20 장소 상세(주소 실패) · S-17 portrait · Dark", widthDp = 840, heightDp = 1184)
@Composable private fun PlaceAddressFailedDark() = AppPreview(dark = true, nav = detailOf(PreviewData.placeAddressFailed.id))

@Preview(name = "21 차량 상태 상세 · L portrait · Light · 좁은 폭", widthDp = 628, heightDp = 855)
@Composable private fun VehicleDetailNarrowLight() = AppPreview(dark = false, nav = detailOf(PreviewData.vehiclePartial.id))

@Preview(name = "22 타임라인 · S-17 portrait · Dark", widthDp = 840, heightDp = 1184)
@Composable private fun ListPortraitDark() = AppPreview(dark = true)

@Composable
private fun DeletePreview(dark: Boolean, deleting: Boolean, failed: Boolean) = ChaKakTheme(darkTheme = dark) {
    Box(Modifier.fillMaxSize().background(ChaKakTheme.colors.backgroundPrimary)) {
        Box(
            Modifier.fillMaxSize().background(MaterialTheme.colorScheme.scrim.copy(alpha = SCRIM_ALPHA)),
            contentAlignment = Alignment.Center,
        ) {
            DeleteRecordDialogContent(PreviewData.vehiclePartial, deleting, failed, {}, {}, Modifier.padding(ChaKakSpacing.screen))
        }
    }
}

@Preview(name = "15 삭제 확인 · 삭제 중 · S-17 portrait · Light", widthDp = 840, heightDp = 1184)
@Composable private fun DeleteDeletingLight() = DeletePreview(dark = false, deleting = true, failed = false)

@Preview(name = "16 삭제 확인 · 실패 · WF landscape · Dark · font 1.5", widthDp = 1256, heightDp = 800, fontScale = 1.5f)
@Composable private fun DeleteFailedDarkLargeFont() = DeletePreview(dark = true, deleting = false, failed = true)
