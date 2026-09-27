package com.finnvek.rowtool.ui

import android.app.LocaleManager
import android.os.LocaleList
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextContains
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
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import com.finnvek.rowtool.MainActivity
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.domain.model.CounterUnit
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

@SdkSuppress(minSdkVersion = 33)
class LanguageSelectionTest {
    private val compose = createAndroidComposeRule<MainActivity>()
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private val container get() = (context.applicationContext as RowToolApplication).container
    private lateinit var id: String
    private val locales get() = context.getSystemService(LocaleManager::class.java)

    @get:Rule val rules: TestRule =
        RuleChain
            .outerRule(
                PrepareApplicationStateRule {
                    locales.applicationLocales = LocaleList.forLanguageTags("en")
                    id = container.counterRepository.createProject("Language project", CounterUnit.ROWS, 0, null, null).id
                    container.preferencesRepository.setLastActiveProjectId(id)
                },
            ).around(compose)

    @Test fun pendingCancelApplyAndExternalResetPreserveSettingsAndProject() {
        val before = runBlocking { container.counterRepository.getProject(id) }
        compose.waitUntil(5000) { compose.onAllNodesWithText("LANGUAGE PROJECT").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithContentDescription("Settings").performClick()
        compose.onNodeWithText("Language").performClick()
        compose.onNode(hasText("English") and isSelectable() and hasAnyAncestor(isDialog())).assertIsSelected()
        compose.onNodeWithText("Suomi").performClick()
        assertEquals("en", locales.applicationLocales.toLanguageTags())
        compose.onNodeWithText("Cancel").performClick()
        compose.onNodeWithText("Language").performClick()
        compose.onNode(hasText("English") and isSelectable() and hasAnyAncestor(isDialog())).assertIsSelected()
        compose.onNodeWithText("Suomi").performClick()
        compose.onNodeWithTag("language-apply").performClick()
        awaitLanguage("fi")
        compose.onNodeWithText("Kieli").assertIsDisplayed()
        compose.onNodeWithTag("language-dialog").assertDoesNotExist()
        assertEquals("fi", locales.applicationLocales.toLanguageTags())
        locales.applicationLocales = LocaleList.getEmptyLocaleList()
        awaitLanguage("en")
        compose.onNodeWithText("Language").assertIsDisplayed()
        compose.onNodeWithText("Language").performClick()
        compose.onNode(hasText("System default") and isSelectable() and hasAnyAncestor(isDialog())).assertIsSelected()
        compose.onNodeWithText("Cancel").performClick()
        compose.onNodeWithContentDescription("Back").performClick()
        compose.onNodeWithText("LANGUAGE PROJECT").assertIsDisplayed()
        assertEquals(before, runBlocking { container.counterRepository.getProject(id) })
    }

    @Test fun externalChangeKeepsRawUnsavedNoteAndOptionalSelection() {
        compose.waitUntil(5000) { compose.onAllNodesWithText("LANGUAGE PROJECT").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithContentDescription("More options").performClick()
        compose.onNodeWithText("Note").performClick()
        val raw = "  Unsaved\n\n    🧶"
        compose.onNodeWithTag("note-text").performTextReplacement(raw)
        androidx.test.espresso.Espresso
            .closeSoftKeyboard()
        compose
            .onNodeWithTag("note-attach")
            .performScrollTo()
            .performClick()
            .assertIsOff()
        locales.applicationLocales = LocaleList.forLanguageTags("fi")
        compose.waitUntil(5000) {
            var finnish = false
            compose.activityRule.scenario.onActivity {
                finnish = it.resources.configuration.locales[0]
                    .language == "fi"
            }
            finnish
        }
        compose.onNodeWithTag("note-text").performScrollTo().assertTextContains(raw)
        compose.onNodeWithTag("note-attach").performScrollTo().assertIsOff()
        assertNull(
            runBlocking {
                container.counterRepository.notes
                    .load(id)
                    .note
            },
        )
        assertEquals("Language project", runBlocking { container.counterRepository.getProject(id)!!.name })
    }

    @Test fun allPackagedLanguagesResolveAndBackupImportKeepsDestinationLanguage() {
        val before = runBlocking { container.counterRepository.getProject(id) }
        val expected =
            mapOf(
                "en" to "Language",
                "fi" to "Kieli",
                "sv" to "Språk",
                "de" to "Sprache",
                "fr" to "Langue",
                "es" to "Idioma",
                "pt" to "Idioma",
                "it" to "Lingua",
                "nb" to "Språk",
                "da" to "Sprog",
                "nl" to "Taal",
            )
        for ((tag, label) in expected) {
            locales.applicationLocales = LocaleList.forLanguageTags(tag)
            compose.waitUntil(5000) {
                var matches = false
                compose.activityRule.scenario.onActivity {
                    matches = it.getString(com.finnvek.rowtool.R.string.settings_language) == label &&
                        it.resources.configuration.locales[0]
                            .language == tag
                }
                matches
            }
            assertEquals(before, runBlocking { container.counterRepository.getProject(id) })
        }
        val backup = runBlocking { container.backupRepository.exportJson() }
        val validated =
            (
                container.backupRepository.prepareImport(backup.toByteArray()) as
                    com.finnvek.rowtool.data.repository.BackupDecodeResult.Valid
            ).backup
        org.junit.Assert.assertTrue(
            runBlocking {
                container.backupRepository.replaceWith(validated) is com.finnvek.rowtool.data.repository.BackupImportResult.Success
            },
        )
        assertEquals("nl", locales.applicationLocales.toLanguageTags())
    }

    private fun awaitLanguage(tag: String) {
        compose.waitUntil(5000) {
            var matches = false
            compose.activityRule.scenario.onActivity {
                matches = it.resources.configuration.locales[0]
                    .language == tag
            }
            matches
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
    }
}
