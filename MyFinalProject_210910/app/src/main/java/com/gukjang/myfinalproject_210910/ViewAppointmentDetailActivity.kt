package com.gukjang.myfinalproject_210910

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.gukjang.myfinalproject_210910.databinding.ActivityViewAppointmentDetailBinding
import com.gukjang.myfinalproject_210910.datas.AppointmentData

class ViewAppointmentDetailActivity : BaseActivity() {
    lateinit var binding : ActivityViewAppointmentDetailBinding

    lateinit var mAppointmentData : AppointmentData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_appointment_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        titleTxt.text = "약속 상세 확인"

        mAppointmentData = intent.getSerializableExtra("appointment") as AppointmentData

        binding.titleTxt.text = mAppointmentData.title
        binding.placeTxt.text = mAppointmentData.placeName

        // 참여인원 수
        binding.invitedFriendsCountTxt.text = "(참여인원 : ${mAppointmentData.invitedFriendList.size}명"

        // 약속 시간

        // 도착지 좌표 지도에 설정

        //

    }
}