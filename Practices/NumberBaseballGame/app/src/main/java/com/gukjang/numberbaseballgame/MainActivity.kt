package com.gukjang.numberbaseballgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gukjang.numberbaseballgame.datas.MessageData
import com.gukjang.numberbaseballgame.adapters.MessageAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val mMessageList = ArrayList<MessageData>()

    lateinit var mAdapter : MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMessageList.add(MessageData("안녕하세요", "CPU"))
        mMessageList.add(MessageData("반갑습니다", "USER"))

        mAdapter = MessageAdapter(this, R.layout.message_list_item, mMessageList)

        messageListView.adapter = mAdapter

        okBtn.setOnClickListener {
            val inputNumStr = numberEdt.text.toString()

            val msg = MessageData(inputNumStr, "USER")

            mMessageList.add(msg)

            mAdapter.notifyDataSetChanged()

            // 한번 쓴 numberEdt 의 문구를 비운다
            numberEdt.setText("")

            // 리스트뷰를 최하단으로 내리고 싶다
            messageListView.smoothScrollToPosition(mMessageList.size - 1)
        }
    }
}