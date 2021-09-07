package com.gukjang.colosseum_210903.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData(
    var id : Int,
    var title : String,
    var imageURL : String) : Serializable{

    // 선택진영 목록을 담아줄 ArrayList
    val sideList = ArrayList<SideData>()

    // 내가 투표한 진영의 ID 가 뭔지
    var mySideId = 0

    // 내가 투표한 진영 저장
    var mySelectedSide : SideData? = null

    // json { } 을 넣으면 파싱해서 TopicData 객체로 리턴해주는 함수
    companion object {
        fun getTopicDataFromJson(json : JSONObject) : TopicData{
            val topicData = TopicData()

            topicData.id = json.getInt("id")
            topicData.title = json.getString("title")
            topicData.imageURL = json.getString("img_url")

            // 토론의 하위 정보로 sides 라는 JSONArray 내려줌
            // JSONArray : for 문 돌려서 파싱 -> topicData 의 sideList 에 추가하기

            val sidesArr = json.getJSONArray("sides")

            for(i in 0 until sidesArr.length()){
                val sideObj = sidesArr.getJSONObject(i)

                val sideData = SideData.getSideDataFromJson(sideObj)

                topicData.sideList.add(sideData)
            }
            topicData.mySideId = json.getInt("my_side_id")

            if(!json.isNull("my_side")){
                // null 이 아닐때만 파싱
                topicData.mySelectedSide = SideData.getSideDataFromJson(json.getJSONObject("my_side"))
            }

            return topicData
        }
    }

    // 보조 생성자 추가
    constructor() : this(0, "제목 없음", "")

    constructor(id: Int) : this(id, "제목 없음", "")
}