package com.foureyedstraighthair.aliceandbob.console.programming

import com.foureyedstraighthair.aliceandbob.console.programming.model.Instruction
import com.foureyedstraighthair.aliceandbob.console.programming.model.Invoke
import kotlin.IllegalArgumentException

private typealias ProcessId = Long
private typealias MemoryReference = Pair<ProcessId, VariableName>
private typealias Memory<T> = MutableMap<String, T>
private typealias OrganizedMemory<T> = MutableMap<ProcessId, Memory<T>>

class Environment(scriptProvider: ScriptProvider) {

    private val systemMemory: Memory<Any> = mutableMapOf()
    private val userMemory: OrganizedMemory<Any> = mutableMapOf()
    private val argsMemory: OrganizedMemory<MemoryReference> = mutableMapOf()
    private val processIdProvider = ProcessIdProvider()
    private val interpreter = Interpreter(scriptProvider)

    fun runScript(scriptName: String, fileName: String, args: Map<String, Any>)
            = RootProcess(scriptName, fileName, args).run()

    fun makeUserMemorySnapshot(process: Process): Map<String, String>
            = mutableMapOf<String, String>().apply {
        userMemory[process.id]?.forEach {
            this[it.key] = it.value.toString()
        }
    }

    fun makeSystemMemorySnapShot(): Map<String, String>
            = mutableMapOf<String, String>().apply {
        systemMemory.forEach {
            this[it.key] = it.value.toString()
        }
    }

    fun makeArgsMemorySnapshot(process: Process): Map<String, String>
            = mutableMapOf<String, String>().apply {
        argsMemory[process.id]?.forEach {
            this[it.key] =
                    it.value.loadValue()?.toString()
                    ?: "unresolvable reference [${it.value.display()}]"
        }
    }

    fun allocateMemory(process: Process, userVariableName: String) {
        userMemory.of(process)[userVariableName] = Unit
    }

    fun freeMemory(process: Process, userVariableName: String) {
        userMemory.of(process).remove(userVariableName)
    }

    private fun allocateMemory(process: Process) {
        userMemory[process.id] = mutableMapOf()
        argsMemory[process.id] = mutableMapOf()
    }

    private fun freeMemory(process: Process) {
        userMemory.remove(process.id)
        argsMemory.remove(process.id)
    }

    private fun cleanup() {
        systemMemory.clear()
        userMemory.clear()
        processIdProvider.reset()
    }

    fun loadValue(process: Process, variableName: VariableName)
            = loadValue(process.id, variableName)

    fun saveValue(process: Process, variableName: VariableName, value: Any)
            = saveValue(process.id, variableName, value)

    private fun loadValue(processId: Long, variableName: VariableName)
            = when (variableName.type) {
        VariableName.Type.USER -> userMemory.of(processId).find(variableName)
        VariableName.Type.SYSTEM -> systemMemory.find(variableName)
        VariableName.Type.ARGUMENT -> argsMemory.of(processId).find(variableName)?.loadValue()
    }

    private fun saveValue(processId: Long, variableName: VariableName, value: Any)
            = when (variableName.type) {
        VariableName.Type.USER -> userMemory.of(processId).tryToAssign(variableName, value)
        VariableName.Type.SYSTEM -> systemMemory.tryToAssign(variableName, value)
        VariableName.Type.ARGUMENT -> argsMemory.of(processId).find(variableName)?.saveValue(value) ?: false
    }

    private fun <T> OrganizedMemory<T>.of(process: Process)
            = of(process.id)

    private fun <T> OrganizedMemory<T>.of(processId: Long)
            = get(processId) ?: throw IllegalArgumentException(
        "Memory space is not allocated for the process($processId).")

    private fun <T> Memory<T>.tryToAssign(key: VariableName, value: T) =
        if (containsKey(key.simpleName)) {
            put(key.simpleName, value)
            true
        } else false

    private fun <T> Memory<T>.find(key: VariableName) = get(key.simpleName)

    private fun MemoryReference.loadValue(): Any? = loadValue(first, second)

    private fun MemoryReference.saveValue(value: Any): Boolean = saveValue(first, second, value)

    private fun MemoryReference.display() = "process:$first, name:$second"

    interface Process {

        val id: Long
        val environment: Environment

        fun run(): StackTrace

        fun runChildProcess(
            stackTrace: StackTrace,
            script: List<Instruction>,
            args: Map<String, MemoryReference>)
    }

    private inner class RootProcess(
        val scriptName: String,
        val fileName: String,
        val args: Map<String, Any>)
        : Process {

        override val environment: Environment
            get() = this@Environment

        override val id: Long

        init {
            cleanup()
            id = processIdProvider.newId()
        }

        override fun run(): StackTrace {
            val stackTrace = StackTrace()
            val startUpInstruction = Invoke(
                scriptName, fileName,
                args.map { VariableName.userVariable(it.key) })

            allocateMemory(this)
            userMemory[id]?.putAll(args)

            try {
                interpreter.interpret(startUpInstruction, this)
            } catch (unexpectedError: UnexpectedError) {
                stackTrace.unexpectedError = unexpectedError
            } catch (cause: ScriptFailed) {
                stackTrace.unexpectedError = cause.error
            } finally {
                stackTrace.addFrame(startUpInstruction, this)
            }

            freeMemory(this)
            return stackTrace
        }

        override fun runChildProcess(
            stackTrace: StackTrace,
            script: List<Instruction>,
            args: Map<String, MemoryReference>) {
            ChildProcess(stackTrace, script, args).run()
        }
    }

    private inner class ChildProcess(
        val stackTrace: StackTrace,
        val script: List<Instruction>,
        val args: Map<String, MemoryReference>)
        : Process {

        override val id = processIdProvider.newId()
        override val environment = this@Environment

        override fun run(): StackTrace {
            allocateMemory(this)
            argsMemory[id]?.putAll(args)

            script.forEach {
                try {
                    interpreter.interpret(it, this)
                } catch (unexpectedError: UnexpectedError) {
                    // An root process will catch this exception.
                    throw ScriptFailed(unexpectedError)
                } finally {
                    stackTrace.addFrame(it, this)
                }
            }

            freeMemory(this)
            return stackTrace
        }

        override fun runChildProcess(
            stackTrace: StackTrace,
            script: List<Instruction>,
            args: Map<String, MemoryReference>) {
            ChildProcess(stackTrace, script, args).run()
        }
    }

    private class ScriptFailed(
        val error: UnexpectedError)
        : Exception()
}
