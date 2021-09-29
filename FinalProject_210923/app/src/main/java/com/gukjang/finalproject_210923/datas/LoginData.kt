package com.gukjang.finalproject_210923.datas

import com.google.gson.annotations.SerializedName

class LoginData (
    @SerializedName("userId")
    var userId: String,
    @SerializedName("userPw")
    var userPw: String){

    fun LoginData(userId: String, userPw: String){
        this.userId = userId
        this.userPw = userPw
    }
}