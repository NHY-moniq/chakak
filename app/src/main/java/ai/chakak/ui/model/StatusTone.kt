package ai.chakak.ui.model

import ai.chakak.core.Status

/**
 * 상태 배지의 의미 단계. 색은 테마가 정하고, 배지는 항상 아이콘·텍스트와 함께 쓴다.
 * - [Success]: 관측값 있음 / 수집 완료
 * - [Progress]: 수집 중
 * - [Notice]: 일부 정보 없음(기록은 남음)
 * - [Failure]: 수집 오류·시간 초과·중단·전부 실패
 * - [Neutral]: 미지원·권한 없음·유효값 없음·수집 항목 없음(오류가 아닌 환경 제약)
 *
 * 차량 상태 기록에서 빨간색은 차량 고장으로 오인될 수 있어 "수집 실패"에만 쓴다.
 */
enum class StatusTone { Success, Progress, Notice, Failure, Neutral }

fun CaptureSummary.tone(): StatusTone = when (this) {
    CaptureSummary.Collecting -> StatusTone.Progress
    CaptureSummary.NoObservations -> StatusTone.Neutral
    is CaptureSummary.Interrupted -> StatusTone.Failure
    is CaptureSummary.Complete -> StatusTone.Success
    is CaptureSummary.Partial -> StatusTone.Notice
    is CaptureSummary.NoneCollected -> StatusTone.Failure
}

fun Status.tone(): StatusTone = when (this) {
    Status.PENDING -> StatusTone.Progress
    Status.OK -> StatusTone.Success
    Status.UNAVAILABLE, Status.UNSUPPORTED, Status.DENIED -> StatusTone.Neutral
    Status.TIMEOUT, Status.ERROR, Status.INTERRUPTED -> StatusTone.Failure
}
