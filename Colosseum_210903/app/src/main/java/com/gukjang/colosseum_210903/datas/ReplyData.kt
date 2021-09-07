package com.gukjang.colosseum_210903.datas

import org.json.JSONObject

class ReplyData(
    var id : Int,
    var content : String,
    var likeCount : Int,
    var hateCount : Int,
    var myLike : Boolean,
    var myHate : Boolean,
    var replyCount : Int ) {

    constructor() : this(0, "", 0, 0, false, false, 0)

    // JSON 을 넣으면 ReplyData 로 변환해주는 기능
    companion object{
        fun getReplyDataFromJson(json : JSONObject) : ReplyData{
            val replyData = ReplyData()

            replyData.id = json.getInt("id")
            replyData.content = json.getString("content")
            replyData.likeCount = json.getInt("like_count")
            replyData.hateCount = json.getInt("hate_count")
            replyData.myLike = json.getBoolean("my_like")
            replyData.myHate = json.getBoolean("my_hate")
            replyData.replyCount = json.getInt("reply_count")

            return replyData
        }
    }
}