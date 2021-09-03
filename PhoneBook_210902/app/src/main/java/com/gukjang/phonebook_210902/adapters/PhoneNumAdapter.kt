package com.gukjang.phonebook_210902.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gukjang.phonebook_210902.R
import com.gukjang.phonebook_210902.datas.PhoneNumData

class PhoneNumAdapter(val mContext : Context,
                      resId : Int,
                      val mList : List<PhoneNumData>) : ArrayAdapter<PhoneNumData>(mContext, resId, mList) {

    private val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // getView : xml -> position 에 맞는 데이터를 입혀서 listView 에 각 position 에 뿌려주기
        var row = convertView
        if(row == null){
            row = mInflater.inflate(R.layout.phone_num_list_item, null)
        }
        row!!           // null 이 절대 아님

        val nameTxt = row.findViewById<TextView>(R.id.nameTxt)              // 위에 row!! 안 적으면 row!!.findViewById 한 번 써주면 된다
        val birthDayTxt = row.findViewById<TextView>(R.id.birthDayTxt)
        val phoneNumTxt = row.findViewById<TextView>(R.id.phoneNumTxt)
        val dialImg = row.findViewById<ImageView>(R.id.dialImg)


        // 폰 번호 꺼내오기
        val data = mList[position]          // mList.get(position) - Java 스러운

        nameTxt.text = data.name            // nameTxt.setText(data.getName())
        phoneNumTxt.text = data.phoneNum

        birthDayTxt.text = data.getFormattedBirthday()

        dialImg.setOnClickListener{
            val myUri = Uri.parse("tel:${data.phoneNum}")
            val myIntent = Intent(Intent.ACTION_DIAL, myUri)

            // 어댑터 안에서 액티비티의 기능인 startActivity 함수를 실행하고 싶다.
            mContext.startActivity(myIntent)
        }

        return row
    }
}