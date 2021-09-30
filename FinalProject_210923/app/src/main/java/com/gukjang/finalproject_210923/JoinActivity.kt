package com.gukjang.finalproject_210923

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.gukjang.finalproject_210923.databinding.ActivityJoinBinding
import com.gukjang.finalproject_210923.datas.JoinData
import com.gukjang.finalproject_210923.datas.JoinResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JoinActivity : BaseActivity() {
    lateinit var binding : ActivityJoinBinding

    var joinFlag = false

    var idDuplCheck = false
    var emailDuplCheck = false
    var nicknameDuplCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        setEvents()
        setValues()
    }

    override fun setEvents() {
        binding.idDuplCheckBtn.setOnClickListener {
            val inputId = binding.idEdt.text.toString()

            val idReg = Regex("^[a-zA-Z0-9\\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]+$")

            if(inputId.isEmpty()) {
                Toast.makeText(mContext, "아이디를 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if(inputId.length < 4){
                Toast.makeText(mContext, "아이디를 4자 이상 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if(!inputId.matches(idReg)){
                Toast.makeText(mContext, "영어, 숫자만 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            checkJoin("userId", inputId)
        }

        binding.emailDuplCheckBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()

            val emailReg = Regex("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}")

            if(inputEmail.isEmpty()) {
                Toast.makeText(mContext, "이메일을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if(!inputEmail.matches(emailReg)){
                Toast.makeText(mContext, "이메일 형식으로 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            checkJoin("userEmail", inputEmail)
        }

        binding.nicknameDuplCheckBtn.setOnClickListener {
            val inputNickname = binding.nicknameEdt.text.toString()

            val nicknameReg = Regex("^[가-힣ㄱ-ㅎa-zA-Z0-9]{2,}\$")

            if(inputNickname.isEmpty()) {
                Toast.makeText(mContext, "닉네임을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if(!inputNickname.matches(nicknameReg)){
                Toast.makeText(mContext, "한글, 영어, 숫자만 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            checkJoin("userNickname", inputNickname)
        }

        binding.joinBtn.setOnClickListener {
            val inputId = binding.idEdt.text.toString()
            val inputEmail = binding.emailEdt.text.toString()
            val inputPw = binding.pwEdt.text.toString()
            val inputNickname = binding.nicknameEdt.text.toString()

            if(inputPw.length < 4){
                Toast.makeText(mContext, "패스워드를 4자 이상 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(joinFlag){
                val alert = AlertDialog.Builder(mContext)
                alert.setMessage("회원 가입 하시겠습니까?")
                alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                    startJoin(JoinData(inputId, inputEmail, inputPw, inputNickname))
                    finish()
                })
                alert.setNegativeButton("취소", null)
                alert.show()
            }
            else{
                Toast.makeText(mContext, "중복 검사를 해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }

    override fun setValues() {

    }

    fun checkJoin(type : String, value : String){
        apiService.userCheck(type, value).enqueue(object : Callback<JoinResponse>{
            override fun onResponse(
                call: Call<JoinResponse>,
                response: Response<JoinResponse>
            ) {
                val result = response.body()!!

                if(result.code == 200){
                    joinFlag = true
                    Toast.makeText(mContext, result.message, Toast.LENGTH_SHORT).show()
                }
                else if(result.code == 204){
                    joinFlag = false
                    Toast.makeText(mContext, result.message, Toast.LENGTH_SHORT).show()
                }
                else{
                    joinFlag = false
                    Toast.makeText(mContext, result.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JoinResponse>, t: Throwable) {

            }
        })
    }

    fun startJoin(data : JoinData){
        apiService.userJoin(data).enqueue(object : Callback<JoinResponse> {
            override fun onResponse(
                call : Call<JoinResponse>,
                response : Response<JoinResponse>
            ) {
                val result = response.body()!!
                Toast.makeText(mContext, result.message, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call : Call<JoinResponse>, t : Throwable) {
                Toast.makeText(mContext, "회원가입 에러 발생", Toast.LENGTH_SHORT).show()
                Log.d("회원가입 에러 발생", t.printStackTrace().toString())
            }
        })
    }
}