package com.finnvek.rowtool.test

import android.util.AtomicFile
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption.ATOMIC_MOVE
import java.nio.file.StandardCopyOption.REPLACE_EXISTING

/** Supplies Android's replacing rename semantics on Windows; all other AtomicFile code stays real. */
@Implements(AtomicFile::class)
@Suppress("UtilityClassWithPublicConstructor") // Robolectric requires a public no-argument shadow constructor.
class ShadowAtomicFile {
    companion object {
        @JvmStatic
        @Implementation(minSdk = 30)
        protected fun rename(
            source: File,
            target: File,
        ) {
            if (target.isDirectory) Files.delete(target.toPath())
            // No delete-then-rename gap or non-atomic fallback. Host I/O failures must fail the test.
            Files.move(source.toPath(), target.toPath(), ATOMIC_MOVE, REPLACE_EXISTING)
        }
    }
}
