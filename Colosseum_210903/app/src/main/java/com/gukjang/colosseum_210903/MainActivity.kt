package com.gukjang.colosseum_210903

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        signInBth.setOnClickListener {
            val inputId = emailEdt.text.toString()
            val inputPw = passwordEdt.text.toString()

            // 서버에 이 데이터가 회원이 맞는지 확인 요청

        }
    }

    override fun setValues() {

    }
}