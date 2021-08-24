package com.gukjang.librarypractice_210824

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents(){
        profileImg.setOnClickListener{          // 프로필 사진 클릭하면 사진 크게 보는 화면으로 이동 event 발생
            val myIntent = Intent(mContext, ViewPhotoActivity::class.java)
            startActivity(myIntent)
        }

        callBtn.setOnClickListener{
            val pl = object : PermissionListener{

                override fun onPermissionGranted() {
                    // 권한이 승인되었을때 실행할 코드
                    val myUri = Uri.parse("tel : 02-5555-8888")
                    val myIntent = Intent(Intent.ACTION_CALL, myUri)
                    startActivity(myIntent)
                }
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    // 권한 안될 때
                    Toast.makeText(mContext, "권한이 거부되어 전화 연결이 불가합니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
        // 실제로 권한 확인 - manifest에서 권한 추가해줘야함
        // 라이브러리 문제로 현재 작업 불가
    }
    override fun setValues(){
        // 최근 활동 사진을 인터넷에서 곧바로 다운 받아 앱에서 보여주기
        // drawable 에 붙여넣는 작업

        Glide.with(mContext).load("https://cdn.mos.cms.futurecdn.net/VUL4TtyNpBkotYckYEjUrQ-1200-80.jpg").into(recentImg)

    }
}