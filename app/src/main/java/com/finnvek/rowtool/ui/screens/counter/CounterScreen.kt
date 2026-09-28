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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finnvek.rowtool.R
import com.finnvek.rowtool.domain.counter.RepeatProgressCalculator
import com.finnvek.rowtool.domain.counter.TargetProgressCalculator
import com.finnvek.rowtool.domain.model.AdditionalCounter
import com.finnvek.rowtool.domain.model.CounterConstants
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.ProjectNote
import com.finnvek.rowtool.domain.model.Reminder
import com.finnvek.rowtool.domain.model.ReminderRules
import com.finnvek.rowtool.ui.RowToolDropdownMenuItem
import com.finnvek.rowtool.ui.screens.note.NotePreview
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
    val onRepeatEdit: () -> Unit = {},
    val onReminders: () -> Unit = {},
    val onNote: () -> Unit = {},
    val onHistory: () -> Unit = {},
)

data class CounterScreenActions(
    val navigation: CounterNavigationActions,
    val value: CounterValueActions,
    val project: CounterProjectActions,
    val additional: AdditionalCounterActions = AdditionalCounterActions(),
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
                        text = project?.name.orEmpty().uppercase(LocalConfiguration.current.locales[0]),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = RowToolDimens.Space8).semantics { heading() },
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
                            enabled = project?.isArchived == false,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_more),
                                contentDescription = stringResource(R.string.action_more),
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpanded && project?.isArchived == false,
                            onDismissRequest = { menuExpanded = false },
                        ) {
                            CounterMenuItem(R.string.action_edit, R.drawable.ic_edit) {
                                menuExpanded = false
                                actions.project.onEdit()
                            }
                            CounterMenuItem(R.string.action_repeat, R.drawable.ic_edit) {
                                menuExpanded = false
                                actions.project.onRepeatEdit()
                            }
                            CounterMenuItem(R.string.note_title, R.drawable.ic_edit) {
                                menuExpanded = false
                                actions.project.onNote()
                            }
                            CounterMenuItem(R.string.history_title, R.drawable.ic_restore) {
                                menuExpanded = false
                                actions.project.onHistory()
                            }
                            CounterMenuItem(R.string.reminder_title, R.drawable.ic_edit) {
                                menuExpanded = false
                                actions.project.onReminders()
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
        BoxWithConstraints(
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
                    state = state,
                    actions = actions,
                    viewportHeight = maxHeight,
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
    state: CounterUiState,
    actions: CounterScreenActions,
    viewportHeight: Dp,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val numberFormat = remember(configuration.locales[0]) { NumberFormat.getIntegerInstance(configuration.locales[0]) }
    val formattedCount = remember(project.count, numberFormat) { numberFormat.format(project.count) }
    val formattedTarget =
        remember(project.targetCount, numberFormat) {
            project.targetCount?.let(numberFormat::format)
        }
    val repeatProgress =
        remember(project.count, project.repeatLength, project.repeatStartCount) {
            RepeatProgressCalculator.calculate(project.count, project.repeatLength, project.repeatStartCount)
        }
    val targetProgress =
        remember(project.count, project.targetCount) {
            TargetProgressCalculator.calculate(project.count, project.targetCount)
        }
    val visibleReminders =
        state.reminders.filter {
            !project.isArchived &&
                it.enabled &&
                ReminderRules.status(it, project.count).let { status ->
                    status.dueCount != null || status.nextCount != null
                }
        }
    val resources = counterUnitResources(project.counterUnit)
    val counterLabel = stringResource(resources.label)
    val addDescription = stringResource(resources.addDescription)
    val removeDescription = stringResource(resources.removeDescription)

    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState())
                .heightIn(min = viewportHeight)
                .padding(
                    start = RowToolDimens.PhoneHorizontalPadding,
                    end = RowToolDimens.PhoneHorizontalPadding,
                    top = RowToolDimens.Space24,
                    bottom = RowToolDimens.Space24,
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (visibleReminders.isEmpty()) Arrangement.Center else Arrangement.Top,
    ) {
        Text(
            text = counterLabel,
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = RowToolDimens.CounterUnitFontSize),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
        ResponsiveCount(
            formattedCount = formattedCount,
            enabled = !project.isArchived,
            onSetCount = actions.value.onSetCount,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = 128.dp),
        )
        targetProgress?.let { target ->
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
                style = MaterialTheme.typography.labelMedium.copy(fontSize = RowToolDimens.CounterTargetFontSize),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = RowToolDimens.Space8),
            )
            Spacer(Modifier.height(RowToolDimens.Space8))
            LinearProgressIndicator(
                progress = { target.fraction },
                modifier =
                    Modifier
                        .widthIn(max = RowToolDimens.CounterControlsMaxWidth)
                        .fillMaxWidth()
                        .height(RowToolDimens.CounterTargetProgressHeight),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            )
        }
        repeatProgress?.let { repeat -> RepeatProgress(project, repeat, actions.project.onRepeatEdit) }
        CounterButtons(
            count = project.count,
            canUndo = state.canUndo,
            archived = project.isArchived,
            addDescription = addDescription,
            removeDescription = removeDescription,
            actions = actions.value,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = RowToolDimens.Space32),
        )
        if (visibleReminders.isNotEmpty()) {
            ReminderPanel(project, visibleReminders, actions.project.onReminders)
        }
        AdditionalCountersSection(
            counters = state.additionalCounters,
            actions = actions.additional,
            enabled = !project.isArchived,
            modifier = Modifier.fillMaxWidth().padding(top = RowToolDimens.Space24),
        )
        if (state.note != null) NotePreview(state.note, actions.project.onNote)
    }
}

@Composable
private fun ReminderPanel(
    project: CounterProject,
    reminders: List<Reminder>,
    onOpen: () -> Unit,
) {
    val due = reminders.filter { ReminderRules.status(it, project.count).dueCount != null }
    Surface(
        modifier = Modifier.fillMaxWidth().padding(top = RowToolDimens.Space16).heightIn(min = 96.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
    ) {
        Column(Modifier.padding(RowToolDimens.Space16), verticalArrangement = Arrangement.spacedBy(RowToolDimens.Space4)) {
            if (due.size == 1) {
                val item = due.single()
                Text(
                    reminderCountLabel(project.counterUnit, ReminderRules.status(item, project.count).dueCount!!, true),
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(item.message, maxLines = 2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodyMedium)
            } else if (due.size > 1) {
                Text(pluralStringResource(R.plurals.reminder_due_count, due.size, due.size), style = MaterialTheme.typography.titleSmall)
            } else {
                val next = reminders.mapNotNull { ReminderRules.status(it, project.count).nextCount }.minOrNull()
                if (next != null) Text(reminderCountLabel(project.counterUnit, next, false), style = MaterialTheme.typography.titleSmall)
            }
            TextButton(onClick = onOpen, modifier = Modifier.heightIn(min = 48.dp)) {
                Text(stringResource(R.string.reminder_view_all))
            }
        }
    }
}

@Composable
private fun ResponsiveCount(
    formattedCount: String,
    enabled: Boolean,
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
                MaterialTheme.typography.displayMedium.copy(
                    fontSize = textSize.sp,
                    fontWeight = FontWeight.Bold,
                    fontFeatureSettings = "tnum",
                ),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier =
                Modifier
                    .clickable(enabled = enabled, role = Role.Button, onClick = onSetCount)
                    .semantics {
                        role = Role.Button
                        stateDescription = editDescription
                        heading()
                        if (enabled) {
                            onClick(label = editAction) {
                                onSetCount()
                                true
                            }
                        } else {
                            disabled()
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
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        val scale = counterControlScale(maxWidth)
        Column(
            modifier =
                Modifier
                    .widthIn(max = RowToolDimens.CounterControlsMaxWidth)
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(RowToolDimens.CounterUndoVerticalSpacing),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CounterImageButton(
                    imageRes = R.drawable.counter_minus_button,
                    contentDescription = removeDescription,
                    layout =
                        CounterButtonLayout(
                            visualSize = RowToolDimens.CounterMinusVisualSize * scale,
                            touchSize = RowToolDimens.CounterPrimaryTouchSize * scale,
                            visualOffsetY = RowToolDimens.CounterMinusOpticalOffsetY,
                        ),
                    enabled = !archived && count > CounterConstants.MIN_COUNT,
                    onClick = actions.onDecrement,
                )
                CounterImageButton(
                    imageRes = R.drawable.counter_plus_button,
                    contentDescription = addDescription,
                    layout =
                        CounterButtonLayout(
                            visualSize = RowToolDimens.CounterPlusVisualSize * scale,
                            touchSize = RowToolDimens.CounterPrimaryTouchSize * scale,
                        ),
                    enabled = !archived && count < CounterConstants.MAX_COUNT,
                    onClick = actions.onIncrement,
                )
            }
            CounterImageButton(
                imageRes = R.drawable.counter_undo_button,
                contentDescription = stringResource(R.string.counter_undo),
                layout =
                    CounterButtonLayout(
                        visualSize = RowToolDimens.CounterUndoSize * scale,
                        touchSize = (RowToolDimens.CounterUndoSize * scale).coerceAtLeast(RowToolDimens.MinimumTouchSize),
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

@Composable
private fun RepeatProgress(
    project: CounterProject,
    repeat: com.finnvek.rowtool.domain.model.RepeatProgress,
    onRepeatEdit: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .padding(top = RowToolDimens.Space16)
                .heightIn(min = 48.dp)
                .clickable(role = Role.Button, onClick = onRepeatEdit),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.counter_repeat_progress, repeat.currentStep, repeat.repeatLength),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        if (project.repeatStartCount != null && project.repeatStartCount > 1) {
            Text(
                text =
                    stringResource(
                        if (project.counterUnit == CounterUnit.ROWS) R.string.repeat_starts_row else R.string.repeat_starts_round,
                        project.repeatStartCount,
                    ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
        if (repeat.completedRepeats > 0) {
            Text(
                text = pluralStringResource(R.plurals.repeat_count, repeat.completedRepeats.toInt(), repeat.completedRepeats),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = RowToolDimens.Space4),
            )
        }
    }
}
