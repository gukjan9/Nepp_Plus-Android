package com.gukjang.myfinalproject_210910.datas

// 서버가 주는 기본 형태의 응답을 담는 클래스 (파싱 결과로 활용)
class BasicResponse(
    var code : Int,
    var message : String,
    var data : DataResponse) {

}