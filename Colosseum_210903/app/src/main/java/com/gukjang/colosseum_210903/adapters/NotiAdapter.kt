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
import com.gukjang.colosseum_210903.datas.NotiData
import com.gukjang.colosseum_210903.datas.ReplyData
import com.gukjang.colosseum_210903.datas.TopicData
import com.gukjang.colosseum_210903.utils.ServerUtil
import org.json.JSONObject
import java.text.SimpleDateFormat

class NotiAdapter(
    val mContext : Context,
    resId : Int,
    val mList : List<NotiData>) : ArrayAdapter<NotiData>(mContext, resId, mList) {

        val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if(row == null) row = mInflater.inflate(R.layout.notification_list_item, null)
        row!!

        val data = mList[position]

        val notiTitleTxt = row.findViewById<TextView>(R.id.notiTitleTxt)

        notiTitleTxt.text = data.title

        return row
    }
}