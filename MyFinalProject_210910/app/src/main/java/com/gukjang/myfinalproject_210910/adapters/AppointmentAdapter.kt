package com.gukjang.myfinalproject_210910.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gukjang.myfinalproject_210910.R
import com.gukjang.myfinalproject_210910.ViewMapActivity
import com.gukjang.myfinalproject_210910.datas.AppointmentData

class AppointmentAdapter(
    val mContext : Context,
    resId : Int,
    val mList : List<AppointmentData>) : ArrayAdapter<AppointmentData>(mContext, resId, mList) {

        val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if(row == null) row = mInflater.inflate(R.layout.appointment_list_item, null)
        row!!

        val data = mList[position]

        val titleTxt = row.findViewById<TextView>(R.id.titleTxt)
        val dateTimeTxt = row.findViewById<TextView>(R.id.dateTimeTxt)
        val placeNameTxt = row.findViewById<TextView>(R.id.placeNameTxt)
        val viewPlaceMapBtn = row.findViewById<ImageView>(R.id.viewPlaceMapBtn)

        titleTxt.text = data.title
        // dateTimeTxt.text = data.datetime
        placeNameTxt.text = data.placeName

        viewPlaceMapBtn.setOnClickListener {
            val myIntent = Intent(mContext, ViewMapActivity::class.java)
            // startActivity() - adapter 를 상속받기 때문에 이 기능이 없음
            myIntent.putExtra("appointment", data)          // putExtra 에러 시 AppointmentData 가서 Serializable 달아주기
            mContext.startActivity(myIntent)
        }

        return row
    }
}