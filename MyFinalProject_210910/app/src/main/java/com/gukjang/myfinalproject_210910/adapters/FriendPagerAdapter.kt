package com.gukjang.myfinalproject_210910.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gukjang.myfinalproject_210910.fragments.MyFriendsListFragment
import com.gukjang.myfinalproject_210910.fragments.RequestedUserListFragment

class FriendPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "내 친구 목록"
            else -> "친구 추가 요청"
        }
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> MyFriendsListFragment()
            else -> RequestedUserListFragment()
        }
    }
}