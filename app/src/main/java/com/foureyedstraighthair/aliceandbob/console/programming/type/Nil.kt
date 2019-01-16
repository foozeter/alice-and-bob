package com.foureyedstraighthair.aliceandbob.console.programming.type

object Nil: Type {

    override fun toLiteral() = "nil"

    override fun equals(other: Any?)
            = super.equals(other)

    override fun hashCode()
            = super.hashCode()
}