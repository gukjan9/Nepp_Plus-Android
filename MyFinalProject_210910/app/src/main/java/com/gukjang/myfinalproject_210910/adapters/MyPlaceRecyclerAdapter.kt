package com.gukjang.myfinalproject_210910.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gukjang.myfinalproject_210910.R
import com.gukjang.myfinalproject_210910.datas.PlaceData

class MyPlaceRecyclerAdapter(val mContext : Context, val mList : List<PlaceData>): RecyclerView.Adapter<MyPlaceRecyclerAdapter.MyViewHolder>(){
    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val placeNameTxt = view.findViewById<TextView>(R.id.placeNameTxt)
        val isPrimaryTxt = view.findViewById<TextView>(R.id.isPrimaryTxt)
        val viewPlaceMapBtn = view.findViewById<ImageView>(R.id.viewPlaceMapBtn)
        val backgroundLayout = view.findViewById<LinearLayout>(R.id.backgroundLayout)

        fun setRealData(data : PlaceData){
            placeNameTxt.text = data.name

            if(data.isPrimary) {
                isPrimaryTxt.visibility = View.VISIBLE
            }
            else{
                isPrimaryTxt.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.my_place_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // 실제 데이터를 뿌려주는 부분
//        val data = mList[position]

        // 실제 장소 이름만 우선 출력
//        holder.placeNameTxt.text = data.name

        holder.setRealData(mList[position])

        holder.viewPlaceMapBtn.setOnClickListener {
            Toast.makeText(mContext, "지도 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show()
        }

        holder.backgroundLayout.setOnClickListener{
            Toast.makeText(mContext, "${mList[position].name}을 클릭", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = mList.size                // return mList.size

}