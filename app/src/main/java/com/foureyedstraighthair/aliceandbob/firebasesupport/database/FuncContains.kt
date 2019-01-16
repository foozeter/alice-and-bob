package com.foureyedstraighthair.aliceandbob.firebasesupport.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

class FuncContains(
    private val ref: DatabaseReference,
    private val key: String)
    : Func {

    private var actionOnError
            : (error: DatabaseError) -> Unit = { _ -> }

    private var actionOnExists
            : (snapshot: DataSnapshot) -> Unit = { _ -> }

    private var actionOnNotExists
            : (ref: DatabaseReference) -> Unit = { _ -> }

    fun onError(action: (error: DatabaseError) -> Unit) {
        actionOnError = action
    }

    fun ifExists(action: (snapshot: DataSnapshot) -> Unit) {
        actionOnExists = action
    }

    fun ifNotExists(action: (ref: DatabaseReference) -> Unit) {
        actionOnNotExists = action
    }


    override fun invoke()
            = ref.child(key).once {

        onCancelled {
            actionOnError.invoke(it)
        }

        onFetch {
            if (it.exists()) actionOnExists.invoke(it)
            else actionOnNotExists.invoke(it.ref)
        }
    }
}