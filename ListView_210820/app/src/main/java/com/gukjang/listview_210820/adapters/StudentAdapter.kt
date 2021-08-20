package com.gukjang.listview_210820.adapters

import android.content.Context
import android.widget.ArrayAdapter
import com.gukjang.listview_210820.datas.StudentData

class StudentAdapter(val mContext : Context,
                     val resID : Int,
                     val mList : ArrayList<StudentData>) : ArrayAdapter<StudentData>(mContext, resID, mList) {

}