package com.finnvek.rowtool.ui.screens.history

import com.finnvek.rowtool.domain.model.RecentHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {
    @Test fun loadingErrorRetryEmptyAndMissingAreDistinct() =
        runTest {
            Dispatchers.setMain(StandardTestDispatcher(testScheduler))
            try {
                var attempt = 0
                val vm =
                    HistoryViewModel {
                        flow {
                            when (attempt++) {
                                0 -> throw IOException("read failed")
                                1 -> emit(RecentHistory("Work", emptyList()))
                                else -> emit(null)
                            }
                        }
                    }
                assertEquals(HistoryUiState.Loading, vm.state.value)
                advanceUntilIdle()
                assertEquals(HistoryUiState.Error, vm.state.value)
                vm.retry()
                assertEquals(HistoryUiState.Loading, vm.state.value)
                advanceUntilIdle()
                assertTrue((vm.state.value as HistoryUiState.Content).history.entries.isEmpty())
                vm.retry()
                advanceUntilIdle()
                assertEquals(HistoryUiState.Missing, vm.state.value)
            } finally {
                Dispatchers.resetMain()
            }
        }
}
