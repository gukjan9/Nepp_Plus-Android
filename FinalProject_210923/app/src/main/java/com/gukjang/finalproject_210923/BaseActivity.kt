package com.gukjang.finalproject_210923

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gukjang.finalproject_210923.web.ServerAPI
import com.gukjang.finalproject_210923.web.ServerAPIService
import retrofit2.Retrofit

abstract class BaseActivity : AppCompatActivity() {
    lateinit var mContext : Context

    private lateinit var retrofit : Retrofit
    lateinit var apiService : ServerAPIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        retrofit = ServerAPI.getClient()
        apiService = retrofit.create(ServerAPIService::class.java)
    }

    abstract fun setEvents()
    abstract fun setValues()
}