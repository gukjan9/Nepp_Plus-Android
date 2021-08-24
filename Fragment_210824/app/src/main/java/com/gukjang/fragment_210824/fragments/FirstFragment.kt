package com.gukjang.fragment_210824.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gukjang.fragment_210824.R
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    // 동작에 관련된 코드들이 작성되는 함수 -> 액티비티 : onCreate
    // 화면 x, 화면에 올라가는 부품을 만든 것임
    // fragment의 onCreate는 화면도 준비되기 전 -> 동작시키면 에러 소지가 높다.

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        logBtn.setOnClickListener {
            Log.d("부품에서 찍기", "로그 버튼 눌림")
        }
    }
}