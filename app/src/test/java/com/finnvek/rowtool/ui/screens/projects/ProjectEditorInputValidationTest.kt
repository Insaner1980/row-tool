package com.finnvek.rowtool.ui.screens.projects

import com.finnvek.rowtool.domain.model.CounterUnit
import com.finnvek.rowtool.domain.model.ProjectValidation
import com.finnvek.rowtool.domain.model.ProjectValidationResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProjectEditorInputValidationTest {
    @Test
    fun nameBoundariesMatchDomainValidation() {
        listOf(" \t" to false, "  Project  " to true, "🧶".repeat(60) to true, "🧶".repeat(61) to false)
            .forEach { (name, expectedValid) ->
                val editor = validateProjectEditorInput(name, false, "", false, "")
                val domain = ProjectValidation.validate(name, CounterUnit.ROWS, 0, 0, null, null)

                assertEquals(expectedValid, editor.nameValid)
                assertEquals(expectedValid, editor.canSave)
                assertEquals(expectedValid, domain is ProjectValidationResult.Valid)
                if (domain is ProjectValidationResult.Valid) {
                    assertEquals(domain.value.name, editor.name)
                }
            }
    }

    @Test
    fun optionalValueBoundariesMatchDomainValidation() {
        listOf(0L to false, 1L to true, 999_999L to true, 1_000_000L to false).forEach { (target, expectedValid) ->
            val editor = validateProjectEditorInput("Project", true, target.toString(), false, "")
            val domain = ProjectValidation.validate("Project", CounterUnit.ROWS, 200, 0, target, null)

            assertEquals(expectedValid, editor.targetValid)
            assertEquals(expectedValid, editor.canSave)
            assertEquals(expectedValid, domain is ProjectValidationResult.Valid)
        }
        listOf(1 to false, 2 to true, 999 to true, 1_000 to false).forEach { (repeat, expectedValid) ->
            val editor = validateProjectEditorInput("Project", false, "", true, repeat.toString())
            val domain = ProjectValidation.validate("Project", CounterUnit.ROWS, 0, 0, null, repeat)

            assertEquals(expectedValid, editor.repeatValid)
            assertEquals(expectedValid, editor.canSave)
            assertEquals(expectedValid, domain is ProjectValidationResult.Valid)
        }
    }

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

    @Test
    fun invalidEnabledTargetAlonePreventsSaving() {
        listOf("", "0", "1000000", "12x").forEach { target ->
            val validation = validateProjectEditorInput("Project", true, target, false, "")

            assertTrue(validation.nameValid)
            assertFalse(validation.targetValid)
            assertTrue(validation.repeatValid)
            assertFalse(validation.canSave)
        }
    }

    @Test
    fun invalidEnabledRepeatAlonePreventsSaving() {
        listOf("", "1", "1000", "6x").forEach { repeat ->
            val validation = validateProjectEditorInput("Project", false, "", true, repeat)

            assertTrue(validation.nameValid)
            assertTrue(validation.targetValid)
            assertFalse(validation.repeatValid)
            assertFalse(validation.canSave)
        }
    }
}
