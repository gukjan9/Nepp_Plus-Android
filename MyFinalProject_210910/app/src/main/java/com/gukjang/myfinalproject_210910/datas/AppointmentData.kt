package com.gukjang.myfinalproject_210910.datas

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class AppointmentData(
    var id: Int,
    @SerializedName("user_id")
    var userId: Int,
    var title: String,
    var datetime: Date, // 일단 String -> 파싱 기능 수정 => Date형태로 받자. (Calendar와 엮어서 사용)
    @SerializedName("start_place")
    var startPlace: String,
    @SerializedName("start_latitude")
    var startLatitude: Double,
    @SerializedName("start_longitude")
    var startLongitude: Double,
    @SerializedName("place")
    var placeName: String,
    var latitude: Double,
    var longitude: Double,
    @SerializedName("created_at")
    var createdAt: Date,
    var user: UserData,
    @SerializedName("invited_friends")
    var invitedFriendList : List<UserData>
) : Serializable {

    fun getFormattedDateTime() : String{
        // 현재 시간
        val now = Calendar.getInstance()

        // 약속시간 - 현재시간 : 몇시간?
        val dateTimeToTimeZone = this.datetime.time + now.timeZone.rawOffset

        val diff = dateTimeToTimeZone - now.timeInMillis

        val diffHour = diff / 1000 / 60 / 60

        Log.d("시차", diff.toString())

        if(diffHour < 1){
            val diffMinute = diff / 1000 / 60
            return "${diffMinute}분 남음"
        }
        else if(diffHour < 5) return "${diffHour}시간 남음"
        else{
            val sdf = SimpleDateFormat("M/d a h:mm")
            return sdf.format(dateTimeToTimeZone)
        }
    }
}