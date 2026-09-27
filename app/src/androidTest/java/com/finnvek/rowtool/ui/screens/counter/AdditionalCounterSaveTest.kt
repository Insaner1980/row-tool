package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.PrepareApplicationStateRule
import com.finnvek.rowtool.ui.theme.RowToolTheme
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdditionalCounterSaveTest {
    private val composeRule = createComposeRule()
    private lateinit var projectId: String
    private val container
        get() = (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as RowToolApplication).container

    @get:Rule
    val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    projectId = container.counterRepository.createProject("Recovery", CounterUnit.ROWS, 0, null, null).id
                },
            ).around(composeRule)

    @Test
    fun databaseFailureRetainsNewCounterDraftUntilSuccessfulRetryAfterRestoration() {
        val restoration = StateRestorationTester(composeRule)
        restoration.setContent {
            RowToolTheme {
                CounterRoute(
                    viewModel(factory = CounterViewModel.factory(projectId, container.counterRepository, container.preferencesRepository)),
                    onProjects = {},
                    onSettings = {},
                    onMessage = {},
                )
            }
        }
        composeRule.waitUntil(5_000) { composeRule.onAllNodesWithText("RECOVERY").fetchSemanticsNodes().isNotEmpty() }
        composeRule.onNodeWithText("Add counter").performScrollTo().performClick()
        composeRule.onNodeWithText("Counter name").performTextInput("Sleeve")
        container.database.openHelper.writableDatabase.execSQL(
            "CREATE TRIGGER reject_counter BEFORE INSERT ON additional_counters BEGIN SELECT RAISE(ABORT, 'test'); END",
        )
        try {
            composeRule.onNodeWithText("Save").performClick()
            composeRule.waitUntil(5_000) {
                composeRule.onAllNodesWithText("Could not save the change. Try again.").fetchSemanticsNodes().isNotEmpty()
            }
            restoration.emulateSavedInstanceStateRestore()
            composeRule.onNodeWithText("Sleeve").assertIsDisplayed()
            assertEquals(
                0,
                runBlocking {
                    container.database
                        .additionalCounterDao()
                        .getAll()
                        .size
                },
            )
        } finally {
            container.database.openHelper.writableDatabase
                .execSQL("DROP TRIGGER reject_counter")
        }
        composeRule.onNodeWithText("Save").performClick()
        composeRule.waitUntil(5_000) { composeRule.onAllNodesWithText("Counter name").fetchSemanticsNodes().isEmpty() }
        assertEquals(
            "Sleeve",
            runBlocking {
                container.database
                    .additionalCounterDao()
                    .getActive(projectId)
                    .single()
                    .name
            },
        )
        composeRule.onNodeWithContentDescription("Set count for Sleeve, currently 0").performScrollTo().performClick()
        composeRule.onNodeWithText("Count").performTextClearance()
        composeRule.onNodeWithText("Count").performTextInput("17")
        container.database.openHelper.writableDatabase.execSQL(
            "CREATE TRIGGER reject_count BEFORE UPDATE ON additional_counters BEGIN SELECT RAISE(ABORT, 'test'); END",
        )
        try {
            composeRule.onNodeWithText("Save").performClick()
            composeRule.waitUntil(5_000) {
                composeRule.onAllNodesWithText("Could not save the change. Try again.").fetchSemanticsNodes().isNotEmpty()
            }
            composeRule.onNodeWithText("17").assertIsDisplayed()
            assertEquals(
                0L,
                runBlocking {
                    container.database
                        .additionalCounterDao()
                        .getActive(projectId)
                        .single()
                        .count
                },
            )
        } finally {
            container.database.openHelper.writableDatabase
                .execSQL("DROP TRIGGER reject_count")
        }
        composeRule.onNodeWithText("Save").performClick()
        composeRule.waitUntil(5_000) { composeRule.onAllNodesWithText("Count").fetchSemanticsNodes().isEmpty() }
        assertEquals(
            17L,
            runBlocking {
                container.database
                    .additionalCounterDao()
                    .getActive(projectId)
                    .single()
                    .count
            },
        )
    }
}
