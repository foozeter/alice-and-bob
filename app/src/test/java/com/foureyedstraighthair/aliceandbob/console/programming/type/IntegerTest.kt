package com.foureyedstraighthair.aliceandbob.console.programming.type

import org.junit.Test

import org.junit.Assert.*

class IntegerTest {

    @Test
    fun toLiteral() {
        assertEquals("100", Integer(100).toLiteral())
    }

    @Test
    fun inflate() {
        assertEquals(Integer(100), Integer.inflate("100"))
        assertEquals(null, Integer.inflate("3.14"))
    }
}