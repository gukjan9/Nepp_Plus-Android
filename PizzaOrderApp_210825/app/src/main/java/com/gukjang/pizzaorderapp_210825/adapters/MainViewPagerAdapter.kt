package com.gukjang.pizzaorderapp_210825.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gukjang.pizzaorderapp_210825.fragments.MyProfileFragment
import com.gukjang.pizzaorderapp_210825.fragments.PizzaStoreListFragment

class MainViewPagerAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm) {              // fm - 변수 이름

    // abstract 아님 - 부모가 하라는대로
    // tableLayout 제목 설정 함수
    override fun getPageTitle(position: Int): CharSequence? {
        // return super.getPageTitle(position) - 의미 : 부모 클래스에 있는 super.- 함수 실행해라 / 삭제하는 이유 : 그대로 리턴시 override 의미 x

        // 탭 번호별 제목 설정
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
            0 -> PizzaStoreListFragment()               // 객체화 ()
            else -> MyProfileFragment()
        }
    }
}