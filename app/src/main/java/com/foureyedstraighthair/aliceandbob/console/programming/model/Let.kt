package com.foureyedstraighthair.aliceandbob.console.programming.model

import com.foureyedstraighthair.aliceandbob.console.programming.Literal
import com.foureyedstraighthair.aliceandbob.console.programming.VariableName

class Let(
    var variable: VariableName,
    var literal: String)
    : Instruction