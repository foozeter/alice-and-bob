package com.foureyedstraighthair.aliceandbob.console.programming

class ProcessIdProvider {

    companion object {
        private const val ID_START = Long.MIN_VALUE
    }

    private var id = ID_START

    fun newId() = ++id

    fun reset() {
        id = ID_START
    }
}