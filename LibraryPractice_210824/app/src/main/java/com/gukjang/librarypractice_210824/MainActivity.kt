package com.gukjang.librarypractice_210824

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val myUri = Uri.parse("tel : 02-5555-8888")
            val myIntent = Intent(Intent.ACTION_CALL, myUri)
            startActivity(myIntent)
        }
    }
    override fun setValues(){

    }
}