package com.finnvek.rowtool.ui.screens.projects

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProjectEditorInputValidationTest {
    @Test
    fun validInputIsNormalizedForSaving() {
        val validation =
            validateProjectEditorInput(
                name = "  Project  ",
                targetEnabled = true,
                targetText = "120",
                repeatEnabled = true,
                repeatText = "6",
            )

        assertEquals("Project", validation.name)
        assertEquals(120L, validation.targetCount)
        assertEquals(6, validation.repeatLength)
        assertTrue(validation.nameValid)
        assertTrue(validation.targetValid)
        assertTrue(validation.repeatValid)
        assertTrue(validation.canSave)
    }

    @Test
    fun disabledOptionalValuesAreIgnored() {
        val validation =
            validateProjectEditorInput(
                name = "Project",
                targetEnabled = false,
                targetText = "invalid",
                repeatEnabled = false,
                repeatText = "invalid",
            )

        assertNull(validation.targetCount)
        assertNull(validation.repeatLength)
        assertTrue(validation.targetValid)
        assertTrue(validation.repeatValid)
        assertTrue(validation.canSave)
    }

    @Test
    fun invalidEnabledValuesPreventSaving() {
        val validation =
            validateProjectEditorInput(
                name = " ",
                targetEnabled = true,
                targetText = "0",
                repeatEnabled = true,
                repeatText = "1",
            )

        assertFalse(validation.nameValid)
        assertFalse(validation.targetValid)
        assertFalse(validation.repeatValid)
        assertFalse(validation.canSave)
    }
}
