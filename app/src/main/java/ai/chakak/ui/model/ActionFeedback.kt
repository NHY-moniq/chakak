package ai.chakak.ui.model

import androidx.compose.runtime.Immutable

/** 사용자가 시작한 쓰기 동작 */
enum class ActionKind { Capture, Edit, Delete }

/**
 * 동작 결과.
 * - [Done]: 완료
 * - [Rejected]: 시작하지 않음(캡처 엔진이 false 반환 — 주행 제한·중복 요청)
 * - [Restricted]: 주차 상태가 아니어서 실행하지 않음
 * - [Failed]: 실행 중 오류
 */
enum class ActionResult { Done, Rejected, Restricted, Failed }

@Immutable
data class ActionOutcome(val kind: ActionKind, val recordId: String?, val result: ActionResult) {
    val succeeded: Boolean get() = result == ActionResult.Done
}

@Immutable
data class UserMessage(val text: String, val isError: Boolean)

/**
 * 쓰기 동작의 화면 상태. 한 번에 하나만 실행한다.
 * [outcome]은 화면이 처리(편집 종료·팝업 닫기)한 뒤 비운다. [message]는 잠시 후 자동으로 사라진다.
 */
@Immutable
data class ActionState(
    val running: ActionKind? = null,
    val runningRecordId: String? = null,
    val outcome: ActionOutcome? = null,
    val message: UserMessage? = null,
) {
    val busy: Boolean get() = running != null
    fun isRunning(kind: ActionKind, recordId: String? = null): Boolean =
        running == kind && (recordId == null || runningRecordId == recordId)
    fun failed(kind: ActionKind, recordId: String): Boolean =
        outcome?.let { it.kind == kind && it.recordId == recordId && !it.succeeded } ?: false
}

/** 결과 안내가 보이는 시간 */
const val MESSAGE_VISIBLE_MILLIS = 4_000L
const val RESTRICTED_MESSAGE = "주차 상태를 확인할 수 없어 실행하지 않았습니다."

/** 결과별 안내 문구. 실제로 저장되지 않았으면 저장했다고 말하지 않는다. */
fun ActionOutcome.message(): UserMessage = when (result) {
    ActionResult.Restricted -> UserMessage(RESTRICTED_MESSAGE, isError = true)
    ActionResult.Done -> UserMessage(doneText(kind), isError = false)
    ActionResult.Rejected -> UserMessage(rejectedText(kind), isError = true)
    ActionResult.Failed -> UserMessage(failedText(kind), isError = true)
}

private fun doneText(kind: ActionKind): String = when (kind) {
    ActionKind.Capture -> "기록을 남겼습니다."
    ActionKind.Edit -> "변경 내용을 저장했습니다."
    ActionKind.Delete -> "기록을 삭제했습니다."
}

private fun rejectedText(kind: ActionKind): String = when (kind) {
    ActionKind.Capture -> "기록을 시작하지 못했습니다. 주차 상태를 확인한 뒤 다시 시도해 주세요."
    ActionKind.Edit, ActionKind.Delete -> failedText(kind)
}

private fun failedText(kind: ActionKind): String = when (kind) {
    ActionKind.Capture -> "기록하지 못했습니다. 목록을 확인한 뒤 다시 시도해 주세요."
    ActionKind.Edit -> "저장하지 못했습니다. 입력한 내용은 남아 있으니 다시 시도해 주세요."
    ActionKind.Delete -> "삭제하지 못했습니다. 기록은 그대로 남아 있습니다."
}
