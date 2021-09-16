package com.gukjang.myfinalproject_210910.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gukjang.myfinalproject_210910.web.ServerAPI
import com.gukjang.myfinalproject_210910.web.ServerAPIService
import retrofit2.Retrofit

// 화면에 나올건 아니고 베이스만 - abstract
abstract class BaseFragment : Fragment() {
    lateinit var mContext : Context

    private lateinit var retrofit: Retrofit
    lateinit var apiService: ServerAPIService

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = requireContext()
        retrofit = ServerAPI.getRetrofit(mContext)
        apiService = retrofit.create(ServerAPIService::class.java)
    }

    abstract fun setupEvents()
    abstract fun setValues()
}