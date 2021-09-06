package com.gukjang.colosseum_210903

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.gukjang.colosseum_210903.utils.ContextUtil
import com.gukjang.colosseum_210903.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.json.JSONObject

class SignInActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        signInBth.setOnClickListener {
            val inputId = emailEdt.text.toString()
            val inputPw = passwordEdt.text.toString()

            // 서버에 이 데이터가 회원이 맞는지 확인 요청

            // 서버 로그인 시도 -> 서버에 다녀오면 어떻게 할건지? (인터페이스 객체)
            ServerUtil.postRequestSignIn(inputId, inputPw, object : ServerUtil.JsonResponseHandler{
                // 서버가 보내준 jsonObj 를 가지고 처리하는 코드 작성 영역
                override fun onResponse(jsonObj: JSONObject) {
                    // Log.d("화면에서 받은 JSON", jsonObj.toString())
                    val code = jsonObj.getInt("code")
                    val message = jsonObj.getString("message")

                    // 정상작동
                    if(code == 200){
                        // 서버가 내려주는 토큰값을 기기에 저장
                        val dataObj = jsonObj.getJSONObject("data")
                        val token = dataObj.getString("token")

                        ContextUtil.setToken(mContext, token)

                        // 메인화면으로 이동 + 로그인화면 종료
                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
                        finish()
                    }
                    else{
                        // 백그라운드에서 서버 통신 중 UI 에 토스트 띄우면 다른 스레드가 UI 조작하여 위험하다 판단
                        runOnUiThread{
                            Toast.makeText(mContext, message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

        signUpBtn.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }

        // 자동 로그인 체크박스의 값이 바뀔 때마다 저장
        autoLoginCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            ContextUtil.setAutoLogin(mContext, isChecked)
        }
    }

    override fun setValues() {
        autoLoginCheckBox.isChecked = ContextUtil.getAutoLogin((mContext))
    }
}