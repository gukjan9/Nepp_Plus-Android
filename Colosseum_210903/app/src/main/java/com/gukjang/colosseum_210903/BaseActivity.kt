package com.gukjang.colosseum_210903

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.my_custom_action_bar.*

abstract class BaseActivity : AppCompatActivity() {
    lateinit var mContext : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        // 액션바가 존재하는 화면에서만 실행
        // 액션바가 null 이 아닐 때만 실행시켜줄 코드
        supportActionBar?.let{
            setCustomActionBar()
        }
    }

    abstract fun setupEvents()
    abstract fun setValues()

    // actionbar 를 커스텀 액션바로 바꿔주는 함수
    fun setCustomActionBar(){
        // 기본 액션바를 불러오자
        val defaultActionBar = supportActionBar!!

        // 커스텀 모드로 변경
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defaultActionBar.setCustomView(R.layout.my_custom_action_bar)

        // 양 옆 여백 제거
        val myToolbar = defaultActionBar.customView.parent as Toolbar
        myToolbar.setContentInsetsAbsolute(0,0)

        // 세팅이 끝나면 UI들의 이벤트도 달아주자
        backBtn.setOnClickListener {
            finish()
        }

        notiBtn.setOnClickListener {
            Toast.makeText(mContext, "알림 목록을 보러 갑니다.", Toast.LENGTH_SHORT).show()
        }

        // 모든 화면은 기본적으로 노티버튼을 숨겨두자

    }
}