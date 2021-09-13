package com.gukjang.myfinalproject_210910.utils

import com.gukjang.myfinalproject_210910.datas.UserData

class GlobalData {
    companion object {
        // 로그인한 사람이 없을 수도 있음 -> null 로 로그인한 사람 없음을 표현
        // UserData ? 로 null 허용
        var loginUser : UserData? = null
    }
}