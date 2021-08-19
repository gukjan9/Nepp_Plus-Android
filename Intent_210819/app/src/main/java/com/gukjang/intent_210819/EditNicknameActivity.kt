package com.gukjang.intent_210819

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_nickname.*

class EditNicknameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_nickname)

        modifyBtn.setOnClickListener {
            // 입력한 닉네임 저장
            val inputNickname = nicknameEdt.text.toString()

            // 이전 화면으로 데이터 들고 복귀
            val resultIntent = Intent()             // 왕복이어서 필요 x
            resultIntent.putExtra("nick", inputNickname)

            // ok 눌러야 실행 + 돌아갈 데이터 세팅
            setResult(Activity.RESULT_OK, resultIntent)         // 확인 누른거 맞는지
            finish()
        }
    }
}