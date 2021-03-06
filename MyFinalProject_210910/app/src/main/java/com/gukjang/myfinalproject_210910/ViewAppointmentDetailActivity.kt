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
            // ??? ?????? ??????

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
                        // ????????? ???????????? ????????? ?????? ?????? ?????? x
                        return
                    }
                    val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0L, 0f,
                    object : LocationListener{
                        override fun onLocationChanged(p0: Location) {
                            if(needLocationSendServer){
                                Log.d("??????", p0.latitude.toString())
                                Log.d("??????", p0.longitude.toString())

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
                                                Log.d("?????? ??????", jsonObj.toString())

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
                    Toast.makeText(mContext, "?????? ?????? ????????? ???????????? ?????? ????????? ???????????????.", Toast.LENGTH_SHORT).show()
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
        titleTxt.text = "?????? ?????? ??????"

        mAppointmentData = intent.getSerializableExtra("appointment") as AppointmentData

        binding.titleTxt.text = mAppointmentData.title
        binding.placeTxt.text = mAppointmentData.placeName

        // ???????????? ???
        binding.invitedFriendsCountTxt.text = "(???????????? : ${mAppointmentData.invitedFriendList.size}???"

        // ?????? ??????
        val sdf = SimpleDateFormat("M/d a h:mm")
        binding.timeTxt.text = sdf.format(mAppointmentData.datetime)

        // ????????? ?????? ????????? ??????
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

            // ?????? ??????
            val marker = Marker()
            marker.position = dest
            marker.icon = MarkerIcons.BLACK
            marker.iconTintColor = Color.RED
            marker.map = naverMap

            // ????????? ??????
//            val cameraUpdate = CameraUpdate.scrollTo(dest)
//            naverMap.moveCamera(cameraUpdate)

            val startLatLng = LatLng(mAppointmentData.startLatitude, mAppointmentData.startLongitude)

            val startMarker = Marker()
            startMarker.position = startLatLng
            startMarker.map = naverMap

            // ????????? / ????????? ????????? PathOverlay
            val path = PathOverlay()

            val points = ArrayList<LatLng>()
            points.add(startLatLng)

            points.add(dest)

            path.coords = points
            path.map = naverMap

            // ??? ????????? ??????????????? ????????? ??????
            val centerOfStartAndDest = LatLng(
                (mAppointmentData.startLatitude + mAppointmentData.latitude)/2,
                (mAppointmentData.startLongitude + mAppointmentData.longitude)/2
            )
            val cameraUpdate = CameraUpdate.scrollTo(dest)
            naverMap.moveCamera(cameraUpdate)

            // ????????? ?????? ??? ?????? ??????
            val zoomLevel = 11.0
            naverMap.moveCamera(CameraUpdate.zoomTo(zoomLevel))

            val infoWindow = InfoWindow()

            // ???????????? API ??????
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
//                                    Log.d("API??????", pathObj.toString(4))
//                                }

                        val firstPath = pathArr.getJSONObject(0)

                        // ????????? ~ ??????????????? ~ ???????????? ???????????? Path ????????? ??????.
                        val points = ArrayList<LatLng>()
//                        ??????????????? ??????.
                        points.add(  LatLng(mAppointmentData.startLatitude, mAppointmentData.startLongitude)  )

//                        ??????????????? ?????? -> for????????? ??????.
                        val subPathArr = firstPath.getJSONArray("subPath")
                        for (i  in  0 until subPathArr.length()) {
                            val subPathObj = subPathArr.getJSONObject(i)
                            Log.d("????????????", subPathObj.toString())
                            if (!subPathObj.isNull("passStopList")) {

                                val passStopListObj = subPathObj.getJSONObject("passStopList")
                                val stationsArr = passStopListObj.getJSONArray("stations")
                                for ( j  in  0 until  stationsArr.length() ) {
                                    val stationObj = stationsArr.getJSONObject(j)
                                    Log.d("???????????????", stationObj.toString())

//                                    ??? ???????????? GPS?????? ?????? -> ?????????????????? ??????????????? ??????.
                                    val latLng = LatLng(stationObj.getString("y").toDouble(), stationObj.getString("x").toDouble())

//                                    ????????? ?????? ?????? ?????? ????????? ??????.
                                    points.add(latLng)

                                }
                            }
                        }

//                        ?????? ????????? ?????? => ?????? ????????? ?????? ??????.
                        points.add( LatLng(mAppointmentData.latitude, mAppointmentData.longitude) )


//                        ?????? ?????? ?????? ???. => ????????? ????????? ????????? ????????????.
                        val path = PathOverlay()
                        path.coords = points
                        path.map =  it



                        val infoObj = firstPath.getJSONObject("info")

                        val totalTime = infoObj.getInt("totalTime")

                        // Log.d("??? ????????????", totalTime.toString())

                        val hour = totalTime / 60
                        val minute = totalTime % 60

                        infoWindow.adapter = object : InfoWindow.DefaultViewAdapter(mContext) {
                            override fun getContentView(p0: InfoWindow): View {
                                val myView = LayoutInflater.from(mContext).inflate(R.layout.my_custom_info_window, null)

                                val placeNameTxt = myView.findViewById<TextView>(R.id.placeNameTxt)
                                val arrivalTimeTxt = myView.findViewById<TextView>(R.id.arrivalTimeTxt)

                                placeNameTxt.text = mAppointmentData.placeName

                                if (hour == 0) {
                                    arrivalTimeTxt.text = "${minute}??? ?????? ??????"
                                } else {
                                    arrivalTimeTxt.text = "${hour}?????? ${minute}??? ?????? ??????"
                                }
                                return myView
                            }
                        }
                    }

                    override fun onError(p0: Int, p1: String?, p2: API?) {
                        Log.d("???????????? ??????", p1!!)
                    }
                })
        }
    }

    fun getAppointmentFromServer(){
        // ?????? ?????? ?????? ????????? ???????????? ?????? ??????
        apiService.getRequestAppointmentDetail(mAppointmentData.id).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                val basicResponse = response.body()!!

                mAppointmentData = basicResponse.data.appointment

                // ?????? ???????????? view ?????? ?????? ?????? -> ????????? ?????? ?????? ??????
                binding.invitedFriendsLayout.removeAllViews()

               // ?????? ?????? -> ??????????????? xml
                val inflater = LayoutInflater.from(mContext)

                val sdf = SimpleDateFormat("H:mm ??????")

                for(friend in mAppointmentData.invitedFriendList){
                    val friendView = inflater.inflate(R.layout.invited_friends_list, null)

                    val friendProfileImg = friendView.findViewById<ImageView>(R.id.friendProfileImg)
                    val nicknameTxt = friendView.findViewById<TextView>(R.id.nicknameTxt)
                    val statusTxt = friendView.findViewById<TextView>(R.id.statusTxt)

                    if(friend.arrivedAt == null){
                        statusTxt.text = "?????? ???"
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