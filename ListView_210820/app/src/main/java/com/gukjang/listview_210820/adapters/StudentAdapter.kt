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
        var tempRow = convertView           // 재사용에 사용할 view 담는 변수 (null 상태라는건 재사용할만큼 충분히 만들어지지 않음, 아직 모자라니 inflate 더 하자)

        if(tempRow == null){
            tempRow = mInflater.inflate(R.layout.student_list_item, null)           // 필요할 때만 inflate
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