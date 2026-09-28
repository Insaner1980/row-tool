package com.finnvek.rowtool.widget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.preferences.AppPreferences
import com.finnvek.rowtool.data.preferences.ThemeMode
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.ui.theme.RowToolTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WidgetConfigurationActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        com.finnvek.rowtool.widget
            .refreshWidgetLanguage(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_CANCELED)
        val id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        if (!ownsWidget(id)) {
            finish()
            return
        }
        window.decorView.setFilterTouchesWhenObscured(true)
        enableEdgeToEdge()
        setContent {
            val preferences by widgetContainer().preferencesRepository.preferences.collectAsStateWithLifecycle(AppPreferences())
            val dark =
                when (preferences.themeMode) {
                    ThemeMode.SYSTEM -> isSystemInDarkTheme()
                    ThemeMode.DARK -> true
                    ThemeMode.LIGHT -> false
                }
            RowToolTheme(darkTheme = dark) {
                ConfigurationContent(id)
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean =
        event.flags and MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED == 0 && super.dispatchTouchEvent(event)

    private suspend fun saveSelection(
        id: Int,
        projectId: String,
    ): Boolean {
        if (bindWidget(this, id, projectId) == null) return false
        requestWidgetUpdate(this)
        setResult(RESULT_OK, Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id))
        finish()
        return true
    }

    @Composable
    private fun ConfigurationContent(id: Int) {
        var selected by rememberSaveable { mutableStateOf<String?>(null) }
        var projects by remember { mutableStateOf<List<CounterProject>?>(null) }
        var error by remember { mutableStateOf(false) }
        var saving by remember { mutableStateOf(false) }
        var attempt by remember { androidx.compose.runtime.mutableIntStateOf(0) }
        val scope = rememberCoroutineScope()
        LaunchedEffect(attempt) {
            error = false
            try {
                widgetContainer().counterRepository.projects.collect { projects = it.filterNot(CounterProject::isArchived) }
            } catch (_: java.io.IOException) {
                error = true
            } catch (_: android.database.SQLException) {
                error = true
            }
        }
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                Modifier.safeDrawingPadding().verticalScroll(rememberScrollState()).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(stringResource(R.string.widget_choose), style = MaterialTheme.typography.headlineSmall)
                ConfigurationProjects(projects, error, saving, selected, { selected = it }, { attempt++ })
                TextButton(
                    enabled = !saving && !error && projects?.any { it.id == selected } == true,
                    onClick = {
                        val projectId = selected ?: return@TextButton
                        saving = true
                        scope.launch {
                            try {
                                error = !saveSelection(id, projectId)
                            } catch (_: java.io.IOException) {
                                error = true
                            } catch (_: android.database.SQLException) {
                                error = true
                            } finally {
                                saving = false
                            }
                        }
                    },
                ) { Text(stringResource(R.string.action_save)) }
                TextButton(onClick = { finish() }, enabled = !saving) { Text(stringResource(R.string.action_cancel)) }
            }
        }
    }
}

@Composable
private fun ColumnScope.ConfigurationProjects(
    projects: List<CounterProject>?,
    error: Boolean,
    saving: Boolean,
    selected: String?,
    onSelect: (String) -> Unit,
    onRetry: () -> Unit,
) {
    when {
        error -> {
            Text(stringResource(R.string.widget_error))
            TextButton(onClick = { onRetry() }) { Text(stringResource(R.string.widget_retry)) }
        }

        projects == null -> {
            Text(stringResource(R.string.widget_loading))
        }

        projects.isEmpty() -> {
            Text(stringResource(R.string.widget_empty))
        }

        else -> {
            projects.forEach { project ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(selected == project.id, enabled = !saving, role = Role.RadioButton) {
                            onSelect(project.id)
                        }.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(selected == project.id, onClick = null)
                    Text(project.name, Modifier.padding(start = 12.dp))
                }
            }
        }
    }
}

internal suspend fun bindWidget(
    context: android.content.Context,
    id: Int,
    projectId: String,
    dispatcher: kotlinx.coroutines.CoroutineDispatcher = Dispatchers.IO,
): WidgetBinding? =
    withContext(dispatcher) {
        if (!context.ownsWidget(id)) return@withContext null
        val container = context.widgetContainer()
        container.widgetBindings.bindIf(id, projectId) {
            context.ownsWidget(id) && container.counterRepository.getProject(projectId)?.isArchived == false
        }
    }
