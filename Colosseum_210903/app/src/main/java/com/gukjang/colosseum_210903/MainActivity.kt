package com.gukjang.colosseum_210903

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gukjang.colosseum_210903.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        getMainDataFromServer()
    }

    // 서버에서 메인화면에 보여줄 정보 받아오기
    fun getMainDataFromServer(){
        ServerUtil.getRequestMainData(mContext, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {

            }
        })
    }
}