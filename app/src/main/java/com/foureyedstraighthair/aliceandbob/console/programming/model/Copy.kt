package com.foureyedstraighthair.aliceandbob.console.programming.model

import com.foureyedstraighthair.aliceandbob.console.programming.VariableName

data class Copy(
    var dst: VariableName,
    var src: VariableName)
    : Instruction