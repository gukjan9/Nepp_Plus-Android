package com.gukjang.myfinalproject_210910

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gukjang.myfinalproject_210910.databinding.ActivityEditAppointmentBinding
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.gukjang.myfinalproject_210910.utils.ContextUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class EditAppointmentActivity : BaseActivity() {
    lateinit var binding : ActivityEditAppointmentBinding

    // 선택한 약속 일시를 저장할 변수
    val mSelectedDateTime = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_appointment)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        // 확인 버튼이 눌리면?
        binding.okBtn.setOnClickListener {
            // 입력 값 받아오기
            // 1. 일정 제목
            val inputTitle = binding.titleEdt.text.toString()

            // 2. 약속 일시
            // 날짜 / 시간 설정 안했으면 하라고 Toast 날리기
            if(binding.dateTxt.text == "일자 설정"){
                Toast.makeText(mContext, "일자를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(binding.timeTxt.text == "시간 설정"){
                Toast.makeText(mContext, "시간을 설정하지 않았습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val finalDatetime = sdf.format(mSelectedDateTime.time)

            Log.d("서버에 보낼 약속 일시", finalDatetime)


            // 3. 약속 장소
            // 장소 이름
            val inputPlaceName = binding.placeSearchEdt.text.toString()

            // 장소 위도/경도
            val lat = 37.724520
            val lng = 126.752466

            // 서버에 API 호출
            apiService.postRequestAppointment(
                // ContextUtil.getToken(mContext),
                inputTitle,
                finalDatetime,
                inputPlaceName,
                lat, lng).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })
        }

        // 날짜 선택
        binding.dateTxt.setOnClickListener {
            // DataPicker 띄우기 -> 입력 완료되면 연/월/일 제공
            // mSelec... 에 연/월/일 저장

            val dateSetListener = object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(p0: DatePicker?, year : Int, month: Int, day : Int){
                    // 선택된 날짜로서 지정
                    mSelectedDateTime.set(year, month, day)

                    // 선택된 날짜로 문구 변경
                    val sdf = SimpleDateFormat("yyyy. M. d (E)")
                    binding.dateTxt.text = sdf.format(mSelectedDateTime.time)
                }
            }
            val dpd = DatePickerDialog(mContext, dateSetListener,
                mSelectedDateTime.get(Calendar.YEAR),
                mSelectedDateTime.get(Calendar.MONTH),
                mSelectedDateTime.get(Calendar.DAY_OF_MONTH))

            dpd.show()
        }

        // 시간 선택
        binding.timeTxt.setOnClickListener {
            val tsl = object : TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
                    mSelectedDateTime.set(Calendar.HOUR_OF_DAY, hour)
                    mSelectedDateTime.set(Calendar.MINUTE, minute)

                    // 오후 6:05 형태로 가공
                    val sdf = SimpleDateFormat("a h:mm")
                    binding.timeTxt.text = sdf.format(mSelectedDateTime.time)
                }
            }
            TimePickerDialog(mContext, tsl,
                mSelectedDateTime.get(Calendar.HOUR_OF_DAY),
                mSelectedDateTime.get(Calendar.MINUTE),
                false).show()
        }
    }

    override fun setValues() {
        // 카카오 지도 띄워보기
        val mapView = MapView(mContext)

        binding.mapView.addView(mapView)


        // 네이버 지도 Fragment 다루기
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.naverMapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.naverMapView, it).commit()
            }
        mapFragment.getMapAsync {
            Log.d("지도 객체 - 바로 할일", it.toString())

            // 학원 좌표를 지도 시작점으로
//            it.mapType = NaverMap.MapType.Hybrid

            // 좌표를 다루는 변수 - LatLng 클래스 활용
            val neppplusCoord = LatLng(33.5779, 127.0335)

            val cameraUpdate = CameraUpdate.scrollTo(neppplusCoord)
            it.moveCamera(cameraUpdate)
        }
    }
}