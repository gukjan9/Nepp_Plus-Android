package com.gukjang.myfinalproject_210910

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gukjang.myfinalproject_210910.databinding.ActivityMainBinding
import com.gukjang.myfinalproject_210910.datas.AppointmentData
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.gukjang.myfinalproject_210910.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {
    lateinit var binding : ActivityMainBinding

    val mAppointmentList = ArrayList<AppointmentData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.addAppointmentBtn.setOnClickListener{
            val myIntent = Intent(mContext, EditAppointmentActivity::class.java)
            startActivity(myIntent)
            finish()
        }

    }

    override fun setValues() {
        Toast.makeText(mContext, "${GlobalData.loginUser!!.nickName}님 환영합니다!", Toast.LENGTH_SHORT).show()

        getAppointmentListFromServer()
    }

    fun getAppointmentListFromServer(){
        apiService.getRequestAppointmentList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                val basicResponse = response.body()!!

                Log.d("약속 목록", basicResponse.message)

//                for(apData in basicResponse.data.appointments){
//                    Log.d("약속 제목", apData.title)
//                }
                mAppointmentList.addAll(basicResponse.data.appointments)
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }
}