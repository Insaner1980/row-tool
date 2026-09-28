package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.PrepareApplicationStateRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepeatSettingsSaveTest {
    private val composeRule = createComposeRule()
    private lateinit var projectId: String
    private val container
        get() = (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as RowToolApplication).container

    @get:Rule
    val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    projectId = container.counterRepository.createProject("Repeat", CounterUnit.ROWS, 0, null, null).id
                    container.counterRepository.mutate(projectId, CounterMutation.ManualSet(10))
                },
            ).around(composeRule)

    @Test
    fun menuOpensDisabledRepeatAndFailedSaveKeepsDraftUntilRetryAfterRestoration() {
        val restoration = StateRestorationTester(composeRule)
        restoration.setContent { RecoveryCounterRoute(projectId, container) }
        composeRule.waitUntil(5_000) { composeRule.onAllNodesWithText("REPEAT").fetchSemanticsNodes().isNotEmpty() }
        composeRule.onNodeWithContentDescription("More options").performClick()
        composeRule.onNodeWithText("Repeat").performClick()
        composeRule.onNodeWithText("Track a repeat").performClick()
        composeRule.onNodeWithText("Repeat length").performScrollTo().performTextInput("8")
        composeRule.onNodeWithText("Start at next count").performScrollTo().performClick()
        composeRule.onNodeWithText("First repeat row").assertTextContains("11")
        container.database.openHelper.writableDatabase.execSQL(
            "CREATE TRIGGER reject_repeat BEFORE UPDATE OF repeatLength, repeatStartCount ON projects " +
                "BEGIN SELECT RAISE(ABORT, 'test'); END",
        )
        try {
            assertFailedRepeatSaveAndRestore(composeRule, restoration)
            composeRule.onNodeWithText("First repeat row").assertIsDisplayed()
            assertEquals(null, runBlocking { container.counterRepository.getProject(projectId)?.repeatLength })
        } finally {
            container.database.openHelper.writableDatabase
                .execSQL("DROP TRIGGER reject_repeat")
        }
        composeRule.onNodeWithText("Save").performClick()
        composeRule.waitUntil(5_000) {
            composeRule
                .onAllNodesWithText("First repeat row")
                .fetchSemanticsNodes()
                .isEmpty()
        }
        val saved = runBlocking { container.counterRepository.getProject(projectId) }!!
        assertEquals(10L, saved.count)
        assertEquals(8, saved.repeatLength)
        assertEquals(11L, saved.repeatStartCount)
        assertEquals(1, runBlocking { container.database.counterHistoryDao().countForProject(projectId) })
        composeRule.onNodeWithText("Repeat 0/8").performScrollTo().performClick()
        composeRule.onNodeWithText("First repeat row").assertTextContains("11")
    }
}
