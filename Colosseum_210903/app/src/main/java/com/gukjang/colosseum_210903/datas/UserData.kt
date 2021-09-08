package com.gukjang.colosseum_210903.datas

import org.json.JSONObject
import java.io.Serializable

class UserData(
    var id : Int,
    var email : String,
    var nickname : String) : Serializable {

    constructor() : this(0, "", "")

    companion object {
        fun getUserDataFromJson(json : JSONObject) :UserData{
            val userData = UserData()

            userData.id = json.getInt("id")
            userData.email = json.getString("email")
            userData.nickname = json.getString("nick_name")

            return userData
        }
    }
}