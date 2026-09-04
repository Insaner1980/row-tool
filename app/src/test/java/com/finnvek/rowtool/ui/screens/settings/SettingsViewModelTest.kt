package com.finnvek.rowtool.ui.screens.settings

import android.net.Uri
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.data.repository.BackupRepository
import com.finnvek.rowtool.test.InMemoryPreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

// CPD-OFF: Test setup intentionally mirrors the counter ViewModel fixture.
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class SettingsViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var database: RowToolDatabase

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        database =
            Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    RowToolDatabase::class.java,
                ).allowMainThreadQueries()
                .build()
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
    }
    // CPD-ON

    @Test
    fun latestImportSelectionWinsWhenReadsCompleteOutOfOrder() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val preferencesRepository = PreferencesRepository(InMemoryPreferencesDataStore(), database.projectDao())
        val backupRepository =
            BackupRepository(
                database = database,
                preferencesRepository = preferencesRepository,
                ioDispatcher = Dispatchers.IO,
            )
        val viewModel = SettingsViewModel(preferencesRepository, backupRepository, Dispatchers.IO)
        val firstUri = Uri.parse("content://rowtool.test/first")
        val secondUri = Uri.parse("content://rowtool.test/second")
        val firstStream = ControlledInputStream(backupJson(exportedAt = 1).encodeToByteArray(), blocked = true)
        val secondStream = ControlledInputStream(backupJson(exportedAt = 2).encodeToByteArray(), blocked = false)
        shadowOf(context.contentResolver).registerInputStream(firstUri, firstStream)
        shadowOf(context.contentResolver).registerInputStream(secondUri, secondStream)

        try {
            viewModel.prepareImport(context.contentResolver, firstUri)
            waitUntil { firstStream.started.count == 0L }

            viewModel.prepareImport(context.contentResolver, secondUri)
            waitUntil { secondStream.finished.count == 0L }
            waitUntil {
                viewModel.importPreview.value
                    ?.backup
                    ?.exportedAt == 2L
            }

            firstStream.release.countDown()
            waitUntil { firstStream.finished.count == 0L }
            repeat(50) {
                dispatcher.scheduler.runCurrent()
                Thread.sleep(10)
            }

            assertEquals(
                2L,
                viewModel.importPreview.value
                    ?.backup
                    ?.exportedAt,
            )
        } finally {
            firstStream.release.countDown()
        }
    }

    private fun waitUntil(condition: () -> Boolean) {
        val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(5)
        while (!condition() && System.nanoTime() < deadline) {
            dispatcher.scheduler.runCurrent()
            Thread.sleep(10)
        }
        assertTrue(condition())
    }

    private fun backupJson(exportedAt: Long): String =
        """
        {
          "schemaVersion":1,
          "application":"RowTool",
          "exportedAt":$exportedAt,
          "projects":[]
        }
        """.trimIndent()
}

private class ControlledInputStream(
    bytes: ByteArray,
    blocked: Boolean,
) : InputStream() {
    private val delegate = ByteArrayInputStream(bytes)
    val started = CountDownLatch(1)
    val release = CountDownLatch(if (blocked) 1 else 0)
    val finished = CountDownLatch(1)

    override fun read(): Int {
        awaitRelease()
        return delegate.read().also(::recordCompletion)
    }

    override fun read(
        buffer: ByteArray,
        offset: Int,
        length: Int,
    ): Int {
        awaitRelease()
        return delegate.read(buffer, offset, length).also(::recordCompletion)
    }

    private fun awaitRelease() {
        started.countDown()
        release.await()
    }

    private fun recordCompletion(result: Int) {
        if (result < 0) finished.countDown()
    }
}
