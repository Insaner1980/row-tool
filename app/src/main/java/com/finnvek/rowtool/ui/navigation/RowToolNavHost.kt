package com.finnvek.rowtool.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.finnvek.rowtool.ui.screens.projects.ProjectsRoute
import com.finnvek.rowtool.ui.screens.projects.ProjectsViewModel
import com.finnvek.rowtool.ui.screens.settings.SettingsRoute
import com.finnvek.rowtool.ui.screens.settings.SettingsViewModel

@Composable
fun RowToolNavHost(
    container: AppContainer,
    startProjectId: String?,
    onMessage: suspend (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
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
            arguments = listOf(navArgument("projectId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId").orEmpty()
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
                viewModel = counterViewModel,
                onProjects = { navController.navigateToProjects() },
                onSettings = {
                    navController.navigate(Screen.SETTINGS) { launchSingleTop = true }
                },
                onMessage = onMessage,
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
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
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
