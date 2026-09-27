package com.finnvek.rowtool.ui.screens.counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.model.AdditionalCounter
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.ui.RowToolDropdownMenuItem
import com.finnvek.rowtool.ui.ordinaryDialogActionColors
import com.finnvek.rowtool.ui.theme.RowToolDimens

data class AdditionalCounterActions(
    val onAdd: () -> Unit = {},
    val onEdit: (String) -> Unit = {},
    val onSetCount: (String) -> Unit = {},
    val onReset: (String) -> Unit = {},
    val onDelete: (String) -> Unit = {},
    val onIncrement: (String) -> Unit = {},
    val onDecrement: (String) -> Unit = {},
)

data class AdditionalCounterEditorActions(
    val onSave: suspend (String?, String, Boolean) -> Boolean,
    val onDelete: suspend (String) -> Boolean,
    val onSetCount: suspend (String, CounterMutation) -> Boolean,
)

@Composable
internal fun AdditionalCountersSection(
    counters: List<AdditionalCounter>,
    actions: AdditionalCounterActions,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space8)) {
        if (counters.isNotEmpty()) {
            Text(
                stringResource(R.string.additional_counters),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.semantics { heading() },
            )
        }
        counters.forEach { counter ->
            key(counter.id) {
                AdditionalCounterRow(counter, actions, enabled)
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            }
        }
        TextButton(onClick = actions.onAdd, enabled = enabled, colors = ordinaryDialogActionColors()) {
            Text(stringResource(R.string.additional_add))
        }
    }
}

@Composable
private fun AdditionalCounterRow(
    counter: AdditionalCounter,
    actions: AdditionalCounterActions,
    enabled: Boolean,
) {
    Column(Modifier.fillMaxWidth().padding(vertical = RowToolDimens.Space4)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(counter.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    stringResource(if (counter.followsMain) R.string.additional_following else R.string.additional_manual),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            AdditionalCounterMenu(counter, actions, enabled)
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(RowToolDimens.Space8),
            verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space4),
        ) {
            val editDescription = stringResource(R.string.additional_edit_count, counter.name, counter.count)
            TextButton(
                onClick = { actions.onSetCount(counter.id) },
                enabled = enabled,
                colors = ordinaryDialogActionColors(),
                modifier = Modifier.heightIn(min = RowToolDimens.MinimumTouchSize).semantics { contentDescription = editDescription },
            ) {
                Text(
                    java.text.NumberFormat
                        .getIntegerInstance(androidx.compose.ui.platform.LocalConfiguration.current.locales[0])
                        .format(counter.count),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            if (!counter.followsMain) {
                CounterImageButton(
                    imageRes = R.drawable.counter_minus_button,
                    contentDescription = stringResource(R.string.additional_decrement, counter.name),
                    layout = CounterButtonLayout(visualSize = 48.dp, touchSize = 56.dp),
                    enabled = enabled && counter.count > CounterConstants.MIN_COUNT,
                    onClick = { actions.onDecrement(counter.id) },
                )
                CounterImageButton(
                    imageRes = R.drawable.counter_plus_button,
                    contentDescription = stringResource(R.string.additional_increment, counter.name),
                    layout = CounterButtonLayout(visualSize = 48.dp, touchSize = 56.dp),
                    enabled = enabled && counter.count < CounterConstants.MAX_COUNT,
                    onClick = { actions.onIncrement(counter.id) },
                )
            }
        }
    }
}

@Composable
private fun AdditionalCounterMenu(
    counter: AdditionalCounter,
    actions: AdditionalCounterActions,
    enabled: Boolean,
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = true }, enabled = enabled) {
            Icon(painterResource(R.drawable.ic_more), stringResource(R.string.additional_options, counter.name))
        }
        DropdownMenu(expanded = expanded && enabled, onDismissRequest = { expanded = false }) {
            RowToolDropdownMenuItem(stringResource(R.string.action_edit), R.drawable.ic_edit) {
                expanded = false
                actions.onEdit(counter.id)
            }
            RowToolDropdownMenuItem(stringResource(R.string.action_reset), R.drawable.ic_restore) {
                expanded = false
                actions.onReset(counter.id)
            }
            RowToolDropdownMenuItem(stringResource(R.string.action_delete), R.drawable.ic_delete) {
                expanded = false
                actions.onDelete(counter.id)
            }
        }
    }
}
