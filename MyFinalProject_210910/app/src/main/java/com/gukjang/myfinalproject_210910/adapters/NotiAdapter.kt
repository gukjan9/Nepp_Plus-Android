package com.gukjang.myfinalproject_210910.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.gukjang.myfinalproject_210910.R
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

        return row
    }
}