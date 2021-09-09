package com.gukjang.colosseum_210903

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.gukjang.colosseum_210903.utils.ContextUtil
import com.gukjang.colosseum_210903.utils.GlobalData
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        logoutBtn.setOnClickListener {
            // 1. 로그인? id/ pw 받아서 서버에 이 회원이 있는지 검사 요청

            // 2. 로그아웃? 정말 로그아웃할지 확인누르면 로그인 안했을 때 처럼 초기화
            val alert = AlertDialog.Builder(mContext)
            alert.setMessage("정말 로그아웃 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                // token 없는 걸로 -> ContextUtil 에 저장된 토큰값을 ""로 바꿔주자
                ContextUtil.setToken(mContext, "")

                // GlobalData.loginUser 없던 걸로
                GlobalData.loginUser = null

                // 모두 끝나면 모든 화면 종료, 로그인 화면으로 이동
                val myIntent = Intent(mContext, SignInActivity::class.java)
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(myIntent)

                // finish() : MyProfile 화면 하나만 종료
                // Intent 부가 기능 활용 -> flag 로 기존화면 전부 종료
            })
            alert.setNegativeButton("취소", null)
            alert.show()
        }
    }

    override fun setValues() {

    }
}