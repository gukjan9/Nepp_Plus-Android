package com.gukjang.colosseum_210903

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.gukjang.colosseum_210903.datas.UserData
import com.gukjang.colosseum_210903.utils.ContextUtil
import com.gukjang.colosseum_210903.utils.GlobalData
import com.gukjang.colosseum_210903.utils.ServerUtil
import org.json.JSONObject

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({
            // 1. 자동 로그인 여부 판단 -> 상황에 따라 다른 화면으로 넘어가게
            val myIntent : Intent

            if(ContextUtil.getAutoLogin(mContext) && ContextUtil.getToken(mContext) != ""){
                // 둘다 만족 : 자동로그인 -> 메인화면으로 이동
                myIntent = Intent(mContext, MainActivity::class.java)

                ServerUtil.getRequestUserData(mContext, object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObj: JSONObject) {
                        val dataObj = jsonObj.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")

                        val loginUserData = UserData.getUserDataFromJson(userObj)

                        // 서버가 알려준 로그인 한 사람 데이터를 모든 화면과 공유 (GlobalData 클래스 활용)
                        GlobalData.loginUser = loginUserData

                        Log.d("자동로그인", "로그인한 사람 닉네임 - ${GlobalData.loginUser?.nickname}")
                    }
                })
            }
            else{
                myIntent = Intent(mContext, SignInActivity::class.java)
            }

            startActivity(myIntent)
            finish()
        }, 2500)
    }
}