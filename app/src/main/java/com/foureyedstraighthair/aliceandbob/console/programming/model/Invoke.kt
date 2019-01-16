package com.foureyedstraighthair.aliceandbob.console.programming.model

import com.foureyedstraighthair.aliceandbob.console.programming.VariableName

data class Invoke(
    var scriptName: String,
    var fileName: String,
    var args: List<VariableName>)
    : Instruction