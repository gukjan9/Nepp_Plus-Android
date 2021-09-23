package com.gukjang.myfinalproject_210910

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gukjang.myfinalproject_210910.databinding.ActivityViewAppointmentDetailBinding
import com.gukjang.myfinalproject_210910.datas.AppointmentData
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.MarkerIcons
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat

class ViewAppointmentDetailActivity : BaseActivity() {
    lateinit var binding : ActivityViewAppointmentDetailBinding

    lateinit var mAppointmentData : AppointmentData

    var needLocationSendServer = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_appointment_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.arrivalBtn.setOnClickListener{

            needLocationSendServer = true
            // 내 위치 파악

            val pl = object : PermissionListener{
                override fun onPermissionGranted() {
                    if (ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // 권한이 하나라도 없다면 밑의 코드 실행 x
                        return
                    }
                    val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0L, 0f,
                    object : LocationListener{
                        override fun onLocationChanged(p0: Location) {
                            if(needLocationSendServer){
                                Log.d("위도", p0.latitude.toString())
                                Log.d("경도", p0.longitude.toString())

                                apiService.postRequestArrival(
                                    mAppointmentData.id,
                                    p0.latitude,
                                    p0.longitude).enqueue(object : Callback<BasicResponse>{
                                        override fun onResponse(
                                            call: Call<BasicResponse>,
                                            response: Response<BasicResponse>
                                        ) {
                                            if(response.isSuccessful){
                                                needLocationSendServer = false
                                            }
                                            else{
                                                val jsonObj = JSONObject(response.errorBody()!!.string())
                                                Log.d("응답 전문", jsonObj.toString())

                                                val message = jsonObj.getString("message")
                                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                                            }

                                        }

                                        override fun onFailure(
                                            call: Call<BasicResponse>,
                                            t: Throwable
                                        ) {

                                    }

                                })

                            }
                        }

                        override fun onStatusChanged(
                            provider: String?,
                            status: Int,
                            extras: Bundle?
                        ) {

                        }

                        override fun onProviderEnabled(provider: String) {

                        }

                        override fun onProviderDisabled(provider: String) {

                        }

                    })
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(mContext, "현재 위치 정보를 파악해야 도착 인증이 가능합니다.", Toast.LENGTH_SHORT).show()
                }

            }
            TedPermission.create()
                .setPermissionListener(pl)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check()
        }

        binding.refreshBtn.setOnClickListener {
            getAppointmentFromServer()
        }
    }

    override fun setValues() {
        titleTxt.text = "약속 상세 확인"

        mAppointmentData = intent.getSerializableExtra("appointment") as AppointmentData

        binding.titleTxt.text = mAppointmentData.title
        binding.placeTxt.text = mAppointmentData.placeName

        // 참여인원 수
        binding.invitedFriendsCountTxt.text = "(참여인원 : ${mAppointmentData.invitedFriendList.size}명"

        // 약속 시간
        val sdf = SimpleDateFormat("M/d a h:mm")
        binding.timeTxt.text = sdf.format(mAppointmentData.datetime)

        // 도착지 좌표 지도에 설정
        setNaverMap()

        getAppointmentFromServer()
    }

    fun setNaverMap(){
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.naverMapFrag) as MapFragment?
            ?: MapFragment.newInstance().also{
                fm.beginTransaction().add(R.id.naverMapFrag, it).commit()
            }
        mapFragment.getMapAsync {
            val naverMap = it

            val dest = LatLng(mAppointmentData.latitude, mAppointmentData.longitude)

            // 마커 생성
            val marker = Marker()
            marker.position = dest
            marker.icon = MarkerIcons.BLACK
            marker.iconTintColor = Color.RED
            marker.map = naverMap

            // 카메라 이동
//            val cameraUpdate = CameraUpdate.scrollTo(dest)
//            naverMap.moveCamera(cameraUpdate)

            val startLatLng = LatLng(mAppointmentData.startLatitude, mAppointmentData.startLongitude)

            val startMarker = Marker()
            startMarker.position = startLatLng
            startMarker.map = naverMap

            // 출발지 / 도착지 일직선 PathOverlay
            val path = PathOverlay()

            val points = ArrayList<LatLng>()
            points.add(startLatLng)

            points.add(dest)

            path.coords = points
            path.map = naverMap

            // 두 좌표의 중간점으로 카메라 이동
            val centerOfStartAndDest = LatLng(
                (mAppointmentData.startLatitude + mAppointmentData.latitude)/2,
                (mAppointmentData.startLongitude + mAppointmentData.longitude)/2
            )
            val cameraUpdate = CameraUpdate.scrollTo(dest)
            naverMap.moveCamera(cameraUpdate)

            // 거리에 따른 줌 레벨 변경
            val zoomLevel = 11.0
            naverMap.moveCamera(CameraUpdate.zoomTo(zoomLevel))

            val infoWindow = InfoWindow()

            // 대중교통 API 활용
            val myODsayService = ODsayService.init(mContext, "uAM+of2PdQ84i6tahlikW4YObpDlEiko5y83eKYeOkM")

            myODsayService.requestSearchPubTransPath(
                mAppointmentData.startLongitude.toString(),
                mAppointmentData.startLatitude.toString(),
                mAppointmentData.longitude.toString(),
                mAppointmentData.latitude.toString(),
                null,
                null,
                null,
                object : OnResultCallbackListener {
                    override fun onSuccess(p0: ODsayData?, p1: API?) {
                        val jsonObj = p0!!.json
                        val resultObj = jsonObj.getJSONObject("result")
                        val pathArr = resultObj.getJSONArray("path")

//                                for(i in 0 until pathArr.length()){
//                                    val pathObj = pathArr.getJSONObject(i)
//                                    Log.d("API응답", pathObj.toString(4))
//                                }

                        val firstPath = pathArr.getJSONObject(0)

                        // 출발점 ~ 경유지목록 ~ 도착지를 이어주는 Path 객체를 추가.
                        val points = ArrayList<LatLng>()
//                        출발지부터 추가.
                        points.add(  LatLng(mAppointmentData.startLatitude, mAppointmentData.startLongitude)  )

//                        경유지목록 파싱 -> for문으로 추가.
                        val subPathArr = firstPath.getJSONArray("subPath")
                        for (i  in  0 until subPathArr.length()) {
                            val subPathObj = subPathArr.getJSONObject(i)
                            Log.d("응답내용", subPathObj.toString())
                            if (!subPathObj.isNull("passStopList")) {

                                val passStopListObj = subPathObj.getJSONObject("passStopList")
                                val stationsArr = passStopListObj.getJSONArray("stations")
                                for ( j  in  0 until  stationsArr.length() ) {
                                    val stationObj = stationsArr.getJSONObject(j)
                                    Log.d("정거장목록", stationObj.toString())

//                                    각 정거장의 GPS좌표 추출 -> 네이버지도의 위치객체로 변환.
                                    val latLng = LatLng(stationObj.getString("y").toDouble(), stationObj.getString("x").toDouble())

//                                    지도의 선을 긋는 좌표 목록에 추가.
                                    points.add(latLng)

                                }
                            }
                        }

//                        모든 정거장 추가 => 실제 목적지 좌표 추가.
                        points.add( LatLng(mAppointmentData.latitude, mAppointmentData.longitude) )


//                        모든 경로 설정 끝. => 네이버 지도에 선으로 이어주자.
                        val path = PathOverlay()
                        path.coords = points
                        path.map =  it



                        val infoObj = firstPath.getJSONObject("info")

                        val totalTime = infoObj.getInt("totalTime")

                        // Log.d("총 소요시간", totalTime.toString())

                        val hour = totalTime / 60
                        val minute = totalTime % 60

                        infoWindow.adapter = object : InfoWindow.DefaultViewAdapter(mContext) {
                            override fun getContentView(p0: InfoWindow): View {
                                val myView = LayoutInflater.from(mContext).inflate(R.layout.my_custom_info_window, null)

                                val placeNameTxt = myView.findViewById<TextView>(R.id.placeNameTxt)
                                val arrivalTimeTxt = myView.findViewById<TextView>(R.id.arrivalTimeTxt)

                                placeNameTxt.text = mAppointmentData.placeName

                                if (hour == 0) {
                                    arrivalTimeTxt.text = "${minute}분 소요 예정"
                                } else {
                                    arrivalTimeTxt.text = "${hour}시간 ${minute}분 소요 예정"
                                }
                                return myView
                            }
                        }
                    }

                    override fun onError(p0: Int, p1: String?, p2: API?) {
                        Log.d("예상시간 실패", p1!!)
                    }
                })
        }
    }

    fun getAppointmentFromServer(){
        // 친구 목록 등의 내용을 서버에서 새로 받자
        apiService.getRequestAppointmentDatail(mAppointmentData.id).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                val basicResponse = response.body()!!

                mAppointmentData = basicResponse.data.appointment

                // 기존 친구목록 view 들을 전부 제거 -> 그러고 친구 다시 추가
                binding.invitedFriendsLayout.removeAllViews()

               // 친구 목록 -> 레이아웃에 xml
                val inflater = LayoutInflater.from(mContext)

                val sdf = SimpleDateFormat("H:mm 도착")

                for(friend in mAppointmentData.invitedFriendList){
                    val friendView = inflater.inflate(R.layout.invited_friends_list, null)

                    val friendProfileImg = friendView.findViewById<ImageView>(R.id.friendProfileImg)
                    val nicknameTxt = friendView.findViewById<TextView>(R.id.nicknameTxt)
                    val statusTxt = friendView.findViewById<TextView>(R.id.statusTxt)

                    if(friend.arrivedAt == null){
                        statusTxt.text = "도착 전"
                    }
                    else{
                        statusTxt.text = sdf.format(friend.arrivedAt!!)
                    }

                    Glide.with(mContext).load(friend.profileImgURL).into(friendProfileImg)
                    nicknameTxt.text = friend.nickName

                    binding.invitedFriendsLayout.addView(friendView)
                }
            }
            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}