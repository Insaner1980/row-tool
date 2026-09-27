package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.PrepareApplicationStateRule
import com.finnvek.rowtool.ui.theme.RowToolTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReminderRouteTest {
    private val compose = createComposeRule()
    private val application = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as RowToolApplication
    private lateinit var projectId: String

    @get:Rule val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    projectId = container.counterRepository.createProject("Work", CounterUnit.ROWS, 0, null, null).id
                    container.counterRepository.mutate(projectId, CounterMutation.ManualSet(32))
                },
            ).around(compose)

    @Test fun menuEditorListAndAcknowledgementUseRealRoomState() {
        val viewModel = CounterViewModel(projectId, application.container.counterRepository, application.container.preferencesRepository)
        compose.setContent {
            RowToolTheme {
                CounterRoute(viewModel, onProjects = {}, onSettings = {}, onMessage = {})
            }
        }
        compose.waitUntil(5_000) { !viewModel.uiState.value.isLoading }
        compose.onNodeWithContentDescription("More options").performClick()
        compose.onNodeWithText("Reminders").performClick()
        compose.onNodeWithText("Add reminder").performClick()
        compose.onNodeWithText("Message").performTextReplacement("Check the pattern")
        compose.onNodeWithText("First row").performTextReplacement("32")
        compose.onNodeWithText("Save").performScrollTo().performClick()
        compose.waitUntil(5_000) { viewModel.uiState.value.reminders.size == 1 }
        compose.onAllNodesWithText("Check the pattern").assertCountEquals(2)
        compose.onNodeWithText("Acknowledge").performScrollTo().performClick()
        compose.waitUntil(5_000) {
            viewModel.uiState.value.reminders
                .singleOrNull()
                ?.acknowledgedThrough == 32L
        }
        compose.onNodeWithText("Acknowledged").assertIsDisplayed()
        assertEquals(
            32L,
            runBlocking {
                application.container.counterRepository.reminders
                    .observe(projectId)
                    .first()
                    .single()
                    .acknowledgedThrough
            },
        )
    }
}
