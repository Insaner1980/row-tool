package com.finnvek.rowtool.ui

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File

internal fun ComposeTestRule.captureDiagnostic(name: String) {
    val directory = File(InstrumentationRegistry.getInstrumentation().targetContext.getExternalFilesDir(null), "diagnostics")
    directory.mkdirs()
    File(directory, "$name-screen.png").outputStream().use {
        InstrumentationRegistry
            .getInstrumentation()
            .uiAutomation
            .takeScreenshot()
            .compress(Bitmap.CompressFormat.PNG, 100, it)
    }
    val roots = onAllNodes(isRoot())
    roots.fetchSemanticsNodes().forEachIndexed { index, node ->
        if (node.size.width == 0 || node.size.height == 0) return@forEachIndexed
        File(directory, "$name-$index.png").outputStream().use {
            roots[index].captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }
    Log.i("RowToolDiagnostic", name)
}
