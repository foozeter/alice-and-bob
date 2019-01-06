package com.foureyedstraighthair.aliceandbob

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.foureyedstraighthair.aliceandbob.firebasesupport.database.Coder
import com.foureyedstraighthair.aliceandbob.firebasesupport.database.insert
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val textView: TextView by lazy { text_view }

    val listener = object: ValueEventListener {

        override fun onCancelled(p0: DatabaseError) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onDataChange(p0: DataSnapshot) {
            items.clear()
            Log.d("mylog", "----------- page $currentPage ---------------")
            for (snapshot in p0.children) {
                val value = snapshot.getValue(User::class.java)!!
                items.add(Pair(snapshot.key!!, value))
                Log.d("mylog", "uid[${snapshot.key!!}] -> $value")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uid = System.currentTimeMillis().toString(16)
        val user = User("strobo jouny", 10)

        FirebaseDatabase
            .getInstance()
            .getReference("users")
            .insert(uid, user, UserCoder()) {

                onError {
                    Log.d("mylog", "error! => ${it.message}")
                }

                onAlreadyExists {
                    Log.d("mylog", "$uid is already exists!")
                }

                onSuccessful {
                    Log.d("mylog" , "saved successfully! ($uid)")
                }
            }

        button.setOnClickListener {
            FirebaseDatabase
                .getInstance()
                .getReference("users")
                .insert(uid, user, UserCoder()) {

                    onError { error ->
                        Log.d("mylog", "error! => ${error.message}")
                    }

                    onAlreadyExists { user ->
                        Log.d("mylog", "$uid is already exists!")
                    }

                    onSuccessful { user ->
                        Log.d("mylog" , "saved successfully! ($uid)")
                    }
                }
        }
    }

    //
//        for (i in 0..100) {
//            ref.push().FuncPlace(mapOf(
//                Pair("name", "user_$i"),
    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    fun IntRange.random() =
        Random().nextInt((endInclusive + 1) - start) +  start

    fun addTestData() {
//        val ref = FirebaseDatabase
//            .getInstance()
//            .getReference("users")
//                Pair("like", (0..5).random())))
//        }
    }

    var currentPage = 1
    val pageSize = 10
    val items = mutableListOf<Pair<String, User>>()

    fun loadNextPage() {
//        query?.removeEventListener(listener)

        var query = FirebaseDatabase
            .getInstance()
            .getReference("users")
            .orderByChild("like")
            .limitToFirst(pageSize)

        if (currentPage == 10) {
            currentPage = 1

        } else if (items.isNotEmpty()) {
            ++currentPage
            val prevLast = items.last()
            query = query.startAt(
                prevLast.second.like.toDouble(),
                prevLast.first)
        }

//        query?.addValueEventListener(listener)
        query.addListenerForSingleValueEvent(listener)
    }

    data class User(
        var name: String,
        var like: Int) {

        constructor(): this("not set", 0)
    }

    class UserCoder: Coder<User> {

        override fun serialize(value: User) = mapOf(
            Pair("name", value.name),
            Pair("like", value.like))

        override fun deserialize(snapshot: DataSnapshot)
                = snapshot.getValue(User::class.java)!!
    }
}
