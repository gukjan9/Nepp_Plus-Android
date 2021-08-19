package com.gukjang.kotlinuipractice_210819

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moveToTextViewBtn.setOnClickListener {              // 입력한 메세지를 변수에 저장해서 텍스트뷰의 text 속성으로 대입
            val inputMessage = messageEdt.text.toString()
            resultTxt.text = inputMessage
        }

        toastBtn.setOnClickListener {
            val inputMessage = messageEdt.text.toString()

            Toast.makeText(this, inputMessage, Toast.LENGTH_SHORT).show()
        }
    }
}