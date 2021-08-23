package com.gukjang.jickbangcopy_210820

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gukjang.jickbangcopy_210820.datas.RoomData
import kotlinx.android.synthetic.main.room_list_item.*

class ViewRoomDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_room_detail)

        val roomData = intent.getSerializableExtra("roomData") as RoomData

        roomCost.text = roomData.getFormattedPrice()
        roomDesc.text = roomData.desc
        roomAddress.text = roomData.address
        roomFloor.text = roomData.getFormattedFloor()

    }
}