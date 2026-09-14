package com.finnvek.rowtool.ui

import android.content.Context
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.withTransaction
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.screens.counter.CounterRoute
import com.finnvek.rowtool.ui.screens.counter.CounterViewModel
import com.finnvek.rowtool.ui.theme.RowToolTheme
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CounterHapticLifecycleTest {
    private val composeRule = createComposeRule()
    private val application =
        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as RowToolApplication
    private lateinit var projectId: String
    private lateinit var counterViewModel: CounterViewModel
    private lateinit var navController: NavHostController
    private lateinit var probe: HapticProbe

    @get:Rule
    val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    container.preferencesRepository.setHapticFeedbackEnabled(true)
                    projectId = container.counterRepository.createProject("Haptics", CounterUnit.ROWS, 0, 1, null).id
                },
            ).around(composeRule)

    @Test
    fun delayedMilestoneAfterNavigationNeverReplaysAndNewResumedMutationStillVibrates() =
        runBlocking {
            showCounter()
            val locked = CompletableDeferred<Unit>()
            val release = CompletableDeferred<Unit>()
            val transaction =
                launch(Dispatchers.IO) {
                    application.container.database.withTransaction {
                        locked.complete(Unit)
                        release.await()
                    }
                }
            try {
                locked.await()
                composeRule.runOnIdle { counterViewModel.increment() }
                composeRule.runOnIdle { navController.navigate("settings") }
                waitForResumed("settings")
                release.complete(Unit)
                transaction.join()
                composeRule.waitUntil(5_000) {
                    counterViewModel.uiState.value.project
                        ?.count == 1L
                }
                composeRule.runOnIdle { navController.popBackStack() }
                waitForResumed("counter")
                composeRule.runOnIdle { assertEquals(emptyList<Int>(), probe.feedback) }
                composeRule.runOnIdle { counterViewModel.increment() }
                composeRule.waitUntil(5_000) { composeRule.runOnIdle { probe.feedback.size == 1 } }
                composeRule.runOnIdle {
                    assertEquals(listOf(HapticFeedbackConstants.CLOCK_TICK), probe.feedback)
                }
                application.container.preferencesRepository.setHapticFeedbackEnabled(false)
                composeRule.waitUntil(5_000) { !counterViewModel.preferences.value.hapticFeedbackEnabled }
                composeRule.waitForIdle()
                composeRule.runOnIdle { counterViewModel.decrement() }
                composeRule.waitUntil(5_000) {
                    counterViewModel.uiState.value.project
                        ?.count == 1L
                }
                composeRule.runOnIdle { assertEquals(1, probe.feedback.size) }
            } finally {
                release.complete(Unit)
                transaction.join()
                application.container.preferencesRepository.setHapticFeedbackEnabled(true)
            }
        }

    private fun showCounter() {
        composeRule.runOnUiThread { probe = HapticProbe(application) }
        composeRule.setContent {
            navController = rememberNavController()
            RowToolTheme {
                NavHost(navController, startDestination = "counter") {
                    composable("counter") {
                        counterViewModel =
                            viewModel(
                                factory =
                                    CounterViewModel.factory(
                                        projectId,
                                        application.container.counterRepository,
                                        application.container.preferencesRepository,
                                    ),
                            )
                        CompositionLocalProvider(LocalView provides probe) {
                            CounterRoute(counterViewModel, onProjects = {}, onSettings = {}, onMessage = {})
                        }
                    }
                    composable("settings") { Text("Settings") }
                }
            }
        }
        waitForResumed("counter")
        composeRule.waitUntil(5_000) { !counterViewModel.uiState.value.isLoading }
    }

    private fun waitForResumed(route: String) {
        composeRule.waitUntil(5_000) {
            composeRule.runOnIdle {
                navController.currentBackStackEntry?.let {
                    it.destination.route == route && it.lifecycle.currentState == Lifecycle.State.RESUMED
                } == true
            }
        }
        composeRule.waitForIdle()
    }

    private class HapticProbe(
        context: Context,
    ) : View(context) {
        val feedback = mutableListOf<Int>()

        override fun performHapticFeedback(feedbackConstant: Int): Boolean {
            feedback += feedbackConstant
            return true
        }
    }
}
