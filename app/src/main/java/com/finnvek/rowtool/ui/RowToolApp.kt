package com.finnvek.rowtool.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.unit.dp
import com.finnvek.rowtool.AppContainer
import com.finnvek.rowtool.ui.navigation.RowToolNavHost
import kotlinx.coroutines.launch

@Composable
fun RowToolApp(
    container: AppContainer,
    startProjectId: String?,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val resources = LocalResources.current
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        RowToolNavHost(
            container = container,
            startProjectId = startProjectId,
            onMessage = { message ->
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(message = resources.getString(message))
                }
            },
            modifier = Modifier.fillMaxSize(),
        )
        SnackbarHost(
            hostState = snackbarHostState,
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom,
                        ),
                    ).padding(bottom = 72.dp),
        )
    }
}
