package com.gukjang.phonebook_210902.datas

import java.text.SimpleDateFormat
import java.util.*

class PhoneNumData(var name :String, var phoneNum : String) {

    // 생성자가 실행될 때 추가 동작시킬 코드 작성
//    init {
//
//
//    }

    val birthDay = Calendar.getInstance()

    // 변환 x
    private val fileDateFormat = SimpleDateFormat("yyyy-MM-dd")

    fun getFileFormatData() : String {

        // 코틀린에서 String 가공
        return "${this.name},${this.phoneNum},${fileDateFormat.format(this.birthDay.time)}"
    }

    val birthDayFormatter = SimpleDateFormat("M월 d일")

    fun getFormattedBirthday() : String {
        return birthDayFormatter.format(this.birthDay.time)
    }
}