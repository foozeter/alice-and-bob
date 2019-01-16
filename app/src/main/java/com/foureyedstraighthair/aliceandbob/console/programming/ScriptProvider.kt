package com.foureyedstraighthair.aliceandbob.console.programming

import com.eclipsesource.json.JsonObject

interface ScriptProvider {
    fun load(name: String, file: String): JsonObject?
}