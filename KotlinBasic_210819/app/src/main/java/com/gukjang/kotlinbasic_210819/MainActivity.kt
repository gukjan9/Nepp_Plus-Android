package com.gukjang.kotlinbasic_210819

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logBtn01.setOnClickListener {

            Log.d("메인화면", "첫 번째 로그 버튼 눌림")      // debug
            Log.e("메인화면", "e로 로그 찍어봄")            // error
            Log.i("메인화면", "i로 로그 찍어봄")            //
        }

        logBtn02.setOnClickListener {
            Log.d("메인화면", "두 번째 로그 버튼 눌림")
        }

        toastBtn.setOnClickListener {
            Toast.makeText(this, "연습용 토스트 띄우기", Toast.LENGTH_SHORT).show()
        }

        toastBtn02.setOnClickListener {
            Toast.makeText(this, "두 번째 토스트", Toast.LENGTH_SHORT).show()
        }
    }
}