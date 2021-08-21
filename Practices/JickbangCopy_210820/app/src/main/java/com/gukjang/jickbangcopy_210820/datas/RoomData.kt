package com.gukjang.jickbangcopy_210820.datas

class RoomData (val img : Int, val cost : Int, val address : String, val floor : Int, val desc : String){

    fun costToString(cost : Int) : String{
        val m100 = cost / 10000

        val modifiedCost = cost - (m100 * 10000)
        val m10 = modifiedCost / 1000

        return m100 + "억 " + m10 + "," + modifiedCost - m10*1000
    }
}