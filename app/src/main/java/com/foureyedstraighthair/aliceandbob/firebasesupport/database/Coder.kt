package com.foureyedstraighthair.aliceandbob.firebasesupport.database

import com.google.firebase.database.DataSnapshot

interface Coder<T> {
    fun serialize(value: T): Any
    fun deserialize(snapshot: DataSnapshot): T
}