package com.foureyedstraighthair.aliceandbob.console.programming

import org.junit.Assert.*
import org.junit.Test

class VariableNameTest {

    @Test
    fun inflate() {
        val user = VariableName.inflate("user")
        val system = VariableName.inflate("\$system")
        val arg = VariableName.inflate("@arg")
        val empty = VariableName.inflate("")

        assertEquals("user", user.toString())
        assertEquals("user", user.simpleName)
        assertEquals(VariableName.Type.USER, user.type)

        assertEquals("${VariableName.SYSTEM_VARIABLE_PREFIX}system", system.toString())
        assertEquals("system", system.simpleName)
        assertEquals(VariableName.Type.SYSTEM, system.type)

        assertEquals("${VariableName.ARGUMENT_VARIABLE_PREFIX}arg", arg.toString())
        assertEquals("arg", arg.simpleName)
        assertEquals(VariableName.Type.ARGUMENT, arg.type)

        assertEquals("", empty.toString())
        assertEquals("", empty.simpleName)
        assertEquals(VariableName.Type.USER, empty.type)
    }

    @Test
    fun userVariable() {
        val user = VariableName.userVariable("user")
        assertEquals("user", user.toString())
        assertEquals("user", user.simpleName)
        assertEquals(VariableName.Type.USER, user.type)
    }

    @Test
    fun systemVariable() {
        val system = VariableName.systemVariable("system")
        assertEquals("${VariableName.SYSTEM_VARIABLE_PREFIX}system", system.toString())
        assertEquals("system", system.simpleName)
        assertEquals(VariableName.Type.SYSTEM, system.type)
    }

    @Test
    fun argumentVariable() {
        val arg = VariableName.argumentVariable("arg")
        assertEquals("${VariableName.ARGUMENT_VARIABLE_PREFIX}arg", arg.toString())
        assertEquals("arg", arg.simpleName)
        assertEquals(VariableName.Type.ARGUMENT, arg.type)
    }
}