package com.gukjang.myfinalproject_210910

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.gukjang.myfinalproject_210910.databinding.ActivityMySettingBinding
import com.gukjang.myfinalproject_210910.utils.GlobalData

class MySettingActivity : BaseActivity() {
    lateinit var binding : ActivityMySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_setting)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.readyTimeLayout.setOnClickListener {

            

        }
    }

    override fun setValues() {
        titleTxt.text = "내 정보 설정"

        binding.nicknameTxt.text = GlobalData.loginUser!!.nickName

        // 로그인한 사람의 준비시간
        if(GlobalData.loginUser!!.readyMinute >= 60){
            val hour = GlobalData.loginUser!!.readyMinute / 60
            val minute = GlobalData.loginUser!!.readyMinute % 60

            binding.readyTimeTxt.text = "${hour}시간 ${minute}분"
        }
        else{
            binding.readyTimeTxt.text = "${GlobalData.loginUser!!.readyMinute}"
        }
    }
}