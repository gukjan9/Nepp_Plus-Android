package com.gukjang.numberbaseballgame_210823

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.gukjang.numberbaseballgame_210823.datas.MessageData
import com.gukjang.numberbaseballgame_210823.adapters.MessageAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val mMessageList = ArrayList<MessageData>()

    lateinit var mAdapter : MessageAdapter

    // 세 자리 문제 숫자를 저장하기 위한 ArrayList
    val mQuestionNumbers = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 세 자리 랜덤 문제 만들기
        makeQuestionNumbers()

//        mMessageList.add(MessageData("안녕하세요", "CPU"))
//        mMessageList.add(MessageData("반갑습니다", "USER"))

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

            checkAnswer(inputNumStr.toInt())
        }
    }
    fun makeQuestionNumbers(){
        // 고정된 세 개 숫자를 임시 문제로 생성
//        mQuestionNumbers.add(4)
//        mQuestionNumbers.add(7)
//        mQuestionNumbers.add(1)

        // Random (1 ~ 9) && 중복x

        for(i in 0..2){
            while(true){
                val randomNum = (Math.random() * 9 + 1).toInt()

                var isDuplOk = true

                for(num in mQuestionNumbers){
                    if(num == randomNum){
                        isDuplOk = false
                    }
                }
                if(isDuplOk) {
                    mQuestionNumbers.add(randomNum)
                    break
                }
            }
        }

        for(num in mQuestionNumbers){
            Log.d("출제된 숫자", num.toString())
        }

        mMessageList.add(MessageData("어서오세요", "CPU"))
        mMessageList.add(MessageData("숫자 야구 게임입니다", "CPU"))
        mMessageList.add(MessageData("세자리 숫자를 맞춰주세요", "CPU"))

    }

    fun checkAnswer(inputNum : Int){
        val userInputNumArr = ArrayList<Int>()

        userInputNumArr.add(inputNum / 100)
        userInputNumArr.add(inputNum / 10 % 10)
        userInputNumArr.add(inputNum % 10)

        var strikeCount = 0
        var ballCount = 0

        for(i in 0..2){
            for(j in 0..2){
                if(userInputNumArr[i] == mQuestionNumbers[j]){
                    if(i == j) strikeCount++
                    else ballCount++
                }
            }
        }
        mMessageList.add(MessageData("${strikeCount}S ${ballCount}B 입니다", "CPU"))

        mAdapter.notifyDataSetChanged()

        messageListView.smoothScrollToPosition(mMessageList.size - 1)

        if(strikeCount == 3){
            mMessageList.add(MessageData("축하합니다! 정답입니다", "CPU"))

            mAdapter.notifyDataSetChanged()

            messageListView.smoothScrollToPosition(mMessageList.size - 1)

            Toast.makeText(this, "게임을 종료합니다", Toast.LENGTH_SHORT).show()

            // 입력 막기 -> numberEdt를 enabled : false
            numberEdt.isEnabled = false
        }
    }

}