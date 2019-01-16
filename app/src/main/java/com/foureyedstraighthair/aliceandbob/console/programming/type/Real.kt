package com.foureyedstraighthair.aliceandbob.console.programming.type

class Real(private var value: Double): Type {

    companion object {

        fun inflate(literal: String): Real? {
            if (!literal.contains('.')) return null
            val inflated = literal.toDoubleOrNull()
            inflated ?: return null
            return Real(inflated)
        }
    }

    override fun toLiteral()
            = value.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Real

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }


}