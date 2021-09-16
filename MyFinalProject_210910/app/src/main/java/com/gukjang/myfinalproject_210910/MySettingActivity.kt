package com.gukjang.myfinalproject_210910

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gukjang.myfinalproject_210910.databinding.ActivityMySettingBinding
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.gukjang.myfinalproject_210910.utils.ContextUtil
import com.gukjang.myfinalproject_210910.utils.GlobalData
import com.gukjang.myfinalproject_210910.utils.URIPathHelper
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MySettingActivity : BaseActivity() {
    lateinit var binding : ActivityMySettingBinding

    val REQ_FOR_GALLERY = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_setting)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.readyTimeLayout.setOnClickListener {

            val customView =
                LayoutInflater.from(mContext).inflate(R.layout.my_custom_alert_edit, null)
            val alert = AlertDialog.Builder(mContext)

            alert.setTitle("준비 시간 설정")
            alert.setView(customView)
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                val minuteEdt = customView.findViewById<EditText>(R.id.minuteEdt)

                // Toast.makeText(mContext, "${minuteEdt.text.toString()}", Toast.LENGTH_SHORT).show()
                apiService.patchRequestMyInfo("ready_minute", minuteEdt.text.toString())
                    .enqueue(object : Callback<BasicResponse> {
                        override fun onResponse(
                            call: Call<BasicResponse>,
                            response: Response<BasicResponse>
                        ) {
                            if (response.isSuccessful) {
                                // 외출 소요시간 수정된 정보 파싱 -> 로그인한 사용자의 정보로 갱신
                                val basicResponse = response.body()!!

                                GlobalData.loginUser = basicResponse.data.user

                                setUserInfo()
                            }
                        }

                        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                        }
                    })
            })
            alert.setNegativeButton("취소", null)
            alert.show()
        }

        binding.myPlacesLayout.setOnClickListener {
            val myIntent = Intent(mContext, ViewMyPlaceListActivity::class.java)
            startActivity(myIntent)
        }

        binding.editNicknameLayout.setOnClickListener {
            val customView =
                LayoutInflater.from(mContext).inflate(R.layout.my_custom_alert_nickname, null)
            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("닉네임 변경")
            alert.setView(customView)
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                val nicknameEdt = customView.findViewById<EditText>(R.id.nicknameEdt)

                apiService.patchRequestMyInfo("nickname", nicknameEdt.text.toString())
                    .enqueue(object : Callback<BasicResponse> {
                        override fun onResponse(
                            call: Call<BasicResponse>,
                            response: Response<BasicResponse>
                        ) {
                            if (response.isSuccessful) {
                                val basicResponse = response.body()!!
                                GlobalData.loginUser = basicResponse.data.user

                                setUserInfo()
                            }
                        }

                        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                        }

                    })
            })
            alert.setNegativeButton("취소", null)
            alert.show()
        }

//        binding.myPlacesLayout.setOnClickListener{
//            val myIntent = Intent(mContext, ViewMyPlaceListActivity)
//        }

        binding.profileImg.setOnClickListener {
            val permissionListener = object : PermissionListener {
                override fun onPermissionGranted() {
                    val myIntent = Intent()
                    myIntent.action = Intent.ACTION_GET_CONTENT
                    myIntent.type = "image/*"
                    startActivityForResult(Intent.createChooser(myIntent, ""), REQ_FOR_GALLERY)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(mContext, "권한이 거부되어 갤러리에 접근이 불가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            // 실제로 권한 체크
            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .setDeniedMessage("[설정] > [권한] 에서 갤러리 권한을 열어주세요.")
                .check()
        }

        binding.logoutLayout.setOnClickListener {
            val alert = AlertDialog.Builder(mContext)
            alert.setMessage("정말 로그아웃 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                // 로그아웃 - 토큰 제거
                ContextUtil.setToken(mContext, "")

                // 추가 작업 : GlobalData 의 로그인 사용자 정보도 같이 제거
                GlobalData.loginUser = null

                // splash 화면으로 이동
                val myIntent = Intent(mContext, SplashActivity::class.java)

                // 필요 없는 화면들 모두 종료 -> mySetting, main 종료
                // FLAG 활용하여 다른 모든 화면 제거
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(myIntent)
            })

            alert.setNegativeButton("취소", null)
            alert.show()
        }

        binding.myFriendsLayout.setOnClickListener {
            val myIntent = Intent(mContext, ViewMyFriendsListActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        titleTxt.text = "내 정보 설정"

        setUserInfo()
    }

    fun setUserInfo(){
        binding.nicknameTxt.text = GlobalData.loginUser!!.nickName

        // 로그인한 사람의 준비시간
        if(GlobalData.loginUser!!.readyMinute >= 60){
            val hour = GlobalData.loginUser!!.readyMinute / 60
            val minute = GlobalData.loginUser!!.readyMinute % 60

            binding.readyTimeTxt.text = "${hour}시간 ${minute}분"
        }
        else{
            binding.readyTimeTxt.text = "${GlobalData.loginUser!!.readyMinute}분"
        }

        // 페북 / 카톡 / 일반 이냐에 따라 이미지 다르게 처리
        when(GlobalData.loginUser!!.provider){
            "facebook" -> binding.socialLoginImg.setImageResource(R.drawable.facebook_icon)
            "kakao" -> binding.socialLoginImg.setImageResource(R.drawable.kakaotalk_icon)
            else -> binding.socialLoginImg.visibility = View.GONE
        }

        when(GlobalData.loginUser!!.provider){
            "default" -> binding.passwordLayout.visibility = View.VISIBLE
            else -> binding.passwordLayout.visibility = View.GONE
        }

        // 로그인한 사용자는 프로필 사진 경로도 들고 있다.
        Glide.with(mContext).load(GlobalData.loginUser!!.profileImgURL).into(binding.profileImg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 갤러리에서 가진 가져온 경우
        if(requestCode == REQ_FOR_GALLERY){
            if(resultCode == RESULT_OK){
                // 어떤 사진을 골랐는지

                // data? -> 이전 화면이 넘겨준 intent
                // data?.data -> 선택한 사진이 들어있는 경로 정보
                val dataUri = data?.data

                // Uri -> imageView 의 사진으로 (임시)
                // Glide.with(mContext).load(dataUri).into(binding.profileImg)

                // API 서버에 사진을 전송 -> PUT /user/image 로 API 활용
                // Multipart 형식의 데이터로 첨부 (formData 랑은 다름)

                // Uri -> File 형태로 변환
                val file = File(URIPathHelper().getPath(mContext, dataUri!!))

                // 파일을 Retrofit 에 첨부할 수 있는 RequestBody 형태로 변환 후 -> MultipartBody 형태로 변환
                val fileReqBody = RequestBody.create(MediaType.get("image/*"), file)
                val body = MultipartBody.Part.createFormData("profile_image", "myFile.jpg", fileReqBody)

                apiService.putRequestProfileImg(body).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {

                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            }
        }
    }
}