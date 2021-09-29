package com.gukjang.finalproject_210923.datas

import com.google.gson.annotations.SerializedName

class JoinData (
    @SerializedName("userId")
    var userId : String,
    @SerializedName("userEmail")
    var userEmail : String,
    @SerializedName("userPw")
    var userPw : String,
    @SerializedName("userNickname")
    var userNickname : String
    ){

    fun JoinData(userId : String, userEmail: String, userPw: String, userNickname: String){
        this.userId = userId
        this.userEmail = userEmail
        this.userPw = userPw
        this.userNickname = userNickname
    }
}