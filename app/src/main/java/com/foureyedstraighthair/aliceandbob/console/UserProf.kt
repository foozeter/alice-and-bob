package com.foureyedstraighthair.aliceandbob.console


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
        val joinDate: Long) {

        companion object {

            fun deserialize(snapshot: DataSnapshot)
                    = Model(
                uid = snapshot.key!!,
                name = snapshot.child("name").getAsString(),
                message = snapshot.child("message").getAsString(),
                icon = snapshot.child("icon").getAsString(),
                joinDate = snapshot.child("joinDate").getAsLong())
        }

        fun serialize()
                = mapOf(
            Pair("name", name),
            Pair("message", message),
            Pair("icon", icon),
            Pair("joinDate", joinDate))
    }

    fun create(user: FirebaseUser,
               name: String,
               message: String,
               icon: String,
               joinDate: Long) {
    }

    private fun userConfigsRef()
            = FirebaseDatabase
            .getInstance()
            .getReference("userProf")

    private fun refOf(user: FirebaseUser)
            = userConfigsRef().child(user.uid)

    private fun refOf(prof: Model)
            = userConfigsRef().child(prof.uid)
}
