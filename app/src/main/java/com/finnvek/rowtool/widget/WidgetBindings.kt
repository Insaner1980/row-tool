package com.finnvek.rowtool.widget

import android.database.SQLException
import android.util.AtomicFile
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.util.Properties
import java.util.UUID

data class WidgetBinding(
    val projectId: String,
    val token: String,
    val failed: Boolean = false,
)

/** All callers share this instance; widget components run in the application process. */
class WidgetBindings(
    file: File,
) {
    private val file = AtomicFile(file)
    private val mutex = Mutex()
    val changes = kotlinx.coroutines.flow.MutableStateFlow(0L)

    suspend fun read(id: Int): WidgetBinding? = mutex.withLock { binding(load(), id) }

    suspend fun bind(
        id: Int,
        projectId: String,
    ): WidgetBinding = bindIf(id, projectId) { true }!!

    suspend fun bindIf(
        id: Int,
        projectId: String,
        isActive: suspend () -> Boolean,
    ): WidgetBinding? =
        mutex.withLock {
            require(id > 0 && projectId.isNotBlank())
            if (!isActive()) return@withLock null
            val state = load().let { if (it.getProperty("pending") == "true") Properties() else it }
            val binding = WidgetBinding(projectId, UUID.randomUUID().toString())
            state.setProperty("project.$id", binding.projectId)
            state.setProperty("token.$id", binding.token)
            state.remove("failed.$id")
            save(state)
            binding
        }

    suspend fun remove(id: Int) =
        mutex.withLock {
            val state = load()
            state.remove("project.$id")
            state.remove("token.$id")
            state.remove("failed.$id")
            save(state)
        }

    suspend fun setFailed(
        id: Int,
        failed: Boolean,
    ) = mutex.withLock {
        val state = load()
        state.setProperty("failed.$id", failed.toString())
        save(state)
    }

    suspend fun <T> withBinding(
        id: Int,
        token: String,
        action: suspend (String) -> T,
    ): T? =
        mutex.withLock {
            val current = binding(load(), id)?.takeIf { it.token == token } ?: return@withLock null
            action(current.projectId)
        }

    /** The pending marker is durable BEFORE Room is touched. Only a proven rollback restores it. */
    suspend fun <T> replaceDatabase(transaction: suspend () -> T): T =
        mutex.withLock {
            val before = load()
            val pending = Properties().apply { setProperty("pending", "true") }
            save(pending)
            val result =
                try {
                    transaction()
                } catch (error: SQLException) {
                    save(before)
                    throw error
                }
            // If this write fails, the durable pending marker continues to reject old actions.
            try {
                save(Properties())
            } catch (_: java.io.IOException) {
                // Room committed successfully; the pending marker safely keeps every old binding invalid.
            }
            result
        }

    private fun binding(
        state: Properties,
        id: Int,
    ): WidgetBinding? {
        val project = state.getProperty("project.$id")
        val token = state.getProperty("token.$id")
        return if (state.getProperty("pending") == "true" || project == null || token == null) {
            null
        } else {
            WidgetBinding(project, token, state.getProperty("failed.$id") == "true")
        }
    }

    private fun load(): Properties =
        Properties().apply {
            if (file.baseFile.exists() || File(file.baseFile.path + ".bak").exists()) {
                file.openRead().use { loadFromXML(it) }
            }
        }

    private fun save(state: Properties) {
        val output = file.startWrite()
        try {
            state.storeToXML(output, "Device-local widget bindings", "UTF-8")
            file.finishWrite(output)
            changes.value += 1
        } catch (error: java.io.IOException) {
            file.failWrite(output)
            throw error
        }
    }
}
