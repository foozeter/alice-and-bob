package com.foureyedstraighthair.aliceandbob.firebasesupport.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference

fun DatabaseReference.onceOrThrow(
    action: (snapshot: DataSnapshot) -> Unit) = once {
    onCancelled { throw RuntimeException(it.message) }
    onFetch(action)
}

fun DatabaseReference.once(config: FuncOnce.() -> Unit) {
    val func = FuncOnce(this)
    config(func)
    func.invoke()
}

fun DatabaseReference.contains(
    key: String, config: FuncContains.() -> Unit) {
    val func = FuncContains(this, key)
    config(func)
    func()
}

fun DatabaseReference.place(value: Any, config: FuncPlace.() -> Unit) {
    val func = FuncPlace(this, value)
    config(func)
    func.invoke()
}

fun DatabaseReference.delete(config: FuncDelete.() -> Unit) {
    val func = FuncDelete(this)
    config(func)
    func.invoke()
}

fun <T> DatabaseReference.insert(
    key: String,
    value: T,
    coder: Coder<T>,
    config: FuncInsert<T>.() -> Unit) {

    val func = FuncInsert(this, key, value, coder)
    config(func)
    func.invoke()
}

fun DatabaseReference.inesrt(
    key: String,
    value: Boolean,
    config: FuncInsert<Boolean>.() -> Unit)
        = insert(key, value,
    object: Coder<Boolean> {
        override fun serialize(value: Boolean) = value

        override fun deserialize(snapshot: DataSnapshot)
                = snapshot.getValue(Boolean::class.java)!!
    },
    config)

fun DatabaseReference.inesrt(
    key: String,
    value: Long,
    config: FuncInsert<Long>.() -> Unit)
        = insert(key, value,
    object: Coder<Long> {
        override fun serialize(value: Long) = value

        override fun deserialize(snapshot: DataSnapshot)
                = snapshot.getValue(Long::class.java)!!
    },
    config)

fun DatabaseReference.inesrt(
    key: String,
    value: Double,
    config: FuncInsert<Double>.() -> Unit)
        = insert(key, value,
    object: Coder<Double> {
        override fun serialize(value: Double) = value

        override fun deserialize(snapshot: DataSnapshot)
                = snapshot.getValue(Double::class.java)!!
    },
    config)

fun DatabaseReference.inesrt(
    key: String,
    value: String,
    config: FuncInsert<String>.() -> Unit)
        = insert(key, value,
    object: Coder<String> {
        override fun serialize(value: String) = value

        override fun deserialize(snapshot: DataSnapshot)
                = snapshot.getValue(String::class.java)!!
    },
    config)

fun DatabaseReference.browse(
    order: SortOrder,
    limit: Int = -1,
    startAt: DataSnapshot? = null,
    readForward: Boolean = true,
    config: FuncRead.() -> Unit) {

    val func = FuncRead(this, order, limit, startAt, readForward)
    config(func)
    func.invoke()
}

fun <T> DatabaseReference.makeBrowser(
    order: SortOrder,
    coder: Coder<T>) = Browser(this, order, coder)