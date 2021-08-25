package com.gukjang.pizzaorderapp_210825.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gukjang.pizzaorderapp_210825.R
import com.gukjang.pizzaorderapp_210825.datas.StoreData

class PizzaStoreAdapter(
    val mContext : Context,
    resId : Int,
    val mList : List<StoreData>) : ArrayAdapter<StoreData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if(tempRow == null){
            tempRow = mInflater.inflate(R.layout.store_list_item, null)     // root : null -> ListView 는 줄 정보 없음
        }
        val row = tempRow!!

        val data = mList[position]

        val logoImg = row.findViewById<ImageView>(R.id.logoImg)
        val nameTxt = row.findViewById<TextView>(R.id.nameTxt)

        nameTxt.text = data.name
        Glide.with(mContext).load(data.logoURL).into(logoImg)

        return row
    }
}