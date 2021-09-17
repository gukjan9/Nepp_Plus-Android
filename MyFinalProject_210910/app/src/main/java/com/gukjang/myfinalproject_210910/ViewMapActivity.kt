package com.gukjang.myfinalproject_210910

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.gukjang.myfinalproject_210910.datas.AppointmentData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener

class ViewMapActivity : BaseActivity() {
    lateinit var mAppointmentData : AppointmentData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_map)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mAppointmentData = intent.getSerializableExtra("appointment") as AppointmentData        // 위에서 AppointmentData 로 받기로 했음

        titleTxt.text = "약속 장소 확인"

       val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.naverMapFrag) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.naverMapFrag, it).commit()
            }

        mapFragment.getMapAsync {
            val appointmentLatLng = LatLng(mAppointmentData.latitude, mAppointmentData.longitude)

            // 약속장소로 카메라 이동
            val cameraUpdate = CameraUpdate.scrollTo(appointmentLatLng)
            it.moveCamera(cameraUpdate)

            // 마커를 찍고 표시
            val marker = Marker()
            marker.position = appointmentLatLng
            marker.map = it

            // 출발지도 마커를 찍고 표시
            val startMarker = Marker()
            startMarker.position = LatLng(mAppointmentData.startLatitude, mAppointmentData.startLongitude)
            startMarker.map = it

            val infoWindow = InfoWindow()
//            infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(mContext){
//                override fun getText(p0: InfoWindow): CharSequence {        // ( = String)
//                    return mAppointmentData.placeName
//                }
//            }

            val myODsayService = ODsayService.init(mContext, "uAM+of2PdQ84i6tahlikW4YObpDlEiko5y83eKYeOkM")

            myODsayService.requestSearchPubTransPath(
                mAppointmentData.startLongitude.toString(),
                mAppointmentData.startLatitude.toString(),
                mAppointmentData.longitude.toString(),
                mAppointmentData.latitude.toString(),
                null,
                null,
                null,
                object : OnResultCallbackListener{
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

            infoWindow.adapter = object : InfoWindow.DefaultViewAdapter(mContext) {
                override fun getContentView(p0: InfoWindow): View {

                    val myView = LayoutInflater.from(mContext).inflate(R.layout.my_custom_info_window, null)

                    val placeNameTxt = myView.findViewById<TextView>(R.id.placeNameTxt)
                    val arrivalTimeTxt = myView.findViewById<TextView>(R.id.arrivalTimeTxt)

                    placeNameTxt.text = mAppointmentData.placeName
//                    arrivalTimeTxt.text = "??시간 ?분 소요예상"

                    return  myView
                }

            }
            infoWindow.open(marker)

            // 지도의 아무데나 찍으면 열려있는 마커 닫아주기
            it.setOnMapClickListener { pointF, latLng ->
                infoWindow.close()
            }

            // 마커를 누를 때 정보창이 닫혀있으면 열어주기 / 열려있으면 닫아주기
            marker.setOnClickListener {
                val clickedMarker = it as Marker

                // 마커에 연결된 정보창이 없을 때
                if(clickedMarker.infoWindow == null){
                    infoWindow.open(clickedMarker)
                }
                else{
                    infoWindow.close()
                }

                return@setOnClickListener true
            }
        }
   }
}