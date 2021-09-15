package com.gukjang.myfinalproject_210910.web

import android.content.Context
import com.google.gson.GsonBuilder
import com.gukjang.myfinalproject_210910.utils.ContextUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerAPI {
    companion object {
        // 서버 주소
        private val hostURL = "http://3.36.146.152"

        // Retrofit 형태의 변수가 OkHttpClient 처럼 실제 호출 담당
        // 레트로핏 객체는 하나만 만들어두고 여러 화면에서 공유해서 사용
        // SingleTon 패턴 사용 - 객체를 하나로 유지하자
        private var retrofit : Retrofit? = null

        fun getRetrofit(context : Context) : Retrofit {
            if(retrofit == null) {
                // API 요청이 발생하면 -> 가로채서 -> 헤더를 추가한다 -> API 요청을 이어간다
                    // 모든 통신을 헤더에 토큰을 달아서 진행
                val interceptor = Interceptor{
                    with(it){
                        val newRequest = request().newBuilder()
                            .addHeader("X-Http-Token", ContextUtil.getToken(context))
                            .build()

                        proceed(newRequest)
                    }
                }
                // 클라이언트한테 인터셉터 달아주기
                val myClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

                val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

                // 기본 클라이언트 -> 직접 만든 client를 이용해서 통신
                retrofit = Retrofit.Builder()
                    .baseUrl(hostURL)
                    .client(myClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!
        }
    }
}