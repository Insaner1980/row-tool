package com.finnvek.rowtool.ui

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SnackbarLifecycleTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val hostState = SnackbarHostState()
    private lateinit var enqueueMessage: (String) -> Unit
    private val sourceVisible = mutableStateOf(true)

    @Before
    fun setUp() {
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent {
            RowToolTheme {
                // Keep Material's default timeout independent of device accessibility settings.
                CompositionLocalProvider(LocalAccessibilityManager provides null) {
                    enqueueMessage = rememberSnackbarPresenter(hostState)
                    if (sourceVisible.value) {
                        Text("Source destination")
                    }
                    SnackbarHost(hostState)
                }
            }
        }
    }

    @Test
    fun activeMessageRestartsAfterLongStop() {
        enqueue("Failure")
        advanceTime(1_000)
        composeRule.onNodeWithText("Failure").assertIsDisplayed()

        moveTo(Lifecycle.State.CREATED)
        advanceTime(10_000)
        assertCurrentMessage(null)
        moveTo(Lifecycle.State.RESUMED)
        advanceTime(1_000)
        composeRule.onNodeWithText("Failure").assertIsDisplayed()
        advanceTime(2_000)
        assertCurrentMessage("Failure")
        advanceTime(2_000)
        assertCurrentMessage(null)
    }

    @Test
    fun messageArrivingWhileStoppedWaitsForResume() {
        moveTo(Lifecycle.State.CREATED)
        enqueue("Background failure")
        advanceTime(10_000)
        assertCurrentMessage(null)

        moveTo(Lifecycle.State.STARTED)
        advanceTime(10_000)
        assertCurrentMessage(null)
        moveTo(Lifecycle.State.RESUMED)
        advanceTime(1_000)
        composeRule.onNodeWithText("Background failure").assertIsDisplayed()
    }

    @Test
    fun queuedMessagesStayOrderedWithoutReplayAfterResume() {
        enqueue("First")
        enqueue("Second")
        advanceTime(1_000)
        assertCurrentMessage("First")
        moveTo(Lifecycle.State.CREATED)
        advanceTime(10_000)
        moveTo(Lifecycle.State.RESUMED)
        advanceTime(1_000)
        assertCurrentMessage("First")

        dismissCurrent()
        advanceTime(500)
        composeRule.onNodeWithText("Second").assertIsDisplayed()
        dismissCurrent()
        advanceTime(500)
        assertCurrentMessage(null)
        moveTo(Lifecycle.State.CREATED)
        moveTo(Lifecycle.State.RESUMED)
        advanceTime(10_000)
        assertCurrentMessage(null)
    }

    @Test
    fun activeMessageSurvivesPauseWithoutStop() {
        enqueue("Picker failure")
        advanceTime(1_000)
        moveTo(Lifecycle.State.STARTED)
        advanceTime(10_000)
        assertCurrentMessage(null)
        moveTo(Lifecycle.State.RESUMED)
        advanceTime(1_000)
        composeRule.onNodeWithText("Picker failure").assertIsDisplayed()
    }

    @Test
    fun removingSourceContentDoesNotCancelRootMessage() {
        enqueue("Navigation failure")
        composeRule.runOnUiThread { sourceVisible.value = false }
        advanceTime(1_000)
        composeRule.onNodeWithText("Source destination").assertDoesNotExist()
        composeRule.onNodeWithText("Navigation failure").assertIsDisplayed()
    }

    @Test
    fun identicalQueuedMessagesEachReceiveOnePresentation() {
        enqueue("Failure")
        enqueue("Failure")
        advanceTime(1_000)
        moveTo(Lifecycle.State.CREATED)
        moveTo(Lifecycle.State.RESUMED)
        advanceTime(1_000)
        assertCurrentMessage("Failure")
        dismissCurrent()
        advanceTime(500)
        assertCurrentMessage("Failure")
        dismissCurrent()
        advanceTime(500)
        assertCurrentMessage(null)
    }

    private fun enqueue(message: String) {
        composeRule.runOnUiThread { enqueueMessage(message) }
    }

    private fun moveTo(state: Lifecycle.State) {
        composeRule.activityRule.scenario.moveToState(state)
    }

    private fun advanceTime(milliseconds: Long) {
        composeRule.mainClock.advanceTimeBy(milliseconds)
        composeRule.waitForIdle()
    }

    private fun dismissCurrent() {
        composeRule.runOnUiThread {
            val current = checkNotNull(hostState.currentSnackbarData)
            current.dismiss()
        }
    }

    private fun assertCurrentMessage(expected: String?) {
        composeRule.runOnUiThread {
            if (expected == null) {
                assertNull(hostState.currentSnackbarData)
            } else {
                assertEquals(expected, hostState.currentSnackbarData?.visuals?.message)
            }
        }
    }
}
