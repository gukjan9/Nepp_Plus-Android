package com.gukjang.myfinalproject_210910.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gukjang.myfinalproject_210910.R
import com.gukjang.myfinalproject_210910.ViewMapActivity
import com.gukjang.myfinalproject_210910.datas.AppointmentData
import com.gukjang.myfinalproject_210910.datas.PlaceData
import com.gukjang.myfinalproject_210910.datas.UserData

class MyFriendSpinnerAdapter(
    val mContext : Context,
    resId : Int,
    val mList : List<UserData>) : ArrayAdapter<UserData>(mContext, resId, mList) {

        val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if(row == null) row = mInflater.inflate(R.layout.friend_list_item, null)
        row!!

        val data = mList[position]

        val friendProfileImg = row.findViewById<ImageView>(R.id.friendsProfileImg)
        val nicknameTxt = row.findViewById<TextView>(R.id.nicknameTxt)
        val socialLoginImg = row.findViewById<ImageView>(R.id.socialLoginImg)

        nicknameTxt.text = data.nickName

        Glide.with(context).load(data.profileImgURL).into(friendProfileImg)

        when(data.provider){
            "facebook" -> {
                socialLoginImg.setImageResource(R.drawable.facebook_icon)
                socialLoginImg.visibility = View.VISIBLE
            }
            "kakao" -> {
                socialLoginImg.setImageResource(R.drawable.kakaotalk_icon)
                socialLoginImg.visibility = View.VISIBLE
            }
            else -> {
                socialLoginImg.visibility = View.GONE
            }
        }

        return row
    }

    // Spinner
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}