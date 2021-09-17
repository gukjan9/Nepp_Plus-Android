package com.gukjang.myfinalproject_210910.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.gukjang.myfinalproject_210910.R
import com.gukjang.myfinalproject_210910.adapters.MyFriendsRecyclerAdapter
import com.gukjang.myfinalproject_210910.databinding.FragmentMyFriendsListBinding
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.gukjang.myfinalproject_210910.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFriendsListFragment :BaseFragment() {
    companion object {
        private var frag : MyFriendsListFragment? = null

        fun getFrag() : MyFriendsListFragment{
            if(frag == null){
                frag = MyFriendsListFragment()
            }
            return frag!!
        }
    }

    lateinit var binding : FragmentMyFriendsListBinding

    val mMyFriendsList = ArrayList<UserData>()
    lateinit var mFriendsAdapter : MyFriendsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_friends_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mFriendsAdapter = MyFriendsRecyclerAdapter(mContext, mMyFriendsList)
        binding.myFriendsRecyclerView.adapter = mFriendsAdapter

        binding.myFriendsRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    override fun onResume() {
        super.onResume()
        getMyFriendsListFromServer()
    }

    fun getMyFriendsListFromServer(){
        apiService.getRequestFriendList("my").enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val basicResponse = response.body()!!

                    mMyFriendsList.clear()
                    mMyFriendsList.addAll(basicResponse.data.friends)
                    mFriendsAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }
}