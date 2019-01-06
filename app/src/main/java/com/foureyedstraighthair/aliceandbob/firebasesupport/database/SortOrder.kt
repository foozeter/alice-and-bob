package com.foureyedstraighthair.aliceandbob.firebasesupport.database

abstract class SortOrder(val descending: Boolean = false) {

    abstract class ByChild(
        val key: String,
        descending: Boolean = false)
        : SortOrder(descending)

    class ByKey(descending: Boolean = false)
        : SortOrder(descending)

    class ByValue(descending: Boolean = false)
        : SortOrder(descending)

    class ByStringChild(
        key: String,
        descending: Boolean = false)
        : ByChild(key, descending)

    class ByBooleanChild(
        key: String,
        descending: Boolean = false)
        : ByChild(key, descending)

    class ByDoubleChild(
        key: String,
        descending: Boolean = false)
        : ByChild(key, descending)

}