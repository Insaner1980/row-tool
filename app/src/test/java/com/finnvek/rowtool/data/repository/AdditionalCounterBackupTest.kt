package com.finnvek.rowtool.data.repository

import androidx.room.Room
import androidx.room.withTransaction
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.local.AdditionalCounterEntity
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.test.InMemoryPreferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class AdditionalCounterBackupTest {
    private lateinit var db: RowToolDatabase
    private lateinit var counters: CounterRepository
    private lateinit var backup: BackupRepository

    @Before
    fun setUp() {
        db =
            Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), RowToolDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        counters = CounterRepository(db)
        backup = BackupRepository(db, PreferencesRepository(InMemoryPreferencesDataStore(), db.projectDao()), clock = { 100 })
    }

    @After
    fun tearDown() = db.close()

    @Test
    fun v4RoundTripPreservesReminderStateAndLegacyV3ClearsIt() =
        runTest {
            val project = counters.createProject("Work", CounterUnit.ROWS, 0, null, null)
            val reminder = counters.reminders.save(project.id, null, null, " Check ", 32, 6, true)!!
            counters.mutate(project.id, CounterMutation.ManualSet(50))
            assertTrue(counters.reminders.acknowledge(project.id, reminder.id, reminder.revision, 50))
            val off = counters.reminders.save(project.id, reminder.id, reminder.revision, "Check", 32, 6, false)!!
            counters.mutate(project.id, CounterMutation.Reset)
            val exported = backup.exportJson()
            val decoded = backup.prepareImport(exported.encodeToByteArray()) as BackupDecodeResult.Valid
            assertEquals(
                50L,
                decoded.backup.reminders
                    .single()
                    .acknowledgedThrough,
            )
            assertTrue(backup.replaceWith(decoded.backup) is BackupImportResult.Success)
            assertEquals(
                off.copy(acknowledgedThrough = 50),
                counters.reminders
                    .observe(project.id)
                    .first()
                    .single(),
            )
            val v3 = Json.decodeFromString<BackupFile>(exported).copy(schemaVersion = 3, reminders = null)
            val old = backup.prepareImport(BackupCodec.encode(v3).encodeToByteArray()) as BackupDecodeResult.Valid
            assertTrue(backup.replaceWith(old.backup) is BackupImportResult.Success)
            assertTrue(
                counters.reminders
                    .observe(project.id)
                    .first()
                    .isEmpty(),
            )
        }

    @Test
    fun invalidV4ReminderLeavesLiveDataUntouched() =
        runTest {
            val project = counters.createProject("Work", CounterUnit.ROWS, 0, null, null)
            counters.reminders.save(project.id, null, null, "Check", 32, 6, true)
            val before = backup.exportJson()
            val file = Json.decodeFromString<BackupFile>(before)
            val invalid = file.copy(reminders = file.reminders!!.map { it.copy(acknowledgedThrough = 33) })
            assertEquals(
                BackupDecodeResult.Invalid(BackupValidationError.INVALID_REMINDER),
                backup.prepareImport(BackupCodec.encode(invalid).encodeToByteArray()),
            )
            assertEquals(before, backup.exportJson())
        }

    @Test
    fun reminderInsertFailureRollsBackAllTables() =
        runTest {
            val project = counters.createProject("Work", CounterUnit.ROWS, 0, null, null)
            counters.reminders.save(project.id, null, null, "Check", 32, 6, true)
            val before = backup.exportJson()
            val validated = (backup.prepareImport(before.encodeToByteArray()) as BackupDecodeResult.Valid).backup
            db.openHelper.writableDatabase.execSQL(
                "CREATE TRIGGER fail_reminder_import BEFORE INSERT ON reminders BEGIN SELECT RAISE(ABORT, 'test'); END",
            )
            try {
                assertTrue(backup.replaceWith(validated) is BackupImportResult.Failure)
                assertEquals(before, backup.exportJson())
            } finally {
                db.openHelper.writableDatabase.execSQL("DROP TRIGGER fail_reminder_import")
            }
        }

    @Test
    fun v4RoundTripPreservesRepeatStartCountsHiddenCountersAndExactUndo() =
        runTest {
            val project = counters.createProject("Project", CounterUnit.ROUNDS, 1, 200, 6)
            counters.repeatSettings.save(project.id, 6, 11)
            counters.additionalCounters.save(project.id, null, "Visible", true)
            counters.additionalCounters.save(project.id, null, "Hidden")
            val (visible, hidden) = db.additionalCounterDao().getActive(project.id)
            counters.mutate(project.id, CounterMutation.ManualSet(99), hidden.id)
            counters.mutate(project.id, CounterMutation.ManualSet(8))
            counters.additionalCounters.delete(project.id, hidden.id)
            val beforeProject = counters.getProject(project.id)
            val beforeCounters = db.additionalCounterDao().getAll()
            val beforeHistory = db.counterHistoryDao().getAll()
            val beforeEffects = db.counterHistoryEffectDao().getAll()
            val exported = backup.exportJson()
            assertEquals(5, Json.decodeFromString<BackupFile>(exported).schemaVersion)
            counters.mutate(project.id, CounterMutation.Increment)
            val validated = (backup.prepareImport(exported.encodeToByteArray()) as BackupDecodeResult.Valid).backup
            assertTrue(backup.replaceWith(validated) is BackupImportResult.Success)
            assertEquals(beforeProject, counters.getProject(project.id))
            assertEquals(beforeCounters, db.additionalCounterDao().getAll())
            assertEquals(beforeHistory, db.counterHistoryDao().getAll())
            assertEquals(beforeEffects, db.counterHistoryEffectDao().getAll())
            assertEquals(exported, backup.exportJson())
            counters.undo(project.id)
            assertEquals(0L, db.additionalCounterDao().getById(project.id, visible.id)!!.count)
            assertEquals(1L, counters.getProject(project.id)!!.count)
            counters.undo(project.id)
            assertEquals(listOf(visible.id), db.additionalCounterDao().getAll().map { it.id })
        }

    @Test
    fun v2ImportDefaultsRepeatStartAndKeepsCountersAndHistory() =
        runTest {
            val project = counters.createProject("Legacy", CounterUnit.ROWS, 0, null, 8)
            counters.additionalCounters.save(project.id, null, "Extra", true)
            counters.mutate(project.id, CounterMutation.ManualSet(18))
            val current = Json.decodeFromString<BackupFile>(backup.exportJson())
            val v2 = current.copy(schemaVersion = 2, projects = current.projects.map { it.copy(repeatStartCount = null) })
            val decoded = backup.prepareImport(BackupCodec.encode(v2).encodeToByteArray()) as BackupDecodeResult.Valid
            assertTrue(backup.replaceWith(decoded.backup) is BackupImportResult.Success)
            assertEquals(1L, counters.getProject(project.id)?.repeatStartCount)
            assertEquals(18L, counters.getProject(project.id)?.count)
            assertEquals(1, db.additionalCounterDao().getAll().size)
            assertEquals(1, db.counterHistoryDao().getAll().size)
            counters.undo(project.id)
            assertEquals(0L, counters.getProject(project.id)?.count)
        }

    @Test
    fun invalidV3RepeatStartCannotReplaceLiveData() =
        runTest {
            val file = populatedBackup()
            val before = backup.exportJson()
            val invalid = file.copy(projects = file.projects.map { it.copy(repeatLength = 8, repeatStartCount = 0) })
            assertTrue(backup.prepareImport(BackupCodec.encode(invalid).encodeToByteArray()) is BackupDecodeResult.Invalid)
            assertEquals(before, backup.exportJson())
        }

    @Test
    fun v1ImportPreservesOriginalMeaningsAndStartsWithoutCountersOrHistory() =
        runTest {
            val project = BackupProject("old", "Legacy", "ROUNDS", 41, 1, 80, 6, true, 123, 456)
            val file = BackupFile(1, "RowTool", 500, listOf(project))
            val validated = (backup.prepareImport(BackupCodec.encode(file).encodeToByteArray()) as BackupDecodeResult.Valid).backup
            assertTrue(backup.replaceWith(validated) is BackupImportResult.Success)
            val restored = counters.getProject("old")!!
            assertEquals(41L, restored.count)
            assertEquals(1, restored.startValue)
            assertEquals(6, restored.repeatLength)
            assertEquals(1L, restored.repeatStartCount)
            assertEquals(80L, restored.targetCount)
            assertEquals(123L, restored.createdAt)
            assertEquals(456L, restored.updatedAt)
            assertTrue(restored.isArchived)
            assertTrue(db.additionalCounterDao().getAll().isEmpty())
            assertTrue(db.counterHistoryDao().getAll().isEmpty())
        }

    @Test
    fun invalidIdentifiersOwnershipReferencesCountsAndHistoryAreRejectedBeforeReplacement() =
        runTest {
            val file = populatedBackup()
            val counter = file.counters!!.single()
            val history = file.history!!.single()
            val effect = history.effects.single()
            val invalid =
                listOf(
                    file.copy(counters = null),
                    file.copy(history = null),
                    file.copy(counters = listOf(counter, counter)),
                    file.copy(counters = listOf(counter.copy(id = " "))),
                    file.copy(counters = listOf(counter.copy(projectId = "missing"))),
                    file.copy(counters = listOf(counter.copy(count = -1))),
                    file.copy(counters = listOf(counter.copy(name = " "))),
                    file.copy(history = listOf(history, history)),
                    file.copy(history = listOf(history.copy(id = 0))),
                    file.copy(history = listOf(history.copy(projectId = "missing"))),
                    file.copy(history = listOf(history.copy(newCount = 5))),
                    file.copy(history = listOf(history.copy(previousCount = -1))),
                    file.copy(history = listOf(history.copy(changeReason = "UNKNOWN"))),
                    file.copy(history = listOf(history.copy(effects = listOf(effect, effect)))),
                    file.copy(history = listOf(history.copy(effects = listOf(effect.copy(counterId = "missing"))))),
                    file.copy(history = listOf(history.copy(effects = listOf(effect.copy(newCount = 2))))),
                    file.copy(history = listOf(history.copy(effects = listOf(effect.copy(previousCount = 1))))),
                    file.copy(counters = listOf(counter.copy(isDeleted = true)), history = emptyList()),
                )
            val before = backup.exportJson()
            invalid.forEach { candidate ->
                assertTrue(
                    candidate.toString(),
                    backup.prepareImport(BackupCodec.encode(candidate).encodeToByteArray()) is BackupDecodeResult.Invalid,
                )
                assertEquals(before, backup.exportJson())
            }
        }

    @Test
    fun brokenOlderHistoryChainAndOverlongHistoryAreRejected() =
        runTest {
            val file = populatedBackup()
            val latest = file.history!!.single()
            val older = latest.copy(id = latest.id + 1, previousCount = 4, newCount = 5, effects = emptyList())
            val broken = file.copy(history = listOf(latest, older))
            assertTrue(BackupCodec.decode(BackupCodec.encode(broken).encodeToByteArray()) is BackupDecodeResult.Invalid)
            val tooMany = file.copy(history = List(101) { latest.copy(id = it.toLong() + 1) })
            assertTrue(BackupCodec.decode(BackupCodec.encode(tooMany).encodeToByteArray()) is BackupDecodeResult.Invalid)
        }

    @Test
    fun failureDuringImportedEffectInsertRollsBackAllTables() =
        runTest {
            val file = populatedBackup()
            val before = backup.exportJson()
            val validated = (BackupCodec.decode(BackupCodec.encode(file).encodeToByteArray()) as BackupDecodeResult.Valid).backup
            db.openHelper.writableDatabase.execSQL(
                "CREATE TRIGGER fail_import BEFORE INSERT ON counter_history_effects BEGIN SELECT RAISE(ABORT, 'test'); END",
            )
            assertTrue(backup.replaceWith(validated) is BackupImportResult.Failure)
            assertEquals(before, backup.exportJson())
        }

    @Test
    fun exportRejectsOversizedCompleteDataInsteadOfTruncatingOrReportingSuccess() =
        runTest {
            val project = counters.createProject("Project", CounterUnit.ROWS, 0, null, null)
            val counterCount = 30_000
            db.withTransaction {
                repeat(counterCount) {
                    db.additionalCounterDao().insert(AdditionalCounterEntity("counter-$it", project.id, "x".repeat(60), 0, false))
                }
            }
            assertTrue(runCatching { backup.exportJson() }.exceptionOrNull() is IOException)
            assertEquals(counterCount, db.additionalCounterDao().getAll().size)
        }

    @Test
    fun validatedHistoryDoesNotRetainMutableInputLists() =
        runTest {
            val file = populatedBackup()
            val effects =
                file.history!!
                    .single()
                    .effects
                    .toMutableList()
            val history = mutableListOf(file.history.single().copy(effects = effects))
            val counterList = file.counters!!.toMutableList()
            val projects = (BackupCodec.decode(BackupCodec.encode(file).encodeToByteArray()) as BackupDecodeResult.Valid).backup.projects
            val result = (ValidatedBackup.create(100, projects, counterList, history) as BackupDecodeResult.Valid).backup
            effects.clear()
            history.clear()
            counterList.clear()
            assertEquals(1, result.counters.size)
            assertEquals(
                1,
                result.history
                    .single()
                    .effects.size,
            )
            assertFalse(result.counters.single().isDeleted)
        }

    private suspend fun populatedBackup(): BackupFile {
        val project = counters.createProject("Project", CounterUnit.ROWS, 0, null, null)
        counters.additionalCounters.save(project.id, null, "Counter", true)
        counters.mutate(project.id, CounterMutation.Increment)
        return Json.decodeFromString(backup.exportJson())
    }
}
