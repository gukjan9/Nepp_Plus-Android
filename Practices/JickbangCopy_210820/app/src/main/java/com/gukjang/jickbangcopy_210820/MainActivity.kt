package com.gukjang.jickbangcopy_210820

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gukjang.jickbangcopy_210820.datas.RoomData
import com.gukjang.jickbangcopy_210820.adapters.RoomAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val roomList = ArrayList<RoomData>()

    lateinit var mAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arr = arrayOf(
            arrayOf(R.drawable.ic_launcher_background, "8000", "마포구 서교동", "1", "망원/홍대역 풀옵셕 넓은 원룸"),
            arrayOf(R.drawable.ic_launcher_foreground, "28500", "마포구 서교동", "3", "신혼부부의 보금자리 서교동 신축"),
            arrayOf(R.drawable.ic_launcher_background, "12000", "마포구 서교동", "5", "★홍대입구역 인근 원룸 전세★"),
            arrayOf(R.drawable.ic_launcher_background, "12000", "마포구 성산동", "3", "♥♥강추!! 보세요!! 넓고 반듯한"),
            arrayOf(R.drawable.ic_launcher_background, "12000", "마포구 망원1동", "1", "풀옵션 원룸 전세")
        )

        // 질문 1. 배열에서 string 타입으로 입력했는데 왜 또 as String 써야하는지
        for (i in 0..4) {
            roomList.add(
                RoomData(
                    arr[i][0] as Int, costToString(arr[i][1] as String),
                    arr[i][2] as String, floorToString(arr[i][3] as String), arr[i][4] as String
                )
            )
        }

        // alt + 드래그 하고 적으면 여러 줄 동시에 적을 수 있음
        //roomList.add(RoomData(R.drawable.ic_launcher_background, "8000", "서울시 동대문구", "3", "아주 좋아요"))

        mAdapter = RoomAdapter(this, R.layout.room_list_item, roomList)

        roomListView.adapter = mAdapter


        // @@@@@@@@@@@@@@@@@@@@@@
        roomListView.setOnItemClickListener { adapterView, view, position, l ->

            val clickedRoom = roomList[position]

            val myIntent = Intent(this, ViewRoomDetailActivity::class.java)         // this 출발지

            myIntent.putExtra("roomData", clickedRoom)

            startActivity(myIntent)
        }
    }
    fun costToString(cost: String): String {
        val costInt = cost.toInt()
        val m100 = costInt / 10000                                  // 억 단위

        val modifiedCost = costInt % 10000                          // 억 단위를 뺀 나머지
        val m10 = modifiedCost / 1000                               // 천의 자리 숫자

        var restCost = (modifiedCost % 1000).toString()             // 천의 자리 뺀 나머지

        if (restCost == "0") restCost = "000"

        // 질문 2. 왜 m100 단독으로 사용하면 안되는지 0 / 1
        if (m100 != 0) return "${m100}억${m10},${restCost}"
        else return "${m10},${restCost}"
    }

    fun floorToString(floor: String): String {
        val floorInt = floor.toInt()

        if (floorInt < 0) return ", ${floorInt * (-1)}층"           // 지하
        else return ", ${floorInt}층"
    }
}