package com.gukjang.colosseum_210903.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gukjang.colosseum_210903.R
import com.gukjang.colosseum_210903.ViewTopicDetailActivity
import com.gukjang.colosseum_210903.datas.ReplyData
import com.gukjang.colosseum_210903.datas.TopicData
import com.gukjang.colosseum_210903.utils.ServerUtil
import org.json.JSONObject
import java.text.SimpleDateFormat

class ReplyAdapter(
    val mContext : Context,
    resId : Int,
    val mList : List<ReplyData>) : ArrayAdapter<ReplyData>(mContext, resId, mList) {

        val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if(row == null) row = mInflater.inflate(R.layout.reply_list_item, null)
        row!!

        val data = mList[position]

        val selectedSideTxt = row.findViewById<TextView>(R.id.selectedSideTxt)
        val writerNicknameTxt = row.findViewById<TextView>(R.id.writerNicknameTxt)
        val createdAtTxt = row.findViewById<TextView>(R.id.createdAtTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        val replyCountTxt = row.findViewById<TextView>(R.id.replyCountTxt)
        val likeCountTxt = row.findViewById<TextView>(R.id.likeCountTxt)
        val hateCountTxt = row.findViewById<TextView>(R.id.hateCountTxt)

        contentTxt.text = data.content
        replyCountTxt.text = "댓글 ${data.replyCount}개"
        likeCountTxt.text = "좋아요 ${data.likeCount}개"
        hateCountTxt.text = "싫어요 ${data.hateCount}개"

        selectedSideTxt.text = "(${data.selectedSide.title})"

        writerNicknameTxt.text = data.writer.nickname

        createdAtTxt.text = data.getFormattedTimeAgo()

        if(data.myLike){
            likeCountTxt.setBackgroundResource(R.drawable.red_border_rect)
            likeCountTxt.setTextColor(mContext.resources.getColor(R.color.like_red))
        }
        else{
            likeCountTxt.setBackgroundResource(R.drawable.black_border_rect)
            likeCountTxt.setTextColor(mContext.resources.getColor(R.color.black))
        }

        if(data.myHate){
            hateCountTxt.setBackgroundResource(R.drawable.blue_border_rect)
            hateCountTxt.setTextColor(mContext.resources.getColor(R.color.hate_blue))
        }
        else{
            hateCountTxt.setBackgroundResource(R.drawable.black_border_rect)
            hateCountTxt.setTextColor(mContext.resources.getColor(R.color.black))
        }

        likeCountTxt.tag = true
        hateCountTxt.tag = false


        // 해당 댓글에 좋아요, 싫어요 찍었다 -> 서버에 전송
        likeCountTxt.setOnClickListener {
            Toast.makeText(mContext, "좋아요 클릭", Toast.LENGTH_SHORT).show()
        }

        hateCountTxt.setOnClickListener {
            Toast.makeText(mContext, "싫어요 클릭", Toast.LENGTH_SHORT).show()
        }

        val ocl = object : View.OnClickListener{
            override fun onClick(view: View?) {
                val isLike = view!!.tag.toString().toBoolean()

                ServerUtil.postRequestReplyLikeOrHate(mContext, data.id, isLike, object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObj: JSONObject) {
                        // 어댑터 안에서 ViewTopicDetailActivity 의 기능 수행
                        // 다형성 이용
                        (mContext as ViewTopicDetailActivity).getTopicDetailDataFromServer()
                    }
                })
            }
        }

        likeCountTxt.setOnClickListener(ocl)
        hateCountTxt.setOnClickListener(ocl)

        replyCountTxt.setOnClickListener {

        }

        return row
    }
}