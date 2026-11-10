package ai.chakak.ui.model

import ai.chakak.core.Record
import java.text.DateFormat
import java.util.Date

/** 기록·관측 시각 표시. 기기 로캘을 따른다. */
fun formatDateTime(millis: Long): String =
    DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(Date(millis))

/** 이름 없는 기록의 표시 이름. 분류는 바로 아래 메타 줄에 따로 보이므로 여기서 반복하지 않는다. */
const val UNTITLED_RECORD = "이름 없는 기록"

/** 제목이 비어 있으면 [UNTITLED_RECORD]를 쓴다(목록·상세·삭제 확인 공통). 주차 후 편집으로 이름을 보완한다. */
fun Record.displayTitle(): String = title.ifBlank { UNTITLED_RECORD }
