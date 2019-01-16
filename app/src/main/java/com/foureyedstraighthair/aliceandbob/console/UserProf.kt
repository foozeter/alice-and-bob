package com.foureyedstraighthair.aliceandbob.console


import com.foureyedstraighthair.aliceandbob.console.database.DatabaseSchema
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.foureyedstraighthair.aliceandbob.firebasesupport.Extensions.Companion.getAsLong
import com.foureyedstraighthair.aliceandbob.firebasesupport.Extensions.Companion.getAsString
import com.foureyedstraighthair.aliceandbob.firebasesupport.database.*

class UserProf {

    data class Model(
        val uid: String,
        val name: String,
        val message: String,
        val icon: String,
        val joinDate: Long)

    class Coder
        : com.foureyedstraighthair.aliceandbob.firebasesupport.database.Coder<Model> {

        override fun serialize(value: Model)
                = mapOf(
        Pair(DatabaseSchema.Root.UserProf.Model.name, value.name),
        Pair(DatabaseSchema.Root.UserProf.Model.message, value.message),
        Pair(DatabaseSchema.Root.UserProf.Model.icon, value.icon),
        Pair(DatabaseSchema.Root.UserProf.Model.joinDate, value.joinDate))

        override fun deserialize(snapshot: DataSnapshot)
                = Model(
            snapshot.key!!,
            snapshot.child(DatabaseSchema.Root.UserProf.Model.name).getAsString(),
            snapshot.child(DatabaseSchema.Root.UserProf.Model.message).getAsString(),
            snapshot.child(DatabaseSchema.Root.UserProf.Model.message).getAsString(),
            snapshot.child(DatabaseSchema.Root.UserProf.Model.joinDate).getAsLong())
    }

    fun create(user: FirebaseUser,
               name: String,
               message: String,
               icon: String,
               joinDate: Long,
               config: FuncInsert<Model>.() -> Unit)
            = refOf(user.uid).insert(
        user.uid,
        Model(user.uid, name, message, icon, joinDate),
        Coder(),
        config)

    fun delete(prof: Model,config: FuncDelete.() -> Unit)
            = refOf(prof.uid)

    private fun refOf(uid: String)
            = FirebaseDatabase
                .getInstance()
                .getReference(DatabaseSchema.Root.UserProf.key)
                .child(uid)
}
