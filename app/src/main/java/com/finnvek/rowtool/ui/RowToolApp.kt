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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.finnvek.rowtool.AppContainer
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.repository.BackupCodec
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterConstants.MIN_COUNT
import com.finnvek.rowtool.ui.navigation.RowToolNavHost
import kotlinx.coroutines.flow.first

@Composable
fun RowToolApp(
    container: AppContainer,
    startProjectId: String?,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val resources = LocalResources.current
    val enqueueMessage = rememberSnackbarPresenter(snackbarHostState)

    Box(modifier = modifier.fillMaxSize()) {
        RowToolNavHost(
            container = container,
            startProjectId = startProjectId,
            onMessage = { message ->
                val text =
                    when (message) {
                        R.string.counter_max_reached -> resources.getString(message, CounterConstants.MAX_COUNT)
                        R.string.counter_set_error -> resources.getString(message, MIN_COUNT, CounterConstants.MAX_COUNT)
                        R.string.backup_import_too_large -> resources.getString(message, BackupCodec.MAX_BACKUP_MIB)
                        else -> resources.getString(message)
                    }
                enqueueMessage(text)
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

@Composable
internal fun rememberSnackbarPresenter(snackbarHostState: SnackbarHostState): (String) -> Unit {
    val pendingMessages = remember { mutableStateListOf<String>() }
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(lifecycle, snackbarHostState) {
        // A paused Activity may be covered; restart the default visible duration on resume.
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            while (true) {
                snapshotFlow { pendingMessages.isNotEmpty() }.first { it }
                // Keep the head queued when lifecycle cancellation removes the active snackbar.
                snackbarHostState.showSnackbar(message = pendingMessages.first())
                // No suspension between successful presentation and consuming this queue entry.
                pendingMessages.removeAt(0)
            }
        }
    }

    return remember {
        { message ->
            pendingMessages.add(message)
        }
    }
}
