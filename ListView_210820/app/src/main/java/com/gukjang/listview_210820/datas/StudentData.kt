package com.gukjang.listview_210820.datas

class StudentData(val name : String,                // 생성자
                  val birthYear : Int,
                  val  address : String) {

// 학생의 기능으로 -> 나이를 계산해서 알려주는 기능을 함수로
    fun getKoreanAgeByYear(year : Int) : Int {
        val age = year - this.birthYear + 1

        return age
    }
}