package com.gukjang.colosseum_210903.datas

import android.util.Log
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ReplyData(
    var id : Int,
    var content : String,
    var likeCount : Int,
    var hateCount : Int,
    var myLike : Boolean,
    var myHate : Boolean,
    var replyCount : Int ) {

    lateinit var selectedSide : SideData

    // 이 댓글을 적은 사람
    lateinit var writer : UserData

    // 이 댓글이 적힌 시점
    val createdAt = Calendar.getInstance()


    constructor() : this(0, "", 0, 0, false, false, 0)

    // ~ 일 전 등으로 가공
    fun getFormattedTimeAgo() : String{
        val now = Calendar.getInstance()
        val interval = now.timeInMillis - this.createdAt.timeInMillis

        Log.d("두 시간의 간격", interval.toString())

        if (interval < 1000) {
//            간격: 밀리초 까지 계산. (1/1000)
//            1초도 안된다. => "방금 전"  으로 결과.
            return "방금 전"
        }
        else if (interval < 1 * 60 * 1000) {
//            1분 이내. => "?초 전" 으로 결과.
            return  "${interval / 1000}초 전"
        }
        else if (interval < 1 * 60 * 60 * 1000) {
//            1시간 이내. => "?분 전" 으로 결과.
            return "${interval / 1000 / 60}분 전"
        }
        else if (interval < 24 * 60 * 60 * 1000) {
//            24시간 이내. => "?시간 전" 으로 결과
            return "${interval / 1000 / 60 / 60}시간 전"
        }
        else if (interval < 5 * 24 * 60 * 60 * 1000) {
//            5일 이내. -> "?일 전" 으로 결과.
            return "${interval / 1000 / 60 / 60 / 24}일 전"
        }
        else {
//            5일 이상. -> "yyyy년 M월 d일" 양식 가공.
            val replyDisplayFormat = SimpleDateFormat("yyyy년 M월 d일")
            return replyDisplayFormat.format(this.createdAt.time)
        }
    }


    companion object{
        // 서버가 주는 날짜 양식을 분석하기 위한
        val serverFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        // JSON 을 넣으면 ReplyData 로 변환해주는 기능
        fun getReplyDataFromJson(json : JSONObject) : ReplyData{
            val replyData = ReplyData()

            replyData.id = json.getInt("id")
            replyData.content = json.getString("content")
            replyData.likeCount = json.getInt("like_count")
            replyData.hateCount = json.getInt("dislike_count")
            replyData.myLike = json.getBoolean("my_like")
            replyData.myHate = json.getBoolean("my_dislike")
            replyData.replyCount = json.getInt("reply_count")

            // 선택 진영 파싱 -> SideData 에 만들어둔 파싱 기능 활용
            val selectedSideObj = json.getJSONObject("selected_side")
            replyData.selectedSide = SideData.getSideDataFromJson(selectedSideObj)

            // 작성자 정보 파싱 -> UserData 사용
            val userObj = json.getJSONObject("user")
            replyData.writer = UserData.getUserDataFromJson(userObj)

            // 작성 일시 -> String 으로 받아서 Calendar로 변환해서 저장
            val createdAtString = json.getString("created_at")

            // 댓글 데이터 작성일시에 serverFormat 변수를 이용해서 시간 저장
            replyData.createdAt.time = serverFormat.parse(createdAtString)

            return replyData
        }
    }
}