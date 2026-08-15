package com.finnvek.rowtool.ui.screens.projects

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProjectsScreenContentTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun emptyLibraryShowsSingleClearCreationPath() {
        var newProjectClicks = 0

        composeRule.setContent {
            RowToolTheme(darkTheme = false) {
                ProjectsScreenContent(
                    state = ProjectsScreenState(emptyList(), emptyList(), false),
                    actions = screenActions(onNewProject = { newProjectClicks += 1 }),
                )
            }
        }

        composeRule.onNodeWithText("Create your first project").assertIsDisplayed()
        composeRule.onNodeWithText("New project").performClick()
        assertEquals(1, newProjectClicks)
    }

    @Test
    fun activeProjectCardOpensTheSelectedProject() {
        val project = project(id = "active", name = "Garden scarf")
        var openedId: String? = null

        composeRule.setContent {
            RowToolTheme {
                ProjectsScreenContent(
                    state = ProjectsScreenState(listOf(project), emptyList(), false),
                    actions = screenActions(onOpenProject = { openedId = it.id }),
                )
            }
        }

        composeRule.onNodeWithText("Garden scarf").performClick()
        assertEquals("active", openedId)
    }

    private fun project(
        id: String,
        name: String,
    ) = CounterProject(
        id = id,
        name = name,
        counterUnit = CounterUnit.ROWS,
        count = 12,
        startValue = 0,
        targetCount = null,
        repeatLength = null,
        isArchived = false,
        createdAt = 1,
        updatedAt = 1,
    )

    private fun screenActions(
        onNewProject: () -> Unit = {},
        onOpenProject: (CounterProject) -> Unit = {},
    ) = ProjectsScreenActions(
        onArchivedExpandedChange = {},
        onNewProject = onNewProject,
        onSettings = {},
        project = ProjectCardActions(onOpenProject, {}, {}, {}, {}),
    )
}
