package com.gukjang.myfinalproject_210910

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.gukjang.myfinalproject_210910.utils.ContextUtil
import com.gukjang.myfinalproject_210910.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

                apiService.getRequestMyInfo().enqueue(object : Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if(response.isSuccessful){
                            val basicResponse = response.body()!!
                            GlobalData.loginUser = basicResponse.data.user
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }

                })
                val myHandler = Handler(Looper.getMainLooper())
                myHandler.postDelayed({
                    val myIntent : Intent

                    if(GlobalData.loginUser != null){
                        myIntent = Intent(mContext, MainActivity::class.java)
                    }
                    else{
                        myIntent = Intent(mContext, LoginActivity::class.java)
                    }
                })
            }
        }, 2500)
    }
}