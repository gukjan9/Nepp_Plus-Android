package com.gukjang.viewpager_210824.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gukjang.viewpager_210824.fragments.BirthYearFragment
import com.gukjang.viewpager_210824.fragments.HelloFragment
import com.gukjang.viewpager_210824.fragments.NameFragment

class MainViewPagerAdapter( fm : FragmentManager) :FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        // 뷰페이저가 총 몇 장인지
        return 3
    }

    override fun getItem(position: Int): Fragment {
        // 각각의 position에 어떤 fragment가 배치되어야 하는가

        if(position == 0) return HelloFragment()
        else if(position == 1) return NameFragment()
        else return BirthYearFragment()
    }
}