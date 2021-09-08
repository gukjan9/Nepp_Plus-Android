package com.gukjang.colosseum_210903.datas

import android.util.Log
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class NotiData(
    var id : Int,
    var title : String) {

    val createdAt = Calendar.getInstance()

    constructor() : this(0, "제목 없음")

    companion object{
        fun getNotiDataFromJson(json : JSONObject) : NotiData{
            val notiData = NotiData()

            notiData.id = json.getInt("id")
            notiData.title = json.getString("title")

            val createdAtString = json.getString("created_at")
            val serverFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            notiData.createdAt.time = serverFormat.parse(createdAtString)

            val localTimeZone = notiData.createdAt.timeZone
            Log.d("내 폰의 시간대", localTimeZone.displayName)

            val timeDiff = localTimeZone.rawOffset
            Log.d("내 폰의 시차", timeDiff.toString())

            notiData.createdAt.add(Calendar.HOUR, timeDiff)

            return notiData
        }
    }
}