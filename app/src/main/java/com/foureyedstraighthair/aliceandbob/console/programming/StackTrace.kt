package com.foureyedstraighthair.aliceandbob.console.programming

import com.foureyedstraighthair.aliceandbob.console.programming.model.Instruction

class StackTrace {

    val frames = mutableListOf<Frame>()

    var unexpectedError: UnexpectedError? = null

    val wasSuccessful; get() = unexpectedError != null

    fun organize(): Map<Long, List<Frame>>
            = mutableMapOf<Long, MutableList<Frame>> {
        frames.forEach {
            getOrPut(it.processId) { mutableListOf() }.add(it)
        }
    }

    fun addFrame(
        instruction: Instruction,
        process: Environment.Process)
            = frames.add(Frame(
        process.id,
        instruction,
        process.environment.makeUserMemorySnapshot(process),
        process.environment.makeArgsMemorySnapshot(process),
        process.environment.makeSystemMemorySnapShot()))

    data class Frame(
        val processId: Long,
        val instruction: Instruction,
        val userMemorySnapshot: Map<String, String>,
        val argsMemorySnapshot: Map<String, String>,
        val systemMemorySnapshot: Map<String, String>)

    private fun <T, U> mutableMapOf(initialization: MutableMap<T, U>.() -> Unit)
            = mutableMapOf<T, U>().apply { initialization(this) }
}