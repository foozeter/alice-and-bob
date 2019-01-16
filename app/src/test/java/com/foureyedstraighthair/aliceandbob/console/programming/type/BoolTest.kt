package com.foureyedstraighthair.aliceandbob.console.programming.type

import org.junit.Test

import org.junit.Assert.*

class BoolTest {

    @Test
    fun toLiteral() {
        assertEquals("true", Bool.True().toLiteral())
        assertEquals("false", Bool.False().toLiteral())
    }

    @Test
    fun inflate() {
        assertEquals(Bool.True(), Bool.inflate("true"))
        assertEquals(Bool.False(), Bool.inflate("false"))
        assertEquals(Bool.True(), Bool.inflate("TRUE"))
        assertEquals(Bool.False(), Bool.inflate("FALSE"))
        assertEquals(null, Bool.inflate("'true'"))
    }
}