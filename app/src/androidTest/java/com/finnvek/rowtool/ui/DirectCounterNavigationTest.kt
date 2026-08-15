package com.finnvek.rowtool.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.MainActivity
import com.finnvek.rowtool.data.preferences.ThemeMode
import com.finnvek.rowtool.domain.model.CounterUnit
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DirectCounterNavigationTest {
    private val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    val project =
                        container.counterRepository.createProject(
                            name = "Direct start",
                            counterUnit = CounterUnit.ROWS,
                            startValue = 0,
                            targetCount = null,
                            repeatLength = null,
                        )
                    container.preferencesRepository.setThemeMode(ThemeMode.SYSTEM)
                    container.preferencesRepository.setHapticFeedbackEnabled(true)
                    container.preferencesRepository.setKeepScreenAwake(true)
                    container.preferencesRepository.setLastActiveProjectId(project.id)
                },
            ).around(composeRule)

    @Test
    fun projectsFromDirectCounterDoesNotLeaveCounterOnBackStack() {
        composeRule.onNodeWithText("Direct start").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Back").performClick()
        composeRule.onNodeWithText("Active projects").assertIsDisplayed()

        composeRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.activityRule.scenario.state != Lifecycle.State.RESUMED
        }

        assertNotEquals(Lifecycle.State.RESUMED, composeRule.activityRule.scenario.state)
    }
}
