package com.finnvek.rowtool.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.finnvek.rowtool.AppContainer
import com.finnvek.rowtool.ui.screens.counter.CounterRoute
import com.finnvek.rowtool.ui.screens.counter.CounterViewModel
import com.finnvek.rowtool.ui.screens.history.HistoryRoute
import com.finnvek.rowtool.ui.screens.projects.ProjectsRoute
import com.finnvek.rowtool.ui.screens.projects.ProjectsViewModel
import com.finnvek.rowtool.ui.screens.settings.SettingsRoute
import com.finnvek.rowtool.ui.screens.settings.SettingsViewModel
import com.finnvek.rowtool.widget.ownsWidget

@Composable
fun RowToolNavHost(
    container: AppContainer,
    startProjectId: String?,
    onMessage: (Int) -> Unit,
    modifier: Modifier = Modifier,
    widgetRequest: com.finnvek.rowtool.widget.WidgetOpenRequest? = null,
    onWidgetConsume: () -> Unit = {},
) {
    val navController = rememberNavController()
    val currentOnWidgetConsume by androidx.compose.runtime.rememberUpdatedState(onWidgetConsume)
    val currentOnMessage by androidx.compose.runtime.rememberUpdatedState(onMessage)
    val context = LocalContext.current
    var widgetReminderProject by rememberSaveable { mutableStateOf<String?>(null) }
    LaunchedEffect(widgetRequest, container.noteEditorVisible, container.copyEditorVisible) {
        val request = widgetRequest ?: return@LaunchedEffect
        if (container.noteEditorVisible || container.copyEditorVisible) return@LaunchedEffect
        try {
            if (context.ownsWidget(request.widgetId)) {
                container.widgetBindings.withBinding(request.widgetId, request.token) { projectId ->
                    val project = container.counterRepository.getProject(projectId)
                    kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main.immediate) {
                        if (project?.isArchived == false) {
                            navController.navigate(Screen.counter(projectId)) { launchSingleTop = true }
                            widgetReminderProject = projectId.takeIf { request.reminders }
                        } else {
                            navController.navigateToProjects()
                        }
                    }
                }
            }
        } catch (_: java.io.IOException) {
            currentOnMessage(com.finnvek.rowtool.R.string.widget_error)
        } catch (_: android.database.SQLException) {
            currentOnMessage(com.finnvek.rowtool.R.string.widget_error)
        }
        currentOnWidgetConsume()
    }
    val startDestination = startProjectId?.let(Screen::counter) ?: Screen.PROJECTS

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(Screen.PROJECTS) {
            val projectsViewModel: ProjectsViewModel =
                viewModel(
                    factory =
                        ProjectsViewModel.factory(
                            container.counterRepository,
                            container.preferencesRepository,
                        ),
                )
            ProjectsRoute(
                viewModel = projectsViewModel,
                onHistory = { navController.navigate(Screen.history(it)) { launchSingleTop = true } },
                onOpenProject = { projectId ->
                    navController.navigate(Screen.counter(projectId)) {
                        launchSingleTop = true
                    }
                },
                onSettings = {
                    navController.navigate(Screen.SETTINGS) { launchSingleTop = true }
                },
                onMessage = onMessage,
            )
        }

        composable(
            route = Screen.COUNTER_PATTERN,
            arguments = listOf(navArgument(Screen.COUNTER_PROJECT_ID_ARG) { type = NavType.StringType }),
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString(Screen.COUNTER_PROJECT_ID_ARG).orEmpty()
            val counterViewModel: CounterViewModel =
                viewModel(
                    key = "counter:$projectId",
                    factory =
                        CounterViewModel.factory(
                            projectId,
                            container.counterRepository,
                            container.preferencesRepository,
                        ),
                )
            CounterRoute(
                openWidgetReminders = widgetReminderProject == projectId,
                onWidgetRemindersOpen = { widgetReminderProject = null },
                viewModel = counterViewModel,
                onHistory = { navController.navigate(Screen.history(it)) { launchSingleTop = true } },
                onProjects = { navController.navigateToProjects() },
                onSettings = {
                    navController.navigate(Screen.SETTINGS) { launchSingleTop = true }
                },
                onMessage = onMessage,
            )
        }

        composable(
            route = Screen.HISTORY_PATTERN,
            arguments = listOf(navArgument(Screen.COUNTER_PROJECT_ID_ARG) { type = NavType.StringType }),
        ) { backStackEntry ->
            HistoryRoute(
                projectId = backStackEntry.arguments?.getString(Screen.COUNTER_PROJECT_ID_ARG).orEmpty(),
                repository = container.counterRepository,
                onBack = { navController.popBackStack() },
                onMissing = { navController.navigateToProjects() },
            )
        }

        composable(Screen.SETTINGS) {
            val settingsViewModel: SettingsViewModel =
                viewModel(
                    factory =
                        SettingsViewModel.factory(
                            container.preferencesRepository,
                            container.backupRepository,
                        ),
                )
            SettingsRoute(
                viewModel = settingsViewModel,
                onBack = { navController.popBackStack() },
                onMessage = onMessage,
                onImportComplete = { projectId ->
                    val destination = projectId?.let(Screen::counter) ?: Screen.PROJECTS
                    navController.navigate(destination) {
                        popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}

private fun NavHostController.navigateToProjects() {
    if (!popBackStack(Screen.PROJECTS, inclusive = false)) {
        navigate(Screen.PROJECTS) {
            popUpTo(graph.id) { inclusive = true }
            launchSingleTop = true
        }
    }
}
