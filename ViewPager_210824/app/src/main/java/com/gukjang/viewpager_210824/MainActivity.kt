package com.gukjang.viewpager_210824

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gukjang.viewpager_210824.adapters.MainViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mvpa : MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mvpa = MainViewPagerAdapter( supportFragmentManager )
        mainViewPager.adapter = mvpa
    }
}