package com.foureyedstraighthair.aliceandbob.console.programming.type

class Bool(private var value: Boolean): Type {

    companion object {

        fun True() = Bool(true)

        fun False() = Bool(false)

        fun inflate(literal: String): Bool?
                = when(literal.toLowerCase()) {
            "true" -> Bool.True()
            "false" -> Bool.False()
            else -> null
        }

    }

    override fun toLiteral()
            = value.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bool

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }


}