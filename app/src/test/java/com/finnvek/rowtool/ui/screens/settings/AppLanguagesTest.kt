package com.finnvek.rowtool.ui.screens.settings

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class AppLanguagesTest {
    @Test fun pickerMatchesDeclaredLocalesAndExistingCatalogs() {
        val resources = File("src/main/res")
        val declaration =
            DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(File(resources, "xml/locales_config.xml"))
                .getElementsByTagName("locale")
        val tags =
            (0 until declaration.length).map {
                declaration
                    .item(it)
                    .attributes
                    .getNamedItem("android:name")
                    .nodeValue
            }
        assertEquals(tags, AppLanguages.names.keys.toList())
        tags.forEach { tag ->
            assertTrue(File(resources, if (tag == "en") "values/strings.xml" else "values-$tag/strings.xml").exists())
        }
    }

    @Test fun explicitEnglishIsDifferentFromSystemAndIdenticalSelectionDoesNothing() {
        var actual = ""
        var writes = 0
        val write: (String) -> Unit = {
            actual = it
            writes++
        }
        assertTrue(applyLanguageSelection("en", { actual }, write))
        assertEquals("en", actual)
        assertFalse(applyLanguageSelection("en", { actual }, write))
        assertEquals(1, writes)
        assertTrue(applyLanguageSelection("", { actual }, write))
        assertEquals("", actual)
    }

    @Test fun currentExternalRegionalChoiceIsPreservedWithoutCanonicalizingIt() {
        var actual = "pt-BR"
        assertFalse(applyLanguageSelection("pt-BR", { actual }) { error("Unexpected write") })
        actual = ""
        assertFalse(applyLanguageSelection("", { actual }) { error("Stale choice replayed") })
        assertTrue(AppLanguages.label("pt-BR").contains("Brasil"))
    }

    @Test(expected = IllegalStateException::class)
    fun rejectedRequestIsNotReportedAsApplied() {
        applyLanguageSelection("fi", { "en" }) { }
    }
}
