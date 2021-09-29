package com.gukjang.finalproject_210923.web

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerAPI {
    companion object{
        private val hostURL = "http://13.124.208.95:3000"

        // ServiceApi 객체 생성
        private var retrofit : Retrofit? = null

        fun getClient() : Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(hostURL) // 요청을 보낼 base url을 설정한다.
                    .addConverterFactory(GsonConverterFactory.create()) // JSON 파싱을 위한 GsonConverterFactory를 추가한다.
                    .build()
            }
            return retrofit!!
        }
    }
}