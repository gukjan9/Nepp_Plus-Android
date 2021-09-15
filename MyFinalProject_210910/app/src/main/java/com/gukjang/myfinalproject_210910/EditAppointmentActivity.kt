package com.gukjang.myfinalproject_210910

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gukjang.myfinalproject_210910.adapters.StartPlaceSpinnerAdapter
import com.gukjang.myfinalproject_210910.databinding.ActivityEditAppointmentBinding
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.gukjang.myfinalproject_210910.datas.PlaceData
import com.gukjang.myfinalproject_210910.utils.ContextUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditAppointmentActivity : BaseActivity() {
    lateinit var binding : ActivityEditAppointmentBinding

    // 선택한 약속 일시를 저장할 변수
    val mSelectedDateTime = Calendar.getInstance()

    var mSelectedLat = 0.0
    var mSelectedLng = 0.0

    // 출발지 목록을 담아둘 리스트
    val mStartPlaceList = ArrayList<PlaceData>()
    lateinit var mSpinnerAdapter : StartPlaceSpinnerAdapter

    // 선택된 출발지를 담아줄 변수
    lateinit var mSelectedStartPlace : PlaceData

    // 화면에 그려질 출발~도착지 연결
    val mPath = PathOverlay()

    // 도착지에 보여줄 정보창
    val mInfoWindow = InfoWindow()

    // 네이버 지도 담아주기
    var mNaverMap : NaverMap? = null

    // 선택된 출발지를 보여줄 마커
    val mStartPlaceMarker = Marker()

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
//            val lat = 37.724520
//            val lng = 126.752466

            // 지도에서 클릭한 좌표로 위경도 첨부

            if(mSelectedLat == 0.0 && mSelectedLng == 0.0){
                Toast.makeText(mContext, "약속 장소를 지도를 클릭해 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 서버에 API 호출
            apiService.postRequestAppointment(
                // ContextUtil.getToken(mContext),
                inputTitle,
                finalDatetime,
                inputPlaceName,
                mSelectedLat, mSelectedLng).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(mContext, "약속을 등록했습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
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

        binding.startPlaceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                // 화면이 뜨면 자동으로 0번 아이템이 선택된다.
                Log.d("선택된 위치", position.toString())

                // Spinner 의 위치에 맞는 장소를 선택된 출발지점으로 선정
                mSelectedStartPlace = mStartPlaceList[position]

                Log.d("출발지 위경도", "${mSelectedStartPlace.latitude}, ${mSelectedStartPlace.longitude}")

                mNaverMap?.let{
                    drawStartPlaceToDestination(it)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    override fun setValues() {
        titleTxt.text = "약속 잡기"

        // 카카오 지도 띄워보기
//        val mapView = MapView(mContext)
//
//        binding.mapView.addView(mapView)

        mSpinnerAdapter = StartPlaceSpinnerAdapter(mContext, R.layout.my_place_list_item, mStartPlaceList)
        binding.startPlaceSpinner.adapter = mSpinnerAdapter

        apiService.getRequestMyPlaceList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val basicResponse = response.body()!!

                    mStartPlaceList.clear()
                    mStartPlaceList.addAll(basicResponse.data.places)

                    mSpinnerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })


        // 네이버 지도 Fragment 다루기
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.naverMapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.naverMapView, it).commit()
            }

        // 람다
        mapFragment.getMapAsync {                              // it == naverMap
            Log.d("지도 객체 - 바로 할일", it.toString())

            // 멤버변수에서 null 이던 네이버 지도 변수를 채워넣기
            mNaverMap = it

            // 학원 좌표를 지도 시작점으로
//            it.mapType = NaverMap.MapType.Hybrid

            // it.minZoom = 4.0

            // 좌표를 다루는 변수 - LatLng 클래스 활용
            val neppplusCoord = LatLng(33.5779, 127.0335)

            val cameraUpdate = CameraUpdate.scrollTo(neppplusCoord)
            it.moveCamera(cameraUpdate)

            val uiSettings = it.uiSettings
            uiSettings.isCompassEnabled = true
            uiSettings.isScaleBarEnabled = false            // 축척바

            // 선택된 위치를 보여줄 마커 하나만 생성
            val selectedPointMarker = Marker()
            selectedPointMarker.icon = OverlayImage.fromResource(R.drawable.map_marker_icon)

            it.setOnMapClickListener { pointF, latLng ->
                Toast.makeText(mContext, "위도 : ${latLng.latitude}, 경도 : ${latLng.longitude}", Toast.LENGTH_SHORT).show()

                mSelectedLat = latLng.latitude
                mSelectedLng = latLng.longitude

                // 좌표를 받아서 마커 생성 후 맵에 띄우기
//                val marker = Marker(LatLng(mSelectedLat, mSelectedLng))
//                marker.map = it

                // 좌표를 받아서 미리 만들어둔 마커의 좌표로 연결
                selectedPointMarker.position = LatLng(mSelectedLat, mSelectedLng)
                selectedPointMarker.map = it
            }
        }
    }

    fun drawStartPlaceToDestination(naverMap : NaverMap){
        // 시작 지점에 좌표 마커 찍어주기
        mStartPlaceMarker.position = LatLng(mSelectedStartPlace.latitude, mSelectedStartPlace.longitude)
        mStartPlaceMarker.map = naverMap

        // 시작 지점의 위경도 - mSelectedStartPlace.latitude
        // 도착 지점의 위경도 - mSelectedLnt
        val points = ArrayList<LatLng>()

        // 출발 지점의 좌표를 선의 출발점으로 설정
        points.add(LatLng(mSelectedStartPlace.latitude, mSelectedStartPlace.longitude))

        // 대중교통 길찾기 API
        val odsay = ODsayService.init(mContext, "uAM+of2PdQ84i6tahlikW4YObpDlEiko5y83eKYeOkM")

        odsay.requestSearchPubTransPath(
            mSelectedStartPlace.longitude.toString(),
            mSelectedStartPlace.latitude.toString(),
            mSelectedLng.toString(),
            mSelectedLat.toString(),
            null,
            null,
            null,
            object : OnResultCallbackListener{
                override fun onSuccess(p0: ODsayData?, p1: API?) {

                    // 경유지들 좌표를 목록에 추가
                    val jsonObj = p0!!.json

                    val resultObj = jsonObj.getJSONObject("result")
                    val pathArr = resultObj.getJSONArray("path")
                    val firstPathObj = pathArr.getJSONObject(0)

                    // 총 소요시간
                    Log.d("길찾기 응답", firstPathObj.toString())
                    val infoObj = firstPathObj.getJSONObject("info")
                    val totalTime = infoObj.getInt("totalTime")

                    Log.d("총 소요시간", totalTime.toString())

                    // 경우지들 좌표를 목록에 추가
                    // 지도에 선을 긋는데 필요한 좌표 목록 추출
                    val subPathArr = firstPathObj.getJSONArray("subPath")

                    for (i in 0 until subPathArr.length()) {
                        val subPathObj = subPathArr.getJSONObject(i)
                        if (!subPathObj.isNull("passStopList")) {
                            val passStopListObj = subPathObj.getJSONObject("passStopList")
                            val stationsArr = passStopListObj.getJSONArray("stations")

                            for (j in 0 until stationsArr.length()) {
                                val stationObj = stationsArr.getJSONObject(j)
                                Log.d("길찾기 응답", subPathArr.toString())

                                val latLng = LatLng(
                                    stationObj.getString("y").toDouble(),
                                    stationObj.getString("x").toDouble()
                                )

                                // points ArrayList 에 경유지로 추가
                                points.add(latLng)
                            }
                        }
                    }


                        // 최종 목적지 추가
                        points.add(LatLng(mSelectedLat, mSelectedLng))

                        mPath.coords = points
                        mPath.map = naverMap
                    }

                    override fun onError(p0: Int, p1: String?, p2: API?) {

                    }
            })
    }
}