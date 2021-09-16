package com.gukjang.myfinalproject_210910

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.gukjang.myfinalproject_210910.adapters.SearchUserRecyclerAdapter
import com.gukjang.myfinalproject_210910.databinding.ActivityAddFriendBinding
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.gukjang.myfinalproject_210910.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFriendActivity : BaseActivity() {
    lateinit var binding : ActivityAddFriendBinding

    val mSearchedUserList = ArrayList<UserData>()

    lateinit var mSearchedUserAdapter : SearchUserRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_friend)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.searchBtn.setOnClickListener {
            val inputKeyword = binding.keywordEdt.text.toString()

            // 2자 이상
            if(inputKeyword.length < 2){
                Toast.makeText(mContext, "검색어는 2자 이상 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            apiService.getRequestSearchUser(inputKeyword).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        val basicResponse = response.body()!!

                        // 기존 검색 결과 삭제
                        mSearchedUserList.clear()
                        mSearchedUserList.addAll(basicResponse.data.users)
                        mSearchedUserAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
        }
    }

    override fun setValues() {
        titleTxt.text = "친구 검색 / 추가"

        mSearchedUserAdapter = SearchUserRecyclerAdapter(mContext, mSearchedUserList)
        binding.searchUserRecyclerView.adapter = mSearchedUserAdapter
        binding.searchUserRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }
}