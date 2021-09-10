package com.gukjang.myfinalproject_210910

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gukjang.myfinalproject_210910.databinding.ActivitySignUpBinding
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.gukjang.myfinalproject_210910.web.ServerAPI
import com.gukjang.myfinalproject_210910.web.ServerAPIService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {
   lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.signUpBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            val inputPw = binding.pwEdt.text.toString()
            val inputNick = binding.nicknameEdt.text.toString()

            apiService.putRequestSignUp(inputEmail, inputPw, inputNick).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    // 회원가입 성공시
                    if(response.isSuccessful){
                        val basicResponse = response.body()!!
                        Log.d("서버 메세지", basicResponse.message)
                        Toast.makeText(mContext, basicResponse.message, Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val errorBodyStr = response.errorBody()!!.string()
                        Log.d("에러 경우", errorBodyStr)
                        val jsonObj = JSONObject(errorBodyStr)
                        val message = jsonObj.getString("message")
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })
        }
    }

    override fun setValues() {

    }
}