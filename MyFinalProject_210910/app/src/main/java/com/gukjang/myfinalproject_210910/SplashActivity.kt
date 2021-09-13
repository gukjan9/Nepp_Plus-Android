package com.gukjang.myfinalproject_210910

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gukjang.myfinalproject_210910.utils.ContextUtil
import com.gukjang.myfinalproject_210910.utils.GlobalData

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
        val myhandler = Handler(Looper.getMainLooper())
        myhandler.postDelayed({
            val myIntent : Intent

            if(ContextUtil.getToken(mContext) != ""){
                GlobalData.loginUser =
                myIntent = Intent(mContext, MainActivity::class.java)
            }
            else{
                myIntent = Intent(mContext, LoginActivity::class.java)
            }
        }, 2500)
    }
}