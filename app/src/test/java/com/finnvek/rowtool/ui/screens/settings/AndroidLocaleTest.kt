package com.finnvek.rowtool.ui.screens.settings

import android.app.Application
import android.app.LocaleManager
import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.LocaleList
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.LocaleManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.R
import com.finnvek.rowtool.preserveFrameworkLanguage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], application = Application::class)
class AndroidLocaleTest {
    private val context get() = ApplicationProvider.getApplicationContext<Application>()

    @Test fun widgetRefreshBeforeContainerAssignmentDoesNotReadContainer() {
        val application = com.finnvek.rowtool.RowToolApplication()
        val base =
            object : android.content.ContextWrapper(context) {
                override fun getApplicationContext(): android.content.Context = application
            }
        android.content.ContextWrapper::class.java
            .getDeclaredMethod("attachBaseContext", android.content.Context::class.java)
            .apply {
                isAccessible = true
            }.invoke(application, base)
        com.finnvek.rowtool.widget
            .requestWidgetUpdate(application)
    }

    @Test fun frameworkChoiceBeforeFirstActivitySurvivesLegacyHandoffAndExternalReset() {
        context.openFileOutput("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file", 0).use {
            it.write("<?xml version=\"1.0\"?><locales application_locales=\"de\"/>".toByteArray())
        }
        val manager = context.getSystemService(LocaleManager::class.java)
        manager.applicationLocales = LocaleList.forLanguageTags("pt-BR")
        preserveFrameworkLanguage(context)
        val activity = Robolectric.buildActivity(LocaleTestActivity::class.java).setup()
        val component = ComponentName(context, "androidx.appcompat.app.AppLocalesMetadataHolderService")
        await { context.packageManager.getComponentEnabledSetting(component) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED }
        assertEquals("pt-BR", AppCompatDelegate.getApplicationLocales().toLanguageTags())
        manager.applicationLocales = LocaleList.getEmptyLocaleList()
        activity.pause().stop().destroy()
        Robolectric
            .buildActivity(LocaleTestActivity::class.java)
            .setup()
            .pause()
            .stop()
            .destroy()
        assertTrue(manager.applicationLocales.isEmpty)
    }

    @Test
    @Config(sdk = [32])
    fun androidXStorageLocalizesBackgroundContextAndClearsOverride() {
        val activity = Robolectric.buildActivity(LocaleTestActivity::class.java).setup()
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("fi"))
        await { LocaleManagerCompat.getApplicationLocales(context).toLanguageTags() == "fi" }
        assertEquals("Kieli", ContextCompat.getContextForLanguage(context).getString(R.string.settings_language))
        assertEquals("fi", AppCompatDelegate.getApplicationLocales().toLanguageTags())
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
        await { LocaleManagerCompat.getApplicationLocales(context).isEmpty }
        activity.pause().stop().destroy()
    }

    @Test fun legacyChoiceMigratesOnceWhenFrameworkHasNoSelection() {
        context.openFileOutput("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file", 0).use {
            it.write("<?xml version=\"1.0\"?><locales application_locales=\"fi\"/>".toByteArray())
        }
        preserveFrameworkLanguage(context)
        val activity = Robolectric.buildActivity(LocaleTestActivity::class.java).setup()
        val manager = context.getSystemService(LocaleManager::class.java)
        await { manager.applicationLocales.toLanguageTags() == "fi" }
        val component = ComponentName(context, "androidx.appcompat.app.AppLocalesMetadataHolderService")
        await { context.packageManager.getComponentEnabledSetting(component) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED }
        manager.applicationLocales = LocaleList.getEmptyLocaleList()
        preserveFrameworkLanguage(context)
        activity.pause().stop().destroy()
        Robolectric
            .buildActivity(LocaleTestActivity::class.java)
            .setup()
            .pause()
            .stop()
            .destroy()
        assertTrue(manager.applicationLocales.isEmpty)
    }

    private fun await(condition: () -> Boolean) {
        val deadline = System.nanoTime() + 5_000_000_000
        while (!condition() && System.nanoTime() < deadline) Thread.sleep(10)
        assertTrue("AndroidX locale persistence completed", condition())
    }
}

class LocaleTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RowTool)
        super.onCreate(savedInstanceState)
    }
}
