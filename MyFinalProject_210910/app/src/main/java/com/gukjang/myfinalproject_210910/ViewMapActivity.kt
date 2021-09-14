package com.gukjang.myfinalproject_210910

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gukjang.myfinalproject_210910.datas.AppointmentData

class ViewMapActivity : BaseActivity() {
    lateinit var mAppointmentData : AppointmentData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_map)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mAppointmentData = intent.getSerializableExtra("appointment") as AppointmentData        // 위에서 AppointmentData 로 받기로 했음
    }
}