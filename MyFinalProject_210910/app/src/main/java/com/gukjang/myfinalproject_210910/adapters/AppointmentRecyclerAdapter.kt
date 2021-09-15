package com.gukjang.myfinalproject_210910.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gukjang.myfinalproject_210910.R
import com.gukjang.myfinalproject_210910.ViewMapActivity
import com.gukjang.myfinalproject_210910.datas.AppointmentData
import java.text.SimpleDateFormat

class AppointmentRecyclerAdapter(val mContext : Context, val mList : List<AppointmentData>) : RecyclerView.Adapter<AppointmentRecyclerAdapter.AppointmentViewHoler>() {
    class AppointmentViewHoler(view : View) : RecyclerView.ViewHolder(view){
        val titleTxt = view.findViewById<TextView>(R.id.titleTxt)
        val dateTimeTxt = view.findViewById<TextView>(R.id.dateTimeTxt)
        val placeNameTxt = view.findViewById<TextView>(R.id.placeNameTxt)
        val viewPlaceMapBtn = view.findViewById<ImageView>(R.id.viewPlaceMapBtn)

        val dateTimeSDF = SimpleDateFormat("M/d h:mm")

        fun bind(data : AppointmentData){
            titleTxt.text = data.title

            // Date 형태로 파싱 -> String 으로 가공
            dateTimeTxt.text = dateTimeSDF.format(data.datetime)
            placeNameTxt.text = data.placeName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHoler {
        val view = LayoutInflater.from(mContext).inflate(R.layout.appointment_list_item, parent, false)
        return AppointmentViewHoler(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHoler, position: Int) {
        val data = mList[position]

        holder.bind(data)

        holder.viewPlaceMapBtn.setOnClickListener {
            val myIntent = Intent(mContext, ViewMapActivity::class.java)
            myIntent.putExtra("appointment", data)
            mContext.startActivity(myIntent)
        }
    }

    override fun getItemCount() = mList.size
}