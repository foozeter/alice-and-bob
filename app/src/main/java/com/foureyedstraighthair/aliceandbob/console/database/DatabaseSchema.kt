package com.foureyedstraighthair.aliceandbob.console.database

class DatabaseSchema private constructor() {

    class Root {

        class UserProf {
            companion object {
                const val key = "userProf"
            }

            // key is an uid
            class Model {
                companion object {
                    const val name = "name"
                    const val message = "message"
                    const val icon = "icon"
                    const val joinDate = "joinDate"
                }
            }
        }
    }
}