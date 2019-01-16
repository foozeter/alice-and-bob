package com.foureyedstraighthair.aliceandbob.firebasesupport.database

import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

class FuncDelete(
    private val ref: DatabaseReference)
    : Func {

    private var actionOnError
            : (error: DatabaseError) -> Unit = {}

    private var actionOnSuccessful
            : (ref: DatabaseReference) -> Unit = {}

    override fun invoke() {
        ref.removeValue { error, ref ->
            if (error != null) actionOnError.invoke(error)
            else actionOnSuccessful.invoke(ref)
        }
    }
}