package com.gukjang.pizzaorderapp_210825.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gukjang.pizzaorderapp_210825.fragments.MyProfileFragment
import com.gukjang.pizzaorderapp_210825.fragments.PizzaStoreListFragment

class MainViewPagerAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        // return super.getPageTitle(position) - 삭제하는 이유 : 결과가 정해져 있기 때문
        return when(position){
            0 -> "피자 주문"
            else -> "내 정보 설정"
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> PizzaStoreListFragment()
            else -> MyProfileFragment()
        }
    }
}