package com.gukjang.finalproject_210923

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gukjang.finalproject_210923.databinding.ActivityLoginBinding
import com.gukjang.finalproject_210923.datas.LoginData
import com.gukjang.finalproject_210923.datas.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setEvents()
        setValues()
    }

    override fun setEvents() {
        binding.joinTxt.setOnClickListener {
            val myIntent = Intent(mContext, JoinActivity::class.java)
            startActivity(myIntent)
        }

        binding.loginBtn.setOnClickListener {
            val inputId = binding.idEdt.text.toString()
            val inputPw = binding.pwEdt.text.toString()

            startLogin(LoginData(inputId, inputPw))
        }
    }

    override fun setValues() {

    }

    fun startLogin(data : LoginData){
        apiService.userLogin(data).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call : Call<LoginResponse>,
                response : Response<LoginResponse>
            ) {
                val result = response.body()!!
                Toast.makeText(mContext, result.message, Toast.LENGTH_SHORT).show();

                if (result.code == 200) {
                    finish();
                }
            }

            override fun onFailure(call : Call<LoginResponse>, t : Throwable) {
                Toast.makeText(mContext, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
            }
        })
    }
}