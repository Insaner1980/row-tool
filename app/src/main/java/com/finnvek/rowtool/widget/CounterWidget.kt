package com.finnvek.rowtool.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionSendBroadcast
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.semantics.contentDescription
import androidx.glance.semantics.semantics
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.room.withTransaction
import com.finnvek.rowtool.MainActivity
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.local.toDomain
import com.finnvek.rowtool.data.preferences.ThemeMode
import com.finnvek.rowtool.domain.model.CounterProject
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.ReminderRules
import com.finnvek.rowtool.ui.theme.DarkBackground
import com.finnvek.rowtool.ui.theme.DarkText
import com.finnvek.rowtool.ui.theme.LightBackground
import com.finnvek.rowtool.ui.theme.LightText
import kotlinx.coroutines.flow.combine
import java.text.NumberFormat

internal data class WidgetContent(
    val binding: WidgetBinding? = null,
    val project: CounterProject? = null,
    val due: Int = 0,
    val error: Boolean = false,
    val loading: Boolean = false,
)

class CounterWidget : GlanceAppWidget() {
    override val sizeMode = SizeMode.Exact

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        val widgetId = GlanceAppWidgetManager(context).getAppWidgetId(id)
        val container = context.widgetContainer()
        val states =
            combine(container.database.invalidationTracker.createFlow("projects", "reminders"), container.widgetBindings.changes) { _, _ ->
                try {
                    val binding = container.widgetBindings.read(widgetId)
                    if (binding == null) {
                        WidgetContent()
                    } else if (binding.failed) {
                        WidgetContent(binding = binding, error = true)
                    } else {
                        container.widgetBindings.withBinding(widgetId, binding.token) { projectId ->
                            container.database.withTransaction {
                                val project = container.counterRepository.getProject(projectId)
                                val due =
                                    project?.let { current ->
                                        container.database.reminderDao().getForProject(projectId).count {
                                            ReminderRules.status(it.toDomain(), current.count).dueCount != null
                                        }
                                    } ?: 0
                                WidgetContent(binding, project, due)
                            }
                        } ?: WidgetContent()
                    }
                } catch (_: java.io.IOException) {
                    WidgetContent(error = true)
                } catch (_: android.database.SQLException) {
                    WidgetContent(error = true)
                }
            }
        provideContent {
            val state by states.collectAsState(WidgetContent(loading = true))
            val preferences by container.preferencesRepository.preferences.collectAsState(null)
            val configuration by (context.applicationContext as com.finnvek.rowtool.RowToolApplication).widgetConfiguration.collectAsState()
            val localized =
                androidx.core.content.ContextCompat
                    .getContextForLanguage(context)
                    .createConfigurationContext(configuration)
            WidgetLayout(localized, widgetId, state, preferences?.themeMode ?: ThemeMode.SYSTEM)
        }
    }

    override fun onCompositionError(
        context: Context,
        glanceId: GlanceId,
        appWidgetId: Int,
        throwable: Throwable,
    ) {
        val intent =
            Intent(context, WidgetConfigurationActivity::class.java)
                .putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                .setData("rowtool-widget://configure/$appWidgetId".toUri())
        val pending =
            android.app.PendingIntent.getActivity(
                context,
                appWidgetId,
                intent,
                android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE,
            )
        val views = android.widget.RemoteViews(context.packageName, R.layout.widget_error)
        val localized =
            androidx.core.content.ContextCompat
                .getContextForLanguage(context)
        views.setTextViewText(R.id.widget_error_message, localized.getString(R.string.widget_error))
        views.setTextViewText(R.id.widget_error_action, localized.getString(R.string.widget_choose))
        views.setOnClickPendingIntent(R.id.widget_error_action, pending)
        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, views)
    }

    override suspend fun onDelete(
        context: Context,
        glanceId: GlanceId,
    ) {
        context.widgetContainer().widgetBindings.remove(GlanceAppWidgetManager(context).getAppWidgetId(glanceId))
    }
}

class CounterWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = CounterWidget()

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (intent.action == Intent.ACTION_LOCALE_CHANGED || intent.action == Intent.ACTION_CONFIGURATION_CHANGED) {
            val manager = AppWidgetManager.getInstance(context)
            onUpdate(context, manager, manager.getAppWidgetIds(android.content.ComponentName(context, CounterWidgetReceiver::class.java)))
        } else {
            super.onReceive(context, intent)
        }
    }
}

@Composable
private fun WidgetLayout(
    context: Context,
    id: Int,
    state: WidgetContent,
    theme: ThemeMode,
) {
    val foreground = widgetColor(theme, LightText, DarkText)
    val inactive = widgetColor(theme, LightText.copy(alpha = 0.4f), DarkText.copy(alpha = 0.4f))
    val background = widgetColor(theme, LightBackground, DarkBackground)
    val project = state.project
    val binding = state.binding
    val active = project != null && !project.isArchived && binding != null
    val format = NumberFormat.getIntegerInstance(context.resources.configuration.locales[0])
    val configure =
        Intent(context, WidgetConfigurationActivity::class.java)
            .putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id)
            .setData("rowtool-widget://configure/$id".toUri())
    val open =
        Intent(context, MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .setData("rowtool-widget://open/$id/${binding?.token.orEmpty()}".toUri())
    val openAction = actionStartActivity(if (binding == null) configure else open)
    Column(
        GlanceModifier.fillMaxSize().background(background).padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val title =
            when {
                state.loading -> context.getString(R.string.widget_loading)
                state.error -> context.getString(R.string.widget_error)
                binding == null -> context.getString(R.string.widget_choose)
                project == null -> context.getString(R.string.widget_missing)
                else -> project.name
            }
        Row(GlanceModifier.fillMaxWidth().height(48.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                title,
                GlanceModifier
                    .defaultWeight()
                    .height(48.dp)
                    .clickable(openAction)
                    .semantics { contentDescription = title },
                style = TextStyle(color = foreground, fontSize = 14.sp),
                maxLines = 1,
            )
            Image(
                ImageProvider(R.drawable.ic_edit),
                context.getString(R.string.widget_choose),
                GlanceModifier.size(48.dp).padding(12.dp).clickable(actionStartActivity(configure)),
                colorFilter = ColorFilter.tint(foreground),
            )
        }
        Text(
            project?.let { format.format(it.count) } ?: "—",
            GlanceModifier.height(64.dp).clickable(openAction),
            style = TextStyle(color = foreground, fontSize = 26.sp, fontWeight = FontWeight.Bold),
            maxLines = 1,
        )
        Text(
            if (project?.isArchived == true) {
                context.getString(R.string.project_archived)
            } else {
                project
                    ?.let {
                        context.getString(if (it.counterUnit == CounterUnit.ROWS) R.string.project_rows else R.string.project_rounds)
                    }.orEmpty()
            },
            GlanceModifier.height(32.dp),
            style = TextStyle(color = foreground, fontSize = 12.sp),
            maxLines = 1,
        )
        val reminderIntent = Intent(open).setData("rowtool-widget://reminders/$id/${binding?.token.orEmpty()}".toUri())
        Text(
            if (state.error) {
                context.getString(R.string.widget_retry)
            } else if (state.due >
                0
            ) {
                context.getString(R.string.widget_reminders, format.format(state.due))
            } else {
                ""
            },
            GlanceModifier
                .height(
                    48.dp,
                ).fillMaxWidth()
                .clickable(
                    if (state.error) {
                        actionSendBroadcast(
                            widgetActionIntent(context, id, "", "refresh"),
                        )
                    } else {
                        actionStartActivity(reminderIntent)
                    },
                ),
            style = TextStyle(color = foreground, fontSize = 12.sp),
            maxLines = 1,
        )
        Row(GlanceModifier.fillMaxWidth().height(56.dp)) {
            WidgetCountButton(
                context,
                id,
                "−",
                "minus",
                active,
                binding,
                if (active) foreground else inactive,
                project?.counterUnit == CounterUnit.ROUNDS,
                GlanceModifier.defaultWeight(),
            )
            WidgetCountButton(
                context,
                id,
                "+",
                "plus",
                active,
                binding,
                if (active) foreground else inactive,
                project?.counterUnit == CounterUnit.ROUNDS,
                GlanceModifier.defaultWeight(),
            )
        }
    }
}

@Composable
private fun WidgetCountButton(
    context: Context,
    id: Int,
    label: String,
    operation: String,
    active: Boolean,
    binding: WidgetBinding?,
    foreground: ColorProvider,
    rounds: Boolean,
    modifier: GlanceModifier = GlanceModifier,
) {
    val description =
        context.getString(
            when {
                operation == "plus" && rounds -> R.string.counter_add_round
                operation == "plus" -> R.string.counter_add_row
                rounds -> R.string.counter_remove_round
                else -> R.string.counter_remove_row
            },
        )
    val click =
        if (active) {
            modifier.clickable(
                actionSendBroadcast(widgetActionIntent(context, id, binding!!.token, operation)),
            )
        } else {
            modifier
        }
    Box(click.height(56.dp).semantics { contentDescription = description }, contentAlignment = Alignment.Center) {
        Text(label, style = TextStyle(color = foreground, fontSize = 20.sp))
    }
}

private fun widgetColor(
    theme: ThemeMode,
    light: androidx.compose.ui.graphics.Color,
    dark: androidx.compose.ui.graphics.Color,
): ColorProvider =
    when (theme) {
        ThemeMode.LIGHT -> ColorProvider(light)
        ThemeMode.DARK -> ColorProvider(dark)
        ThemeMode.SYSTEM -> androidx.glance.color.ColorProvider(day = light, night = dark)
    }
