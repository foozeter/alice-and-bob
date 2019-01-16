package com.foureyedstraighthair.aliceandbob.console.programming.type

interface Type {

    fun toLiteral(): String
    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int
}