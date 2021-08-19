package com.gukjang.loginlogicpractice_210819

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val greetingMsg = "관리자님 환영합니다"
        val loginFailedMsg = "로그인에 실패했습니다"
        val enterID = "아이디를 입력하세요"
        val enterPW = "패스워드를 입력하세요"

        loginBtn.setOnClickListener {
            val id = idText.text.toString()
            val pw = pwText.text.toString()

            if(id == "admin" && pw == "qwer"){
                Toast.makeText(this, greetingMsg, Toast.LENGTH_SHORT).show()
            }
            else if(id == "" || pw == ""){
                if(id == "") Toast.makeText(this, enterID, Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, enterPW, Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, loginFailedMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}