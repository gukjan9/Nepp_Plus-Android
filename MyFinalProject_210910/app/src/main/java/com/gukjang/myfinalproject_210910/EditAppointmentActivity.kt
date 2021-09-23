package com.gukjang.myfinalproject_210910

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import com.gukjang.myfinalproject_210910.adapters.MyFriendSpinnerAdapter
import com.gukjang.myfinalproject_210910.adapters.StartPlaceSpinnerAdapter
import com.gukjang.myfinalproject_210910.databinding.ActivityEditAppointmentBinding
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.gukjang.myfinalproject_210910.datas.PlaceData
import com.gukjang.myfinalproject_210910.datas.UserData
import com.gukjang.myfinalproject_210910.services.MyJobService
import com.gukjang.myfinalproject_210910.services.MyJobService.Companion.JOB_TIME_SET
import com.gukjang.myfinalproject_210910.utils.SizeUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
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
import java.util.concurrent.TimeUnit
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

    // 내 친구 목록을 담아둘 스피너
    val mMyFriendsList = ArrayList<UserData>()
    lateinit var mFriendSpinnerAdapter : MyFriendSpinnerAdapter

    // 약속에 참가시킬 친구 리스트.
    val mSelectedFriendsList = ArrayList<UserData>()

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

    // 선택된 도착지를 보여줄 마커 하나만 생성
    val selectedPointMarker = Marker()

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

            // 서버가 사용하는 시간 UTC / 약속 시간을 UTC 시간대로 변경하자

            val myTimeZone = mSelectedDateTime.timeZone

            val myTimeOffset = myTimeZone.rawOffset / 1000 / 60 / 60

            mSelectedDateTime.add(Calendar.HOUR_OF_DAY, -myTimeOffset)

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

            // 선택한 친구 목록
            var friendListStr = ""

            for(friend in mSelectedFriendsList){
                friendListStr += friend.id
                friendListStr += ","
            }

            if(friendListStr != ""){
                friendListStr = friendListStr.substring(0, friendListStr.length-1)
            }
            return@setOnClickListener


            // 서버에 API 호출
            apiService.postRequestAppointment(
                inputTitle,
                finalDatetime,
                mSelectedStartPlace.name,
                mSelectedStartPlace.latitude,
                mSelectedStartPlace.longitude,
                inputPlaceName,
                mSelectedLat, mSelectedLng,
                friendListStr).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        // 약속시간 2~3시간 전에 교통 상황 파악 작업 - JobScheduler 클래스
                            // 예약을 걸도록 도와주는 도구
                        val js = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

                        // 실제로 예약시간이 되면 어떤 일을 할지 적어둔 클래스 필요
                        // 백그라운드 작업 가정 -> 서비스 클래스 작업 필요
                       val serviceComponent = ComponentName(mContext, MyJobService::class.java)

                        // 언제 어떤 일을 할지 모아주는 클래스
                        val jobInfo = JobInfo.Builder(MyJobService.JOB_TIME_SET, serviceComponent)
                            .setMinimumLatency(TimeUnit.MINUTES.toMillis(1))        // 얼마 후에 실행할건지, 약속 시간 기준으로 하려면 계산 필요
                            .setOverrideDeadline(TimeUnit.MINUTES.toMillis(3))      // 3분까지 기다리겠다 -> 안드로이드가 배터리 이슈로 정확한 시간예약 x
                            .build()

                        // 예약 도구를 이용해 스케줄 설정
                        js.schedule(jobInfo)

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

//        지도 영역에 손을 대면 => 스크롤뷰를 정지.
//        대안 : 지도 위에 겹쳐둔 텍스트뷰에 손을대면 => 스크롤뷰를 정지.

        binding.scrollHelpTxt.setOnTouchListener { view, motionEvent ->

            binding.scrollView.requestDisallowInterceptTouchEvent(true)

//            터치 이벤트만 먹히게? X. => 뒤에 가려진 지도 동작도 같이 실행.
            return@setOnTouchListener false
        }

        binding.addFriendToListBtn.setOnClickListener {
            // 고른 친구가 누구인지
            val selectedFriend = mMyFriendsList[binding.myFriendsSpinner.selectedItemPosition]

            // 중복된 친구인지
            if(mSelectedFriendsList.contains(selectedFriend)){
                Toast.makeText(mContext, "이미 추가한 친구입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Toast.makeText(mContext, selectedFriend.nickName, Toast.LENGTH_SHORT).show()

            // 텍스트뷰 생성
            val textView = TextView(mContext)
            textView.setBackgroundResource(R.drawable.selected_friend_box)

            textView.setPadding(SizeUtil.dpToPx(mContext, 5f).toInt() )

            textView.text = selectedFriend.nickName

            // 클릭되면 삭제 (레이아웃, 친구목록)
            textView.setOnClickListener {
                binding.friendListLayout.removeView(textView)
                mSelectedFriendsList.remove(selectedFriend)
            }

            // 레이아웃에 추가 + 친구 목록
            binding.friendListLayout.addView(textView)

            mSelectedFriendsList.add(selectedFriend)
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


        // 내 친구 목록

        mFriendSpinnerAdapter = MyFriendSpinnerAdapter(mContext, R.layout.friend_list_item, mMyFriendsList)
        binding.myFriendsSpinner.adapter = mFriendSpinnerAdapter

        apiService.getRequestFriendList("my").enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    mMyFriendsList.clear()
                    mMyFriendsList.addAll(response.body()!!.data.friends)

                    mFriendSpinnerAdapter.notifyDataSetChanged()
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
            val neppplusCoord = LatLng(37.57793737795487, 127.03355269913862)

            val cameraUpdate = CameraUpdate.scrollTo(neppplusCoord)
            it.moveCamera(cameraUpdate)

            val uiSettings = it.uiSettings
            uiSettings.isCompassEnabled = true
            uiSettings.isScaleBarEnabled = false            // 축척바

            // 선택된 위치를 보여줄 마커 하나만 생성

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

                drawStartPlaceToDestination(it)
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

                    // 멤버변수로 만들어둔 정보창의 내용 설정, 열어주기
                    mInfoWindow.adapter = object : InfoWindow.DefaultTextAdapter(mContext) {
                        override fun getText(p0: InfoWindow): CharSequence {
                            return "${totalTime}분 소요 예정"
                        }
                    }
                    mInfoWindow.open(selectedPointMarker)

                    // 경유지들 좌표를 목록에 추가
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