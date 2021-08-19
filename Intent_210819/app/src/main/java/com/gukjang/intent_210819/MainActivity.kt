package com.gukjang.intent_210819

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val REQ_FOR_NICKNAME = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moveToOtherBtn.setOnClickListener {                 // 버튼이 눌리면 OtherActivity 로
            val myIntent = Intent(this, OtherActivity::class.java)
            startActivity(myIntent)
        }

        sendMsgBtn.setOnClickListener {
            val inputMsg = msgEdt.text.toString()
            val myIntent = Intent(this, ViewMessageActivity::class.java)
            myIntent.putExtra("message", inputMsg)
            startActivity(myIntent)
        }

        editNicknameBtn.setOnClickListener {
            val myIntent = Intent(this, EditNicknameActivity::class.java)
            startActivityForResult(myIntent, REQ_FOR_NICKNAME)          // 왕복
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQ_FOR_NICKNAME){
            if(resultCode == Activity.RESULT_OK){
                val newNick = data?.getStringExtra("nick")          // null
                nicknameTxt.text = newNick
            }
        }
    }
}