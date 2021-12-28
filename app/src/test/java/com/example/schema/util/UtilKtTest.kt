package com.example.schema.util

import org.junit.Assert.*
import org.junit.Test

class UtilKtTest{

    @Test
    fun `valid string without e, returns the truncated string to the selected place`(){
        val testString = "4.23232"

        assertEquals(formatAfterDot(testString, 6), "4.23232")
        assertEquals(formatAfterDot(testString, 5), "4.23232")
        assertEquals(formatAfterDot(testString, 4), "4.2323")
        assertEquals(formatAfterDot(testString, 3), "4.232")
        assertEquals(formatAfterDot(testString, 1), "4.2")
    }

    @Test
    fun `string with e, returns the same string`(){
        val testString = "4.23232e2332"

        assertEquals(formatAfterDot(testString, 6), testString)
        assertEquals(formatAfterDot(testString, 5), testString)
        assertEquals(formatAfterDot(testString, 4), testString)
        assertEquals(formatAfterDot(testString, 3), testString)
        assertEquals(formatAfterDot(testString, 1), testString)
    }
}