package com.foureyedstraighthair.aliceandbob.console.programming

import com.foureyedstraighthair.aliceandbob.console.programming.model.Instruction
import com.foureyedstraighthair.aliceandbob.console.programming.model.Let

class Interpreter(
    private val scriptProvider: ScriptProvider) {

    fun interpret(instruction: Instruction, process: Environment.Process) {
        throw IllegalArgumentException("unknown instruction")
    }

    fun interpret(instruction: Let, process: Environment.Process) {
    }
}