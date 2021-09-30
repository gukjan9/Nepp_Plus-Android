package com.gukjang.finalproject_210923.web
import com.gukjang.finalproject_210923.datas.JoinData
import com.gukjang.finalproject_210923.datas.JoinResponse
import retrofit2.http.*
import com.gukjang.finalproject_210923.datas.LoginData
import com.gukjang.finalproject_210923.datas.LoginResponse
import retrofit2.Call

interface ServerAPIService {
    // 회원가입
    @POST("/user/join")
    fun userJoin(@Body data : JoinData): Call<JoinResponse>

    // 아이디, 이메일, 닉네임 중복검사
    @GET("/user/check")
    fun userCheck(
        @Query("type") type : String,
        @Query("value") value : String): Call<JoinResponse>

    // 로그인
    @POST("/user/login")
    fun userLogin(@Body data : LoginData): Call<LoginResponse>
}