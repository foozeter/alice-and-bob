package com.foureyedstraighthair.aliceandbob.firebasesupport.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

class Browser<T>(
    private val ref: DatabaseReference,
    private val sortOrder: SortOrder,
    private val coder: Coder<T>) {

    private var resultListener: ResultListener<T>? = null
    private var currentPageFirst: DataSnapshot? = null
    private var currentPageLast: DataSnapshot? = null

    // unlimited by default
    var pageSizeLimit = -1

    fun setResultListener(listener: ResultListener<T>) {
        resultListener = listener
    }

    fun loadFirst() = load(null, !sortOrder.descending)

    fun loadForward() {
        if (currentPageFirst == null || currentPageLast == null)
            throw IllegalStateException(
                "Call Browser#loadFirst() before call this method.")

        if (sortOrder.descending) load(currentPageFirst, false)
        else load(currentPageLast, true)
    }

    fun loadBackward() {
        if (currentPageFirst == null || currentPageLast == null)
            throw IllegalStateException(
                "Call Browser#loadFirst() before call this method.")

        if (sortOrder.descending) load(currentPageLast, true)
        else load(currentPageFirst, false)
    }

    private fun load(
        startAt: DataSnapshot?,
        browseForward: Boolean) {

        ref.browse(sortOrder,
            pageSizeLimit + 1,
            startAt,
            browseForward) {

            onCancelled {
                resultListener?.onError(it)
            }

            onLoaded { data ->

                if (data.isNotEmpty()) {
                    currentPageFirst = data.first()
                    currentPageLast = data.last()
                    if (data.size == pageSizeLimit + 1) {
                        data.removeAt(
                            if (sortOrder.descending) 0
                            else data.lastIndex)
                    }
                }

                resultListener?.onLoaded(
                    if (sortOrder.descending) data.invert().map { coder.deserialize(it) }
                    else data.map { coder.deserialize(it) })
            }
        }
    }

    private fun <T> MutableList<T>.invert(): MutableList<T> {
        reverse()
        return this
    }

    interface ResultListener<T> {
        fun onError(error: DatabaseError)
        fun onLoaded(data: List<T>)
    }
}