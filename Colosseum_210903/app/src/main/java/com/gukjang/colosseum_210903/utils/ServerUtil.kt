package com.gukjang.colosseum_210903.utils

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

// 단순 기능 수행 -> 서버에 요청 날리고 -> 응답을 화면에 전달
class ServerUtil {
    // 이 안에 만드는 변수 / 함수는 전부 static 처럼 동작
    companion object{
        // 호스트 주소를 변수로 저장 (ServerUtil 안에서만)
        private val HOST_URL = "http://54.180.52.26"

        // 로그인 기능 실행 함수
        fun postRequestSignIn(id: String, pw: String){
            // 1. 어느 URL 로 갈 것인가? HOST_URL + Endpoint
            val urlString = "${HOST_URL}/user"

            // 2. 어떤 데이터를 들고 갈 것인가
            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .build()

            // 3. 어떤 방식으로 접근? Request 에 모두 모아서 하나의 Request 정보로
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()

            // 만들어진 request를 실제로 호출해야함
            // 요청 -> 앱이 클라이언트로 동작
            val client = OkHttpClient()
            client.newCall(request)
        }

    }
}