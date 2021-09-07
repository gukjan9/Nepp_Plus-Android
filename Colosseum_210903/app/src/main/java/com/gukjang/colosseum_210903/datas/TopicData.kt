package com.gukjang.colosseum_210903.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData(
    var id : Int,
    var title : String,
    var imageURL : String) : Serializable{

    // 선택진영 목록을 담아줄 ArrayList
    val sideList = ArrayList<SideData>()

    // json { } 을 넣으면 파싱해서 TopicData 객체로 리턴해주는 함수
    companion object {
        fun getTopicDataFromJson(json : JSONObject) : TopicData{
            val topicData = TopicData()

            topicData.id = json.getInt("id")


        }
    }

    // 보조 생성자 추가
    constructor() : this(0, "제목 없음", "")

    constructor(id: Int) : this(id, "제목 없음", "")
}