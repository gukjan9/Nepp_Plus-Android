package com.gukjang.colosseum_210903

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gukjang.colosseum_210903.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        signUpBtn.setOnClickListener {

//            1. 입력 한 값 받아서

            val inputEmail = emailEdt.text.toString()
            val inputPw = pwEdt.text.toString()
            val inputNickname = nicknameEdt.text.toString()

//            2. 서버에서 가입 요청 -> 응답에 따라 어떤 처리?

            ServerUtil.putRequestSignUp(inputEmail, inputPw, inputNickname, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {
//                    가입 성공 (200) / 실패 (200 외의 값)
//                    실패 : 이메일양식 X,  중복된 이메일, 중복된 닉네임

//                    성공일때 -> {   } 내부 비워두자.
//                    실패시만 동작 -> message에 담긴 가입 실패 사유를 갖고 -> 토스트로 출력.

                    val code = jsonObj.getInt("code")

                    if (code == 200) {
//                        가입에 성공했습니다. 토스트
//                        이전 화면으로 복귀

                        runOnUiThread {
                            Toast.makeText(mContext, "가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                    else {
                        val message = jsonObj.getString("message")

                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

        // 이메일 중복 검사 함수
        checkEmailBtn.setOnClickListener {
            // 1. 입력한 이메일을 받아서
            val inputEmail = emailEdt.text.toString()

            // 2. 서버에 이메일 중복 확인 요청 -> 응답에 따라 결과 textView
            ServerUtil.getRequestDuplCheck("EMAIL", inputEmail, null)
        }

    }

    override fun setValues() {

    }
}