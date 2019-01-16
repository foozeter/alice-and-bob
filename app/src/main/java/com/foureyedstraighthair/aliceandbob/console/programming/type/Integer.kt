package com.foureyedstraighthair.aliceandbob.console.programming.type

class Integer(private var value: Long): Type {

    companion object {

        fun inflate(literal: String): Integer? {
            val inflated = literal.toLongOrNull()
            inflated ?: return null
            return Integer(inflated)
        }
    }

    override fun toLiteral()
            = value.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Integer

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }


}