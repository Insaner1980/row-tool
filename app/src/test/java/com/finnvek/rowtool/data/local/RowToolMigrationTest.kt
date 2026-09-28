package com.finnvek.rowtool.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.finnvek.rowtool.data.repository.CounterRepository
import com.finnvek.rowtool.domain.model.CounterMutation
import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.ReminderValues
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.SQLiteMode
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
@SQLiteMode(SQLiteMode.Mode.NATIVE)
class RowToolMigrationTest {
    @Test
    fun realExportedV1SchemaMigratesWithoutChangingProjectsOrLegacyHistory() =
        runTest {
            val context = ApplicationProvider.getApplicationContext<Context>()
            val name = "migration-test.db"
            context.deleteDatabase(name)
            createLegacyDatabase(context, name)
            try {
                open(context, name).useDatabase { db ->
                    assertEquals(
                        ProjectEntity("active", "Old project", "ROUNDS", 42, 1, 80, 6, false, 100, 200),
                        db.projectDao().getById("active"),
                    )
                    assertEquals(
                        ProjectEntity("archived", "Archived", "ROWS", 99, 0, null, null, true, 300, 400),
                        db.projectDao().getById("archived"),
                    )
                    assertEquals(CounterHistoryEntity(17, "active", 41, 42, "INCREMENT", 200), db.counterHistoryDao().getLatest("active"))
                    assertTrue(db.additionalCounterDao().getAll().isEmpty())
                    assertTrue(db.counterHistoryEffectDao().getAll().isEmpty())
                    val repository = CounterRepository(db)
                    repository.additionalCounters.save("active", null, "Later", true)
                    repository.undo("active")
                    assertEquals(41L, db.projectDao().getById("active")!!.count)
                    assertEquals(
                        0L,
                        db
                            .additionalCounterDao()
                            .getActive("active")
                            .single()
                            .count,
                    )
                }
                open(context, name).useDatabase { db ->
                    assertEquals(41L, db.projectDao().getById("active")!!.count)
                    assertEquals(5, db.openHelper.readableDatabase.version)
                }
            } finally {
                context.deleteDatabase(name)
            }
        }

    @Test
    fun countersHiddenRowsAndUndoSurviveCloseAndReopen() =
        runTest {
            val context = ApplicationProvider.getApplicationContext<Context>()
            val name = "persistence-test.db"
            context.deleteDatabase(name)
            lateinit var projectId: String
            try {
                open(context, name).useDatabase { db ->
                    val repository = CounterRepository(db)
                    projectId = repository.createProject("Persistent", CounterUnit.ROWS, 0, null, 6).id
                    repository.repeatSettings.save(projectId, 6, 11)
                    repository.additionalCounters.save(projectId, null, "Visible", true)
                    repository.additionalCounters.save(projectId, null, "Hidden", true)
                    repository.mutate(projectId, CounterMutation.ManualSet(8))
                    val reminder = repository.reminders.save(projectId, null, null, ReminderValues("Check", 8, null, true))!!
                    assertTrue(repository.reminders.acknowledge(projectId, reminder.id, reminder.revision, 8))
                    repository.additionalCounters.delete(
                        projectId,
                        db
                            .additionalCounterDao()
                            .getActive(projectId)
                            .last()
                            .id,
                    )
                }
                open(context, name).useDatabase { db ->
                    assertEquals(8L, db.projectDao().getById(projectId)!!.count)
                    assertEquals(11L, db.projectDao().getById(projectId)!!.repeatStartCount)
                    assertEquals(
                        8L,
                        db
                            .reminderDao()
                            .getForProject(projectId)
                            .single()
                            .acknowledgedThrough,
                    )
                    assertEquals(2, db.additionalCounterDao().getAll().size)
                    assertEquals(
                        8L,
                        db
                            .additionalCounterDao()
                            .getActive(projectId)
                            .single()
                            .count,
                    )
                    CounterRepository(db).undo(projectId)
                    assertEquals(0L, db.projectDao().getById(projectId)!!.count)
                    assertEquals(
                        0L,
                        db
                            .additionalCounterDao()
                            .getActive(projectId)
                            .single()
                            .count,
                    )
                    assertEquals(1, db.additionalCounterDao().getAll().size)
                }
            } finally {
                context.deleteDatabase(name)
            }
        }

    @Test
    fun projectDeletionCascadesReminders() =
        runTest {
            val context = ApplicationProvider.getApplicationContext<Context>()
            val name = "reminder-cascade-test.db"
            context.deleteDatabase(name)
            try {
                open(context, name).useDatabase { db ->
                    val repository = CounterRepository(db)
                    val project = repository.createProject("Work", CounterUnit.ROWS, 0, null, null)
                    repository.reminders.save(project.id, null, null, ReminderValues("Check", 1, null, true))
                    repository.deleteProject(project.id)
                    assertTrue(db.reminderDao().getAll().isEmpty())
                }
            } finally {
                context.deleteDatabase(name)
            }
        }

    @Test
    fun realExportedV2SchemaMigratesProjectsCountersAndHistory() =
        runTest {
            val context = ApplicationProvider.getApplicationContext<Context>()
            val name = "migration-v2-test.db"
            context.deleteDatabase(name)
            createLegacyDatabase(context, name, 2)
            try {
                open(context, name).useDatabase { db ->
                    val active = db.projectDao().getById("active")!!
                    val archived = db.projectDao().getById("archived")!!
                    assertEquals(1L, active.repeatStartCount)
                    assertEquals(null, archived.repeatStartCount)
                    assertEquals(42L, active.count)
                    assertEquals(200L, active.updatedAt)
                    assertEquals(1, db.additionalCounterDao().getAll().size)
                    assertEquals(1, db.counterHistoryEffectDao().getAll().size)
                    assertEquals(17L, db.counterHistoryDao().getLatest("active")!!.id)
                }
                open(context, name).useDatabase { db ->
                    assertEquals(1L, db.projectDao().getById("active")!!.repeatStartCount)
                    assertEquals(5, db.openHelper.readableDatabase.version)
                }
            } finally {
                context.deleteDatabase(name)
            }
        }

    @Test
    fun realExportedV3SchemaMigratesWithoutChangingExistingData() =
        runTest {
            val context = ApplicationProvider.getApplicationContext<Context>()
            val name = "migration-v3-test.db"
            context.deleteDatabase(name)
            createLegacyDatabase(context, name, 3)
            try {
                open(context, name).useDatabase { db ->
                    assertEquals(42L, db.projectDao().getById("active")!!.count)
                    assertEquals(200L, db.projectDao().getById("active")!!.updatedAt)
                    assertEquals(1L, db.projectDao().getById("active")!!.repeatStartCount)
                    assertEquals(1, db.additionalCounterDao().getAll().size)
                    assertEquals(1, db.counterHistoryEffectDao().getAll().size)
                    assertTrue(db.reminderDao().getAll().isEmpty())
                    assertEquals(5, db.openHelper.readableDatabase.version)
                }
            } finally {
                context.deleteDatabase(name)
            }
        }

    @Test
    fun realV4MigrationPreservesAllRowsAndNoteSurvivesReopen() =
        runTest {
            val context = ApplicationProvider.getApplicationContext<Context>()
            val name = "note-v4-migration.db"
            context.deleteDatabase(name)
            createLegacyDatabase(context, name, 4)
            try {
                val path = context.getDatabasePath(name)
                SQLiteDatabase.openDatabase(path.path, null, SQLiteDatabase.OPEN_READWRITE).use {
                    it.execSQL("INSERT INTO reminders VALUES ('r', 'active', 'Check', 32, 6, 1, 38, 7)")
                }
                open(context, name).useDatabase { db ->
                    assertEquals(5, db.openHelper.readableDatabase.version)
                    assertTrue(db.projectNoteDao().getAll().isEmpty())
                    assertEquals(42L, db.projectDao().getById("active")!!.count)
                    assertEquals(200L, db.projectDao().getById("active")!!.updatedAt)
                    assertEquals(400L, db.projectDao().getById("archived")!!.updatedAt)
                    assertEquals(1L, db.projectDao().getById("active")!!.repeatStartCount)
                    assertEquals(1, db.additionalCounterDao().getAll().size)
                    assertEquals(17L, db.counterHistoryDao().getLatest("active")!!.id)
                    assertEquals(1, db.counterHistoryEffectDao().getAll().size)
                    assertEquals(
                        38L,
                        db
                            .reminderDao()
                            .getAll()
                            .single()
                            .acknowledgedThrough,
                    )
                    assertEquals(
                        7L,
                        db
                            .reminderDao()
                            .getAll()
                            .single()
                            .revision,
                    )
                    CounterRepository(db, clock = { 777L }).notes.save("active", null, " A\n    🧶", true)
                }
                open(context, name).useDatabase { db ->
                    val note = db.projectNoteDao().get("active")!!
                    assertEquals(" A\n    🧶", note.text)
                    assertEquals(42L, note.savedCount)
                    assertEquals(777L, note.savedAt)
                    assertEquals(200L, db.projectDao().getById("active")!!.updatedAt)
                }
            } finally {
                context.deleteDatabase(name)
            }
        }

    private fun createLegacyDatabase(
        context: Context,
        name: String,
        version: Int = 1,
    ) {
        val path = context.getDatabasePath(name)
        path.parentFile!!.mkdirs()
        val schema =
            JSONObject(
                File("schemas/com.finnvek.rowtool.data.local.RowToolDatabase/$version.json").readText(),
            ).getJSONObject("database")
        SQLiteDatabase.openOrCreateDatabase(path, null).use { old ->
            val entities = schema.getJSONArray("entities")
            for (index in 0 until entities.length()) {
                val entity = entities.getJSONObject(index)
                val table = entity.getString("tableName")
                old.execSQL(entity.getString("createSql").replace("\${TABLE_NAME}", table))
                val indices = entity.getJSONArray("indices")
                for (i in 0 until indices.length()) {
                    old.execSQL(
                        indices.getJSONObject(i).getString("createSql").replace("\${TABLE_NAME}", table),
                    )
                }
            }
            val setup = schema.getJSONArray("setupQueries")
            for (i in 0 until setup.length()) old.execSQL(setup.getString(i))
            if (version >= 3) {
                old.execSQL("INSERT INTO projects VALUES ('active', 'Old project', 'ROUNDS', 42, 1, 80, 6, 0, 100, 200, 1)")
                old.execSQL("INSERT INTO projects VALUES ('archived', 'Archived', 'ROWS', 99, 0, NULL, NULL, 1, 300, 400, NULL)")
            } else {
                old.execSQL("INSERT INTO projects VALUES ('active', 'Old project', 'ROUNDS', 42, 1, 80, 6, 0, 100, 200)")
                old.execSQL("INSERT INTO projects VALUES ('archived', 'Archived', 'ROWS', 99, 0, NULL, NULL, 1, 300, 400)")
            }
            old.execSQL("INSERT INTO counter_history VALUES (17, 'active', 41, 42, 'INCREMENT', 200)")
            if (version >= 2) {
                old.execSQL("INSERT INTO additional_counters VALUES ('extra', 'active', 'Rows', 5, 1, 0)")
                old.execSQL("INSERT INTO counter_history_effects VALUES (17, 'extra', 4, 5)")
            }
            old.version = version
        }
    }

    private fun open(
        context: Context,
        name: String,
    ): RowToolDatabase =
        Room
            .databaseBuilder(context, RowToolDatabase::class.java, name)
            .addMigrations(
                RowToolDatabase.MIGRATION_1_2,
                RowToolDatabase.MIGRATION_2_3,
                RowToolDatabase.MIGRATION_3_4,
                RowToolDatabase.MIGRATION_4_5,
            ).allowMainThreadQueries()
            .build()
}

private inline fun <T> RowToolDatabase.useDatabase(block: (RowToolDatabase) -> T): T =
    try {
        block(this)
    } finally {
        close()
    }
