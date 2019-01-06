package com.foureyedstraighthair.aliceandbob.firebasesupport.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class FuncOnce(private val ref: DatabaseReference) {

    private var actionOnFetch
            : (snapshot: DataSnapshot) -> Unit = { _ -> }

    private var actionOnCancelled
            : (error: DatabaseError) -> Unit = { _ -> }

    fun onFetch(action: (snapshot: DataSnapshot) -> Unit) {
        actionOnFetch = action
    }

    fun onCancelled(action: (error: DatabaseError) -> Unit) {
        actionOnCancelled = action
    }

    fun invoke() {
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onCancelled(error: DatabaseError)
                    = actionOnCancelled(error)

            override fun onDataChange(snapshot: DataSnapshot)
                    = actionOnFetch(snapshot)
        })
    }
}