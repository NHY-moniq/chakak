package ai.chakak.ui.model

import ai.chakak.core.Category
import ai.chakak.core.Record
import ai.chakak.core.Source
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class LibraryUiStateTest {
    private val record = Record("r", 0, Category.PLACE, Source.MOCK)

    @Test fun contentFollowsLoading() = runTest {
        val states = flowOf(listOf(record)).toLibraryUiState(timeoutMillis = 1_000).toList()
        assertEquals(listOf(LibraryUiState.Loading, LibraryUiState.Content(listOf(record))), states)
    }

    @Test fun emptyListIsContentNotError() = runTest {
        val states = flowOf(emptyList<Record>()).toLibraryUiState(timeoutMillis = 1_000).toList()
        assertEquals(LibraryUiState.Content(emptyList()), states.last())
    }

    @Test fun readFailureIsErrorNotEmptyList() = runTest {
        val failing = flow<List<Record>> { throw IOException("disk") }
        val states = failing.toLibraryUiState(timeoutMillis = 1_000).toList()
        assertEquals(listOf(LibraryUiState.Loading, LibraryUiState.Error(READ_ERROR_MESSAGE)), states)
    }

    @Test fun loadingDoesNotSpinForever() = runTest {
        val silent = flow<List<Record>> { awaitCancellation() }
        val states = silent.toLibraryUiState(timeoutMillis = LOAD_TIMEOUT_MILLIS).take(2).toList()
        assertEquals(listOf(LibraryUiState.Loading, LibraryUiState.Error(LOAD_TIMEOUT_MESSAGE)), states)
    }

    @Test fun lateRecordsReplaceTimeoutError() = runTest {
        val late = flow {
            kotlinx.coroutines.delay(LOAD_TIMEOUT_MILLIS + 1)
            emit(listOf(record))
        }
        val states = late.toLibraryUiState(timeoutMillis = LOAD_TIMEOUT_MILLIS).toList()
        assertEquals(
            listOf(LibraryUiState.Loading, LibraryUiState.Error(LOAD_TIMEOUT_MESSAGE), LibraryUiState.Content(listOf(record))),
            states,
        )
    }
}
