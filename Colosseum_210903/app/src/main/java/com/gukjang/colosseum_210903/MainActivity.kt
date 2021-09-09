package com.gukjang.colosseum_210903

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.ArrayAdapter
import com.google.firebase.iid.FirebaseInstanceId
import com.gukjang.colosseum_210903.adapters.TopicAdapter
import com.gukjang.colosseum_210903.datas.TopicData
import com.gukjang.colosseum_210903.datas.UserData
import com.gukjang.colosseum_210903.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.my_custom_action_bar.*
import org.json.JSONObject

class MainActivity : BaseActivity() {
    val mTopicList = ArrayList<TopicData>()
    lateinit var mTopicAdapter: TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()

        Log.d("푸시알림-디바이스토큰", FirebaseInstanceId.getInstance().token!!)
    }

    override fun setupEvents() {
        topicListView.setOnItemClickListener { adapterView, view, position, l ->
            val clickedTopic = mTopicList[position]

            val myIntent = Intent(mContext, ViewTopicDetailActivity::class.java)
            myIntent.putExtra("topic", clickedTopic)
            startActivity(myIntent)
        }

        profileImg.setOnClickListener {
            val myIntent = Intent(mContext, MyProfileActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        getMainDataFromServer()

        mTopicAdapter = TopicAdapter(mContext, R.layout.topic_list_item, mTopicList)
        topicListView.adapter = mTopicAdapter

        // main 에서만 backBtn 숨김 처리
        backBtn.visibility = View.GONE

        // main 에서만 notiBtn 보임 처리
//        notiBtn.visibility = View.VISIBLE

        notiLayout.visibility = View.VISIBLE

        // 내 프로필 화면 버튼 보여주기
        profileImg.visibility = View.VISIBLE

    }

    // 서버에서 메인화면에 보여줄 정보 받아오기
    fun getMainDataFromServer(){
        ServerUtil.getRequestMainData(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {
                // 응답 - jsonObj 분석(파싱) -> 토론 주제를 서버에서 내려줌
                val dataObj = jsonObj.getJSONObject("data")
                val topicsArr = dataObj.getJSONArray("topics")

                // 서버가 내려주는 토론주제들 (JsonObject 목록) -> TopicData 로 변환해서 ArrayList 에 추가 (반복문 활용)
                for(i in 0 until topicsArr.length()){
                    val topicObj = topicsArr.getJSONObject(i)

                    // TopicData 를 만들어서 멤버변수들에 topicObj 에서 파싱한 데이터를 대입
//                    val tempTopicData = TopicData()
//                    tempTopicData.id = topicObj.getInt("id")
//                    tempTopicData.title = topicObj.getString("title")
//                    tempTopicData.imageURL = topicObj.getString("img_url")

                    val tempTopicData = TopicData.getTopicDataFromJson(topicObj)

                    mTopicList.add(tempTopicData)
                }

                // 로그인한 사용자 닉네임 가져오기
                val userObj = dataObj.getJSONObject("user")
//                val nickname = userObj.getString("nick_name")
                val loginUser = UserData.getUserDataFromJson(userObj)

               // 목록의 변화 -> ListView 가 인지 -> 새로고침 공지 -> 백그라운드에서 UI 변경
                runOnUiThread {
                    mTopicAdapter.notifyDataSetChanged()
                    Toast.makeText(mContext, "${loginUser.nickname}님 환영합니다!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    // 서버에서 안 읽은 알림이 몇개인지 받아오자 -> 화면에 들어올 때마다 재확인
    override fun onResume() {
        super.onResume()

        ServerUtil.getRequestNotificationCountOrList(mContext, false, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val dataObj = jsonObj.getJSONObject("data")
                val unreadCount = dataObj.getInt("unread_noty_count")

                // 알림 갯수 0개 -> 빨간 동그라미 없음
                // 1개 이상 -> 동그라미 보여주기 + 몇 개인지
                runOnUiThread{
                    if(unreadCount == 0){
                        notiCountTxt.visibility = View.GONE
                    }
                    else{
                        notiCountTxt.text = unreadCount.toString()
                        notiCountTxt.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}