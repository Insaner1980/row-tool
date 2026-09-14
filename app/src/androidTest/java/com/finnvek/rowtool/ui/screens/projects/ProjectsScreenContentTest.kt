package com.finnvek.rowtool.ui.screens.projects

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.assertTextFits
import com.finnvek.rowtool.ui.theme.RowToolTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

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
        composeRule.onAllNodesWithText("New project").assertCountEquals(1)
        composeRule.onNodeWithContentDescription("New project").assertDoesNotExist()
        composeRule.onNodeWithText("New project").performClick()
        assertEquals(1, newProjectClicks)
    }

    @Test
    fun loadingStateDoesNotShowTheEmptyLibraryAction() {
        composeRule.setContent {
            RowToolTheme(darkTheme = false) {
                ProjectsScreenContent(
                    state = ProjectsScreenState(emptyList(), emptyList(), false, isLoading = true),
                    actions = screenActions(),
                )
            }
        }

        composeRule.onNodeWithText("Create your first project").assertDoesNotExist()
    }

    @Test
    fun activeProjectRowOpensTheSelectedProjectExactlyOnce() {
        val project = project(id = "active", name = "Garden scarf")
        val openedIds = mutableListOf<String>()

        composeRule.setContent {
            RowToolTheme {
                ProjectsScreenContent(
                    state = ProjectsScreenState(listOf(project), emptyList(), false),
                    actions = screenActions(onOpenProject = { openedIds += it.id }),
                )
            }
        }

        composeRule.onNodeWithTag("project-active").assertHasClickAction()
        composeRule.onNodeWithText("Garden scarf").performClick()
        assertEquals(listOf("active"), openedIds)
    }

    @Test
    fun nonemptyLibraryHasOneLabelledImageCreationAction() {
        var newProjectClicks = 0
        composeRule.setContent {
            RowToolTheme {
                ProjectsScreenContent(
                    state = ProjectsScreenState(listOf(project("active", "Scarf")), emptyList(), false),
                    actions = screenActions(onNewProject = { newProjectClicks += 1 }),
                )
            }
        }

        composeRule.onNodeWithText("New project").assertDoesNotExist()
        composeRule
            .onNodeWithContentDescription("New project")
            .assertHasClickAction()
            .assertWidthIsEqualTo(72.dp)
            .assertHeightIsEqualTo(72.dp)
            .performClick()
        assertEquals(1, newProjectClicks)
    }

    @Test
    fun activeMenuKeepsItsProjectIdentityWhenRowsReorder() {
        val first = project(id = "first", name = "First")
        val second = project(id = "second", name = "Second")
        var activeProjects by mutableStateOf(listOf(first, second))
        var archivedId: String? = null
        var openCount = 0

        composeRule.setContent {
            RowToolTheme {
                ProjectsScreenContent(
                    state = ProjectsScreenState(activeProjects, emptyList(), false),
                    actions =
                        screenActions(
                            onOpenProject = { openCount += 1 },
                            onArchiveProject = { archivedId = it.id },
                        ),
                )
            }
        }

        composeRule.onNodeWithContentDescription("Options for First").performClick()
        composeRule.runOnIdle { activeProjects = activeProjects.reversed() }
        composeRule.onNodeWithText("Archive").performClick()
        assertEquals("first", archivedId)
        assertEquals(0, openCount)
    }

    @Test
    fun archivedSectionExpandsAndCollapsesWithoutOpeningTheCounter() {
        var expanded by mutableStateOf(false)
        val archived = project("archived", "Old scarf").copy(isArchived = true)
        var openCount = 0
        var restoredId: String? = null
        var deletedId: String? = null

        composeRule.setContent {
            RowToolTheme {
                ProjectsScreenContent(
                    state = ProjectsScreenState(emptyList(), listOf(archived), expanded),
                    actions =
                        screenActions(
                            onArchivedExpandedChange = { expanded = it },
                            onOpenProject = { openCount += 1 },
                            onRestoreProject = { restoredId = it.id },
                            onDeleteProject = { deletedId = it.id },
                        ),
                )
            }
        }

        composeRule.onNodeWithTag("project-archived").assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Show archived projects").performClick()
        composeRule.onNodeWithTag("project-archived").assertIsDisplayed().assertHasNoClickAction()
        composeRule.onNodeWithText("Archived").assertIsDisplayed()
        composeRule.onNodeWithText("Old scarf").performClick()
        assertEquals(0, openCount)
        composeRule.onNodeWithContentDescription("Options for Old scarf").performClick()
        composeRule.onNodeWithText("Restore").performClick()
        assertEquals("archived", restoredId)
        composeRule.onNodeWithContentDescription("Options for Old scarf").performClick()
        composeRule.onNodeWithText("Delete").performClick()
        assertEquals("archived", deletedId)
        composeRule.onNodeWithContentDescription("Hide archived projects").performClick()
        composeRule.onNodeWithTag("project-archived").assertDoesNotExist()
        composeRule.onNodeWithContentDescription("New project").assertIsDisplayed()
    }

    @Test
    fun narrowRowGrowsWithLargeTextAndKeepsOptionalMetadata() {
        var fontScale by mutableFloatStateOf(1f)
        var currentProject by mutableStateOf(
            project("long", "A long project name that wraps across multiple lines").copy(
                counterUnit = CounterUnit.ROUNDS,
                targetCount = 999999,
                repeatLength = 999,
            ),
        )
        composeRule.setContent {
            val density = LocalDensity.current.density
            CompositionLocalProvider(LocalDensity provides Density(density, fontScale)) {
                RowToolTheme(darkTheme = true) {
                    Box(Modifier.width(320.dp)) {
                        ProjectsScreenContent(
                            state = ProjectsScreenState(listOf(currentProject), emptyList(), false),
                            actions = screenActions(),
                        )
                    }
                }
            }
        }

        val normalHeight =
            composeRule
                .onNodeWithTag("project-long")
                .fetchSemanticsNode()
                .boundsInRoot.height
        composeRule.runOnIdle { fontScale = 2f }
        val largeHeight =
            composeRule
                .onNodeWithTag("project-long")
                .fetchSemanticsNode()
                .boundsInRoot.height
        assertTrue(largeHeight > normalHeight)
        listOf("12 rounds", "Target 999999", "Repeat every 999").forEach { text ->
            composeRule
                .onNodeWithText(text, useUnmergedTree = true)
                .assertIsDisplayed()
                .assertTextFits()
        }
        composeRule.onNodeWithContentDescription("Options for ${currentProject.name}").assertHasClickAction()
        composeRule.runOnIdle { currentProject = currentProject.copy(targetCount = null) }
        composeRule.onNodeWithText("Target 999999", useUnmergedTree = true).assertDoesNotExist()
        composeRule.onNodeWithText("Repeat every 999", useUnmergedTree = true).assertIsDisplayed()
        composeRule.runOnIdle { currentProject = currentProject.copy(targetCount = 999999, repeatLength = null) }
        composeRule.onNodeWithText("Target 999999", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithText("Repeat every 999", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun sectionCapitalizationUsesTheAppConfigurationLocale() {
        composeRule.setContent {
            val configuration = Configuration(LocalConfiguration.current).apply { setLocale(Locale.forLanguageTag("tr")) }
            CompositionLocalProvider(LocalConfiguration provides configuration) {
                RowToolTheme {
                    ProjectsScreenContent(
                        state =
                            ProjectsScreenState(
                                listOf(project("active", "Scarf")),
                                listOf(project("archived", "Old scarf").copy(isArchived = true)),
                                false,
                            ),
                        actions = screenActions(),
                    )
                }
            }
        }

        composeRule.onNodeWithText("ACT\u0130VE PROJECTS").assertIsDisplayed()
        composeRule.onNodeWithText("ARCH\u0130VED").assertIsDisplayed()
    }

    @Test
    fun archivedMenuKeepsItsProjectIdentityWhenRowsReorder() {
        val first = project(id = "first", name = "First").copy(isArchived = true)
        val second = project(id = "second", name = "Second").copy(isArchived = true)
        var archivedProjects by mutableStateOf(listOf(first, second))
        var editedId: String? = null

        composeRule.setContent {
            RowToolTheme {
                ProjectsScreenContent(
                    state = ProjectsScreenState(emptyList(), archivedProjects, archivedExpanded = true),
                    actions = screenActions(onEditProject = { editedId = it.id }),
                )
            }
        }

        composeRule.onNodeWithContentDescription("Options for First").performClick()
        composeRule.runOnIdle { archivedProjects = archivedProjects.reversed() }
        composeRule.onNodeWithText("Edit").performClick()

        assertEquals("first", editedId)
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
        onEditProject: (CounterProject) -> Unit = {},
        onArchiveProject: (CounterProject) -> Unit = {},
        onRestoreProject: (CounterProject) -> Unit = {},
        onDeleteProject: (CounterProject) -> Unit = {},
        onArchivedExpandedChange: (Boolean) -> Unit = {},
    ) = ProjectsScreenActions(
        onArchivedExpandedChange = onArchivedExpandedChange,
        onNewProject = onNewProject,
        onSettings = {},
        project = ProjectCardActions(onOpenProject, onEditProject, onArchiveProject, onRestoreProject, onDeleteProject),
    )
}
