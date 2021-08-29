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
    resId : Int,                // resId 는 한번만 쓸 것이기 때문에 val 안 붙임
    val mList : List<StoreData>) : ArrayAdapter<StoreData>(mContext, resId, mList) {            // StoreData 를 가지고 PizzaStoreList 에 뿌려줌

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView       // convertView - 재사용성에 관련된 변수
        if(tempRow == null){
            tempRow = mInflater.inflate(R.layout.store_list_item, null)     // root : null -> ListView 는 줄 정보 없음
        }
        val row = tempRow!!         // 절대 null 아님

        // ListView 에 들어갈 데이터 가공
        val data = mList[position]

        val logoImg = row.findViewById<ImageView>(R.id.logoImg)
        val nameTxt = row.findViewById<TextView>(R.id.nameTxt)

        nameTxt.text = data.name
        Glide.with(mContext).load(data.logoURL).into(logoImg)

        return row
    }
}