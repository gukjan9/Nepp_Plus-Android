package com.gukjang.jickbangcopy_210820

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gukjang.jickbangcopy_210820.datas.RoomData
import kotlinx.android.synthetic.main.activity_view_room_detail.*

class ViewRoomDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_room_detail)

        val roomData = intent.getSerializableExtra("roomData") as RoomData

        roomCostDetail.text = roomData.getFormattedPrice()
        roomDescDetail.text = roomData.desc
        roomAddressDetail.text = roomData.address
        roomFloorDetail.text = roomData.getFormattedFloor()
    }
}