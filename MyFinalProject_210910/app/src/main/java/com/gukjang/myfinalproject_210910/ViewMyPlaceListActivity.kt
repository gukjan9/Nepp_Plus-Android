package com.gukjang.myfinalproject_210910

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.gukjang.myfinalproject_210910.adapters.AppointmentAdapter
import com.gukjang.myfinalproject_210910.adapters.MyPlaceRecyclerAdapter
import com.gukjang.myfinalproject_210910.databinding.ActivityViewMyPlaceListBinding
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.gukjang.myfinalproject_210910.datas.PlaceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewMyPlaceListActivity : BaseActivity() {
    lateinit var binding : ActivityViewMyPlaceListBinding

    val mMyPlaceList = ArrayList<PlaceData>()
    lateinit var mPlaceAdapter : MyPlaceRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_my_place_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        addBtn.setOnClickListener {
            val myIntent = Intent(mContext, EditMyPlaceActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        titleTxt.text = "내가 자주 쓰는 출발 장소들"

        addBtn.visibility = View.VISIBLE

        binding.myPlaceRecyclerView.layoutManager = LinearLayoutManager(mContext)

        mPlaceAdapter = MyPlaceRecyclerAdapter(mContext, mMyPlaceList)
        binding.myPlaceRecyclerView.adapter = mPlaceAdapter
    }

    override fun onResume() {
        super.onResume()
        getMyPlaceListFromServer()
    }

    fun getMyPlaceListFromServer(){
        apiService.getRequestMyPlaceList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val basicResponse = response.body()!!

                    mMyPlaceList.clear()
                    mMyPlaceList.addAll(basicResponse.data.places)
                    mPlaceAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }
}