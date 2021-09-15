package com.gukjang.myfinalproject_210910

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.gukjang.myfinalproject_210910.adapters.AppointmentAdapter
import com.gukjang.myfinalproject_210910.adapters.AppointmentRecyclerAdapter
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
    lateinit var mAdapter : AppointmentAdapter

    lateinit var mRecyclerAdapter : AppointmentRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        getAppointmentListFromServer()
    }

    override fun setupEvents() {

        binding.addAppointmentBtn.setOnClickListener{
            val myIntent = Intent(mContext, EditAppointmentActivity::class.java)
            startActivity(myIntent)
            finish()
        }

        profileImg.setOnClickListener {
            val myIntent = Intent(mContext, MySettingActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        Toast.makeText(mContext, "${GlobalData.loginUser!!.nickName}님 환영합니다!", Toast.LENGTH_SHORT).show()

        // getAppointmentListFromServer()

//        mAdapter = AppointmentAdapter(mContext, R.layout.appointment_list_item, mAppointmentList)
//        binding.appointmentListView.adapter = mAdapter

        mRecyclerAdapter = AppointmentRecyclerAdapter(mContext, mAppointmentList)
        binding.appointmentRecyclerView.adapter = mRecyclerAdapter

        binding.appointmentRecyclerView.layoutManager = LinearLayoutManager(mContext)

        // 상속받은 액션바에 있는 프로필버튼 보여주기
        profileImg.visibility = View.VISIBLE

        titleTxt.text = "메인 화면"
    }

    fun getAppointmentListFromServer(){
        apiService.getRequestAppointmentList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                val basicResponse = response.body()!!

                mAppointmentList.clear()

                Log.d("약속 목록", basicResponse.message)

//                for(apData in basicResponse.data.appointments){
//                    Log.d("약속 제목", apData.title)
//                }
                mAppointmentList.addAll(basicResponse.data.appointments)

                // 어댑터 새로고침
                mRecyclerAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }
}