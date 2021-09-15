package com.gukjang.myfinalproject_210910.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gukjang.myfinalproject_210910.R
import com.gukjang.myfinalproject_210910.datas.AppointmentData

class AppointmentRecyclerAdapter(val mContext : Context, val mList : List<AppointmentData>) : RecyclerView.Adapter<AppointmentRecyclerAdapter.AppointmentViewHoler>() {
    class AppointmentViewHoler(view : View) : RecyclerView.ViewHolder(view){
        val titleTxt = view.findViewById<TextView>(R.id.titleTxt)
        val dateTimeTxt = view.findViewById<TextView>(R.id.dateTimeTxt)
        val placeNameTxt = view.findViewById<TextView>(R.id.placeNameTxt)
        val viewPlaceMapBtn = view.findViewById<ImageView>(R.id.viewPlaceMapBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHoler {
        val view = LayoutInflater.from(mContext).inflate(R.layout.appointment_list_item, parent, false)
        return AppointmentViewHoler(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHoler, position: Int) {

    }

    override fun getItemCount() = mList.size
}