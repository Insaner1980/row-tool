package com.finnvek.rowtool.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.ComponentName
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.R
import com.finnvek.rowtool.RowToolApplication
import com.finnvek.rowtool.data.local.toDomain
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.test.projectEntities
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import java.text.NumberFormat
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class WidgetBehaviorTest {
    private val app get() = ApplicationProvider.getApplicationContext<RowToolApplication>()

    @Test fun statusLabelsDistinguishLoadingFailureMissingAndArchivedProjects() {
        val binding = WidgetBinding("project", "token")
        val project = projectEntities(1).single().toDomain()
        assertEquals(app.getString(R.string.widget_loading), widgetTitle(app, WidgetContent(loading = true)))
        assertEquals(app.getString(R.string.widget_error), widgetTitle(app, WidgetContent(error = true)))
        assertEquals(app.getString(R.string.widget_choose), widgetTitle(app, WidgetContent()))
        assertEquals(app.getString(R.string.widget_missing), widgetTitle(app, WidgetContent(binding)))
        assertEquals(project.name, widgetTitle(app, WidgetContent(binding, project)))
        assertEquals("", widgetUnitLabel(app, null))
        assertEquals(app.getString(R.string.project_rows), widgetUnitLabel(app, project))
        assertEquals(app.getString(R.string.project_rounds), widgetUnitLabel(app, project.copy(counterUnit = CounterUnit.ROUNDS)))
        assertEquals(app.getString(R.string.project_archived), widgetUnitLabel(app, project.copy(isArchived = true)))
        val numbers = NumberFormat.getIntegerInstance(Locale.US)
        assertEquals("", widgetReminderLabel(app, WidgetContent(), numbers))
        assertEquals(app.getString(R.string.widget_reminders, "2"), widgetReminderLabel(app, WidgetContent(due = 2), numbers))
        assertEquals(app.getString(R.string.widget_retry), widgetReminderLabel(app, WidgetContent(due = 2, error = true), numbers))
    }

    @Test fun updatesTargetOnlyTheApplicationsWidgetProvider() {
        registerWidget(31)
        shadowOf(AppWidgetManager.getInstance(app)).addBoundWidget(
            32,
            AppWidgetProviderInfo().apply { provider = ComponentName("another.app", "AnotherWidget") },
        )
        assertTrue(app.ownsWidget(31))
        assertFalse(app.ownsWidget(32))
        assertFalse(app.ownsWidget(0))
        assertFalse(app.ownsWidget(999))
        val permission = "${app.packageName}.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION"
        val permissionInfo = app.packageManager.getPermissionInfo(permission, 0)
        assertEquals(android.content.pm.PermissionInfo.PROTECTION_SIGNATURE, permissionInfo.protection)
        val packageInfo = app.packageManager.getPackageInfo(app.packageName, android.content.pm.PackageManager.GET_PERMISSIONS)
        assertTrue(permission in packageInfo.requestedPermissions.orEmpty())
        var receiverPermission: String? = null
        val context =
            object : android.content.ContextWrapper(app) {
                override fun sendBroadcast(
                    intent: Intent,
                    permission: String?,
                ) {
                    receiverPermission = permission
                    super.sendBroadcast(intent, permission)
                }
            }
        requestWidgetUpdate(context)
        assertEquals(permission, receiverPermission)
        val broadcast = shadowOf(app).broadcastIntents.last { it.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE }
        assertEquals(app.packageName, broadcast.`package`)
        assertEquals(ComponentName(app, CounterWidgetReceiver::class.java), broadcast.component)
        assertEquals(listOf(31), broadcast.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)!!.toList())
    }

    @Test fun bindingRejectsForeignMissingAndArchivedProjectsAndActionsRejectStaleTokens() =
        runTest {
            val repository = app.container.counterRepository
            val project = repository.createProject("Widget", CounterUnit.ROWS, 0, null, null)
            registerWidget(41)
            val dispatcher = StandardTestDispatcher(testScheduler)
            assertNull(bindWidget(app, 999, project.id, dispatcher))
            assertNull(bindWidget(app, 41, "missing", dispatcher))
            val binding = bindWidget(app, 41, project.id, dispatcher)!!
            performWidgetAction(app, 41, "plus", binding.token)
            assertEquals(1L, repository.getProject(project.id)!!.count)
            performWidgetAction(app, 41, "minus", binding.token)
            assertEquals(0L, repository.getProject(project.id)!!.count)
            performWidgetAction(app, 41, "plus", "stale-token")
            assertEquals(0L, repository.getProject(project.id)!!.count)
            app.container.widgetBindings.setFailed(41, true)
            performWidgetAction(app, 41, "refresh", null)
            assertFalse(
                app.container.widgetBindings
                    .read(41)!!
                    .failed,
            )
            repository.setArchived(project.id, true)
            assertNull(bindWidget(app, 41, project.id, dispatcher))
            assertEquals(
                binding.token,
                app.container.widgetBindings
                    .read(41)!!
                    .token,
            )
        }

    @Test fun actionIntentsAreExplicitAndInvalidBroadcastsDoNotStartAsyncWork() {
        val intent = widgetActionIntent(app, 51, "token/with spaces", "plus")
        assertEquals(ComponentName(app, WidgetActionReceiver::class.java), intent.component)
        assertEquals(listOf("51", "token/with spaces"), intent.data!!.pathSegments)
        val receiver = WidgetActionReceiver()
        receiver.onReceive(app, Intent())
        receiver.onReceive(app, intent)
        receiver.onReceive(app, widgetActionIntent(app, 51, "token", "unknown"))
        assertFalse(shadowOf(receiver).wentAsync())
        assertNotNull(intent.action)
    }

    private fun registerWidget(id: Int) {
        shadowOf(AppWidgetManager.getInstance(app)).addBoundWidget(
            id,
            AppWidgetProviderInfo().apply { provider = ComponentName(app, CounterWidgetReceiver::class.java) },
        )
    }
}
