package com.foureyedstraighthair.aliceandbob.firebasesupport

import com.google.firebase.database.*

class Extensions private constructor() {
    companion object {

        fun DataSnapshot.getAsBool()
                = getValue(Boolean::class.java)!!

        fun DataSnapshot.getAsString()
                = getValue(String::class.java)!!

        fun DataSnapshot.getAsLong()
                = getValue(Long::class.java)!!

        fun DataSnapshot.getAsDouble()
                = getValue(Double::class.java)!!
    }
}