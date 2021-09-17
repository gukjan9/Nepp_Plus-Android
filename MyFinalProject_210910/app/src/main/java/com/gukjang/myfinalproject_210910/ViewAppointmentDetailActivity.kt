package com.gukjang.myfinalproject_210910

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gukjang.myfinalproject_210910.databinding.ActivityViewAppointmentDetailBinding
import com.gukjang.myfinalproject_210910.datas.AppointmentData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import java.text.SimpleDateFormat

class ViewAppointmentDetailActivity : BaseActivity() {
    lateinit var binding : ActivityViewAppointmentDetailBinding

    lateinit var mAppointmentData : AppointmentData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_appointment_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

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

        // 친구 목록 -> 레이아웃에 xml
        val inflater = LayoutInflater.from(mContext)

        for(friend in mAppointmentData.invitedFriendList){
            val friendView = inflater.inflate(R.layout.invited_friends_list, null)

            val friendProfileImg = friendView.findViewById<ImageView>(R.id.friendProfileImg)
            val nicknameTxt = friendView.findViewById<TextView>(R.id.nicknameTxt)
            val statusTxt = friendView.findViewById<TextView>(R.id.statusTxt)

            Glide.with(mContext).load(friend.profileImgURL).into(friendProfileImg)
            nicknameTxt.text = friend.nickName

            binding.invitedFriendsLayout.addView(friendView)
        }

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
            val cameraUpdate = CameraUpdate.scrollTo(dest)
            naverMap.moveCamera(cameraUpdate)

            val startLatLng = LatLng(mAppointmentData.startLatitude, mAppointmentData.startLongitude)

            val startMarker = Marker()
            startMarker.position = startLatLng
            startMarker.map = naverMap
        }
    }
}