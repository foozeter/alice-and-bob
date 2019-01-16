package com.foureyedstraighthair.aliceandbob.console.programming

import org.junit.Assert.*
import org.junit.Test

class VariableNameTest {

    @Test
    fun from() {
        val user = VariableName.from("\$user")
        val system = VariableName.from("&system")
        val arg = VariableName.from("@arg")
        val illegal = VariableName.from("illegal")

        assertEquals("${VariableName.Type.USER.prefix}user", user.toString())
        assertEquals("user", user.simpleName)
        assertEquals(VariableName.Type.USER, user.type)

        assertEquals("${VariableName.Type.SYSTEM.prefix}system", system.toString())
        assertEquals("system", system.simpleName)
        assertEquals(VariableName.Type.SYSTEM, system.type)

        assertEquals("${VariableName.Type.ARGUMENT.prefix}arg", arg.toString())
        assertEquals("arg", arg.simpleName)
        assertEquals(VariableName.Type.ARGUMENT, arg.type)

        assertEquals("illegal", illegal.toString())
        assertEquals("illegal", illegal.simpleName)
        assertEquals(VariableName.Type.UNKNOWN, illegal.type)
    }

    @Test
    fun userVariable() {
        val user = VariableName.userVariable("user")
        assertEquals("${VariableName.Type.USER.prefix}user", user.toString())
        assertEquals("user", user.simpleName)
        assertEquals(VariableName.Type.USER, user.type)
    }

    @Test
    fun systemVariable() {
        val system = VariableName.systemVariable("system")
        assertEquals("${VariableName.Type.SYSTEM.prefix}system", system.toString())
        assertEquals("system", system.simpleName)
        assertEquals(VariableName.Type.SYSTEM, system.type)
    }

    @Test
    fun argumentVariable() {
        val arg = VariableName.argumentVariable("arg")
        assertEquals("${VariableName.Type.ARGUMENT.prefix}arg", arg.toString())
        assertEquals("arg", arg.simpleName)
        assertEquals(VariableName.Type.ARGUMENT, arg.type)
    }
}