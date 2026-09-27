package com.finnvek.rowtool.widget

import android.database.SQLException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class WidgetBindingsTest {
    @get:Rule val folder = TemporaryFolder()

    @Test fun independentBindingsRejectOldActionsAndSurviveRestart() =
        runTest {
            val file = folder.newFile()
            file.delete()
            val store = WidgetBindings(file)
            val first = store.bind(10, "a")
            store.bind(11, "b")
            assertEquals("a", store.withBinding(10, first.token) { it })
            val next = store.bind(10, "b")
            assertNotEquals(first.token, next.token)
            assertNull(store.withBinding(10, first.token) { it })
            assertEquals("b", WidgetBindings(file).read(11)?.projectId)
            store.remove(10)
            assertNull(store.read(10))
            assertEquals("b", store.read(11)?.projectId)
        }

    @Test fun replacementInvalidatesEvenIdenticalIdsAndRollbackPreservesBinding() =
        runTest {
            val store = WidgetBindings(folder.root.resolve("state"))
            val binding = store.bind(10, "same/id")
            try {
                store.replaceDatabase { throw SQLException("rolled back") }
            } catch (_: SQLException) {
                // The Room transaction is known to have rolled back.
            }
            assertEquals(binding, store.read(10))
            store.replaceDatabase { Unit }
            assertNull(store.withBinding(10, binding.token) { it })
            assertNull(store.read(10))
        }

    @Test fun interruptedReplacementFailsClosedAcrossRestart() =
        runTest {
            val file = folder.root.resolve("state")
            val store = WidgetBindings(file)
            val old = store.bind(10, "a")
            try {
                store.replaceDatabase { throw IllegalStateException("unknown outcome") }
            } catch (_: IllegalStateException) {
                // Simulates termination after the durable pending marker.
            }
            val restarted = WidgetBindings(file)
            assertNull(restarted.withBinding(10, old.token) { it })
            val fresh = restarted.bind(10, "a")
            assertNotEquals(old.token, fresh.token)
            assertEquals("a", restarted.withBinding(10, fresh.token) { it })
        }

    @Test fun reconfigurationCannotInterleaveValidatedAction() =
        runTest {
            val store = WidgetBindings(folder.root.resolve("state"))
            val old = store.bind(10, "a")
            val entered = CompletableDeferred<Unit>()
            val release = CompletableDeferred<Unit>()
            val action =
                async {
                    store.withBinding(10, old.token) {
                        entered.complete(Unit)
                        release.await()
                        it
                    }
                }
            entered.await()
            val rebind = async { store.bind(10, "b") }
            release.complete(Unit)
            assertEquals("a", action.await())
            rebind.await()
            assertNull(store.withBinding(10, old.token) { it })
        }

    @Test fun queuedOldActionIsRejectedAfterReplacement() =
        runTest {
            val store = WidgetBindings(folder.root.resolve("state"))
            val binding = store.bind(1, "same")
            val entered = CompletableDeferred<Unit>()
            val finish = CompletableDeferred<Unit>()
            val replacement =
                async {
                    store.replaceDatabase {
                        entered.complete(Unit)
                        finish.await()
                    }
                }
            entered.await()
            val action = async { store.withBinding(1, binding.token) { error("Old action executed") } }
            finish.complete(Unit)
            replacement.await()
            assertNull(action.await())
        }

    @Test fun failedReadsAndWritesAreNotConvertedToEmptySuccess() =
        runTest {
            val corrupt = folder.newFile("corrupt")
            corrupt.writeText("not XML")
            val readFailure = runCatching { WidgetBindings(corrupt).read(1) }.exceptionOrNull()
            org.junit.Assert.assertTrue(readFailure is java.io.IOException)
            val notDirectory = folder.newFile("not-directory")
            val writeFailure = runCatching { WidgetBindings(java.io.File(notDirectory, "state")).bind(1, "a") }.exceptionOrNull()
            org.junit.Assert.assertTrue(writeFailure is java.io.IOException)
        }
}
