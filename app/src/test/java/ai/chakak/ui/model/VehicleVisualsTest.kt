package ai.chakak.ui.model

import ai.chakak.core.Observation
import ai.chakak.core.Status
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = android.app.Application::class)
class VehicleVisualsTest {
    @Test fun combinedSeatsBecomeFiveShortLabeledCells() {
        val cells = Observation("vehicle.getHvacAcState", Status.OK,
            """{"entries":[{"area":"ROW_1_LEFT, ROW_1_RIGHT, ROW_2_LEFT, ROW_2_CENTER, ROW_2_RIGHT","status":"OK","value":false}]}""").visualCells()
        assertEquals(5, cells.size)
        assertEquals("1열 왼쪽", cells.first().label)
        assertTrue(cells.all { it.value == "꺼짐" && it.active == false })
    }

    @Test fun specificFailureIsNotHiddenByCombinedSuccess() {
        val cells = Observation("vehicle.getHvacTemperatureCurrent", Status.UNAVAILABLE,
            """{"entries":[{"area":"ROW_1_LEFT, ROW_1_RIGHT","status":"OK","value":19.1,"unit":"°C"},{"area":"ROW_1_LEFT","status":"ERROR"}]}""").visualCells()
        assertEquals(2, cells.size)
        assertEquals("조회 오류", cells.first { it.area == "ROW_1_LEFT" }.value)
        assertEquals("19.1 °C", cells.first { it.area == "ROW_1_RIGHT" }.value)
        assertNull(cells.first { it.area == "ROW_1_LEFT" }.active)
    }

    @Test fun malformedDataAndUnknownStatusDoNotBecomeSuccess() {
        assertTrue(Observation("vehicle.test", Status.OK, "{broken").visualCells().isEmpty())
        val cell = Observation("vehicle.test", Status.OK,
            """{"entries":[{"area":"UNKNOWN","status":"INVALID","value":true}]}""").visualCells().single()
        assertEquals(Status.ERROR, cell.status)
        assertNull(cell.active)
        assertEquals("기타 위치", cell.label)
    }
}
