package com.gukjang.colosseum_210903.datas

import org.json.JSONObject

class NotiData(
    var id : Int,
    var title : String) {

    constructor() : this(0, "제목 없음")

    companion object{
        fun getNotiDataFromJson(json : JSONObject) : NotiData{
            val notiData = NotiData()

            notiData.id = json.getInt("id")
            notiData.title = json.getString("title")

            return notiData
        }
    }
}