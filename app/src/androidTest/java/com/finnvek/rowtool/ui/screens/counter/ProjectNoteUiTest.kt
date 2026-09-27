package com.finnvek.rowtool.ui.screens.counter

import android.graphics.Bitmap
import android.view.View
import android.view.WindowInsets
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.unit.Density
import androidx.lifecycle.SavedStateHandle
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.PrepareApplicationStateRule
import com.finnvek.rowtool.ui.screens.note.NoteEditorHost
import com.finnvek.rowtool.ui.screens.note.NoteEditorViewModel
import com.finnvek.rowtool.ui.theme.RowToolTheme
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class ProjectNoteUiTest {
    private val compose = createComposeRule()
    private val container get() =
        (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as RowToolApplication)
            .container
    private lateinit var id: String

    @get:Rule val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    id = container.counterRepository.createProject("Note work", CounterUnit.ROWS, 0, null, null).id
                },
            ).around(compose)

    @Test fun longTextKeyboardSaveAndDeleteFitBothThemesAt320dpAnd200Percent() {
        val text = "  Continue here\n\n    Knit 🧶\n".repeat(40)
        runBlocking { container.counterRepository.notes.save(id, null, text, true) }
        lateinit var vm: NoteEditorViewModel
        var dark by mutableStateOf(false)
        var editorView: View? = null
        compose.runOnUiThread { vm = NoteEditorViewModel(id, container.counterRepository.notes, SavedStateHandle()) }
        compose.setContent {
            editorView = LocalView.current
            val density = LocalWindowInfo.current.containerSize.width / 320f
            CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 2f)) {
                RowToolTheme(
                    darkTheme = dark,
                ) { NoteEditorHost(id, "test", container.counterRepository.notes, viewModel = vm, onDismiss = {}) }
            }
        }
        compose.waitUntil(5000) { vm.state.value.ready }
        for (theme in listOf(false, true)) {
            compose.runOnIdle { dark = theme }
            compose
                .onNodeWithTag("note-text")
                .performScrollTo()
                .assertTextContains(text)
                .performClick()
            compose.waitUntil(5000) { editorView?.rootWindowInsets?.isVisible(WindowInsets.Type.ime()) == true }
            compose.onNodeWithTag("note-save").assertIsDisplayed()
            screenshot("note-${if (theme) "dark" else "light"}-keyboard")
            Espresso.pressBack()
            compose.onNodeWithTag("note-text").assertExists()
            compose.onNodeWithTag("note-delete").performScrollTo().performClick()
            compose.onNodeWithText("Delete note?").assertIsDisplayed()
            screenshot("note-${if (theme) "dark" else "light"}-delete")
            compose.onNodeWithText("Cancel").performClick()
        }
        compose.onNodeWithTag("note-text").performScrollTo().performTextReplacement("Changed\n    🧶")
        compose.onNodeWithTag("note-save").performClick()
        compose.waitUntil(5000) { vm.state.value.completed }
        assertEquals(
            "Changed\n    🧶",
            runBlocking {
                container.counterRepository.notes
                    .load(id)
                    .note!!
                    .text
            },
        )
    }

    @Test fun counterMenuPreviewAndDiscardStayScopedToEditor() {
        val vm = CounterViewModel(id, container.counterRepository, container.preferencesRepository)
        var dark by mutableStateOf(false)
        compose.setContent {
            val density = LocalWindowInfo.current.containerSize.width / 320f
            CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 2f)) {
                RowToolTheme(darkTheme = dark) { CounterRoute(vm, {}, {}, {}) }
            }
        }
        compose.waitUntil(5000) { !vm.uiState.value.isLoading }
        compose.onNodeWithContentDescription("More options").performClick()
        compose.onNodeWithText("Note").performClick()
        compose.onNodeWithTag("note-text").performTextReplacement("First\n  Next")
        compose.onNodeWithTag("note-back").performClick()
        compose.onNodeWithText("Cancel").performClick()
        compose.onNodeWithTag("note-text").assertTextContains("First\n  Next")
        compose.onNodeWithTag("note-save").performClick()
        compose.waitUntil(5000) { compose.onAllNodesWithTag("note-editor").fetchSemanticsNodes().isEmpty() }
        for (theme in listOf(false, true)) {
            compose.runOnIdle { dark = theme }
            compose.onNodeWithTag("note-preview").performScrollTo().assertIsDisplayed()
            screenshot("note-${if (theme) "dark" else "light"}-preview")
            compose.onNodeWithTag("note-preview").performClick()
            compose.onNodeWithTag("note-text").assertTextContains("First\n  Next")
            compose.onNodeWithTag("note-back").performClick()
        }
        compose.onNodeWithTag("note-preview").performClick()
        compose.onNodeWithTag("note-delete").performScrollTo().performClick()
        compose.onNode(hasText("Delete") and hasAnyAncestor(isDialog() and hasAnyDescendant(hasText("Delete note?")))).performClick()
        compose.waitUntil(5000) { compose.onAllNodesWithTag("note-editor").fetchSemanticsNodes().isEmpty() }
        assertNull(
            runBlocking {
                container.counterRepository.notes
                    .load(id)
                    .note
            },
        )
    }

    private fun screenshot(name: String) {
        compose.waitForIdle()
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val bitmap = instrumentation.uiAutomation.takeScreenshot()
        File(instrumentation.targetContext.getExternalFilesDir(null), "$name.png").outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        bitmap.recycle()
    }
}
