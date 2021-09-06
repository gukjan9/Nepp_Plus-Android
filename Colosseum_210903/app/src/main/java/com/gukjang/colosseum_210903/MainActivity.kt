package com.gukjang.colosseum_210903

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.gukjang.colosseum_210903.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

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

            // 서버 로그인 시도 -> 서버에 다녀오면 어떻게 할건지? (인터페이스 객체)
            ServerUtil.postRequestSignIn(inputId, inputPw, object : ServerUtil.JsonResponseHandler{
                // 서버가 보내준 jsonObj 를 가지고 처리하는 코드 작성 영역
                override fun onResponse(jsonObj: JSONObject) {
                    // Log.d("화면에서 받은 JSON", jsonObj.toString())
                    val code = jsonObj.getInt("code")
                    val message = jsonObj.getString("message")

                    // 정상작동
                    if(code == 200){

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
    }

    override fun setValues() {

    }
}