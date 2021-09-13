package com.gukjang.myfinalproject_210910

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.gukjang.myfinalproject_210910.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.addAppointmentBtn.setOnClickListener{
            val myIntent = Intent(mContext, EditAppointmentActivity::class.java)
            startActivity(myIntent)
            finish()
        }

    }

    override fun setValues() {

    }
}