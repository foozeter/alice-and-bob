package com.foureyedstraighthair.aliceandbob.console.programming

import com.foureyedstraighthair.aliceandbob.console.programming.type.*

object Literal {

    fun inflate(literal: String): Type =
        Letters.inflate(literal)
        ?: Bool.inflate(literal)
        ?: Integer.inflate(literal)
        ?: Real.inflate(literal)
        ?: Nil

}