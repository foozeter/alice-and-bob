package com.foureyedstraighthair.aliceandbob.console.programming.type

import org.junit.Test

import org.junit.Assert.*

class RealTest {

    @Test
    fun toLiteral() {
        assertEquals("3.14", Real(3.14).toLiteral())
    }

    @Test
    fun inflate() {
        assertEquals(Real(3.14), Real.inflate("3.14"))
        assertEquals(null, Real.inflate("3"))
        assertEquals(null, Real.inflate("abc"))
    }
}