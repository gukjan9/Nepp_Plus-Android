package com.gukjang.myfinalproject_210910

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.databinding.DataBindingUtil
import java.security.MessageDigest

import com.facebook.login.LoginResult

import android.R
import android.content.Intent
import android.view.View
import com.facebook.*
import com.facebook.login.LoginManager

import com.facebook.login.widget.LoginButton
import com.gukjang.myfinalproject_210910.databinding.ActivityLoginBinding
import org.json.JSONObject
import java.util.*


class LoginActivity : BaseActivity() {
    lateinit var binding : ActivityLoginBinding
    lateinit var  callbackManager : CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.gukjang.myfinalproject_210910.R.layout.activity_login)
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

                            // 페북이 알려준 이름 / id값을 API 서버에 전달달
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