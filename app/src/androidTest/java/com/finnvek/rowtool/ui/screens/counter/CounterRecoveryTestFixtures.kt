package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.AppContainer
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.PrepareApplicationStateRule
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

internal fun recoveryProjectRule(
    composeRule: TestRule,
    setProjectId: (String) -> Unit,
): TestRule =
    RuleChain
        .outerRule(
            PrepareApplicationStateRule {
                val application = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as RowToolApplication
                val container = application.container
                setProjectId(container.counterRepository.createProject("Recovery", CounterUnit.ROWS, 0, null, null).id)
            },
        ).around(composeRule)

@Composable
internal fun RecoveryCounterRoute(
    projectId: String,
    container: AppContainer,
) {
    RowToolTheme {
        CounterRoute(
            viewModel(factory = CounterViewModel.factory(projectId, container.counterRepository, container.preferencesRepository)),
            onProjects = {},
            onSettings = {},
            onMessage = {},
        )
    }
}

internal fun assertFailedRepeatSaveAndRestore(
    composeRule: ComposeTestRule,
    restoration: StateRestorationTester,
) {
    composeRule.onNodeWithText("Save").performScrollTo().performClick()
    composeRule.waitUntil(5_000) {
        composeRule.onAllNodesWithText("Could not save the change. Try again.").fetchSemanticsNodes().isNotEmpty()
    }
    restoration.emulateSavedInstanceStateRestore()
    composeRule.onNodeWithText("First repeat row").assertTextContains("11")
}
