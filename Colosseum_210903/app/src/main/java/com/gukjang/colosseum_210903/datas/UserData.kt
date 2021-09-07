package com.gukjang.colosseum_210903.datas

import org.json.JSONObject

class UserData(
    var id : Int,
    var email : String,
    var nickname : String) {

    companion object {
        fun getUserDataFromJson(json : JSONObject) :UserData
    }
}