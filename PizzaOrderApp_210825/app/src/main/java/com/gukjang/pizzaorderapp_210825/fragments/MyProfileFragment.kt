package com.gukjang.pizzaorderapp_210825.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gukjang.pizzaorderapp_210825.EditNicknameActivity
import com.gukjang.pizzaorderapp_210825.R
import kotlinx.android.synthetic.main.activity_edit_nickname.*
import kotlinx.android.synthetic.main.fragment_my_profile.*

class MyProfileFragment : Fragment() {

    val REQ_FOR_NICKNAME = 1001

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate( R.layout.fragment_my_profile, container, false )
    }

    // 닉네임 변경 창으로 이동하기
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        editNicknameBtn.setOnClickListener {
            val myIntent = Intent(requireContext(), EditNicknameActivity::class.java)   // 닉네임 변경 창으로
            startActivityForResult(myIntent, REQ_FOR_NICKNAME)          // 결과 (닉네임) 를 가지러
        }
    }

    // 닉네임 변경 후 돌아와서
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQ_FOR_NICKNAME){
            if(resultCode == Activity.RESULT_OK){
                val newNickname = data!!.getStringExtra("nickname")               // Intent? 여서 data? 이지만 - NULL 일리가 없음 data!!
                nickNameTxt.text = newNickname
            }
        }
    }
}