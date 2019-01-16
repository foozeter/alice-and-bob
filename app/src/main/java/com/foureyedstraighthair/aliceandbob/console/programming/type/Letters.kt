package com.foureyedstraighthair.aliceandbob.console.programming.type

class Letters(private var value: String): Type {

    companion object {

        fun inflate(literal: String): Letters?
                = when {

            literal.length < 2
            -> null

            literal.first() == '\'' && literal.last() == '\''
            -> Letters(literal.substring(1, literal.length - 1))

            else
            -> null
        }
    }

    override fun toLiteral()
            = "'$value'"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Letters

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }


}