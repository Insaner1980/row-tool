package com.finnvek.rowtool.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.core.os.LocaleListCompat
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.MainActivity
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.ReminderValues
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

@SdkSuppress(minSdkVersion = 29, maxSdkVersion = 32)
class LegacyLanguageSelectionTest {
    private val compose = createAndroidComposeRule<MainActivity>()
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private val container get() = (context.applicationContext as RowToolApplication).container
    private lateinit var projectId: String

    @get:Rule val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
                    projectId = container.counterRepository.createProject("Legacy language project", CounterUnit.ROWS, 0, null, null).id
                    container.preferencesRepository.setLastActiveProjectId(projectId)
                },
            ).around(compose)

    @Test fun systemCancelFinnishSameChoiceAndSystemAgain() {
        val before = runBlocking { container.counterRepository.getProject(projectId) }
        compose.waitUntil(5000) { compose.onAllNodesWithText("LEGACY LANGUAGE PROJECT").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithContentDescription("Settings").performClick()
        compose.onNodeWithText("Language").performClick()
        compose.onNode(hasText("System default") and isSelectable() and hasAnyAncestor(isDialog())).assertIsSelected()
        compose.onNodeWithText("Suomi").performClick()
        compose.onNodeWithText("Cancel").performClick()
        assertEquals("", AppCompatDelegate.getApplicationLocales().toLanguageTags())

        compose.onNodeWithText("Language").performClick()
        compose.onNodeWithText("Suomi").performClick()
        compose.onNodeWithTag("language-apply").performClick()
        awaitLocale("fi")
        compose.onNodeWithText("Kieli").assertIsDisplayed()
        assertEquals("fi", AppCompatDelegate.getApplicationLocales().toLanguageTags())

        var activity: MainActivity? = null
        compose.activityRule.scenario.onActivity { activity = it }
        compose.onNodeWithText("Kieli").performClick()
        compose.onNodeWithTag("language-apply").performClick()
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        compose.activityRule.scenario.onActivity { assertSame(activity, it) }

        compose.onNodeWithText("Kieli").performClick()
        compose.onNode(hasText("Järjestelmän oletus") and isSelectable() and hasAnyAncestor(isDialog())).performClick()
        compose.onNodeWithTag("language-apply").performClick()
        awaitLocale("en")
        assertEquals("", AppCompatDelegate.getApplicationLocales().toLanguageTags())
        compose.onNodeWithContentDescription("Back").performClick()
        compose.onNodeWithText("LEGACY LANGUAGE PROJECT").assertIsDisplayed()
        assertEquals(before, runBlocking { container.counterRepository.getProject(projectId) })
    }

    @Test fun allPackagedLanguagesResolveOnLegacyDevice() {
        val repository = container.counterRepository
        runBlocking {
            repository.mutate(projectId, CounterMutation.Increment)
            val reminder = requireNotNull(repository.reminders.save(projectId, null, null, ReminderValues("Keep reminder", 1, null, true)))
            check(repository.reminders.acknowledge(projectId, reminder.id, reminder.revision, 1))
            repository.notes.save(projectId, null, "Saved note", true)
        }
        val beforeHistory = runBlocking { container.database.counterHistoryDao().getAll() }
        val beforeReminders = runBlocking { container.database.reminderDao().getForProject(projectId) }
        val beforeNote = runBlocking { repository.notes.load(projectId).note }
        val before = runBlocking { repository.getProject(projectId) }
        for ((tag, label) in packagedLanguageLabels) {
            compose.activityRule.scenario.onActivity {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
            }
            compose.waitUntil(5000) {
                var matches = false
                compose.activityRule.scenario.onActivity {
                    val locale = it.resources.configuration.locales[0]
                    matches =
                        locale.language == tag && it.getString(com.finnvek.rowtool.R.string.settings_language) == label
                }
                matches
            }
            assertEquals(before, runBlocking { repository.getProject(projectId) })
        }
        assertEquals(beforeHistory, runBlocking { container.database.counterHistoryDao().getAll() })
        assertEquals(beforeReminders, runBlocking { container.database.reminderDao().getForProject(projectId) })
        assertEquals(beforeNote, runBlocking { repository.notes.load(projectId).note })
    }

    private fun awaitLocale(tag: String) {
        compose.waitUntil(5000) {
            var matches = false
            compose.activityRule.scenario.onActivity {
                val locale = it.resources.configuration.locales[0]
                matches = locale.language == tag
            }
            matches
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
    }
}
