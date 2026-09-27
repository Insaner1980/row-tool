package com.finnvek.rowtool.ui

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Assert.assertEquals

/** External Room/DataStore work can still be pending after Activity launch and Compose idleness. */
internal fun ComposeTestRule.awaitDirectCounter(
    expectedProjectId: String,
    selectedProjectId: () -> String?,
    timeoutMillis: Long = 5_000,
) {
    val heading = onNode(hasText("DIRECT START") and SemanticsMatcher.keyIsDefined(SemanticsProperties.Heading))
    val count = onNode(hasText("0") and hasClickAction())
    val add = onNodeWithContentDescription("Add one row")
    val remove = onNodeWithContentDescription("Remove one row")
    val back = onNodeWithContentDescription("Back")
    waitUntil(timeoutMillis) {
        selectedProjectId() == expectedProjectId &&
            heading.isDisplayed() && count.isDisplayed() && add.isDisplayed() && remove.isDisplayed() && back.isDisplayed()
    }
    assertEquals(expectedProjectId, selectedProjectId())
    heading.assertIsDisplayed()
    count.assertIsDisplayed().assertHasClickAction()
    add.assertIsDisplayed().assertHasClickAction()
    remove.assertIsDisplayed().assertHasClickAction()
    back.assertIsDisplayed().assertHasClickAction()
}
