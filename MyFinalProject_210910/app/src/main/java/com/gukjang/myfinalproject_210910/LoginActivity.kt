package com.gukjang.myfinalproject_210910

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.facebook.CallbackManager
import java.security.MessageDigest
import com.facebook.FacebookException

import com.facebook.login.LoginResult

import com.facebook.FacebookCallback

import android.R
import android.content.Intent
import android.view.View
import com.facebook.AccessToken
import com.facebook.login.LoginManager

import com.facebook.login.widget.LoginButton
import com.gukjang.myfinalproject_210910.databinding.ActivityLoginBinding
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

            // 

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