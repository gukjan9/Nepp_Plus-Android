package com.gukjang.myfinalproject_210910.datas

import com.google.gson.annotations.SerializedName

class UserData(
    var id : Int,
    var provider : String,
    @SerializedName("nick_name")
    var nickName : String,
    var email : String) {

}