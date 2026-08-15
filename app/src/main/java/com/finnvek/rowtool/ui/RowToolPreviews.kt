package com.finnvek.rowtool.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.finnvek.rowtool.data.preferences.AppPreferences
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.screens.counter.CounterNavigationActions
import com.finnvek.rowtool.ui.screens.counter.CounterProjectActions
import com.finnvek.rowtool.ui.screens.counter.CounterScreenActions
import com.finnvek.rowtool.ui.screens.counter.CounterScreenContent
import com.finnvek.rowtool.ui.screens.counter.CounterUiState
import com.finnvek.rowtool.ui.screens.counter.CounterValueActions
import com.finnvek.rowtool.ui.screens.projects.ProjectCardActions
import com.finnvek.rowtool.ui.screens.projects.ProjectsScreenActions
import com.finnvek.rowtool.ui.screens.projects.ProjectsScreenContent
import com.finnvek.rowtool.ui.screens.projects.ProjectsScreenState
import com.finnvek.rowtool.ui.screens.settings.SettingsScreenActions
import com.finnvek.rowtool.ui.screens.settings.SettingsScreenContent
import com.finnvek.rowtool.ui.theme.RowToolTheme

@Preview(name = "Projects empty", showBackground = true)
@Composable
private fun EmptyProjectsPreview() {
    ProjectsPreview(activeProjects = emptyList(), archivedProjects = emptyList())
}

@Preview(name = "Projects active and archived", showBackground = true)
@Composable
private fun ProjectsListPreview() {
    ProjectsPreview(
        activeProjects = listOf(previewProject(name = "Garden scarf", count = 42)),
        archivedProjects =
            listOf(
                previewProject(name = "Round cushion", count = 18, archived = true),
            ),
        archivedExpanded = true,
    )
}

@Composable
private fun ProjectsPreview(
    activeProjects: List<CounterProject>,
    archivedProjects: List<CounterProject>,
    archivedExpanded: Boolean = false,
) {
    RowToolTheme {
        ProjectsScreenContent(
            state = ProjectsScreenState(activeProjects, archivedProjects, archivedExpanded),
            actions =
                ProjectsScreenActions(
                    onArchivedExpandedChange = {},
                    onNewProject = {},
                    onSettings = {},
                    project = ProjectCardActions({}, {}, {}, {}, {}),
                ),
        )
    }
}

@Preview(name = "Counter basic", showBackground = true)
@Composable
private fun BasicCounterPreview() {
    CounterPreview(project = previewProject(count = 27))
}

@Preview(name = "Counter target", showBackground = true)
@Composable
private fun TargetCounterPreview() {
    CounterPreview(project = previewProject(count = 42, target = 120))
}

@Preview(name = "Counter repeat", showBackground = true)
@Composable
private fun RepeatCounterPreview() {
    CounterPreview(project = previewProject(count = 15, repeat = 6))
}

@Preview(
    name = "Counter target reached dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ReachedCounterPreview() {
    CounterPreview(
        project = previewProject(count = 120, target = 120, repeat = 6),
        darkTheme = true,
    )
}

@Preview(name = "Settings light", showBackground = true)
@Preview(
    name = "Settings dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SettingsPreview() {
    RowToolTheme {
        SettingsScreenContent(
            preferences = AppPreferences(),
            versionName = "1.0.0",
            actions = SettingsScreenActions({}, {}, {}, {}, {}, {}),
        )
    }
}

@Composable
private fun CounterPreview(
    project: CounterProject,
    darkTheme: Boolean = false,
) {
    RowToolTheme(darkTheme = darkTheme) {
        CounterScreenContent(
            state = CounterUiState(project = project, canUndo = true),
            actions =
                CounterScreenActions(
                    navigation = CounterNavigationActions({}, {}),
                    value = CounterValueActions({}, {}, {}, {}),
                    project = CounterProjectActions({}, {}, {}, {}),
                ),
        )
    }
}

private fun previewProject(
    name: String = "Garden scarf",
    count: Long = 0,
    target: Long? = null,
    repeat: Int? = null,
    archived: Boolean = false,
) = CounterProject(
    id = "preview-$name",
    name = name,
    counterUnit = if (name.contains("Round")) CounterUnit.ROUNDS else CounterUnit.ROWS,
    count = count,
    startValue = 0,
    targetCount = target,
    repeatLength = repeat,
    isArchived = archived,
    createdAt = 1,
    updatedAt = 1,
)
