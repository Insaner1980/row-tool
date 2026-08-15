package com.finnvek.rowtool.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import com.finnvek.rowtool.AppContainer
import com.finnvek.rowtool.ui.navigation.RowToolNavHost

@Composable
fun RowToolApp(
    container: AppContainer,
    startProjectId: String?,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val resources = LocalResources.current

    Box(modifier = modifier.fillMaxSize()) {
        RowToolNavHost(
            container = container,
            startProjectId = startProjectId,
            onMessage = { message ->
                snackbarHostState.showSnackbar(message = resources.getString(message))
            },
            modifier = Modifier.fillMaxSize(),
        )
        SnackbarHost(
            hostState = snackbarHostState,
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding(),
        )
    }
}
