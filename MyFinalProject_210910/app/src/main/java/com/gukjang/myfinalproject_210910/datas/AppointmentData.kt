package com.gukjang.myfinalproject_210910.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AppointmentData(
    var id : Int,
    @SerializedName("user_id")
    var userId : Int,
    var title : String,
    var datetime : String,      // 일단 String 으로 받고 data 형태로 받기
    @SerializedName("place")
    var placeName : String,
    var latitude : Double,
    var longitude : Double,
    @SerializedName("created_at")
    var createdAt : String,
    var user: UserData
) : Serializable {
}