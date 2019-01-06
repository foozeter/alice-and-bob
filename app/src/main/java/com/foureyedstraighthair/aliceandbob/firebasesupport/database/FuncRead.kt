package com.foureyedstraighthair.aliceandbob.firebasesupport.database

import com.google.firebase.database.*

class FuncRead(
    private val ref: DatabaseReference,
    private val sortOrder: SortOrder,
    private val pageSizeLimit: Int = -1,
    private val startAt: DataSnapshot? = null,
    private val readForward: Boolean = true) {

    private var actionOnLoaded
            : (data: MutableList<DataSnapshot>) -> Unit = {}

    private var actionOnCancelled
            : (error: DatabaseError) -> Unit = {}

    fun onLoaded(action: (data: MutableList<DataSnapshot>) -> Unit) {
        actionOnLoaded = action
    }

    fun onCancelled(action: (error: DatabaseError) -> Unit) {
        actionOnCancelled = action
    }

    fun invoke() = ref
        .confirmOrder()
        .confirmLimit()
        .confirmStart()
        .addListenerForSingleValueEvent(
            object: ValueEventListener {

                override fun onCancelled(error: DatabaseError)
                        = actionOnCancelled.invoke(error)

                override fun onDataChange(snapshot: DataSnapshot)
                        = actionOnLoaded(snapshot.children.toMutableList())
            })

    private fun Query.confirmOrder() = when (sortOrder) {
        is SortOrder.ByKey -> orderByKey()
        is SortOrder.ByValue -> orderByValue()
        is SortOrder.ByChild -> orderByChild(sortOrder.key)
        else -> throw RuntimeException(
            "unknown sort order")
    }

    private fun Query.confirmLimit() =
        if (pageSizeLimit < 0) this
        else if (readForward) limitToFirst(pageSizeLimit)
        else limitToLast(pageSizeLimit)

    private fun Query.confirmStart() =
        if (startAt == null) this
        else when (sortOrder) {

            is SortOrder.ByStringChild ->
                if (readForward) startAt(
                    startAt.stringComparisonValue(sortOrder.key),
                    startAt.key)
                else endAt(
                    startAt.stringComparisonValue(sortOrder.key),
                    startAt.key)


            is SortOrder.ByBooleanChild ->
                if (readForward) startAt(
                    startAt.booleanComparisonValue(sortOrder.key),
                    startAt.key)
                else endAt(
                    startAt.booleanComparisonValue(sortOrder.key),
                    startAt.key)

            is SortOrder.ByDoubleChild ->
                if (readForward) startAt(
                    startAt.doubleComparisonValue(sortOrder.key),
                    startAt.key)
                else endAt(
                    startAt.doubleComparisonValue(sortOrder.key),
                    startAt.key)

            else -> throw RuntimeException(
                "invalid sort order")
        }

    private fun DataSnapshot.stringComparisonValue(key: String)
            = child(key).getValue(String::class.java)

    private fun DataSnapshot.booleanComparisonValue(key: String)
            = child(key).getValue(Boolean::class.java)!!

    private fun DataSnapshot.doubleComparisonValue(key: String)
            = child(key).getValue(Double::class.java)!!
}