package com.gukjang.pizzaorderapp_210825

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gukjang.pizzaorderapp_210825.adapters.MainViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {                               // 추상 함수를 상속 받았는데 구현하지 않아서 에러 발생 -> 상속 받는 쪽에서 어떻게 대응할지 써야함

    lateinit var mainViewPagerAdapter : MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mainViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager)         // fm
        mainViewPager.adapter = mainViewPagerAdapter                                // fragment xml 에 viewPager(내부 : +.kt) 와 adapter 연결

        mainTabLayout.setupWithViewPager(mainViewPager)                             // tabLayout 과 viewPager 연결
    }
}