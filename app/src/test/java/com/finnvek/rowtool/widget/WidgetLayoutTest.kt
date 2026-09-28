package com.finnvek.rowtool.widget

import android.app.Application
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.ExperimentalGlanceRemoteViewsApi
import androidx.glance.appwidget.GlanceRemoteViews
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.R
import com.finnvek.rowtool.data.local.toDomain
import com.finnvek.rowtool.data.preferences.ThemeMode
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.test.projectEntities
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@OptIn(ExperimentalGlanceRemoteViewsApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], application = Application::class)
class WidgetLayoutTest {
    private val context get() = ApplicationProvider.getApplicationContext<Application>()
    private val project = projectEntities(1).single().toDomain().copy(count = 123)
    private val binding = WidgetBinding(project.id, "token")

    @Test fun activeWidgetRendersCountAndAccessibleActionsInEveryTheme() =
        runTest {
            for (theme in ThemeMode.entries) {
                val view = compose(WidgetContent(binding, project, due = 2), theme)
                assertText(view, project.name)
                assertText(view, "123")
                assertText(view, context.getString(R.string.project_rows))
                assertText(view, context.getString(R.string.widget_reminders, "2"))
                val plus = view.descendants().single { it.contentDescription == context.getString(R.string.counter_add_row) }
                val minus = view.descendants().single { it.contentDescription == context.getString(R.string.counter_remove_row) }
                assertAction(plus, "plus")
                assertAction(minus, "minus")
            }
        }

    @Test fun archivedWidgetShowsCountButDisablesBothCountingActions() =
        runTest {
            val view = compose(WidgetContent(binding, project.copy(isArchived = true, counterUnit = CounterUnit.ROUNDS)))
            assertText(view, "123")
            assertText(view, context.getString(R.string.project_archived))
            val plus = view.descendants().single { it.contentDescription == context.getString(R.string.counter_add_round) }
            val minus = view.descendants().single { it.contentDescription == context.getString(R.string.counter_remove_round) }
            assertFalse(plus.hasClickAction())
            assertFalse(minus.hasClickAction())
        }

    @Test fun loadingUnconfiguredMissingAndErrorStatesRenderTheirRecoveryLabels() =
        runTest {
            val cases =
                listOf(
                    WidgetContent(loading = true) to R.string.widget_loading,
                    WidgetContent() to R.string.widget_choose,
                    WidgetContent(binding) to R.string.widget_missing,
                    WidgetContent(binding, error = true) to R.string.widget_error,
                )
            for ((state, label) in cases) {
                val view = compose(state)
                assertText(view, context.getString(label))
                assertText(view, "—")
                if (state.error) assertText(view, context.getString(R.string.widget_retry))
                val plus = view.descendants().single { it.contentDescription == context.getString(R.string.counter_add_row) }
                assertFalse(plus.hasClickAction())
            }
            val rounds = compose(WidgetContent(binding, project.copy(counterUnit = CounterUnit.ROUNDS)))
            assertText(rounds, context.getString(R.string.project_rounds))
            val plus = rounds.descendants().single { it.contentDescription == context.getString(R.string.counter_add_round) }
            assertAction(plus, "plus")
        }

    private suspend fun compose(
        state: WidgetContent,
        theme: ThemeMode = ThemeMode.SYSTEM,
    ): View {
        val result =
            GlanceRemoteViews().compose(context, DpSize(280.dp, 280.dp)) {
                WidgetLayout(context, 1, state, theme)
            }
        return result.remoteViews.apply(context, FrameLayout(context))
    }

    private fun View.hasClickAction(): Boolean = generateSequence(this) { it.parent as? View }.any(View::hasOnClickListeners)

    private fun assertAction(
        view: View,
        operation: String,
    ) {
        val target = generateSequence(view) { it.parent as? View }.first(View::hasOnClickListeners)
        assertTrue(target.performClick())
        val sent = shadowOf(context).broadcastIntents.last()
        assertEquals("com.finnvek.rowtool.WIDGET_ACTION", sent.action)
        assertEquals(operation, sent.data!!.host)
        assertEquals(listOf("1", "token"), sent.data!!.pathSegments)
    }

    private fun assertText(
        view: View,
        text: String,
    ) {
        assertTrue("Missing text: $text", view.descendants().filterIsInstance<TextView>().any { it.text.toString() == text })
    }

    private fun View.descendants(): List<View> =
        listOf(this) + if (this is ViewGroup) (0 until childCount).flatMap { getChildAt(it).descendants() } else emptyList()
}
