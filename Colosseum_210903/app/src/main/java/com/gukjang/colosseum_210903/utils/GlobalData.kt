package com.gukjang.colosseum_210903.utils

import com.gukjang.colosseum_210903.datas.UserData

class GlobalData {
    companion object {
        // 로그인한 사용자를 담아둘 변수
        var loginUser : UserData? = null            // 앱이 처음 켜졌을 때는 로그인한 사용자 없다 표시

    }
}