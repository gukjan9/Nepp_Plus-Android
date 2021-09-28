package com.gukjang.finalproject_210923

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gukjang.finalproject_210923.databinding.ActivityJoinBinding
import com.gukjang.finalproject_210923.datas.JoinData
import com.gukjang.finalproject_210923.datas.JoinResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JoinActivity : BaseActivity() {
    lateinit var binding : ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        setEvents()
        setValues()
    }

    override fun setEvents() {
        binding.joinBtn.setOnClickListener {
            val inputNickname = binding.nicknameEdt.text.toString()
            val inputId = binding.idEdt.text.toString()
            val inputPw = binding.pwEdt.text.toString()

            startJoin(JoinData(inputNickname, inputId, inputPw))
            finish()
        }
    }

    override fun setValues() {

    }

    fun startJoin(data : JoinData){
        apiService.userJoin(data).enqueue(object : Callback<JoinResponse> {
            override fun onResponse(
                call : Call<JoinResponse>,
                response : Response<JoinResponse>
            ) {
                val result = response.body()!!
                Toast.makeText(mContext, result.message, Toast.LENGTH_SHORT).show()
//                if (result.code == 200) {
//                    finish()
//                }
            }

            override fun onFailure(call : Call<JoinResponse>, t : Throwable) {
                Toast.makeText(mContext, "회원가입 에러 발생", Toast.LENGTH_SHORT).show()
                Log.d("회원가입 에러 발생", t.printStackTrace().toString())

            }
        })
    }
}