package com.finnvek.rowtool.data.repository

import android.database.SQLException
import androidx.room.withTransaction
import com.finnvek.rowtool.data.local.AdditionalCounterEntity
import com.finnvek.rowtool.data.local.CounterHistoryEffectEntity
import com.finnvek.rowtool.data.local.CounterHistoryEntity
import com.finnvek.rowtool.data.local.ProjectNoteEntity
import com.finnvek.rowtool.data.local.ReminderEntity
import com.finnvek.rowtool.data.local.RowToolDatabase
import com.finnvek.rowtool.data.local.toEntity
import com.finnvek.rowtool.data.preferences.PreferencesRepository
import com.finnvek.rowtool.domain.model.CounterProject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.util.UUID

class BackupRepository(
    private val database: RowToolDatabase,
    private val preferencesRepository: PreferencesRepository,
    private val clock: () -> Long = System::currentTimeMillis,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val replaceTransaction: suspend (suspend () -> Unit) -> Unit = { it() },
) {
    suspend fun exportJson(): String =
        withContext(ioDispatcher) {
            val snapshot =
                database.withTransaction {
                    val effects = database.counterHistoryEffectDao().getAll().groupBy { it.historyId }
                    BackupFile(
                        schemaVersion = BackupFormat.CURRENT_SCHEMA_VERSION,
                        application = BackupFormat.APPLICATION_ID,
                        exportedAt = clock(),
                        projects =
                            database.projectDao().getAll().map { entity ->
                                BackupProject(
                                    id = entity.id,
                                    name = entity.name,
                                    counterUnit = entity.counterUnit,
                                    count = entity.count,
                                    startValue = entity.startValue,
                                    targetCount = entity.targetCount,
                                    repeatLength = entity.repeatLength,
                                    repeatStartCount = entity.repeatStartCount,
                                    isArchived = entity.isArchived,
                                    createdAt = entity.createdAt,
                                    updatedAt = entity.updatedAt,
                                )
                            },
                        counters =
                            database.additionalCounterDao().getAll().map {
                                BackupCounter(it.id, it.projectId, it.name, it.count, it.followsMain, it.isDeleted)
                            },
                        history =
                            database.counterHistoryDao().getAll().map {
                                BackupHistory(
                                    it.id,
                                    it.projectId,
                                    it.previousCount,
                                    it.newCount,
                                    it.changeReason,
                                    it.createdAt,
                                    effects[it.id].orEmpty().map { effect ->
                                        BackupCounterEffect(effect.counterId, effect.previousCount, effect.newCount)
                                    },
                                )
                            },
                        reminders = database.reminderDao().getAll().map(ReminderEntity::toBackupReminder),
                        notes = database.projectNoteDao().getAll().map { BackupNote(it.projectId, it.text, it.savedAt, it.savedCount) },
                    )
                }
            val encoded = BackupCodec.encode(snapshot)
            if (BackupCodec.decode(encoded.encodeToByteArray()) !is BackupDecodeResult.Valid) {
                throw IOException("Backup exceeds the import limit or contains invalid data")
            }
            encoded
        }

    fun prepareImport(bytes: ByteArray): BackupDecodeResult = BackupCodec.decode(bytes)

    suspend fun prepareImport(input: InputStream): BackupDecodeResult =
        withContext(ioDispatcher) {
            BackupCodec.decode(input)
        }

    suspend fun replaceWith(backup: ValidatedBackup): BackupImportResult =
        withContext(ioDispatcher) {
            try {
                replaceTransaction {
                    database.withTransaction {
                        database.counterHistoryDao().deleteAll()
                        database.projectDao().deleteAll()
                        database.projectDao().insertAll(backup.projects.map(CounterProject::toEntity))
                        backup.notes.forEach {
                            database.projectNoteDao().insert(
                                ProjectNoteEntity(it.projectId, UUID.randomUUID().toString(), it.text, it.savedAt, it.savedCount),
                            )
                        }
                        backup.counters.forEach {
                            database.additionalCounterDao().insert(
                                AdditionalCounterEntity(it.id, it.projectId, it.name, it.count, it.followsMain, it.isDeleted),
                            )
                        }
                        backup.history.forEach {
                            database.counterHistoryDao().insert(
                                CounterHistoryEntity(it.id, it.projectId, it.previousCount, it.newCount, it.changeReason, it.createdAt),
                            )
                            database.counterHistoryEffectDao().insertAll(
                                it.effects.map { effect ->
                                    CounterHistoryEffectEntity(it.id, effect.counterId, effect.previousCount, effect.newCount)
                                },
                            )
                        }
                        backup.reminders.forEach {
                            database.reminderDao().insert(
                                ReminderEntity(
                                    it.id,
                                    it.projectId,
                                    it.message,
                                    it.firstCount,
                                    it.intervalCount,
                                    it.enabled,
                                    it.acknowledgedThrough,
                                    it.revision,
                                ),
                            )
                        }
                        database.copyGeneration = UUID.randomUUID().toString()
                    }
                }
            } catch (_: IOException) {
                return@withContext BackupImportResult.Failure(BackupImportFailure.DATABASE_WRITE_FAILED)
            } catch (_: SQLException) {
                return@withContext BackupImportResult.Failure(BackupImportFailure.DATABASE_WRITE_FAILED)
            }

            val lastActiveProjectId = database.projectDao().getMostRecentlyUpdatedActive()?.id
            try {
                preferencesRepository.setLastActiveProjectId(lastActiveProjectId)
            } catch (_: IOException) {
                // The project replacement is already committed; startup resolution repairs stale selection.
            }
            BackupImportResult.Success(
                projectCount = backup.projects.size,
                lastActiveProjectId = lastActiveProjectId,
            )
        }
}

private fun ReminderEntity.toBackupReminder(): BackupReminder =
    BackupReminder(id, projectId, message, firstCount, intervalCount, enabled, acknowledgedThrough, revision)
