package com.finnvek.rowtool.ui.screens.counter

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.counter.RepeatProgressCalculator
import com.finnvek.rowtool.domain.counter.TargetProgressCalculator
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.ui.RowToolDropdownMenuItem
import com.finnvek.rowtool.ui.theme.RowToolDimens
import java.text.NumberFormat

data class CounterNavigationActions(
    val onBack: () -> Unit,
    val onSettings: () -> Unit,
)

data class CounterValueActions(
    val onIncrement: () -> Unit,
    val onDecrement: () -> Unit,
    val onUndo: () -> Unit,
    val onSetCount: () -> Unit,
)

data class CounterProjectActions(
    val onEdit: () -> Unit,
    val onReset: () -> Unit,
    val onArchive: () -> Unit,
    val onDelete: () -> Unit,
)

data class CounterScreenActions(
    val navigation: CounterNavigationActions,
    val value: CounterValueActions,
    val project: CounterProjectActions,
)

internal data class CounterUnitResources(
    @StringRes val label: Int,
    @StringRes val addDescription: Int,
    @StringRes val removeDescription: Int,
    @PluralsRes val targetProgress: Int,
)

internal fun counterUnitResources(unit: CounterUnit): CounterUnitResources =
    when (unit) {
        CounterUnit.ROWS -> {
            CounterUnitResources(
                label = R.string.counter_rows_label,
                addDescription = R.string.counter_add_row,
                removeDescription = R.string.counter_remove_row,
                targetProgress = R.plurals.counter_target_rows,
            )
        }

        CounterUnit.ROUNDS -> {
            CounterUnitResources(
                label = R.string.counter_rounds_label,
                addDescription = R.string.counter_add_round,
                removeDescription = R.string.counter_remove_round,
                targetProgress = R.plurals.counter_target_rounds,
            )
        }
    }

internal fun responsiveCountTextSize(
    digitCount: Int,
    maxWidth: Float,
    fontScale: Float,
): Float {
    val baseSize =
        when (digitCount) {
            in 0..3 -> 115f
            4 -> 102f
            5 -> 90f
            else -> 76f
        }
    val widthScale = (maxWidth / 340f).coerceIn(0.78f, 1f)
    val fontCompensation = if (fontScale > 1.3f) 1.3f / fontScale else 1f
    return baseSize * widthScale * fontCompensation
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreenContent(
    state: CounterUiState,
    actions: CounterScreenActions,
    modifier: Modifier = Modifier,
) {
    val project = state.project
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = project?.name.orEmpty(),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = actions.navigation.onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = stringResource(R.string.action_back),
                        )
                    }
                },
                actions = {
                    IconButton(onClick = actions.navigation.onSettings) {
                        Icon(
                            painter = painterResource(R.drawable.ic_settings),
                            contentDescription = stringResource(R.string.action_settings),
                        )
                    }
                    Box {
                        IconButton(
                            onClick = { menuExpanded = true },
                            enabled = project != null,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_more),
                                contentDescription = stringResource(R.string.action_more),
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false },
                        ) {
                            CounterMenuItem(R.string.action_edit, R.drawable.ic_edit) {
                                menuExpanded = false
                                actions.project.onEdit()
                            }
                            CounterMenuItem(R.string.action_set_count, R.drawable.ic_edit) {
                                menuExpanded = false
                                actions.value.onSetCount()
                            }
                            CounterMenuItem(R.string.action_reset, R.drawable.ic_restore) {
                                menuExpanded = false
                                actions.project.onReset()
                            }
                            CounterMenuItem(R.string.action_archive, R.drawable.ic_archive) {
                                menuExpanded = false
                                actions.project.onArchive()
                            }
                            CounterMenuItem(R.string.action_delete, R.drawable.ic_delete) {
                                menuExpanded = false
                                actions.project.onDelete()
                            }
                        }
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
            )
        },
    ) { contentPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
            contentAlignment = Alignment.TopCenter,
        ) {
            if (project == null) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else {
                CounterWorkspace(
                    project = project,
                    canUndo = state.canUndo,
                    actions = actions.value,
                    modifier =
                        Modifier
                            .widthIn(max = RowToolDimens.MaxContentWidth)
                            .fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun CounterWorkspace(
    project: CounterProject,
    canUndo: Boolean,
    actions: CounterValueActions,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val numberFormat = remember(configuration) { NumberFormat.getIntegerInstance() }
    val formattedCount = remember(project.count, numberFormat) { numberFormat.format(project.count) }
    val formattedTarget =
        remember(project.targetCount, numberFormat) {
            project.targetCount?.let(numberFormat::format)
        }
    val repeatProgress =
        remember(project.count, project.repeatLength) {
            RepeatProgressCalculator.calculate(project.count, project.repeatLength)
        }
    val targetProgress =
        remember(project.count, project.targetCount) {
            TargetProgressCalculator.calculate(project.count, project.targetCount)
        }
    val resources = counterUnitResources(project.counterUnit)
    val counterLabel = stringResource(resources.label)
    val addDescription = stringResource(resources.addDescription)
    val removeDescription = stringResource(resources.removeDescription)

    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    start = RowToolDimens.PhoneHorizontalPadding,
                    end = RowToolDimens.PhoneHorizontalPadding,
                    bottom = RowToolDimens.Space24,
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(RowToolDimens.Space8))
        repeatProgress?.let { repeat ->
            Surface(
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ) {
                Text(
                    text =
                        stringResource(
                            R.string.counter_repeat_progress,
                            repeat.currentStep,
                            repeat.repeatLength,
                        ),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )
            }
            if (repeat.completedRepeats > 0) {
                Text(
                    text =
                        pluralStringResource(
                            R.plurals.repeat_count,
                            repeat.completedRepeats.toInt(),
                            repeat.completedRepeats,
                        ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = RowToolDimens.Space4),
                )
            }
        }
        Text(
            text = counterLabel,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(top = RowToolDimens.Space16),
        )
        ResponsiveCount(
            formattedCount = formattedCount,
            onSetCount = actions.onSetCount,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = 128.dp),
        )
        targetProgress?.let { target ->
            LinearProgressIndicator(
                progress = { target.fraction },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            )
            Text(
                text =
                    if (target.isReached) {
                        stringResource(R.string.counter_target_reached)
                    } else {
                        pluralStringResource(
                            resources.targetProgress,
                            target.targetCount.toInt(),
                            formattedCount,
                            formattedTarget.orEmpty(),
                        )
                    },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = RowToolDimens.Space8),
            )
        }
        CounterButtons(
            count = project.count,
            canUndo = canUndo,
            archived = project.isArchived,
            addDescription = addDescription,
            removeDescription = removeDescription,
            actions = actions,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = RowToolDimens.Space12),
        )
    }
}

@Composable
private fun ResponsiveCount(
    formattedCount: String,
    onSetCount: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val fontScale = LocalDensity.current.fontScale
    val editDescription = stringResource(R.string.counter_edit_count, formattedCount)
    val editAction = stringResource(R.string.action_set_count)
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val digits = formattedCount.count(Char::isDigit)
        val textSize = responsiveCountTextSize(digits, maxWidth.value, fontScale)
        Text(
            text = formattedCount,
            style =
                MaterialTheme.typography.displayLarge.copy(
                    fontSize = textSize.sp,
                    fontWeight = FontWeight.Bold,
                ),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier =
                Modifier
                    .clickable(role = Role.Button, onClick = onSetCount)
                    .semantics {
                        role = Role.Button
                        stateDescription = editDescription
                        heading()
                        onClick(label = editAction) {
                            onSetCount()
                            true
                        }
                    }.padding(horizontal = RowToolDimens.Space8, vertical = RowToolDimens.Space4),
        )
    }
}

@Composable
private fun CounterButtons(
    count: Long,
    canUndo: Boolean,
    archived: Boolean,
    addDescription: String,
    removeDescription: String,
    actions: CounterValueActions,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier) {
        val scale = (maxWidth.value / 370f).coerceIn(0.72f, 1f)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CounterImageButton(
                imageRes = R.drawable.counter_minus_button,
                contentDescription = removeDescription,
                layout =
                    CounterButtonLayout(
                        visualSize = (102 * scale).dp,
                        touchSize = (120 * scale).dp,
                        visualOffsetY = 2.dp,
                    ),
                enabled = !archived && count > 0,
                onClick = actions.onDecrement,
            )
            CounterImageButton(
                imageRes = R.drawable.counter_plus_button,
                contentDescription = addDescription,
                layout =
                    CounterButtonLayout(
                        visualSize = (130 * scale).dp,
                        touchSize = (150 * scale).dp,
                    ),
                enabled = !archived && count < CounterConstants.MAX_COUNT,
                onClick = actions.onIncrement,
            )
            CounterImageButton(
                imageRes = R.drawable.counter_undo_button,
                contentDescription = stringResource(R.string.counter_undo),
                layout =
                    CounterButtonLayout(
                        visualSize = (82 * scale).dp,
                        touchSize = (104 * scale).dp,
                        visualOffsetY = 4.dp,
                    ),
                enabled = !archived && canUndo,
                onClick = actions.onUndo,
            )
        }
    }
}

@Composable
private fun CounterMenuItem(
    labelRes: Int,
    iconRes: Int,
    onClick: () -> Unit,
) {
    RowToolDropdownMenuItem(
        label = stringResource(labelRes),
        iconRes = iconRes,
        onClick = onClick,
    )
}
