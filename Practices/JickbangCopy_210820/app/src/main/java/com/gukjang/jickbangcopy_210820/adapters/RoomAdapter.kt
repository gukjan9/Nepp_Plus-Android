package com.gukjang.jickbangcopy_210820.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gukjang.jickbangcopy_210820.R
import com.gukjang.jickbangcopy_210820.datas.RoomData
import java.util.zip.Inflater

class RoomAdapter(val mContext : Context,
                      val resID : Int,
                      val mList : ArrayList<RoomData>) : ArrayAdapter<RoomData>(mContext, resID, mList){

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        if(tempRow == null){
            tempRow = mInflater.inflate(R.layout.room_list_item, null)
        }

        val row = tempRow!!

        val data = mList[position]

        val roomImg = row.findViewById<ImageView>(R.id.roomImg)
        val roomCost = row.findViewById<TextView>(R.id.roomCost)
        val roomAddress = row.findViewById<TextView>(R.id.roomAddress)
        val roomFloor = row.findViewById<TextView>(R.id.roomFloor)
        val roomDesc = row.findViewById<TextView>(R.id.roomDesc)

        // 질문 3. 여기서 Int로 받는 법
        roomImg.setImageResource(data.img)
        roomCost.text = data.cost
        roomAddress.text = data.address
        roomFloor.text = data.floor
        roomDesc.text = data.desc

        return row
    }
}