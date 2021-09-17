package com.gukjang.myfinalproject_210910

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.gukjang.myfinalproject_210910.databinding.ActivityViewMyFriendsListBinding
import com.gukjang.myfinalproject_210910.adapters.FriendPagerAdapter
import com.gukjang.myfinalproject_210910.fragments.MyFriendsListFragment
import com.gukjang.myfinalproject_210910.fragments.RequestedUserListFragment

class ViewMyFriendsListActivity : BaseActivity() {

    lateinit var binding : ActivityViewMyFriendsListBinding

    lateinit var mFPA : FriendPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_my_friends_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        addBtn.setOnClickListener {
            val myIntent = Intent(mContext, AddFriendActivity::class.java)
            startActivity(myIntent)
        }

        binding.friendsViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // TabLayout 에서 좌우 스크롤할 때
                // Log.d("오프셋 값", positionOffset.toString())
            }

            override fun onPageSelected(position: Int) {
                Log.d("선택된 페이지", position.toString())

                // 각 페이지에 맞는 Fragment 새로고침 실행
                when(position){
                    0 -> {
                        (mFPA.getItem(position) as MyFriendsListFragment).getMyFriendsListFromServer()
                    }
                    else -> {
                        (mFPA.getItem(position) as RequestedUserListFragment).getRequestUserListFromServer()
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    override fun setValues() {
        titleTxt.text = "친구 관리"
        addBtn.visibility = View.VISIBLE

        mFPA = FriendPagerAdapter(supportFragmentManager)
        binding.friendsViewPager.adapter = mFPA

        binding.friendsTabLayout.setupWithViewPager(binding.friendsViewPager)
    }
}