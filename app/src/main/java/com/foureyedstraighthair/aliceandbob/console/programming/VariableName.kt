package com.foureyedstraighthair.aliceandbob.console.programming

class VariableName private constructor(
    val simpleName: String,
    val type: Type) {

    companion object {

        const val SYSTEM_VARIABLE_PREFIX = '$'
        const val ARGUMENT_VARIABLE_PREFIX = '@'

        fun userVariable(simpleName: String)
                = VariableName(simpleName, Type.USER)

        fun systemVariable(simpleName: String)
                = VariableName(simpleName, Type.SYSTEM)

        fun argumentVariable(simpleName: String)
                = VariableName(simpleName, Type.ARGUMENT)

        fun inflate(name: String) = when {
            name.isEmpty() -> VariableName(name, Type.USER)
            else -> when (name.first()) {
                SYSTEM_VARIABLE_PREFIX -> VariableName(name.excludeFirst(), Type.SYSTEM)
                ARGUMENT_VARIABLE_PREFIX -> VariableName(name.excludeFirst(), Type.ARGUMENT)
                else -> VariableName(name, Type.USER)
            }
        }

        private fun String.excludeFirst()
                = if (length < 2) "" else substring(1)
    }

    enum class Type {
        USER, SYSTEM, ARGUMENT
    }

    fun toLiteral() = when (type) {
        Type.USER -> simpleName
        Type.SYSTEM -> "$SYSTEM_VARIABLE_PREFIX$simpleName"
        Type.ARGUMENT -> "$ARGUMENT_VARIABLE_PREFIX$simpleName"
    }

    override fun toString()
            = toLiteral()
}