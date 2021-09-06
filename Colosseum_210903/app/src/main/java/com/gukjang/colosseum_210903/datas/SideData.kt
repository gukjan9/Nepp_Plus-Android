package com.gukjang.colosseum_210903.datas

class SideData(
    var id : Int,
    var topicId : Int,
    var title : String,
    var voteCount : Int) {

    constructor() : this(0, 0, "미정", 0)
}