package com.gukjang.myfinalproject_210910

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.gukjang.myfinalproject_210910.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {
   lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}