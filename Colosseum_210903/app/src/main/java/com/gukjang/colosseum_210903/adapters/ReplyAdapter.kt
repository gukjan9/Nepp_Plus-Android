package com.gukjang.colosseum_210903.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.SimpleAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gukjang.colosseum_210903.R
import com.gukjang.colosseum_210903.datas.ReplyData
import com.gukjang.colosseum_210903.datas.TopicData
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

        val selectedSideTxt = row.findViewById<TextView>(R.id.selectSideTxt)
        val writerNicknameTxt = row.findViewById<TextView>(R.id.writerNickNameTxt)
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

        val sdf = SimpleDateFormat("yyyy년 M월 d일")
        createdAtTxt.text = sdf.format(data.createdAt.time)

        return row
    }
}