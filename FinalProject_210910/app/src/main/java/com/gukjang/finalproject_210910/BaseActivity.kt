package com.gukjang.finalproject_210910

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BaseActivity: AppCompatActivity() {
    lateinit var mContext : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
    }

    abstract fun setupEvents()
    abstract fun setValues()
}