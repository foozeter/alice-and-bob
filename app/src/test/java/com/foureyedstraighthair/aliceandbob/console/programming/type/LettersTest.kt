package com.foureyedstraighthair.aliceandbob.console.programming.type

import org.junit.Test

import org.junit.Assert.*

class LettersTest {

    @Test
    fun toLiteral() {
        assertEquals("'abc'def'", Letters("abc'def").toLiteral())
    }

    @Test
    fun inflate() {
        assertEquals(Letters("abc'def"), Letters.inflate("'abc'def'"))
        assertEquals(null, Letters.inflate("abc'def"))
    }
}