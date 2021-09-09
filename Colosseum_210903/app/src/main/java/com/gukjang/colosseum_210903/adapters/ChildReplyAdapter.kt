package com.gukjang.colosseum_210903.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gukjang.colosseum_210903.R
import com.gukjang.colosseum_210903.ViewReplyDetailActivity
import com.gukjang.colosseum_210903.ViewTopicDetailActivity
import com.gukjang.colosseum_210903.datas.ReplyData
import com.gukjang.colosseum_210903.datas.TopicData
import com.gukjang.colosseum_210903.utils.ServerUtil
import org.json.JSONObject
import java.text.SimpleDateFormat

class ChildReplyAdapter(
    val mContext : Context,
    resId : Int,
    val mList : List<ReplyData>) : ArrayAdapter<ReplyData>(mContext, resId, mList) {

        val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if(row == null) row = mInflater.inflate(R.layout.child_reply_list_item, null)
        row!!

        val data = mList[position]

        val sideAndNicknameTxt = row.findViewById<TextView>(R.id.sideAndNicknameTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)

        // 댓글에 답글 좋아요 싫어요
        val likeCountTxt = row.findViewById<TextView>(R.id.likeCountTxt)
        val hateCountTxt = row.findViewById<TextView>(R.id.hateCountTxt)

        sideAndNicknameTxt.text = "(${data.selectedSide.title}) ${data.writer.nickname}"

        contentTxt.text = data.content

        likeCountTxt.text = "좋아요 ${data.likeCount}개"
        hateCountTxt.text = "싫어요 ${data.hateCount}개"

        if(data.myLike){
            likeCountTxt.setBackgroundResource(R.drawable.red_border_box)
            likeCountTxt.setTextColor(mContext.resources.getColor(R.color.red))
        }
        else{
            likeCountTxt.setBackgroundResource(R.drawable.black_border_rect)
            likeCountTxt.setTextColor(mContext.resources.getColor(R.color.black))
        }

        if(data.myHate){
            hateCountTxt.setBackgroundResource(R.drawable.blue_border_box)
            hateCountTxt.setTextColor(mContext.resources.getColor(R.color.blue))
        }
        else{
            hateCountTxt.setBackgroundResource(R.drawable.black_border_rect)
            hateCountTxt.setTextColor(mContext.resources.getColor(R.color.black))
        }

        likeCountTxt.tag = true
        hateCountTxt.tag = false

        val ocl = object : View.OnClickListener {
            override fun onClick(view: View?) {
                val isLike = view!!.tag.toString().toBoolean()
                ServerUtil.postRequestReplyLikeOrHate(mContext, data.id, isLike, object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObj: JSONObject) {
                        (mContext as ViewReplyDetailActivity).getChildRepliesFromServer()
                    }
                })
            }
        }

        likeCountTxt.setOnClickListener(ocl)
        hateCountTxt.setOnClickListener(ocl)

        return row
    }
}