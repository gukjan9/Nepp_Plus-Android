package com.gukjang.myfinalproject_210910.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FriendPagerAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> MyFriendsListFragment()
            else -> RequestedUserListFragment()
        }
    }
}