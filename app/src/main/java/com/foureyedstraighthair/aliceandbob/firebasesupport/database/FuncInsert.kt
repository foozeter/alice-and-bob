package com.foureyedstraighthair.aliceandbob.firebasesupport.database

import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

class FuncInsert<T>(
    private val parentRef: DatabaseReference,
    private val key: String,
    private val value: T,
    private val coder: Coder<T>)
    : Func {

    private var actionOnSuccessful
            : (value: T) -> Unit = { _ -> }

    private var actionOnError
            : (error: DatabaseError) -> Unit = { _ -> }

    private var actionOnAlreadyExists
            : (value: T) -> Unit = { _ -> }

    fun onSuccessful(listener: (value: T) -> Unit) {
        actionOnSuccessful = listener
    }

    fun onError(listener: (error: DatabaseError) -> Unit) {
        actionOnError = listener
    }

    fun onAlreadyExists(listener: (value: T) -> Unit) {
        actionOnAlreadyExists = listener
    }

    override fun invoke() {
        parentRef.contains(key) {

                onError { error ->
                    actionOnError.invoke(error)
                }

                ifExists { snapshot ->
                    actionOnAlreadyExists.invoke(
                        coder.deserialize(snapshot))
                }

                ifNotExists { ref ->
                    ref.place(coder.serialize(value)) {

                        onError { error ->
                            actionOnError.invoke(error)
                        }

                        onSuccessful {
                            actionOnSuccessful.invoke(value)
                        }
                    }
                }
            }
    }
}