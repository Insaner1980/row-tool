package com.finnvek.rowtool.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ComposeTimeoutException
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.core.os.LocaleListCompat
import androidx.room.withTransaction
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.MainActivity
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.data.local.AdditionalCounterEntity
import com.finnvek.rowtool.data.local.CounterHistoryEffectEntity
import com.finnvek.rowtool.data.local.CounterHistoryEntity
import com.finnvek.rowtool.data.local.ProjectEntity
import com.finnvek.rowtool.data.repository.BackupDecodeResult
import com.finnvek.rowtool.data.repository.BackupImportResult
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.captureAssertionFailure
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WidgetHostTest {
    @get:Rule val compose = createEmptyComposeRule()
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val context get() = instrumentation.targetContext
    private val container get() = (context.applicationContext as RowToolApplication).container

    @Before fun prepare() {
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            context.getSystemService(android.app.LocaleManager::class.java).applicationLocales = android.os.LocaleList.getEmptyLocaleList()
        }
        runBlocking { container.database.clearAllTables() }
        android.appwidget.AppWidgetHost(context, 5327).deleteHost()
    }

    @Test
    @androidx.test.filters.SdkSuppress(minSdkVersion = 33)
    fun externalLanguageChangesRefreshHostedTextWithoutRebindingOrChangingTargets() {
        val locales = context.getSystemService(android.app.LocaleManager::class.java)
        val first = runBlocking { container.counterRepository.createProject("Locale widget", CounterUnit.ROWS, 0, null, null) }
        val other = runBlocking { container.counterRepository.createProject("Other project", CounterUnit.ROWS, 0, null, null) }
        ActivityScenario.launch(WidgetTestHostActivity::class.java).use { host ->
            val id = allocate(host)
            configure(id, first.name)
            attach(host, id)
            val binding = runBlocking { container.widgetBindings.read(id) }
            for (tag in listOf("fi", "sv", "")) {
                var previousHost = 0
                host.onActivity { previousHost = System.identityHashCode(it) }
                val language = tag.ifEmpty { locales.systemLocales[0].language }
                locales.applicationLocales = android.os.LocaleList.forLanguageTags(tag)
                val expected =
                    when (tag) {
                        "fi" -> "Kerrokset"
                        "sv" -> "Varv"
                        else -> "Rows"
                    }
                compose.awaitRecreatedWidget(host, previousHost, id, language, expected)
                assertEquals(binding, runBlocking { container.widgetBindings.read(id) })
                assertEquals(first, runBlocking { container.counterRepository.getProject(first.id) })
            }
            click(host, id, "+")
            waitCount(first.id, 1)
            assertEquals(other, runBlocking { container.counterRepository.getProject(other.id) })
            host.onActivity { it.host.deleteAppWidgetId(id) }
        }
    }

    @Test
    @androidx.test.filters.SdkSuppress(minSdkVersion = 33)
    fun defaultLanguageReadinessRejectsHostBeforeQueuedRecreation() {
        val locales = context.getSystemService(android.app.LocaleManager::class.java)
        val project = runBlocking { container.counterRepository.createProject("Readiness fixture", CounterUnit.ROWS, 0, null, null) }
        ActivityScenario.launch(WidgetTestHostActivity::class.java).use { host ->
            val id = allocate(host)
            configure(id, project.name)
            attach(host, id)
            val binding = runBlocking { container.widgetBindings.read(id) }
            var previousHost = 0
            host.onActivity { previousHost = System.identityHashCode(it) }
            locales.applicationLocales = android.os.LocaleList.forLanguageTags("fi")
            compose.awaitRecreatedWidget(host, previousHost, id, "fi", "Kerrokset")
            val systemLanguage = locales.systemLocales[0].language
            // Both operations run before Android can dispatch the queued locale recreation.
            host.onActivity {
                previousHost = System.identityHashCode(it)
                val tag = ""
                locales.applicationLocales = android.os.LocaleList.forLanguageTags(tag)
                assertTrue(
                    "Original condition accepts the old host",
                    tag.isEmpty() || it.resources.configuration.locales[0]
                        .language == tag,
                )
                assertFalse(it.recreatedWidgetReady(previousHost, id, systemLanguage, "Rows"))
            }
            compose.awaitRecreatedWidget(host, previousHost, id, systemLanguage, "Rows")
            assertEquals(binding, runBlocking { container.widgetBindings.read(id) })
            assertEquals(project, runBlocking { container.counterRepository.getProject(project.id) })
            host.onActivity { it.host.deleteAppWidgetId(id) }
        }
    }

    @Test
    @androidx.test.filters.SdkSuppress(minSdkVersion = 33)
    fun recreatedWidgetReadinessRequiresAttachedMatchingContentAndReportsTimeout() {
        val project = runBlocking { container.counterRepository.createProject("Attachment fixture", CounterUnit.ROWS, 0, null, null) }
        ActivityScenario.launch(WidgetTestHostActivity::class.java).use { host ->
            val id = allocate(host)
            configure(id, project.name)
            attach(host, id)
            val binding = runBlocking { container.widgetBindings.read(id) }
            var previousHost = 0
            var language = ""
            host.onActivity {
                previousHost = System.identityHashCode(it)
                language =
                    it.resources.configuration.locales[0]
                        .language
            }
            host.recreate()
            host.onActivity {
                assertFalse(it.views.containsKey(id))
                assertThrows(NoSuchElementException::class.java) { it.views.getValue(id) }
            }
            compose.awaitRecreatedWidget(host, previousHost, id, language, "Rows")
            var reattach: () -> Unit = {}
            host.onActivity {
                assertFalse(it.recreatedWidgetReady(System.identityHashCode(it), id, language, "Rows"))
                assertFalse(it.recreatedWidgetReady(previousHost, id + 1, language, "Rows"))
                assertFalse(it.recreatedWidgetReady(previousHost, id, language, "Kerrokset"))
                val view = it.views.getValue(id)
                val parent = view.parent as ViewGroup
                val params = view.layoutParams
                parent.removeView(view)
                assertFalse(it.recreatedWidgetReady(previousHost, id, language, "Rows"))
                reattach = { parent.post { parent.addView(view, params) } }
            }
            val failure =
                assertThrows(AssertionError::class.java) {
                    compose.awaitRecreatedWidget(host, previousHost, id, language, "Rows", timeoutMillis = 100)
                }
            for (detail in listOf("host=", "widgetId=$id", "attached=false", "visibleText=", "expected=$language/Rows")) {
                assertTrue(failure.message, failure.message.orEmpty().contains(detail))
            }
            instrumentation.runOnMainSync { reattach() }
            compose.awaitRecreatedWidget(host, previousHost, id, language, "Rows")
            assertEquals(binding, runBlocking { container.widgetBindings.read(id) })
            assertEquals(project, runBlocking { container.counterRepository.getProject(project.id) })
            host.onActivity { it.host.deleteAppWidgetId(id) }
        }
    }

    @Test
    @androidx.test.filters.SdkSuppress(minSdkVersion = 29, maxSdkVersion = 32)
    fun legacyLanguageChangesRefreshHostedTextWithoutRebindingOrCounting() {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
        val first = runBlocking { container.counterRepository.createProject("Legacy widget", CounterUnit.ROWS, 0, null, null) }
        val other = runBlocking { container.counterRepository.createProject("Other project", CounterUnit.ROWS, 0, null, null) }
        runBlocking { container.counterRepository.additionalCounters.save(first.id, null, "Linked rows", true) }
        val beforeFirst = runBlocking { container.counterRepository.getProject(first.id) }
        val linkedCount =
            runBlocking {
                container.counterRepository.additionalCounters
                    .observe(first.id)
                    .first()
                    .single()
                    .count
            }
        assertEquals(0L, linkedCount)
        ActivityScenario.launch(WidgetTestHostActivity::class.java).use { host ->
            val id = allocate(host)
            configure(id, first.name)
            attach(host, id)
            val binding = runBlocking { container.widgetBindings.read(id) }
            ActivityScenario.launch(MainActivity::class.java).use {
                for ((tag, label) in listOf("fi" to "Kerrokset", "sv" to "Varv", "" to "Rows")) {
                    instrumentation.runOnMainSync {
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
                    }
                    assertEquals(tag, AppCompatDelegate.getApplicationLocales().toLanguageTags())
                    requestWidgetUpdate(context)
                    waitText(host, id, label)
                    assertEquals(binding, runBlocking { container.widgetBindings.read(id) })
                    assertEquals(beforeFirst, runBlocking { container.counterRepository.getProject(first.id) })
                    assertEquals(other, runBlocking { container.counterRepository.getProject(other.id) })
                }
            }
            click(host, id, "+")
            waitCount(first.id, 1)
            val linkedAfterPlus =
                runBlocking {
                    container.counterRepository.additionalCounters
                        .observe(first.id)
                        .first()
                        .single()
                        .count
                }
            assertEquals(1L, linkedAfterPlus)
            click(host, id, "−")
            waitCount(first.id, 0)
            val linkedAfterMinus =
                runBlocking {
                    container.counterRepository.additionalCounters
                        .observe(first.id)
                        .first()
                        .single()
                        .count
                }
            assertEquals(0L, linkedAfterMinus)
            assertEquals(other, runBlocking { container.counterRepository.getProject(other.id) })
            host.onActivity { it.host.deleteAppWidgetId(id) }
        }
    }

    @Test fun minimumHostedLayoutFitsLongNameMaximumCountAndBothThemes() {
        val locale = InstrumentationRegistry.getArguments().getString("widgetLocale")
        if (locale != null && android.os.Build.VERSION.SDK_INT >= 33) {
            context.getSystemService(android.app.LocaleManager::class.java).applicationLocales =
                android.os.LocaleList.forLanguageTags(locale)
        }
        val repo = container.counterRepository
        val name = "A very long synthetic project name for the minimum widget"
        val project = runBlocking { repo.createProject(name, CounterUnit.ROUNDS, 0, null, null) }
        runBlocking { repo.mutate(project.id, CounterMutation.ManualSet(999999)) }
        runBlocking { repo.reminders.save(project.id, null, null, "Maximum reminder", 999999, null, true) }
        ActivityScenario.launch(WidgetTestHostActivity::class.java).use { host ->
            val id = allocate(host)
            configure(id, name)
            attach(host, id)
            for (theme in listOf(
                com.finnvek.rowtool.data.preferences.ThemeMode.LIGHT,
                com.finnvek.rowtool.data.preferences.ThemeMode.DARK,
            )) {
                runBlocking { container.preferencesRepository.setThemeMode(theme) }
                val count =
                    java.text.NumberFormat
                        .getIntegerInstance(context.resources.configuration.locales[0])
                        .format(999999)
                waitText(host, id, count)
                instrumentation.waitForIdleSync()
                host.onActivity { activity ->
                    val root = activity.views.getValue(id)
                    val density = activity.resources.displayMetrics.density
                    assertEquals(280f, root.width / density, 1f)
                    assertEquals(280f, root.height / density, 1f)
                    val countView = texts(root).single { it.text.toString() == count }
                    assertEquals(0, countView.layout.getEllipsisCount(0))
                    assertTrue(countView.layout.height <= countView.height)
                    val dueText =
                        context.getString(
                            com.finnvek.rowtool.R.string.widget_reminders,
                            java.text.NumberFormat
                                .getIntegerInstance()
                                .format(1),
                        )
                    val dueView = texts(root).single { it.text.toString() == dueText }
                    assertTrue("Reminder text fits height", dueView.layout.height <= dueView.height)
                    val buttons = texts(root).filter { it.text.toString() in listOf("+", "−") }
                    assertEquals(2, buttons.size)
                    buttons.forEach { text ->
                        var touch: View = text
                        while (!touch.isClickable && touch.parent is View) touch = touch.parent as View
                        val bounds = android.graphics.Rect(0, 0, touch.width, touch.height)
                        root.offsetDescendantRectToMyCoords(touch, bounds)
                        assertTrue(bounds.bottom <= root.height && bounds.top >= 0)
                        assertTrue(touch.height / density >= 48 && touch.width / density >= 48)
                    }
                    val bitmap = android.graphics.Bitmap.createBitmap(root.width, root.height, android.graphics.Bitmap.Config.ARGB_8888)
                    root.draw(android.graphics.Canvas(bitmap))
                    java.io
                        .File(
                            context.getExternalFilesDir(null),
                            "widget-${theme.name}-${context.resources.configuration.fontScale}.png",
                        ).outputStream()
                        .use {
                            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, it)
                        }
                    bitmap.recycle()
                }
            }
            runBlocking { container.preferencesRepository.setThemeMode(com.finnvek.rowtool.data.preferences.ThemeMode.SYSTEM) }
            host.onActivity { it.host.deleteAppWidgetId(id) }
        }
    }

    @Test fun explicitOpenDefersForNoteDraftAndReminderOpenDoesNotAcknowledge() {
        val repo = container.counterRepository
        val first = runBlocking { repo.createProject("Widget target", CounterUnit.ROWS, 0, null, null) }
        val second = runBlocking { repo.createProject("Other target", CounterUnit.ROUNDS, 0, null, null) }
        val reminder = runBlocking { repo.reminders.save(first.id, null, null, "Read this reminder", 1, null, true)!! }
        runBlocking { container.preferencesRepository.setLastActiveProjectId(second.id) }
        ActivityScenario.launch(WidgetTestHostActivity::class.java).use { host ->
            val id = allocate(host)
            configure(id, first.name)
            assertEquals(
                second.id,
                runBlocking {
                    container.preferencesRepository.preferences
                        .first()
                        .lastActiveProjectId
                },
            )
            attach(host, id)
            waitText(host, id, first.name)
            val firstBinding = runBlocking { container.widgetBindings.read(id)!! }
            click(host, id, first.name)
            compose.captureAssertionFailure(
                label = "widget-target-open",
                activity = { checkNotNull(currentMainActivity()) },
                state = {
                    val requestState = pendingOpenState()
                    var hostState = ""
                    host.onActivity {
                        hostState = "host=${System.identityHashCode(it)} widget=$id ids=${it.views.keys} " +
                            "destroyed=${it.isDestroyed} focus=${it.hasWindowFocus()}"
                    }
                    runBlocking {
                        "$hostState\n$requestState\n" +
                            "boundProject=${container.widgetBindings.read(id)?.projectId}\n" +
                            "first=${repo.getProject(first.id)}\nsecond=${repo.getProject(second.id)}\n" +
                            "firstNote=${repo.notes.load(first.id).note}\nsecondNote=${repo.notes.load(second.id).note}"
                    }
                },
            ) {
                awaitHostedCounter(first)
                compose.onNodeWithText("WIDGET TARGET").assertIsDisplayed()
            }
            assertTrue(firstBinding == runBlocking { container.widgetBindings.read(id) })
            assertEquals(first, runBlocking { repo.getProject(first.id) })
            assertEquals(second, runBlocking { repo.getProject(second.id) })
            compose.onNodeWithContentDescription("More options").performClick()
            compose.onNodeWithText("Note").performClick()
            compose.onNodeWithTag("note-text").performTextReplacement("Keep this draft")
            runBlocking { container.widgetBindings.bind(id, second.id) }
            val secondBinding = runBlocking { container.widgetBindings.read(id)!! }
            requestWidgetUpdate(context)
            waitText(host, id, second.name)
            click(host, id, second.name)
            // Wait for delivery, not navigation: the editor still owns the unsaved draft.
            compose.waitUntil(5_000) {
                var delivered = false
                instrumentation.runOnMainSync {
                    val request = WidgetOpenRequest.parse(currentMainActivity()?.intent?.data)
                    delivered = request?.widgetId == id && request.token == secondBinding.token && container.noteEditorVisible
                }
                delivered
            }
            compose.onNodeWithTag("note-text").assertTextContains("Keep this draft")
            compose.onNode(hasText(first.name) and hasAnyAncestor(isDialog())).assertIsDisplayed()
            assertNull(runBlocking { repo.notes.load(first.id).note })
            assertNull(runBlocking { repo.notes.load(second.id).note })
            compose.onNodeWithTag("note-back").performClick()
            compose.onNode(hasText("Cancel") and hasAnyAncestor(isDialog())).performClick()
            compose.onNodeWithTag("note-text").assertTextContains("Keep this draft")
            assertTrue(container.noteEditorVisible)
            assertNull(runBlocking { repo.notes.load(first.id).note })
            android.util.Log.i("WidgetOpenContract", "Deferred request; draft owner=${first.id}; neither project has a persisted note")
            compose.onNodeWithTag("note-save").performClick()
            awaitHostedCounter(second)
            compose.onNodeWithText("OTHER TARGET").assertIsDisplayed()
            assertEquals(
                "Keep this draft",
                runBlocking {
                    repo.notes
                        .load(first.id)
                        .note!!
                        .text
                },
            )
            assertNull(runBlocking { repo.notes.load(second.id).note })
            assertTrue(secondBinding == runBlocking { container.widgetBindings.read(id) })
            assertEquals(first, runBlocking { repo.getProject(first.id) })
            assertEquals(second, runBlocking { repo.getProject(second.id) })
            var previousActivity = 0
            instrumentation.runOnMainSync {
                val activity = checkNotNull(currentMainActivity())
                assertNull(activity.intent.data)
                previousActivity = System.identityHashCode(activity)
                activity.recreate()
            }
            compose.waitUntil(5_000) {
                var recreated = false
                instrumentation.runOnMainSync {
                    recreated = currentMainActivity()?.let { System.identityHashCode(it) != previousActivity } == true
                }
                recreated
            }
            awaitHostedCounter(second)
            instrumentation.runOnMainSync { assertNull(checkNotNull(currentMainActivity()).intent.data) }
            compose.onNodeWithTag("note-editor").assertDoesNotExist()
            android.util.Log.i(
                "WidgetOpenContract",
                "UI Save persisted only source note; target=${second.id}; consumed request absent after recreation",
            )
            runBlocking { container.widgetBindings.bind(id, first.id) }
            val reminderBinding = runBlocking { container.widgetBindings.read(id)!! }
            requestWidgetUpdate(context)
            waitText(host, id, first.name)
            click(host, id, "+")
            waitCount(first.id, 1)
            val dueText =
                context.getString(
                    com.finnvek.rowtool.R.string.widget_reminders,
                    java.text.NumberFormat
                        .getIntegerInstance()
                        .format(1),
                )
            waitText(host, id, dueText)
            val remindersBefore = runBlocking { container.database.reminderDao().getAll() }
            val projectsBefore = runBlocking { container.database.projectDao().getAll() }
            click(host, id, dueText)
            compose.waitUntil(5_000) {
                compose.onNode(hasText("Read this reminder") and hasAnyAncestor(isDialog())).isDisplayed()
            }
            compose.onNode(hasText("Read this reminder") and hasAnyAncestor(isDialog())).assertIsDisplayed()
            assertNull(
                runBlocking {
                    repo.reminders
                        .observe(first.id)
                        .first()
                        .single()
                        .acknowledgedThrough
                },
            )
            compose.onNodeWithText("WIDGET TARGET").assertExists()
            assertEquals(remindersBefore, runBlocking { container.database.reminderDao().getAll() })
            assertEquals(projectsBefore, runBlocking { container.database.projectDao().getAll() })
            assertTrue(reminderBinding == runBlocking { container.widgetBindings.read(id) })
            instrumentation.runOnMainSync { assertNull(checkNotNull(currentMainActivity()).intent.data) }
            android.util.Log.i(
                "WidgetOpenContract",
                "Reminder view opened for ${first.id}; acknowledgements, counters and binding unchanged",
            )
            runBlocking { repo.reminders.acknowledge(first.id, reminder.id, reminder.revision, 1) }
            compose.waitUntil(15_000) {
                var absent = false
                host.onActivity { absent = texts(it.views.getValue(id)).none { view -> view.text.toString() == dueText } }
                absent
            }
            host.onActivity { it.host.deleteAppWidgetId(id) }
        }
    }

    @Test fun hostedOpenWaitsForBindingValidationBeforeTargetCounterIsReady() {
        val repo = container.counterRepository
        val target = runBlocking { repo.createProject("Widget target", CounterUnit.ROWS, 0, null, null) }
        val fallback = runBlocking { repo.createProject("Other target", CounterUnit.ROUNDS, 0, null, null) }
        runBlocking { container.preferencesRepository.setLastActiveProjectId(fallback.id) }
        ActivityScenario.launch(WidgetTestHostActivity::class.java).use { host ->
            val id = allocate(host)
            configure(id, target.name)
            attach(host, id)
            waitText(host, id, target.name)
            val binding = runBlocking { container.widgetBindings.read(id)!! }
            val entered = kotlinx.coroutines.CompletableDeferred<Unit>()
            val release = kotlinx.coroutines.CompletableDeferred<Unit>()
            val blocker =
                kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                    container.widgetBindings.withBinding(id, binding.token) {
                        entered.complete(Unit)
                        release.await()
                    }
                }
            try {
                runBlocking { kotlinx.coroutines.withTimeout(5_000) { entered.await() } }
                click(host, id, target.name)
                compose.waitUntil(5_000) { compose.onNodeWithText("OTHER TARGET").isDisplayed() }
                val failure =
                    assertThrows(AssertionError::class.java) {
                        compose.captureAssertionFailure(
                            label = "controlled-widget-open",
                            activity = { checkNotNull(currentMainActivity()) },
                            state = {
                                "binding validation gate held; target=${target.id} fallback=${fallback.id} widget=$id\n" +
                                    pendingOpenState()
                            },
                        ) { compose.onNodeWithText("WIDGET TARGET").assertIsDisplayed() }
                    }
                assertTrue(failure.message.orEmpty().contains("WIDGET TARGET"))
                assertThrows(ComposeTimeoutException::class.java) { awaitHostedCounter(target, timeoutMillis = 100) }
                instrumentation.runOnMainSync {
                    val request = checkNotNull(WidgetOpenRequest.parse(checkNotNull(currentMainActivity()).intent.data))
                    assertEquals(id, request.widgetId)
                    assertTrue(request.token == binding.token)
                    assertFalse(request.reminders)
                }
                assertNull(runBlocking { repo.notes.load(target.id).note })
                assertNull(runBlocking { repo.notes.load(fallback.id).note })
                release.complete(Unit)
                awaitHostedCounter(target)
                assertEquals(target, runBlocking { repo.getProject(target.id) })
                assertEquals(fallback, runBlocking { repo.getProject(fallback.id) })
                assertTrue(binding == runBlocking { container.widgetBindings.read(id) })
            } finally {
                release.complete(Unit)
                runBlocking { blocker.join() }
                host.onActivity { it.host.deleteAppWidgetId(id) }
            }
        }
    }

    private fun currentMainActivity(): MainActivity? =
        androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
            .getInstance()
            .getActivitiesInStage(androidx.test.runner.lifecycle.Stage.RESUMED)
            .filterIsInstance<MainActivity>()
            .singleOrNull()

    private fun pendingOpenState(): String {
        var state = ""
        instrumentation.runOnMainSync {
            val activity = currentMainActivity()
            val request = WidgetOpenRequest.parse(activity?.intent?.data)
            state = "activity=${activity?.let(System::identityHashCode)} pendingWidget=${request?.widgetId} " +
                "reminders=${request?.reminders} noteEditor=${container.noteEditorVisible} copyEditor=${container.copyEditorVisible}"
        }
        return state
    }

    private fun awaitHostedCounter(
        project: CounterProject,
        timeoutMillis: Long = 5_000,
    ) {
        val heading =
            compose.onNode(
                hasText(project.name.uppercase(context.resources.configuration.locales[0])) and
                    SemanticsMatcher.keyIsDefined(SemanticsProperties.Heading),
            )
        val count = compose.onNode(hasText(project.count.toString()) and hasClickAction())
        val add = compose.onNodeWithContentDescription(if (project.counterUnit == CounterUnit.ROWS) "Add one row" else "Add one round")
        compose.waitUntil(timeoutMillis) {
            var consumed = false
            instrumentation.runOnMainSync {
                val activity = currentMainActivity()
                consumed = activity != null && activity.intent.data == null && !container.noteEditorVisible
            }
            consumed && heading.isDisplayed() && count.isDisplayed() && add.isDisplayed()
        }
        heading.assertIsDisplayed()
        count.assertIsDisplayed()
        add.assertIsDisplayed()
    }

    @Test fun configurationRequiresChoiceAndHostedRetryNeverRepeatsCount() {
        val first = runBlocking { container.counterRepository.createProject("Only project", CounterUnit.ROWS, 0, null, null) }
        runBlocking {
            assertTrue(container.counterRepository.additionalCounters.save(first.id, null, "Linked", followsMain = true))
            assertTrue(container.counterRepository.additionalCounters.save(first.id, null, "Manual"))
        }
        ActivityScenario.launch(WidgetTestHostActivity::class.java).use { host ->
            val id = allocate(host)
            val intent = Intent(context, WidgetConfigurationActivity::class.java).putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id)
            ActivityScenario.launch<WidgetConfigurationActivity>(intent).use {
                compose.onNodeWithText("Only project").assertIsDisplayed()
                compose.onNodeWithText("Save").assertIsNotEnabled()
                assertNull(runBlocking { container.widgetBindings.read(id) })
                compose.onNodeWithText("Only project").performClick()
                compose.onNodeWithText("Save").performScrollTo().performClick()
            }
            attach(host, id)
            waitText(host, id, first.name)
            observeRetryHost(host, id)
            val binding = runBlocking { container.widgetBindings.read(id)!! }
            assertEquals(first.id, binding.projectId)
            val before = recordRetryState("before-plus", id)
            assertEquals(0L, before.projects.single().count)
            assertTrue(before.counters.all { it.count == 0L })
            assertTrue(before.history.isEmpty())
            assertTrue(before.effects.isEmpty())
            click(host, id, "+")
            waitCount(first.id, 1)
            val committed = recordRetryState("after-plus", id)
            assertEquals(1L, committed.projects.single().count)
            assertEquals(1L, committed.counters.single { it.followsMain }.count)
            assertEquals(0L, committed.counters.single { !it.followsMain }.count)
            val history = committed.history.single()
            assertEquals(first.id, history.projectId)
            assertEquals(0L, history.previousCount)
            assertEquals(1L, history.newCount)
            assertEquals("INCREMENT", history.changeReason)
            assertEquals(
                listOf(CounterHistoryEffectEntity(history.id, committed.counters.single { it.followsMain }.id, 0, 1)),
                committed.effects,
            )
            runBlocking { container.widgetBindings.setFailed(id, true) }
            requestWidgetUpdate(context)
            waitText(host, id, context.getString(com.finnvek.rowtool.R.string.widget_error))
            waitText(host, id, "—")
            assertEquals(committed, recordRetryState("failed-binding", id))
            assertTrue(binding.copy(failed = true) == runBlocking { container.widgetBindings.read(id) })
            // The real Retry control releases the existing failed-binding seam and only refreshes.
            click(host, id, context.getString(com.finnvek.rowtool.R.string.widget_retry))
            waitText(host, id, "1")
            assertEquals(committed, recordRetryState("after-retry", id))
            assertTrue(binding == runBlocking { container.widgetBindings.read(id) })
            assertEquals(1L, runBlocking { container.counterRepository.getProject(first.id)!!.count })
            awaitWidgetSessionFinished(id)
            CounterWidget().onCompositionError(
                context,
                androidx.glance.appwidget
                    .GlanceAppWidgetManager(context)
                    .getGlanceIdBy(id),
                id,
                IllegalStateException("Injected render failure"),
            )
            android.util.Log.i("WidgetRetryProbe", "onCompositionError returned; widget=$id wall=${System.currentTimeMillis()}")
            compose.captureAssertionFailure(
                label = "widget-retry-error",
                activity = {
                    androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
                        .getInstance()
                        .getActivitiesInStage(androidx.test.runner.lifecycle.Stage.RESUMED)
                        .filterIsInstance<WidgetTestHostActivity>()
                        .single()
                },
                state = { retryHostState(host, id) + recordRetryState("timeout", id) },
            ) { awaitCompositionFallback(host, id) }
            assertEquals(1L, runBlocking { container.counterRepository.getProject(first.id)!!.count })
            assertEquals(committed, recordRetryState("composition-fallback", id))
            requestWidgetUpdate(context)
            waitText(host, id, "1")
            assertEquals(committed, recordRetryState("after-fallback-refresh", id))
            assertTrue(binding == runBlocking { container.widgetBindings.read(id) })
            host.onActivity { activity ->
                val scale = activity.resources.displayMetrics.density
                texts(activity.views.getValue(id)).filter { it.text.toString() in listOf("+", "−") }.forEach { text ->
                    var touch: View = text
                    while (!touch.isClickable && touch.parent is View) touch = touch.parent as View
                    assertTrue("Count touch height", touch.height / scale >= 48)
                    assertTrue("Count touch width", touch.width / scale >= 48)
                }
                activity.host.deleteAppWidgetId(id)
            }
        }
    }

    @Test fun compositionFallbackBoundaryRejectsAnActiveWidgetSession() {
        val project = runBlocking { container.counterRepository.createProject("Session boundary", CounterUnit.ROWS, 0, null, null) }
        ActivityScenario.launch(WidgetTestHostActivity::class.java).use { host ->
            val id = allocate(host)
            configure(id, project.name)
            attach(host, id)
            waitText(host, id, project.name)
            val binding = runBlocking { container.widgetBindings.read(id) }
            val before = recordRetryState("active-session-regression", id)
            assertThrows(ComposeTimeoutException::class.java) { awaitWidgetSessionFinished(id, timeoutMillis = 100) }
            assertEquals(before, recordRetryState("active-session-rejected", id))
            assertTrue(binding == runBlocking { container.widgetBindings.read(id) })
            host.onActivity { it.host.deleteAppWidgetId(id) }
        }
    }

    private fun awaitCompositionFallback(
        host: ActivityScenario<WidgetTestHostActivity>,
        id: Int,
    ) {
        compose.waitUntil(15_000) {
            var ready = false
            host.onActivity { activity ->
                val view = activity.views[id]
                val error = view?.findViewById<TextView>(com.finnvek.rowtool.R.id.widget_error_message)
                val choose = view?.findViewById<TextView>(com.finnvek.rowtool.R.id.widget_error_action)
                ready = view != null && view.appWidgetId == id && view.isAttachedToWindow &&
                    view.rootView === activity.window.decorView && !activity.isDestroyed &&
                    androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
                        .getInstance()
                        .getLifecycleStageOf(activity) ==
                    androidx.test.runner.lifecycle.Stage.RESUMED &&
                    error?.isShown == true && error.width > 0 && error.height > 0 &&
                    error.text.toString() == activity.getString(com.finnvek.rowtool.R.string.widget_error) &&
                    choose?.isShown == true && choose.isClickable &&
                    choose.text.toString() == activity.getString(com.finnvek.rowtool.R.string.widget_choose)
            }
            ready
        }
        android.util.Log.i("WidgetRetryProbe", retryHostState(host, id))
    }

    private fun observeRetryHost(
        host: ActivityScenario<WidgetTestHostActivity>,
        id: Int,
    ) {
        host.onActivity { activity ->
            val view = activity.views.getValue(id)
            view.setOnHierarchyChangeListener(
                object : ViewGroup.OnHierarchyChangeListener {
                    override fun onChildViewAdded(
                        parent: View,
                        child: View,
                    ) {
                        android.util.Log.i(
                            "WidgetRetryProbe",
                            "child-added wall=${System.currentTimeMillis()} widget=$id " +
                                "host=${System.identityHashCode(activity)} view=${System.identityHashCode(view)} " +
                                "attached=${view.isAttachedToWindow} texts=${texts(view).map { it.text.toString() }}",
                        )
                    }

                    override fun onChildViewRemoved(
                        parent: View,
                        child: View,
                    ) = Unit
                },
            )
            var previous = ""
            view.viewTreeObserver.addOnGlobalLayoutListener {
                val current = texts(view).joinToString(" | ") { it.text.toString() }
                if (current != previous) {
                    android.util.Log.i(
                        "WidgetRetryProbe",
                        "layout wall=${System.currentTimeMillis()} widget=$id " +
                            "host=${System.identityHashCode(activity)} view=${System.identityHashCode(view)} " +
                            "attached=${view.isAttachedToWindow} texts=$current",
                    )
                    previous = current
                }
            }
        }
    }

    private fun awaitWidgetSessionFinished(
        id: Int,
        timeoutMillis: Long = 60_000,
    ) {
        // Glance 1.2.0 names its existing WorkManager session appWidget-ID and runs it for 45 seconds.
        // A direct fallback callback does not close that live composition as a real session error does.
        // WorkManager is Glance's runtime dependency, absent from this test's compile classpath.
        val managerType = Class.forName("androidx.work.WorkManager")
        val manager = managerType.getMethod("getInstance", android.content.Context::class.java).invoke(null, context)

        fun work(): List<String> {
            val future =
                managerType
                    .getMethod("getWorkInfosForUniqueWork", String::class.java)
                    .invoke(manager, "appWidget-$id") as java.util.concurrent.Future<*>
            return (future.get(5, java.util.concurrent.TimeUnit.SECONDS) as List<*>).map { item ->
                checkNotNull(item)
                    .javaClass
                    .getMethod("getState")
                    .invoke(item)
                    .toString()
            }
        }
        assertTrue("Expected existing Glance work for widget $id", work().isNotEmpty())
        android.util.Log.i("WidgetRetryProbe", "session-before widget=$id states=${work()}")
        compose.waitUntil(timeoutMillis) {
            work().let {
                it.isNotEmpty() &&
                    it.all { item -> item in setOf("SUCCEEDED", "FAILED", "CANCELLED") }
            }
        }
        assertTrue("Glance session must finish successfully", work().all { it == "SUCCEEDED" })
        instrumentation.waitForIdleSync()
        android.util.Log.i("WidgetRetryProbe", "session-finished widget=$id states=${work()}")
    }

    private fun retryHostState(
        host: ActivityScenario<WidgetTestHostActivity>,
        id: Int,
    ): String {
        var result = ""
        host.onActivity { activity ->
            fun tree(
                view: View,
                indent: String = "",
            ): String =
                "$indent${view.javaClass.name} id=${view.id} attached=${view.isAttachedToWindow} " +
                    "shown=${view.isShown} size=${view.width}x${view.height} " +
                    "text=${(view as? TextView)?.text}\n" +
                    if (view is ViewGroup) (0 until view.childCount).joinToString("") { tree(view.getChildAt(it), "$indent  ") } else ""
            result = "wall=${System.currentTimeMillis()} host=${System.identityHashCode(activity)} " +
                "widget=$id locale=${activity.resources.configuration.locales.toLanguageTags()} " +
                "expected=${context.getString(com.finnvek.rowtool.R.string.widget_error)}\n" +
                tree(activity.views.getValue(id))
        }
        return result
    }

    private data class RetrySnapshot(
        val projects: List<ProjectEntity>,
        val counters: List<AdditionalCounterEntity>,
        val history: List<CounterHistoryEntity>,
        val effects: List<CounterHistoryEffectEntity>,
    )

    private fun recordRetryState(
        label: String,
        id: Int,
    ): RetrySnapshot =
        runBlocking {
            val started = System.currentTimeMillis()
            val binding = container.widgetBindings.read(id)
            val state =
                container.database.withTransaction {
                    RetrySnapshot(
                        container.database.projectDao().getAll(),
                        container.database.additionalCounterDao().getAll(),
                        container.database.counterHistoryDao().getAll(),
                        container.database.counterHistoryEffectDao().getAll(),
                    )
                }
            android.util.Log.i(
                "WidgetRetryProbe",
                "checkpoint=$label start=$started end=${System.currentTimeMillis()} " +
                    "widget=$id project=${binding?.projectId} failed=${binding?.failed} $state",
            )
            state
        }

    @Test fun hostedWidgetsConfigureCountRefreshRebindAndReplace() {
        val repo = container.counterRepository
        val first = runBlocking { repo.createProject("Widget Alpha", CounterUnit.ROWS, 0, null, null) }
        val second = runBlocking { repo.createProject("Widget Beta", CounterUnit.ROUNDS, 0, null, null) }
        ActivityScenario.launch(WidgetTestHostActivity::class.java).use { host ->
            val one = allocate(host)
            val two = allocate(host)
            configure(one, first.name)
            configure(two, second.name)
            attach(host, one, two)
            waitText(host, one, "Widget Alpha")
            waitText(host, two, "Widget Beta")
            click(host, one, "+")
            click(host, two, "+")
            waitCount(first.id, 1)
            waitCount(second.id, 1)
            runBlocking { repo.mutate(first.id, CounterMutation.Increment) }
            waitText(host, one, "2")
            val old = runBlocking { container.widgetBindings.read(one)!! }
            configure(one, second.name)
            context.sendBroadcast(widgetActionIntent(context, one, old.token, "plus"))
            waitText(host, one, "Widget Beta")
            assertEquals(1L, runBlocking { repo.getProject(second.id)!!.count })
            click(host, one, "+")
            waitCount(second.id, 2)
            waitText(host, two, "2")
            runBlocking { repo.setArchived(second.id, true) }
            waitText(host, one, "Archived")
            click(host, one, "+")
            assertEquals(2L, runBlocking { repo.getProject(second.id)!!.count })
            runBlocking { repo.setArchived(second.id, false) }
            waitText(host, one, "Rounds")
            val backup = runBlocking { container.backupRepository.exportJson() }
            val validated = (container.backupRepository.prepareImport(backup.toByteArray()) as BackupDecodeResult.Valid).backup
            assertTrue(runBlocking { container.backupRepository.replaceWith(validated) } is BackupImportResult.Success)
            waitText(host, one, "Choose project")
            assertNull(runBlocking { container.widgetBindings.read(one) })
            context.sendBroadcast(widgetActionIntent(context, one, old.token, "plus"))
            assertEquals(2L, runBlocking { repo.getProject(second.id)!!.count })
            host.onActivity {
                it.host.deleteAppWidgetId(one)
                it.host.deleteAppWidgetId(two)
            }
        }
    }

    @Test fun cancellingReconfigurationAndActivityRecreationPreserveSelection() {
        val repo = container.counterRepository
        val first = runBlocking { repo.createProject("Selected widget project", CounterUnit.ROWS, 0, null, null) }
        ActivityScenario.launch(WidgetTestHostActivity::class.java).use { host ->
            val id = allocate(host)
            configure(id, first.name, recreate = true)
            val before = runBlocking { container.widgetBindings.read(id) }
            val intent = Intent(context, WidgetConfigurationActivity::class.java).putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id)
            ActivityScenario.launch<WidgetConfigurationActivity>(intent).use {
                compose.onNodeWithText("Cancel").performClick()
            }
            assertEquals(before, runBlocking { container.widgetBindings.read(id) })
            assertNotEquals(null, before)
            runBlocking { repo.setArchived(first.id, true) }
            ActivityScenario.launch<WidgetConfigurationActivity>(intent).use {
                compose.onNodeWithText(context.getString(com.finnvek.rowtool.R.string.widget_empty)).assertIsDisplayed()
                compose.onNodeWithText("Save").assertIsNotEnabled()
                compose.onNodeWithText("Cancel").performClick()
            }
            assertEquals(before, runBlocking { container.widgetBindings.read(id) })
            host.onActivity { it.host.deleteAppWidgetId(id) }
        }
    }

    private fun allocate(scenario: ActivityScenario<WidgetTestHostActivity>): Int {
        var id = 0
        instrumentation.uiAutomation.adoptShellPermissionIdentity("android.permission.BIND_APPWIDGET")
        try {
            scenario.onActivity {
                id = it.host.allocateAppWidgetId()
                assertTrue(
                    AppWidgetManager.getInstance(it).bindAppWidgetIdIfAllowed(id, ComponentName(it, CounterWidgetReceiver::class.java)),
                )
            }
        } finally {
            instrumentation.uiAutomation.dropShellPermissionIdentity()
        }
        return id
    }

    private fun configure(
        id: Int,
        name: String,
        recreate: Boolean = false,
    ) {
        val intent = Intent(context, WidgetConfigurationActivity::class.java).putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id)
        ActivityScenario.launch<WidgetConfigurationActivity>(intent).use {
            compose.onNodeWithText(name).performScrollTo().performClick()
            if (recreate) it.recreate()
            compose.onNodeWithText(context.getString(com.finnvek.rowtool.R.string.action_save)).performScrollTo().performClick()
        }
        assertEquals(name, runBlocking { container.counterRepository.getProject(container.widgetBindings.read(id)!!.projectId)!!.name })
    }

    private fun attach(
        scenario: ActivityScenario<WidgetTestHostActivity>,
        vararg ids: Int,
    ) {
        scenario.onActivity { activity -> ids.forEach { activity.attach(it) } }
    }

    private fun waitText(
        scenario: ActivityScenario<WidgetTestHostActivity>,
        id: Int,
        text: String,
    ) {
        compose.waitUntil(15_000) {
            var found = false
            scenario.onActivity { found = texts(it.views.getValue(id)).any { view -> view.text.toString() == text } }
            found
        }
    }

    private fun click(
        scenario: ActivityScenario<WidgetTestHostActivity>,
        id: Int,
        text: String,
    ) {
        scenario.onActivity {
            var view: View? = texts(it.views.getValue(id)).first { item -> item.text.toString() == text }
            while (view != null && !view.isClickable) view = view.parent as? View
            view?.performClick()
        }
    }

    private fun waitCount(
        id: String,
        count: Long,
    ) = compose.waitUntil(15_000) {
        runBlocking { container.counterRepository.getProject(id)?.count == count }
    }

    private fun texts(view: View): List<TextView> =
        when (view) {
            is TextView -> listOf(view)
            is ViewGroup -> (0 until view.childCount).flatMap { texts(view.getChildAt(it)) }
            else -> emptyList()
        }
}
