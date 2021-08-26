package com.gukjang.pizzaorderapp_210825

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gukjang.pizzaorderapp_210825.adapters.MainViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

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
        mainViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        mainViewPager.adapter = mainViewPagerAdapter

        mainTabLayout.setupWithViewPager(mainViewPager)
    }
}