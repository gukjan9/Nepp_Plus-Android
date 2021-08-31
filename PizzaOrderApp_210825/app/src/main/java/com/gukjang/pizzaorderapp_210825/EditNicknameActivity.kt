package com.gukjang.pizzaorderapp_210825

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_nickname.*

class EditNicknameActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_nickname)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        okBtn.setOnClickListener {
            val inputNickname = newNicknameEdt.text.toString()

            val resultIntent = Intent()                                  // 복귀 Intent -> 돌아갈 땐 출발-도착 안 적어도됨 (왕복)
            resultIntent.putExtra("nickname", inputNickname)      // putExtra : 데이터 담기
            setResult(RESULT_OK, resultIntent)                          // resultIntent 들고 간다
            finish()
        }
    }

    override fun setValues() {

    }


}