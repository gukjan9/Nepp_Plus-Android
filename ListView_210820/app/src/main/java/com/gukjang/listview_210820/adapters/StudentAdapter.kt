package com.gukjang.listview_210820.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.gukjang.listview_210820.R
import com.gukjang.listview_210820.datas.StudentData
import java.util.zip.Inflater

class StudentAdapter(val mContext : Context,
                     val resID : Int,
                     val mList : ArrayList<StudentData>) : ArrayAdapter<StudentData>(mContext, resID, mList) {

    val mInflater =  LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        if(tempRow == null){
            tempRow = mInflater.inflate(R.layout.student_list_item, null)
        }

        val row = tempRow!!

        val data = mList[position]

        val nameTxt = row.findViewById<TextView>(R.id.nameTxt)
        val ageTxt = row.findViewById<TextView>(R.id.ageTxt)
        val addressTxt = row.findViewById<TextView>(R.id.addressTxt)

        nameTxt.text = data.name
        ageTxt.text = "(${2021 - data.birthYear + 1}세)"
        addressTxt.text = data.address

        return row
    }
}