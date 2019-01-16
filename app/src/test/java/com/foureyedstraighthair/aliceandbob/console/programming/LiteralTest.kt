package com.foureyedstraighthair.aliceandbob.console.programming

import org.junit.Assert.*
import org.junit.Test

class LiteralTest {

    @Test
    fun test() {
        val int = Literal("100")
        val real = Literal("3.14")
        val letters = Literal("'abc'def'")
        val bool_true = Literal("true")
        val bool_false = Literal("false")
        val invalid = Literal("abc'def")

        assertEquals(100L, int.resolved)
        assertEquals(DataType.INT, int.type)

        assertEquals(3.14, real.resolved)
        assertEquals(DataType.REAL, real.type)

        assertEquals("abc'def", letters.resolved)
        assertEquals(DataType.LETTERS, letters.type)

        assertEquals(true, bool_true.resolved)
        assertEquals(DataType.BOOL, bool_true.type)

        assertEquals(false, bool_false.resolved)
        assertEquals(DataType.BOOL, bool_false.type)

        assertEquals(null, invalid.resolved)
        assertEquals(DataType.UNKNOWN, invalid.type)
    }
}