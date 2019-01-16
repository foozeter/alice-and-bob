package com.foureyedstraighthair.aliceandbob.firebasesupport.database

import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

class FuncPlace(
    private val ref: DatabaseReference,
    private val value: Any)
    : Func {

    private var actionOnSuccessful
            : (ref: DatabaseReference) -> Unit = { _ -> }

    private var actionOnError
            : (error: DatabaseError) -> Unit = { _ -> }

    fun onSuccessful(action: (ref: DatabaseReference) -> Unit) {
        actionOnSuccessful = action
    }

    fun onError(action: (error: DatabaseError) -> Unit) {
        actionOnError = action
    }

    override fun invoke() {
        ref.setValue(value, object: DatabaseReference.CompletionListener {
            override fun onComplete(error: DatabaseError?, ref: DatabaseReference) {
                if (error != null) actionOnError.invoke(error)
                else actionOnSuccessful.invoke(ref)
            }
        })
    }
}