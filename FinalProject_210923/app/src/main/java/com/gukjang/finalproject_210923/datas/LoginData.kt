package com.gukjang.finalproject_210923.datas

import com.google.gson.annotations.SerializedName

class LoginData (
    @SerializedName("userEmail")
    var userEmail: String,
    @SerializedName("userPwd")
    var userPwd: String){

    fun LoginData(userEmail: String, userPwd: String){
        this.userEmail = userEmail
        this.userPwd = userPwd
    }
}