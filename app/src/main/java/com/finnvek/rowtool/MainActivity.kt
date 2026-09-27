package com.finnvek.rowtool

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finnvek.rowtool.data.preferences.ThemeMode
import com.finnvek.rowtool.ui.RowToolApp
import com.finnvek.rowtool.ui.RowToolAppViewModel
import com.finnvek.rowtool.ui.theme.RowToolTheme

class MainActivity : AppCompatActivity() {
    private var widgetUri by mutableStateOf<Uri?>(null)

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        widgetUri = intent.data
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("pending-widget", widgetUri?.toString())
        super.onSaveInstanceState(outState)
    }

    private val appContainer: AppContainer
        get() = (application as RowToolApplication).container

    private val appViewModel: RowToolAppViewModel by viewModels {
        RowToolAppViewModel.factory(appContainer.preferencesRepository)
    }

    override fun onResume() {
        super.onResume()
        com.finnvek.rowtool.widget
            .refreshWidgetLanguage(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        widgetUri = if (savedInstanceState == null) intent.data else savedInstanceState.getString("pending-widget")?.let(Uri::parse)
        window.decorView.setFilterTouchesWhenObscured(true)
        enableEdgeToEdge()
        splashScreen.setKeepOnScreenCondition {
            !appViewModel.startupState.value.isResolved
        }

        setContent {
            val startupState by appViewModel.startupState.collectAsStateWithLifecycle()
            val preferences by appViewModel.preferences.collectAsStateWithLifecycle()
            val useDarkTheme =
                when (preferences.themeMode) {
                    ThemeMode.SYSTEM -> isSystemInDarkTheme()
                    ThemeMode.LIGHT -> false
                    ThemeMode.DARK -> true
                }

            SideEffect {
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = !useDarkTheme
                    isAppearanceLightNavigationBars = !useDarkTheme
                }
            }

            RowToolTheme(darkTheme = useDarkTheme) {
                if (startupState.isResolved) {
                    RowToolApp(
                        container = appContainer,
                        widgetRequest =
                            com.finnvek.rowtool.widget.WidgetOpenRequest
                                .parse(widgetUri),
                        onWidgetConsume = {
                            widgetUri = null
                            intent.data = null
                        },
                        startProjectId = startupState.projectId,
                    )
                }
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.flags and MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED != 0) {
            return false
        }
        return super.dispatchTouchEvent(event)
    }
}
