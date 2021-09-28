package com.gukjang.finalproject_210923.datas

import com.google.gson.annotations.SerializedName

class JoinData (
    @SerializedName("userName")
    var userName : String,
    @SerializedName("userEmail")
    var userEmail : String,
    @SerializedName("userPwd")
    var userPwd : String
    ){

    fun JoinData(userName: String, userEmail: String, userPwd: String){
        this.userName = userName
        this.userEmail = userEmail
        this.userPwd = userPwd
    }
}