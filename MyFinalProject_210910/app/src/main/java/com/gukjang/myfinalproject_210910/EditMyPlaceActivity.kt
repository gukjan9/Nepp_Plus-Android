package com.gukjang.myfinalproject_210910

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gukjang.myfinalproject_210910.databinding.ActivityEditMyPlaceBinding
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMyPlaceActivity : BaseActivity() {
    lateinit var binding : ActivityEditMyPlaceBinding

    var mSelectedLat = 0.0
    var mSelectedLng = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_my_place)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.saveBtn.setOnClickListener {
            val inputName = binding.nameEdt.text.toString()

            // 멤버변수에 있는 Lat / Lng 사용
            apiService.postRequestAddMyPlace(
                inputName,
                mSelectedLat,
                mSelectedLng,
                true).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(mContext, "내 출발 장소로 출발하였습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })
        }
    }

    override fun setValues() {
        titleTxt.text = "내 출발 장소 추가"

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync {
            val marker = Marker()

            it.setOnMapClickListener { pointF, latLng ->
                mSelectedLat = latLng.latitude
                mSelectedLng = latLng.longitude

                marker.position = latLng
                marker.map = it
            }
        }
    }
}