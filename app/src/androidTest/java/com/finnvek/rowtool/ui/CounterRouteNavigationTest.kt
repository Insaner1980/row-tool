package com.finnvek.rowtool.ui

import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.ui.screens.counter.CounterRoute
import com.finnvek.rowtool.ui.screens.counter.CounterViewModel
import com.finnvek.rowtool.ui.screens.counter.recoveryProjectRule
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CounterRouteNavigationTest {
    private val composeRule = createComposeRule()
    private lateinit var projectId: String
    private lateinit var counterViewModel: CounterViewModel
    private val generation = mutableIntStateOf(0)
    private var returnAttempts = 0

    @get:Rule
    val rules: TestRule = recoveryProjectRule(composeRule) { projectId = it }

    @Test
    fun deletionRetriesAfterCompositionRecreationWhenFirstNavigationDidNotComplete() {
        showCounter()
        composeRule.runOnIdle { counterViewModel.delete() }
        assertReturnAndRecreate()
    }

    @Test
    fun archiveRetriesAfterCompositionRecreationWhenFirstNavigationDidNotComplete() {
        showCounter()
        composeRule.runOnIdle { counterViewModel.archive() }
        assertReturnAndRecreate()
    }

    @Test
    fun validProjectStaysOnCounterAfterCompositionRecreation() {
        showCounter()
        composeRule.runOnIdle { generation.intValue++ }
        composeRule.onNodeWithText("RECOVERY").assertIsDisplayed()
        composeRule.runOnIdle { assertEquals(0, returnAttempts) }
    }

    private fun showCounter() {
        val application =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as RowToolApplication
        composeRule.setContent {
            counterViewModel =
                viewModel(
                    factory =
                        CounterViewModel.factory(
                            projectId,
                            application.container.counterRepository,
                            application.container.preferencesRepository,
                        ),
                )
            RowToolTheme {
                key(generation.intValue) {
                    CounterRoute(
                        viewModel = counterViewModel,
                        // Deliberately leave the destination present to model an uncompleted navigation.
                        onProjects = { returnAttempts++ },
                        onSettings = {},
                        onMessage = {},
                    )
                }
            }
        }
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("RECOVERY").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("RECOVERY").assertIsDisplayed()
        composeRule.runOnIdle { assertEquals(0, returnAttempts) }
    }

    private fun assertReturnAndRecreate() {
        composeRule.waitUntil(timeoutMillis = 5_000) { composeRule.runOnIdle { returnAttempts == 1 } }
        composeRule.runOnIdle {
            assertEquals(1, returnAttempts)
            generation.intValue++
        }
        composeRule.waitUntil(timeoutMillis = 5_000) { composeRule.runOnIdle { returnAttempts == 2 } }
        composeRule.runOnIdle { assertEquals(2, returnAttempts) }
    }
}
