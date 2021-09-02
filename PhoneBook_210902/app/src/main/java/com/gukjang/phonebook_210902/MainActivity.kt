package com.gukjang.phonebook_210902

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    // 1. 화면에 들어오면 -> 파일에 저장된 "이름,폰번,1997-04-14" 문장 불러내기
    // 안드로이드에서 파일 다루는법

    // 2. 불러낸 데이터들을 ListView 에 뿌려준다.

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
//        addPhoneNumBtn.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//                TODO("Not yet implemented")
//            }

        // JAVA의 interface 를 개량한 버전의 코드 (위 참조)
        // EditPhoneNumActivity 로 이동 : intent
        addPhoneNumBtn.setOnClickListener{
            val myIntent = Intent(mContext, EditPhoneNumActivity::class.java)
            startActivity(myIntent)         // (= super.startActivity)
        }
    }

    override fun setValues() {
        TODO("Not yet implemented")
    }
}