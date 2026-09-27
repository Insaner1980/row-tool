package com.finnvek.rowtool.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.room.withTransaction
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.MainActivity
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.data.preferences.ThemeMode
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CopySetupFlowTest {
    private val compose = createAndroidComposeRule<MainActivity>()
    private val container get() =
        (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as RowToolApplication).container
    private lateinit var sourceId: String
    private val sourceName = "Long source project for a striped cardigan and cables"

    @get:Rule val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    val repository = container.counterRepository
                    sourceId = repository.createProject(sourceName, CounterUnit.ROWS, 1, 120, 8, 11).id
                    repository.additionalCounters.save(sourceId, null, "Leg rows", true)
                    repository.additionalCounters.save(sourceId, null, "Decreases", false)
                    repository.mutate(sourceId, CounterMutation.ManualSet(75))
                    container.preferencesRepository.setLastActiveProjectId(sourceId)
                },
            ).around(compose)

    @Test fun activeReviewCancelAndEditedDraftRecreationWriteNothing() {
        projects()
        val before = runBlocking { container.database.projectDao().getAll() }
        val preferences = runBlocking { container.preferencesRepository.preferences.first() }
        openCopy()
        compose.onNodeWithText("Create").assertIsNotEnabled()
        compose.onNodeWithText("Cancel").performClick()
        compose.onNodeWithText("Discard changes?").assertDoesNotExist()
        openCopy()
        compose.onNodeWithText("Project name").performScrollTo().performTextReplacement("Draft name")
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("Project name").performScrollTo().assertTextContains("Draft name")
        compose.onNodeWithText("Cancel").performClick()
        compose.onNodeWithText("Discard changes?").assertIsDisplayed()
        compose.onNodeWithText("Discard").performClick()
        assertEquals(before, runBlocking { container.database.projectDao().getAll() })
        assertEquals(preferences, runBlocking { container.preferencesRepository.preferences.first() })
    }

    @Test fun archivedCopyOpensIndependentCounterAndUndo() {
        projects()
        // Finish leaving the source before this fixture archives it outside the UI.
        compose.waitUntil(10_000) { compose.onAllNodesWithText(sourceName.uppercase()).fetchSemanticsNodes().isEmpty() }
        compose.onNodeWithText("ACTIVE PROJECTS").assertIsDisplayed()
        runBlocking { container.counterRepository.setArchived(sourceId, true) }
        compose.onNodeWithContentDescription("Show archived projects").performClick()
        val before = runBlocking { container.counterRepository.getProject(sourceId) }
        val sourceCounters = runBlocking { container.database.additionalCounterDao().getActive(sourceId) }
        val sourceHistory = runBlocking { container.database.counterHistoryDao().getAll() }
        openCopy()
        compose.onNodeWithText("Project name").performScrollTo().performTextReplacement("Copied cardigan")
        compose.onNodeWithText("Create").performClick()
        waitFor("COPIED CARDIGAN")
        val copy =
            runBlocking {
                container.database
                    .projectDao()
                    .getAll()
                    .single { it.id != sourceId }
            }
        assertEquals(1L, copy.count)
        assertEquals(11L, copy.repeatStartCount)
        val copiedCounters = runBlocking { container.database.additionalCounterDao().getActive(copy.id) }
        assertEquals(mapOf("Leg rows" to true, "Decreases" to false), copiedCounters.associate { it.name to it.followsMain })
        assertTrue(copiedCounters.all { it.projectId == copy.id && it.count == 0L && it.id !in sourceCounters.map { source -> source.id } })
        assertEquals(0, runBlocking { container.database.counterHistoryDao().countForProject(copy.id) })
        compose.captureAssertionFailure(
            label = "copied-counter-increment",
            activity = { compose.activity },
            state = {
                runBlocking {
                    "sourceId=$sourceId copyId=${copy.id}\n" +
                        "copyEditorVisible=${container.copyEditorVisible}\n" +
                        "projects=${container.database.projectDao().getAll()}\n" +
                        "counters=${container.database.additionalCounterDao().getAll()}\n" +
                        "history=${container.database.counterHistoryDao().getAll()}\n" +
                        "effects=${container.database.counterHistoryEffectDao().getAll()}\n" +
                        "preferences=${container.preferencesRepository.preferences.first()}"
                }
            },
        ) {
            compose.onNodeWithText("Restore this project before changing its count.").assertDoesNotExist()
            assertCopiedMainCount(1)
            compose
                .onNodeWithContentDescription("Add one row")
                .performScrollTo()
                .assertIsDisplayed()
                .assertIsEnabled()
                .performClick()
            compose.waitUntil(10_000) { runBlocking { container.counterRepository.getProject(copy.id)!!.count == 2L } }
        }
        assertCopiedMainCount(2)
        assertEquals(
            mapOf("Leg rows" to 1L, "Decreases" to 0L),
            runBlocking { container.database.additionalCounterDao().getActive(copy.id) }.associate { it.name to it.count },
        )
        assertEquals(1, runBlocking { container.database.counterHistoryDao().countForProject(copy.id) })
        compose
            .onNodeWithContentDescription("Undo last count change")
            .performScrollTo()
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        compose.waitUntil(10_000) { runBlocking { container.counterRepository.getProject(copy.id)!!.count == 1L } }
        assertCopiedMainCount(1)
        compose.activityRule.scenario.recreate()
        waitFor("COPIED CARDIGAN")
        assertEquals(2, runBlocking { container.database.projectDao().count() })
        assertEquals(before, runBlocking { container.counterRepository.getProject(sourceId) })
        assertEquals(sourceCounters, runBlocking { container.database.additionalCounterDao().getActive(sourceId) })
        assertEquals(sourceHistory, runBlocking { container.database.counterHistoryDao().getAll() })
        assertEquals(copiedCounters, runBlocking { container.database.additionalCounterDao().getActive(copy.id) })
        assertTrue(
            runBlocking {
                container.database
                    .additionalCounterDao()
                    .getActive(copy.id)
                    .all { it.count == 0L }
            },
        )
    }

    @Test fun recreationWhileCreateWaitsForRoomCompletesOnlyOnce() {
        projects()
        openCopy()
        compose.onNodeWithText("Project name").performScrollTo().performTextReplacement("Pending copy")
        Espresso.closeSoftKeyboard()
        val entered = CompletableDeferred<Unit>()
        val release = CompletableDeferred<Unit>()
        val blocker =
            CoroutineScope(Dispatchers.IO).launch {
                container.database.withTransaction {
                    entered.complete(Unit)
                    release.await()
                }
            }
        try {
            runBlocking { entered.await() }
            compose.onNodeWithText("Create").performClick()
            compose.onNodeWithText("Create").assertIsNotEnabled()
            compose.activityRule.scenario.recreate()
            compose.onNodeWithText("Create").assertIsNotEnabled()
            release.complete(Unit)
            waitFor("PENDING COPY")
            assertEquals(2, runBlocking { container.database.projectDao().count() })
            compose.activityRule.scenario.recreate()
            waitFor("PENDING COPY")
            assertEquals(2, runBlocking { container.database.projectDao().count() })
        } finally {
            release.complete(Unit)
            runBlocking { blocker.join() }
        }
    }

    @Test fun copyIsSelectableWithoutChangingSourceWidget() {
        projects()
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val context = instrumentation.targetContext
        val host = android.appwidget.AppWidgetHost(context, 5328)
        val ids = mutableListOf<Int>()
        try {
            instrumentation.uiAutomation.adoptShellPermissionIdentity("android.permission.BIND_APPWIDGET")
            try {
                repeat(2) {
                    val id = host.allocateAppWidgetId()
                    ids.add(id)
                    assertTrue(
                        android.appwidget.AppWidgetManager.getInstance(context).bindAppWidgetIdIfAllowed(
                            id,
                            android.content.ComponentName(context, com.finnvek.rowtool.widget.CounterWidgetReceiver::class.java),
                        ),
                    )
                }
            } finally {
                instrumentation.uiAutomation.dropShellPermissionIdentity()
            }
            val sourceBinding = runBlocking { container.widgetBindings.bind(ids[0], sourceId) }
            openCopy()
            compose.onNodeWithText("Project name").performScrollTo().performTextReplacement("Widget copy")
            compose.onNodeWithText("Create").performClick()
            waitFor("WIDGET COPY")
            val copy =
                runBlocking {
                    container.database
                        .projectDao()
                        .getAll()
                        .single { it.id != sourceId }
                }
            val intent =
                android.content
                    .Intent(context, com.finnvek.rowtool.widget.WidgetConfigurationActivity::class.java)
                    .putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, ids[1])
            androidx.test.core.app.ActivityScenario.launch<com.finnvek.rowtool.widget.WidgetConfigurationActivity>(intent).use {
                compose.onNodeWithText("Widget copy").performScrollTo().performClick()
                compose.onNodeWithText("Save").performScrollTo().performClick()
            }
            assertEquals(sourceBinding, runBlocking { container.widgetBindings.read(ids[0]) })
            assertEquals(copy.id, runBlocking { container.widgetBindings.read(ids[1])!!.projectId })
        } finally {
            ids.forEach { id -> runBlocking { container.widgetBindings.remove(id) } }
            host.deleteHost()
        }
    }

    @Test fun longReviewKeyboardAndBothThemesKeepActionsVisible() {
        projects()
        for (theme in listOf(ThemeMode.LIGHT, ThemeMode.DARK)) {
            runBlocking { container.preferencesRepository.setThemeMode(theme) }
            openCopy()
            compose
                .onNodeWithText("Project name")
                .performScrollTo()
                .performClick()
                .performTextReplacement("Theme draft")
            compose.onNodeWithText("Create").assertIsDisplayed()
            compose.onNodeWithText("Cancel").assertIsDisplayed()
            val bounds = compose.onNodeWithText("Create").fetchSemanticsNode().boundsInRoot
            assertTrue(bounds.height >= 48 * compose.density.density)
            InstrumentationRegistry.getInstrumentation().uiAutomation.takeScreenshot()?.let { screenshot ->
                val path =
                    java.io.File(
                        InstrumentationRegistry.getInstrumentation().targetContext.getExternalFilesDir(null),
                        "copy-${theme.name}.png",
                    )
                path.outputStream().use { screenshot.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, it) }
                screenshot.recycle()
            }
            Espresso.closeSoftKeyboard()
            compose.onNodeWithText("Cancel").performClick()
            compose.onNodeWithText("Discard").performClick()
        }
        assertEquals(1, runBlocking { container.database.projectDao().count() })
    }

    private fun projects() {
        compose.waitUntil(10_000) { compose.onAllNodesWithText(sourceName.uppercase()).fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithContentDescription("Back").performClick()
    }

    private fun assertCopiedMainCount(count: Long) {
        compose.onNodeWithText("COPIED CARDIGAN").assertIsDisplayed()
        compose
            .onNode(hasStateDescription("Edit count, currently $count"))
            .performScrollTo()
            .assertIsDisplayed()
            .assertIsEnabled()
            .assertTextEquals(count.toString())
    }

    private fun openCopy() {
        compose.onNodeWithContentDescription("Options for $sourceName").performScrollTo().performClick()
        compose.onNodeWithText("Copy counter setup").performClick()
        waitFor("Project name")
    }

    private fun waitFor(text: String) {
        compose.waitUntil(10_000) { compose.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty() }
    }
}
