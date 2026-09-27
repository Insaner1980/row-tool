package com.finnvek.rowtool.ui.screens.settings

import java.util.Locale

internal object AppLanguages {
    // Deliberate autonyms, in the same stable order as locales_config.xml.
    val names =
        linkedMapOf(
            "en" to "English",
            "fi" to "Suomi",
            "sv" to "Svenska",
            "de" to "Deutsch",
            "fr" to "Français",
            "es" to "Español",
            "pt" to "Português",
            "it" to "Italiano",
            "nb" to "Norsk bokmål",
            "da" to "Dansk",
            "nl" to "Nederlands",
        )

    fun label(tags: String): String =
        names[tags] ?: tags.split(',').joinToString(", ") {
            val locale = Locale.forLanguageTag(it)
            locale.getDisplayName(locale)
        }
}

internal fun applyLanguageSelection(
    requested: String,
    current: () -> String,
    set: (String) -> Unit,
): Boolean {
    if (requested == current()) return false
    set(requested)
    check(current() == requested) { "Application locale request was not accepted" }
    return true
}
