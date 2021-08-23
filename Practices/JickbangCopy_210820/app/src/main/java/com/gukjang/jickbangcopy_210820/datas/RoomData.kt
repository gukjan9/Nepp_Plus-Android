package com.gukjang.jickbangcopy_210820.datas

import java.text.NumberFormat
import java.util.*

class RoomData (val img : Int, val cost : String, val address : String, val floor : String, val desc : String){

    fun getFormattedPrice() : String{
        if(this.cost >= 10000){
            val uk = this.cost / 10000
            val rest = this.cost % 10000

            val result = "${uk}억${NumberFormat.getNumberInstance(Locale.KOREA).format(rest)}"

            return result
        }
        else{
            val result = java.text.NumberFormat.getNumberInstance(Locale.KOREA).format(this.cost)
            return result
        }
    }

    fun getFormattedFloor() : String{
        if(this.floor > 0) return "${this.floor}층"
        else if(this.floor == 0) return "반지하"
        else return "지하 ${-this.floor}층"
    }
}