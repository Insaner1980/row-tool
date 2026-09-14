package com.finnvek.rowtool.ui

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.DarkMode
import androidx.compose.ui.test.DeviceConfigurationOverride
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.ViewModelStore
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.BackupRepository
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.screens.counter.CountEditorDialog
import com.finnvek.rowtool.ui.screens.counter.CounterRoute
import com.finnvek.rowtool.ui.screens.counter.CounterViewModel
import com.finnvek.rowtool.ui.screens.projects.ProjectsRoute
import com.finnvek.rowtool.ui.screens.projects.ProjectsViewModel
import com.finnvek.rowtool.ui.screens.settings.SettingsRoute
import com.finnvek.rowtool.ui.screens.settings.SettingsViewModel
import com.finnvek.rowtool.ui.theme.RowToolTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.File

@RunWith(Parameterized::class)
class DialogReadabilityTest(
    private val darkTheme: Boolean,
) {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val viewModels = ViewModelStore()
    private lateinit var database: RowToolDatabase
    private lateinit var repository: CounterRepository
    private lateinit var preferences: PreferencesRepository
    private lateinit var project: CounterProject
    private lateinit var colors: ColorScheme
    private var dialogSurface = Color.Unspecified

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(context, RowToolDatabase::class.java).build()
        repository = CounterRepository(database)
        val store =
            object : DataStore<Preferences> {
                override val data = MutableStateFlow(emptyPreferences())

                override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences =
                    transform(data.value).also { data.value = it }
            }
        preferences = PreferencesRepository(store, database.projectDao())
        project = runBlocking { repository.createProject("Dialog test", CounterUnit.ROWS, 0, null, null) }
    }

    @After
    fun tearDown() {
        composeRule.runOnIdle { viewModels.clear() }
        database.close()
    }

    @Test
    fun countEditorRetainsSelectionValidationAndCallbacksWithReadableText() {
        var saved: Long? = null
        var dismissals = 0
        var showDialog by mutableStateOf(false)
        setContent {
            if (showDialog) CountEditorDialog(5, onDismiss = { dismissals++ }, onSave = { saved = it })
        }
        composeRule.waitUntil(5_000) { composeRule.activity.hasWindowFocus() }
        composeRule.runOnIdle { showDialog = true }
        val field = composeRule.onNodeWithText("Count")
        composeRule.captureDiagnostic("count-initial-$darkTheme")
        composeRule.waitUntil(5_000) { field.fetchSemanticsNode().config[SemanticsProperties.Focused] }
        field.assertIsFocused()
        assertEquals(TextRange(0, 1), field.fetchSemanticsNode().config[SemanticsProperties.TextSelectionRange])
        assertText("Count", colors.secondary, dialogSurface)
        assertAction("Save", destructive = false)
        capture("count-focused")
        for (invalid in listOf("", "-1", "1000000", "abc")) {
            field.performTextReplacement(invalid)
            composeRule.onNodeWithText("Save").assertIsNotEnabled()
            assertNull(saved)
        }
        assertText("Count", colors.onErrorContainer, dialogSurface)
        assertText("Enter a whole number from 0 to 999,999.", colors.onErrorContainer, dialogSurface)
        assertTrue(contrast(colors.onErrorContainer, colors.errorContainer) >= 4.5f)
        capture("count-error")
        field.performTextReplacement("0")
        composeRule.onNodeWithText("Save").assertIsEnabled()
        field.performTextReplacement("999999")
        composeRule.onNodeWithText("Save").performClick()
        assertEquals(999999L, saved)
        composeRule.onNodeWithText("Cancel").performClick()
        assertEquals(1, dismissals)
    }

    @Test
    fun counterKeepsResetAndArchiveOrdinaryAndDeleteDestructive() {
        val viewModel = CounterViewModel(project.id, repository, preferences)
        viewModels.put("counter", viewModel)
        setContent { CounterRoute(viewModel, onProjects = {}, onSettings = {}, onMessage = {}) }
        composeRule.waitUntil(5_000) { viewModel.uiState.value.project != null }
        for (action in listOf("Reset count", "Archive", "Delete")) {
            composeRule.onNodeWithContentDescription("More options").performClick()
            composeRule.onNodeWithText(action).performClick()
            assertAction(action, destructive = action == "Delete")
            capture("counter-${action.replace(' ', '-')}")
            composeRule.onNodeWithText("Cancel").performClick()
            assertEquals(project, runBlocking { repository.getProject(project.id) })
        }
        confirmProjectDeletion("More options")
    }

    @Test
    fun projectsDeletionRetainsCancelAndConfirmBehavior() {
        val viewModel = ProjectsViewModel(repository, preferences)
        viewModels.put("projects", viewModel)
        setContent { ProjectsRoute(viewModel, onOpenProject = {}, onSettings = {}, onMessage = {}) }
        composeRule.waitUntil(5_000) { !viewModel.uiState.value.isLoading }
        composeRule.onNodeWithContentDescription("Options for Dialog test").performClick()
        composeRule.onNodeWithText("Delete").performClick()
        assertAction("Delete", destructive = true)
        capture("projects-delete")
        composeRule.onNodeWithText("Cancel").performClick()
        assertEquals(project, runBlocking { repository.getProject(project.id) })
        confirmProjectDeletion("Options for Dialog test")
    }

    @Test
    fun replacementImportRetainsCancelAndConfirmBehavior() {
        val backup = BackupRepository(database, preferences)
        val viewModel = SettingsViewModel(preferences, backup)
        viewModels.put("settings", viewModel)
        setContent { SettingsRoute(viewModel, onBack = {}, onMessage = {}, onImportComplete = {}) }
        val file = File.createTempFile("dialog-backup", ".json", context.cacheDir)
        try {
            file.writeText(runBlocking { backup.exportJson() })
            val extra = runBlocking { repository.createProject("To replace", CounterUnit.ROWS, 0, null, null) }
            composeRule.runOnIdle { viewModel.prepareImport(context.contentResolver, Uri.fromFile(file)) }
            composeRule.waitUntil(5_000) { viewModel.importPreview.value != null }
            assertAction("Replace projects", destructive = true)
            capture("backup-replace")
            composeRule.onNodeWithText("Cancel").performClick()
            assertEquals(extra, runBlocking { repository.getProject(extra.id) })
            composeRule.runOnIdle { viewModel.prepareImport(context.contentResolver, Uri.fromFile(file)) }
            composeRule.waitUntil(5_000) { viewModel.importPreview.value != null }
            composeRule.onNodeWithText("Replace projects").performClick()
            composeRule.waitUntil(5_000) { runBlocking { repository.getProject(extra.id) == null } }
            assertEquals(project, runBlocking { repository.getProject(project.id) })
        } finally {
            file.delete()
        }
    }

    private fun setContent(content: @androidx.compose.runtime.Composable () -> Unit) {
        composeRule.setContent {
            DeviceConfigurationOverride(DeviceConfigurationOverride.DarkMode(darkTheme)) {
                RowToolTheme(darkTheme = darkTheme) {
                    colors = MaterialTheme.colorScheme
                    dialogSurface = AlertDialogDefaults.containerColor
                    content()
                }
            }
        }
    }

    private fun confirmProjectDeletion(optionsDescription: String) {
        composeRule.onNodeWithContentDescription(optionsDescription).performClick()
        composeRule.onNodeWithText("Delete").performClick()
        composeRule.onNodeWithText("Delete").performClick()
        composeRule.waitUntil(5_000) { runBlocking { repository.getProject(project.id) == null } }
    }

    private fun assertAction(
        label: String,
        destructive: Boolean,
    ) {
        composeRule.waitForIdle()
        val foreground = if (destructive) colors.onErrorContainer else colors.secondary
        val background = if (destructive) colors.errorContainer else dialogSurface
        assertText(label, foreground, background)
        assertText("Cancel", colors.secondary, dialogSurface)
        if (destructive) {
            composeRule.captureDiagnostic("action-$label-$darkTheme")
            val pixels = composeRule.onNodeWithText(label).captureToImage().toPixelMap()
            assertTrue(
                "Destructive action must render its error container: expected=${colors.errorContainer}, corner=${pixels[pixels.width / 2, 5]}",
                (0 until pixels.width).any { x ->
                    (0 until pixels.height).any { y -> pixels[x, y] == colors.errorContainer }
                },
            )
        }
    }

    private fun assertText(
        label: String,
        expected: Color,
        background: Color,
    ) {
        composeRule.waitForIdle()
        val layouts = mutableListOf<TextLayoutResult>()
        composeRule
            .onNodeWithText(label, useUnmergedTree = true)
            .performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
        val foreground =
            layouts
                .single()
                .layoutInput.style.color
        assertEquals(label, expected, foreground)
        assertTrue("$label contrast=${contrast(foreground, background)}", contrast(foreground, background) >= 4.5f)
    }

    private fun capture(name: String) {
        val directory = File(context.getExternalFilesDir(null), "dialog-readability").also { it.mkdirs() }
        val bitmap = composeRule.onNode(isDialog()).captureToImage().asAndroidBitmap()
        File(directory, "$name-${if (darkTheme) "dark" else "light"}.png").outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    private fun contrast(
        foreground: Color,
        background: Color,
    ): Float =
        (maxOf(foreground.luminance(), background.luminance()) + 0.05f) /
            (minOf(foreground.luminance(), background.luminance()) + 0.05f)

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "darkTheme={0}")
        fun themes(): List<Boolean> = listOf(false, true)
    }
}
