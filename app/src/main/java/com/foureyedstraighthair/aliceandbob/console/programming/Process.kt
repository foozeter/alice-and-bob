package com.foureyedstraighthair.aliceandbob.console.programming

import com.foureyedstraighthair.aliceandbob.console.programming.model.Script

class Process(
    val id: Long,
    val script: Script,
    val environment: Environment,
    val parent: Process?) {

//    private val interpreter = Interpreter(environment)

    companion object {
        private const val STEP_NOT_RUNNING = -1
    }

    var step: Int = STEP_NOT_RUNNING
        get() = Math.min(script.instructions.size, field)

    fun executeNextStep() {
        if (step.isValid()) {
//            interpreter.interpret(script.instructions[step])
        }
    }

    private fun Int.isValid()
            = (0 until script.instructions.size).contains(this)
}