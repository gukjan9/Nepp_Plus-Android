package com.gukjang.myfinalproject_210910.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gukjang.myfinalproject_210910.R
import com.gukjang.myfinalproject_210910.ViewAppointmentDetailActivity
import com.gukjang.myfinalproject_210910.ViewMapActivity
import com.gukjang.myfinalproject_210910.datas.AppointmentData
import java.text.SimpleDateFormat

class AppointmentRecyclerAdapter(
    val mContext : Context,
    val mList : List<AppointmentData>) : RecyclerView.Adapter<AppointmentRecyclerAdapter.AppointmentViewHoler>() {
    class AppointmentViewHoler(view : View) : RecyclerView.ViewHolder(view){
        val titleTxt = view.findViewById<TextView>(R.id.titleTxt)
        val dateTimeTxt = view.findViewById<TextView>(R.id.dateTimeTxt)
        val placeNameTxt = view.findViewById<TextView>(R.id.placeNameTxt)
        val viewPlaceMapBtn = view.findViewById<ImageView>(R.id.viewPlaceMapBtn)
        val rootLayout = view.findViewById<LinearLayout>(R.id.rootLayout)

        // val dateTimeSDF = SimpleDateFormat("M/d h:mm")

        fun bind(context : Context, data : AppointmentData){
            titleTxt.text = data.title

            // Date 형태로 파싱 -> String 으로 가공
            dateTimeTxt.text = data.getFormattedDateTime()
            placeNameTxt.text = data.placeName

            // 이벤트 처리
            viewPlaceMapBtn.setOnClickListener {
                val myIntent = Intent(context, ViewMapActivity::class.java)
                myIntent.putExtra("appointment", data)
                context.startActivity(myIntent)
            }

            rootLayout.setOnClickListener {
                val myIntent = Intent(context, ViewAppointmentDetailActivity::class.java)
                myIntent.putExtra("appointment",  data)
                context.startActivity(myIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHoler {
        val view = LayoutInflater.from(mContext).inflate(R.layout.appointment_list_item, parent, false)
        return AppointmentViewHoler(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHoler, position: Int) {
        val data = mList[position]

        holder.bind(mContext, data)
    }

    override fun getItemCount() = mList.size
}