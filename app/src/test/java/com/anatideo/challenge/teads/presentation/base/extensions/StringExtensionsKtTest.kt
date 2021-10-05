package com.anatideo.challenge.teads.presentation.base.extensions

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test


class StringExtensionsKtTest {

    @Test
    fun `when checking if a string is numeric then it is`() {
        // Given
        val value = "1"

        // When Then
        assertTrue(value.isNumeric())
    }

    @Test
    fun `when checking if a string is numeric then it is not`() {
        // Given
        val value = "1s"

        // When Then
        assertFalse(value.isNumeric())
    }
}