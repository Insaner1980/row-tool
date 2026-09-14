package com.finnvek.rowtool.ui

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.text.MultiParagraph
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Constraints
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

internal fun SemanticsNodeInteraction.assertTextFits(): SemanticsNodeInteraction {
    val layouts = mutableListOf<TextLayoutResult>()
    performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
    assertTrue("Text layout must be available", layouts.isNotEmpty())
    layouts.forEach { layout ->
        // Compose 1.12.1's simple Text semantics rebuilds MultiParagraph at the parent's
        // maximum width, but returns the measured text size. Re-layout at that actual size.
        val paragraph =
            MultiParagraph(
                intrinsics = layout.multiParagraph.intrinsics,
                constraints = Constraints(maxWidth = layout.size.width),
                maxLines = layout.layoutInput.maxLines,
                overflow = layout.layoutInput.overflow,
            )
        val measured = TextLayoutResult(layout.layoutInput, paragraph, layout.size)
        assertFalse("Clipped text: ${layout.layoutInput.text}", measured.hasVisualOverflow)
        for (line in 0 until measured.lineCount) {
            assertFalse("Ellipsized text: ${layout.layoutInput.text}", measured.isLineEllipsized(line))
        }
    }
    return this
}
