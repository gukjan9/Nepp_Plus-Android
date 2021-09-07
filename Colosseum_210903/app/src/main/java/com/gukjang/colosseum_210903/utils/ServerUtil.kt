package com.gukjang.colosseum_210903.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

// 단순 기능 수행 -> 서버에 요청 날리고 -> 응답을 화면에 전달
class ServerUtil {

    // 응답을 화면에 전달 : 나에게 발생한 이벤트를 화면단에서 대신 해달라고 한다. (Interface 활용)
    interface JsonResponseHandler{
        fun onResponse(jsonObj : JSONObject)
    }

    // 이 안에 만드는 변수 / 함수는 전부 static 처럼 동작
    companion object{

        // 호스트 주소를 변수로 저장 (ServerUtil 안에서만)
        private val HOST_URL = "http://54.180.52.26"

        // 로그인 기능 실행 함수
        // ID/ PW 전달 -> 서버에 다녀오면 어떤 일을 할건지 : 인터페이스 객체 같이 전달

        fun postRequestSignIn(id: String, pw: String, handler : JsonResponseHandler?){
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

            // 만들어진 요청 호출 -> 응답 왔을 때 분석
            // 호출을 하면 -> 응답 받아서 처리 (처리할 코드 등록)
            client.newCall(request).enqueue(object : Callback {
                //
                override fun onFailure(call: Call, e: IOException) {

                }

                // 로그인 성공, 실패 - 응답 온 경우
                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버 응답 본문", jsonObj.toString())

                    // 코드값 추출 연습
//                    val code = jsonObj.getInt("code")
//                    Log.d("코드값", code.toString())

                    // 받아낸 jsonObj를 통째로 화면의 응답 처리 코드로
                    handler?.onResponse(jsonObj)
                }

            })
        }

        // 회원가입 요청 함수
       fun putRequestSignUp(email : String, password : String, nickname : String, handler : JsonResponseHandler){
            val urlString = "${HOST_URL}/user"
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("nick_name", nickname)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .put(formData)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonobj = JSONObject(bodyString)
                    Log.d("서버 응답 본문", jsonobj.toString())
                    handler?.onResponse(jsonobj)
                }

            })
        }

        // 이메일 / 닉네임 중복 확인 함수
        // get method 로 서버에 요청 -> URL 을 적을 때 (query) 파라미터들도 같이 적어줘야함
        fun getRequestDuplCheck(type : String, value : String, handler: JsonResponseHandler?){
            // 호스트주소/엔드포인트 기반으로 파라미터들을 쉽게 첨부할 수 있도록 도와주는 변수
            val url = "${HOST_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()

            // URL 뒤에 파라미터 추가 value=~&type=~
            url.addEncodedQueryParameter("type", type)
            url.addEncodedQueryParameter("value", value)

            val urlString = url.toString()

            Log.d("완성된 URL", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonobj = JSONObject(bodyString)
                    Log.d("서버 응답", jsonobj.toString())
                    handler?.onResponse(jsonobj)
                }
            })
        }

        // 메인화면 데이터 가져오기
        // 저장된 토큰값을 서버에 전송 -> 메모장을 열기 위한 재료로 Context 가 필요함
        fun getRequestMainData(context: Context, handler1: JsonResponseHandler, handler: JsonResponseHandler?){
            val url = "${HOST_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()

//            url.addEncodedQueryParameter("type", type)
//            url.addEncodedQueryParameter("value", value)

            val urlString = url.toString()

            Log.d("완성된 URL", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonobj = JSONObject(bodyString)
                    Log.d("서버 응답", jsonobj.toString())
                    handler?.onResponse(jsonobj)
                }
            })
        }

        // 토론 상세 정보 (특정 주제에 대해서만) 가져오기
        fun getRequestTopicDetail(context : Context, topicId : Int, handler: JsonResponseHandler?){
            val url = "${HOST_URL}/topic".toHttpUrlOrNull()!!.newBuilder()
            // 주소/3 등 어떤 데이터를 보고 싶은지, /숫자 형태로 이어붙이는 주소 -> path
            // 주소?type=Email 등 파라미터이름=값 형태로 이어붙이는 주소 -> Query

            url.addPathSegment(topicId.toString())

            url.addEncodedQueryParameter("order_type", "NEW")
//            url.addEncodedQueryParameter("value", value)

            val urlString = url.toString()

            Log.d("완성된 URL", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonobj = JSONObject(bodyString)
                    Log.d("서버 응답", jsonobj.toString())
                    handler?.onResponse(jsonobj)
                }
            })
        }
    }
}