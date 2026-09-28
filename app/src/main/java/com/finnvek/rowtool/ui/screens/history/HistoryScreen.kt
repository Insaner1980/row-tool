package com.finnvek.rowtool.ui.screens.history

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.HistoryCountChange
import com.finnvek.rowtool.domain.model.RecentHistoryEntry
import com.finnvek.rowtool.ui.theme.RowToolDimens
import java.text.NumberFormat
import java.util.TimeZone

@Composable
internal fun HistoryRoute(
    projectId: String,
    repository: CounterRepository,
    onBack: () -> Unit,
    onMissing: () -> Unit,
    model: HistoryViewModel =
        viewModel(
            key = "history:$projectId",
            factory = viewModelFactory { initializer { HistoryViewModel { repository.history.observe(projectId) } } },
        ),
) {
    val state by model.state.collectAsStateWithLifecycle()
    val missing by rememberUpdatedState(onMissing)
    LaunchedEffect(state) { if (state == HistoryUiState.Missing) missing() }
    HistoryScreen(state, onBack, model::retry)
}

@Composable
internal fun HistoryScreen(
    state: HistoryUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
) {
    val locale = LocalConfiguration.current.locales[0]
    val historyLimit = NumberFormat.getIntegerInstance(locale).format(CounterConstants.MAX_HISTORY_ENTRIES)
    Surface(Modifier.fillMaxSize().testTag("history-screen"), color = MaterialTheme.colorScheme.background) {
        Column(Modifier.safeDrawingPadding().padding(horizontal = RowToolDimens.Space16)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack, modifier = Modifier.testTag("history-back")) {
                    Icon(painterResource(R.drawable.ic_back), stringResource(R.string.action_back))
                }
                Text(stringResource(R.string.history_title), style = MaterialTheme.typography.headlineSmall)
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f).testTag("history-list"),
                verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space16),
            ) {
                if (state is HistoryUiState.Content) {
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space8)) {
                            Text(state.history.projectName, style = MaterialTheme.typography.titleMedium)
                            Text(stringResource(R.string.history_explanation, historyLimit))
                            Text(stringResource(R.string.history_names))
                        }
                    }
                    if (state.history.entries.isEmpty()) {
                        item { Text(stringResource(R.string.history_empty), Modifier.testTag("history-empty")) }
                    }
                    items(state.history.entries, key = { it.id }) { entry -> HistoryEntry(entry) }
                } else {
                    item {
                        HistoryStatus(state, onRetry)
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryEntry(entry: RecentHistoryEntry) {
    val locale = LocalConfiguration.current.locales[0]
    val timestamp = historyTime(entry.createdAt, locale, DateFormat.is24HourFormat(LocalContext.current), TimeZone.getDefault())
    Column(
        modifier = Modifier.fillMaxWidth().testTag("history-entry-${entry.id}"),
        verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space8),
    ) {
        Text(timestamp ?: stringResource(R.string.history_unknown_time), style = MaterialTheme.typography.bodyMedium)
        Text(stringResource(historyReason(entry.reason)), style = MaterialTheme.typography.titleMedium)
        entry.changes.forEach { change -> HistoryChange(change) }
        HorizontalDivider()
    }
}

@Composable
private fun HistoryChange(change: HistoryCountChange) {
    val locale = LocalConfiguration.current.locales[0]
    val numbers = NumberFormat.getIntegerInstance(locale)
    Column(Modifier.fillMaxWidth()) {
        val name =
            if (change.counterId == null) {
                stringResource(R.string.history_main)
            } else {
                change.name ?: stringResource(R.string.history_counter)
            }
        Text(name, style = MaterialTheme.typography.titleSmall)
        if (change.deleted) Text(stringResource(R.string.history_deleted))
        Text(stringResource(R.string.history_before, numbers.format(change.before)))
        Text(stringResource(R.string.history_after, numbers.format(change.after)))
    }
}

@Composable
private fun LazyItemScope.HistoryStatus(
    state: HistoryUiState,
    onRetry: () -> Unit,
) {
    when (state) {
        HistoryUiState.Loading -> {
            CircularProgressIndicator(Modifier.testTag("history-loading"))
        }

        HistoryUiState.Error -> {
            Text(stringResource(R.string.history_error))
            TextButton(onClick = onRetry) { Text(stringResource(R.string.history_retry)) }
        }

        else -> {}
    }
}
