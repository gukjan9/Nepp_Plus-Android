package com.gukjang.myfinalproject_210910

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.gukjang.myfinalproject_210910.databinding.ActivityLoginBinding
import java.security.MessageDigest

import com.facebook.login.LoginResult

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager

import com.facebook.login.widget.LoginButton
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.gukjang.myfinalproject_210910.utils.ContextUtil
import com.kakao.sdk.user.UserApiClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class LoginActivity : BaseActivity() {
    lateinit var binding : ActivityLoginBinding
    lateinit var  callbackManager : CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        callbackManager = CallbackManager.Factory.create();

        binding.loginButton.setReadPermissions("email")

        binding.facebookLoginBtn.setOnClickListener {

            // 커스텀 버튼에 로그인 하고 돌아온 callback 따로 설정
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                    Log.d("로그인 성공", "우리가 만든 버튼으로 성공")

                    // 나의 (로그인한 사람) 정보 (GraphRequest) 를 받아오는데 토큰 활용
                    val graphRequest = GraphRequest.newMeRequest(result?.accessToken, object : GraphRequest.GraphJSONObjectCallback{
                        override fun onCompleted(jsonObj: JSONObject?, response: GraphResponse?) {
                            Log.d("내 정보 내용", jsonObj.toString())

                            val name = jsonObj!!.getString("name")
                            val id = jsonObj.getString("id")

                            Log.d("이름", name)
                            Log.d("id값", id)

                            apiService.postRequestSocialLogin("facebook", id, name).enqueue(object : Callback<BasicResponse>{
                                override fun onResponse(
                                    call: Call<BasicResponse>,
                                    response: Response<BasicResponse>
                                ) {
                                        val basicResponse = response.body()!!

                                        Toast.makeText(mContext, basicResponse.message, Toast.LENGTH_SHORT).show()
                                        Log.d("API 서버가 준 토큰 값", basicResponse.data.token)

                                        ContextUtil.setToken(mContext, basicResponse.data.token)
                                }

                                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                                }

                            })

                            // 페북이 알려준 이름 / id값을 API 서버에 전달
                        }
                    })
                    graphRequest.executeAsync()
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException?) {

                }

            })

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"))
        }

        // Callback registration
//        binding.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
//            override fun onSuccess(loginResult: LoginResult?) {
//                // App code
//                Log.d("확인용", loginResult.toString())
//                val accessToken = AccessToken.getCurrentAccessToken()
//                Log.d("페북토큰", accessToken.toString())
//            }
//
//            override fun onCancel() {
//                // App code
//            }
//
//            override fun onError(exception: FacebookException) {
//                // App code
//            }
//        })

        binding.kakaoLoginBtn.setOnClickListener {
            // 카카오계정으로 로그인
            UserApiClient.instance.loginWithKakaoAccount(mContext) { token, error ->
                if (error != null) {
                    Log.e("카카오로그인", "로그인 실패", error)
                }
                else if (token != null) {
                    Log.i("카카오로그인", "로그인 성공 ${token.accessToken}")

                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e("카카오로그인", "사용자 정보 요청 실패", error)
                        } else if (user != null) {
                            Log.i(
                                "카카오로그인", "사용자 정보 요청 성공" +
                                        "\n회원번호: ${user.id}" +
                                        "\n이메일: ${user.kakaoAccount?.email}" +
                                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                            )

                            // 토큰 저장
                            apiService.postRequestSocialLogin(
                                "kakao", user.id.toString(), user.kakaoAccount?.profile?.nickname!!).enqueue(object : Callback<BasicResponse>{
                                override fun onResponse(
                                    call: Call<BasicResponse>,
                                    response: Response<BasicResponse>
                                ) {
                                    val basicResponse = response.body()!!
                                    ContextUtil.setToken(mContext, basicResponse.data.token)
                                }

                                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                                }
                            })
                        }
                    }
                }
            }
        }

        // 로그인창에서 회원가입 버튼
        binding.goSignUpBtn.setOnClickListener{
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }

        // 회원가입 창에서 회원가입 버튼
        binding.loginBtn.setOnClickListener {
            val inputId = binding.emailEdt.text.toString()
            val inputPw = binding.pwEdt.text.toString()

            // 토큰 저장
            apiService.postRequestLogin(inputId, inputPw).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        val basicResponse = response.body()!!
                        Toast.makeText(mContext, basicResponse.message, Toast.LENGTH_SHORT).show()
                        Log.d("API 서버가 준 토큰 값", basicResponse.data.token)

                        ContextUtil.setToken(mContext, basicResponse.data.token)
                    }
                    else{
                        val errorBodyStr = response.errorBody()!!.string()
                        val jsonObj = JSONObject(errorBodyStr)
                        Log.d("응답 본문", jsonObj.toString())
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })
        }
    }

    override fun setValues() {
        val info = packageManager.getPackageInfo(
            "com.gukjang.myfinalproject_210910",
            PackageManager.GET_SIGNATURES
        )
        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}