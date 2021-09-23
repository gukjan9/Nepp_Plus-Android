package com.gukjang.myfinalproject_210910.utils

import android.content.Context

class ContextUtil {
    companion object {
        // 토큰 등을 저장하는 메모장의 파일 명
        private val prefName = "FinalProjectPref"

        // 여러 항목 저장 가능

        // 토큰
        val TOKEN = "TOKEN"
        val MY_READY_MINUTE = "MY_READY_MINUTE"

        // 토큰 저장 / 조회
        fun setToken(context : Context, token : String){
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(TOKEN, token).apply()
        }

        fun getToken(context: Context) : String{
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(TOKEN, "")!!
        }

        fun setMyReadyMinute(context : Context, minute : Int){
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putInt(MY_READY_MINUTE, minute).apply()
        }

        fun getMyReadyMinute(context: Context) : Int{
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getInt(MY_READY_MINUTE, 0)
        }
    }
}