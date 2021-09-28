package com.gukjang.finalproject_210923.web
import com.gukjang.finalproject_210923.datas.JoinData
import com.gukjang.finalproject_210923.datas.JoinResponse
import retrofit2.http.*
import com.gukjang.finalproject_210923.datas.LoginData
import com.gukjang.finalproject_210923.datas.LoginResponse
import retrofit2.Call

interface ServerAPIService {
    @POST("/user/login")
    fun userLogin(@Body data : LoginData): Call<LoginResponse>

    @POST("/user/join")
    fun userJoin(@Body data : JoinData): Call<JoinResponse>
}