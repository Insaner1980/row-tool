package com.finnvek.rowtool.ui

import android.content.Context
import android.content.res.Configuration
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.R
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.text.NumberFormat
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class LimitMessageResourcesTest {
    @Test
    fun everyLocaleUsesSuppliedBoundsAndGroupsLargeCounts() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val languages = listOf("en", "fi", "sv", "de", "fr", "es", "pt", "it", "nb", "da", "nl")
        languages.forEach { language ->
            val locale = Locale.forLanguageTag(language)
            val configuration = Configuration(context.resources.configuration).apply { setLocale(locale) }
            val resources = context.createConfigurationContext(configuration).resources
            val groupedCount = NumberFormat.getIntegerInstance(locale).format(888_888L)
            // Different bounds prove the translations use arguments rather than today's limits.
            val messages =
                listOf(
                    resources.getString(R.string.backup_import_too_large, 7) to listOf("7 MiB"),
                    resources.getString(R.string.project_name_too_long, 73) to listOf("73"),
                    resources.getString(R.string.project_target_error, 3L, 888_888L) to listOf("3", groupedCount),
                    resources.getString(R.string.project_repeat_error, 4, 876) to listOf("4", "876"),
                    resources.getString(R.string.counter_set_error, 5, 888_888L) to listOf("5", groupedCount),
                    resources.getString(R.string.counter_max_reached, 888_888L) to listOf(groupedCount),
                )
            messages.forEach { (message, bounds) ->
                bounds.forEach { bound -> assertTrue("$language: $message must contain $bound", bound in message) }
                assertFalse("$language: unresolved placeholder in $message", "%" in message)
                assertFalse("$language: old name limit in $message", "60" in message)
                assertFalse("$language: old count or repeat limit in $message", "999" in message)
            }
        }
    }
}
