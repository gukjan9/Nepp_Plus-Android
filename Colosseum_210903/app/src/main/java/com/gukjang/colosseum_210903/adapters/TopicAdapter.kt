package com.gukjang.colosseum_210903.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gukjang.colosseum_210903.R
import com.gukjang.colosseum_210903.datas.TopicData

class TopicAdapter(
    val mContext : Context,
    resId : Int,
    val mList : List<TopicData>) : ArrayAdapter<TopicData>(mContext, resId, mList) {

        val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if(row == null) row = mInflater.inflate(R.layout.topic_list_item, null)
        row!!

        val data = mList[position]
        val topicImg = row.findViewById<ImageView>(R.id.topicImg)
        val topicTitleTxt = row.findViewById<TextView>(R.id.topicTitleTxt)

        topicTitleTxt.text = data.title

        Glide.with(mContext).load(data.imageURL).into(topicImg)

        return row
    }
}