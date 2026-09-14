package com.finnvek.rowtool.ui.screens.projects

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.RowToolDropdownMenuItem
import com.finnvek.rowtool.ui.RowToolSectionHeadingText
import com.finnvek.rowtool.ui.screens.counter.CounterButtonLayout
import com.finnvek.rowtool.ui.screens.counter.CounterImageButton
import com.finnvek.rowtool.ui.theme.RowToolDimens

data class ProjectsScreenState(
    val activeProjects: List<CounterProject>,
    val archivedProjects: List<CounterProject>,
    val archivedExpanded: Boolean,
    val isLoading: Boolean = false,
)

data class ProjectCardActions(
    val onOpen: (CounterProject) -> Unit,
    val onEdit: (CounterProject) -> Unit,
    val onArchive: (CounterProject) -> Unit,
    val onRestore: (CounterProject) -> Unit,
    val onDelete: (CounterProject) -> Unit,
)

data class ProjectsScreenActions(
    val onArchivedExpandedChange: (Boolean) -> Unit,
    val onNewProject: () -> Unit,
    val onSettings: () -> Unit,
    val project: ProjectCardActions,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreenContent(
    state: ProjectsScreenState,
    actions: ProjectsScreenActions,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = actions.onSettings) {
                        Icon(
                            painter = painterResource(R.drawable.ic_settings),
                            contentDescription = stringResource(R.string.action_settings),
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
            )
        },
        floatingActionButton = {
            if (state.activeProjects.isNotEmpty() || state.archivedProjects.isNotEmpty()) {
                CounterImageButton(
                    imageRes = R.drawable.counter_plus_button,
                    contentDescription = stringResource(R.string.action_new_project),
                    layout = CounterButtonLayout(visualSize = 64.dp, touchSize = 72.dp),
                    enabled = true,
                    onClick = actions.onNewProject,
                )
            }
        },
    ) { contentPadding ->
        ProjectsBody(
            state = state,
            actions = actions,
            contentPadding = contentPadding,
        )
    }
}

@Composable
private fun ProjectsBody(
    state: ProjectsScreenState,
    actions: ProjectsScreenActions,
    contentPadding: PaddingValues,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(contentPadding),
        contentAlignment = Alignment.TopCenter,
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.activeProjects.isEmpty() && state.archivedProjects.isEmpty()) {
            EmptyProjects(
                onNewProject = actions.onNewProject,
                modifier =
                    Modifier
                        .widthIn(max = RowToolDimens.MaxContentWidth)
                        .fillMaxSize()
                        .padding(horizontal = RowToolDimens.PhoneHorizontalPadding),
            )
        } else {
            ProjectsList(state = state, actions = actions)
        }
    }
}

@Composable
private fun ProjectsList(
    state: ProjectsScreenState,
    actions: ProjectsScreenActions,
) {
    LazyColumn(
        modifier =
            Modifier
                .widthIn(max = RowToolDimens.MaxContentWidth)
                .fillMaxWidth(),
        contentPadding =
            PaddingValues(
                start = RowToolDimens.PhoneHorizontalPadding,
                end = RowToolDimens.PhoneHorizontalPadding,
                top = RowToolDimens.Space12,
                bottom = 104.dp,
            ),
    ) {
        activeProjectItems(state.activeProjects, actions.project)
        archivedProjectItems(
            projects = state.archivedProjects,
            expanded = state.archivedExpanded,
            onExpandedChange = actions.onArchivedExpandedChange,
            actions = actions.project,
        )
    }
}

private fun LazyListScope.activeProjectItems(
    projects: List<CounterProject>,
    actions: ProjectCardActions,
) {
    if (projects.isEmpty()) return
    item {
        RowToolSectionHeadingText(
            text = stringResource(R.string.projects_active_title),
            modifier = Modifier.padding(top = RowToolDimens.Space20, bottom = RowToolDimens.Space8),
        )
    }
    itemsIndexed(projects, key = { _, project -> project.id }) { index, project ->
        ProjectRow(project = project, actions = actions)
        if (index < projects.lastIndex) {
            ProjectDivider()
        }
    }
}

private fun LazyListScope.archivedProjectItems(
    projects: List<CounterProject>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    actions: ProjectCardActions,
) {
    if (projects.isEmpty()) return
    item {
        ArchivedProjectsHeader(
            projectCount = projects.size,
            expanded = expanded,
            onExpandedChange = onExpandedChange,
        )
    }
    item {
        AnimatedVisibility(visible = expanded) {
            Column {
                projects.forEachIndexed { index, project ->
                    key(project.id) {
                        ProjectRow(project = project, actions = actions)
                        if (index < projects.lastIndex) {
                            ProjectDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArchivedProjectsHeader(
    projectCount: Int,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = RowToolDimens.Space20)
                .heightIn(min = RowToolDimens.MinimumTouchSize)
                .clickable(
                    role = Role.Button,
                    onClick = { onExpandedChange(!expanded) },
                ).padding(vertical = RowToolDimens.Space8),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RowToolSectionHeadingText(
            text = stringResource(R.string.projects_archived_title),
            modifier = Modifier.weight(1f),
        )
        Text(
            text =
                pluralStringResource(
                    R.plurals.project_count,
                    projectCount,
                    projectCount,
                ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Icon(
            painter = painterResource(R.drawable.ic_expand),
            contentDescription =
                stringResource(
                    if (expanded) R.string.projects_hide_archived else R.string.projects_show_archived,
                ),
            modifier = Modifier.rotate(if (expanded) 180f else 0f),
        )
    }
}

@Composable
private fun EmptyProjects(
    onNewProject: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = RowToolDimens.Space24),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.projects_empty_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = stringResource(R.string.projects_empty_body),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = RowToolDimens.Space12),
        )
        Button(
            onClick = onNewProject,
            modifier = Modifier.padding(top = RowToolDimens.Space24),
        ) {
            Text(stringResource(R.string.action_new_project))
        }
        Text(
            text = stringResource(R.string.projects_empty_privacy),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = RowToolDimens.Space20),
        )
    }
}

@Composable
private fun ProjectRow(
    project: CounterProject,
    actions: ProjectCardActions,
    modifier: Modifier = Modifier,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .testTag("project-${project.id}")
                .semantics {
                    // Preserve the former Card surface's accessibility container.
                    @Suppress("DEPRECATION")
                    isContainer = true
                }.then(
                    if (project.isArchived) {
                        Modifier
                    } else {
                        Modifier.clickable(role = Role.Button) { actions.onOpen(project) }
                    },
                ).padding(vertical = 14.dp),
        verticalAlignment = Alignment.Top,
    ) {
        ProjectSummary(project = project, modifier = Modifier.weight(1f))
        ProjectOptions(
            project = project,
            expanded = menuExpanded,
            onExpandedChange = { menuExpanded = it },
            actions = actions,
        )
    }
}

@Composable
private fun ProjectDivider() {
    HorizontalDivider(
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
    )
}

@Composable
private fun ProjectSummary(
    project: CounterProject,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space4),
    ) {
        Text(
            text = project.name,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text =
                when (project.counterUnit) {
                    CounterUnit.ROWS -> {
                        pluralStringResource(
                            R.plurals.row_count,
                            project.count.toInt(),
                            project.count,
                        )
                    }

                    CounterUnit.ROUNDS -> {
                        pluralStringResource(
                            R.plurals.round_count,
                            project.count.toInt(),
                            project.count,
                        )
                    }
                },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
        )
        project.targetCount?.let { target ->
            Text(
                text = stringResource(R.string.project_target_summary, target),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        project.repeatLength?.let { repeat ->
            Text(
                text = stringResource(R.string.project_repeat_summary, repeat),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        if (project.isArchived) {
            Text(
                text = stringResource(R.string.project_archived),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun ProjectOptions(
    project: CounterProject,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    actions: ProjectCardActions,
) {
    Box {
        IconButton(onClick = { onExpandedChange(true) }) {
            Icon(
                painter = painterResource(R.drawable.ic_more),
                contentDescription = stringResource(R.string.project_options, project.name),
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
        ) {
            RowToolDropdownMenuItem(
                label = stringResource(R.string.action_edit),
                iconRes = R.drawable.ic_edit,
                onClick = {
                    onExpandedChange(false)
                    actions.onEdit(project)
                },
            )
            RowToolDropdownMenuItem(
                label =
                    stringResource(
                        if (project.isArchived) R.string.action_restore else R.string.action_archive,
                    ),
                iconRes = if (project.isArchived) R.drawable.ic_restore else R.drawable.ic_archive,
                onClick = {
                    onExpandedChange(false)
                    if (project.isArchived) {
                        actions.onRestore(project)
                    } else {
                        actions.onArchive(project)
                    }
                },
            )
            RowToolDropdownMenuItem(
                label = stringResource(R.string.action_delete),
                iconRes = R.drawable.ic_delete,
                onClick = {
                    onExpandedChange(false)
                    actions.onDelete(project)
                },
            )
        }
    }
}
