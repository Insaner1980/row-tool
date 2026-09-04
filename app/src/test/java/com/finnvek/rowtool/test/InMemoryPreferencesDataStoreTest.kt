package com.finnvek.rowtool.test

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.mutablePreferencesOf
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InMemoryPreferencesDataStoreTest {
    @Test
    fun concurrentUpdatesAreSerialized() =
        runTest {
            val dataStore = InMemoryPreferencesDataStore()
            val firstTransformStarted = CompletableDeferred<Unit>()
            val releaseFirstTransform = CompletableDeferred<Unit>()

            val firstUpdate =
                async {
                    dataStore.updateData { preferences ->
                        firstTransformStarted.complete(Unit)
                        releaseFirstTransform.await()
                        mutablePreferencesOf(COUNTER to (preferences[COUNTER] ?: 0) + 1)
                    }
                }
            runCurrent()
            firstTransformStarted.await()

            val secondUpdate =
                async {
                    dataStore.updateData { preferences ->
                        mutablePreferencesOf(COUNTER to (preferences[COUNTER] ?: 0) + 1)
                    }
                }
            runCurrent()
            releaseFirstTransform.complete(Unit)
            awaitAll(firstUpdate, secondUpdate)

            assertEquals(2, dataStore.data.first()[COUNTER])
        }

    private companion object {
        val COUNTER = intPreferencesKey("counter")
    }
}
