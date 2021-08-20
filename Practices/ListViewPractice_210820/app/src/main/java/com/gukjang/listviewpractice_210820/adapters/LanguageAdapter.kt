package com.gukjang.listviewpractice_210820.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gukjang.listviewpractice_210820.R
import com.gukjang.listviewpractice_210820.datas.LanguageData
import java.util.zip.Inflater

class LanguageAdapter(val mContext : Context,
                      val resID : Int,
                      val mList : ArrayList<LanguageData>) : ArrayAdapter<LanguageData>(mContext, resID, mList){

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        if(tempRow == null){
            tempRow = mInflater.inflate(R.layout.lang_list_item, null)
        }

        val row = tempRow!!

        val data = mList[position]

        val langLogo = row.findViewById<ImageView>(R.id.langLogo)
        val langName = row.findViewById<TextView>(R.id.langName)
        val langMotto = row.findViewById<TextView>(R.id.langMotto)

        langLogo.setImageResource(data.logo)
        langName.text = data.lang
        langMotto.text = data.motto

        return row
    }
}