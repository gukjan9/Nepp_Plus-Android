package com.gukjang.colosseum_210903

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.gukjang.colosseum_210903.adapters.ReplyAdapter
import com.gukjang.colosseum_210903.datas.ReplyData
import com.gukjang.colosseum_210903.datas.TopicData
import com.gukjang.colosseum_210903.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {
    lateinit var mTopicData : TopicData

    val mReplyList = ArrayList<ReplyData>()
    lateinit var mReplyAdapter: ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_topic_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        // 첫번째 진영, 두번째 진영 투표버튼의 이벤트
        val ocl = object : View.OnClickListener{
            override fun onClick(view: View?) {
                // 버튼이 눌리면 할 일
                // view -> 눌린게 어떤 버튼인지? 눌린 버튼을 담아준다.
                val clickedSideId = view!!.tag.toString().toInt()

                Log.d("투표 진영 id", clickedSideId.toString())

                ServerUtil.postRequestTopicVote(mContext, clickedSideId, object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObj: JSONObject) {
                        // 투표 결과 확인

                    }

                })
            }
        }
        voteToFirstSideBtn.setOnClickListener(ocl)
        voteToSecondSideBtn.setOnClickListener(ocl)
    }

    override fun setValues() {
        mTopicData = intent.getSerializableExtra("topic") as TopicData

        // 투표 버튼에 각 진영이 어떤 진영인지 버튼에 메모해두면 투표할 때 그 진영이 뭔지 알아낼 수 있다.
        voteToFirstSideBtn.tag = mTopicData.sideList[0].id
        voteToSecondSideBtn.tag = mTopicData.sideList[1].id

        Glide.with(mContext).load(mTopicData.imageURL).into(topicImg)
        titleTxt.text = mTopicData.title

        // 나머지 데이터는 서버에서 가져오자
        getTopicDetailDataFromServer()

        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyList)
        replyListView.adapter = mReplyAdapter
    }

    // 투표현황 등, 최신 토론 상세 데이터를 다시 서버에서 불러오기
    fun getTopicDetailDataFromServer(){
        ServerUtil.getRequestTopicDetail(mContext, mTopicData.id, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val dataObj = jsonObj.getJSONObject("data")
                val topicObj = jsonObj.getJSONObject("topic")

                // mTopicData 를 새로 파싱한 데이터로 교체
                mTopicData = TopicData.getTopicDataFromJson(topicObj)

                // topicObj 에 댓글도 있음
                val repliesArr = topicObj.getJSONArray("replies")

                for(i in 0 until repliesArr.length()){
                    // 댓글 { } JSON 을 ReplyData 에 파싱 -> mReplyList 목록에 추가
//                    val replyObj = repliesArr.getJSONObject(i)
//                    val replyData = ReplyData.getReplyDataFromJson(replyObj)
//                    mReplyList.add(replyData)

                    // 위에 3줄과 똑같음
                    mReplyList.add(ReplyData.getReplyDataFromJson(repliesArr.getJSONObject(i)))
                }


                // 새로 받은 데이터로 UI 반영
                refreshTopicDataToUI()
            }
        })
    }

    fun refreshTopicDataToUI(){
        runOnUiThread {
            firstSideTitleTxt.text = mTopicData.sideList[0].title
            firstSideVoteCountTxt.text = "${mTopicData.sideList[0].voteCount}표"

            secondSideTitleTxt.text = mTopicData.sideList[1].title
            secondSideVoteCountTxt.text = "${mTopicData.sideList[1].voteCount}표"

            // ListView 새로고침
            mReplyAdapter.notifyDataSetChanged()
        }
    }
}