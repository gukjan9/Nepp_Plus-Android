package com.gukjang.myfinalproject_210910.utils

import android.content.Context
import android.util.Log
import com.gukjang.myfinalproject_210910.datas.UserData

class GlobalData {

    companion object {

        var context: Context? = null

//        로그인한 사람은 없을 수도 있다. => null로 로그인한 사람이 없다는걸 표현.
//        UserData?  로 null 허용.
//        기본값 : 로그인한 사람이 없다. null로 미리 대입.
        var loginUser : UserData? = null
            set(value) {
                value?.let {
//                유져 데이터가 null이 아님. => 로그인 등의 이유로 사용자 기록.
//                내 준비 시간을 => ContextUtil에 기록해두자.
                    ContextUtil.setMyReadyMinute(context!!, it.readyMinute)
                }
                if (value == null) {
//                로그아웃 등의 이유로 데이터 파기.
//                내 준비시간을 0 (기본값) 으로 되돌리기.
                    ContextUtil.setMyReadyMinute(context!!, 0)
                }
//            실제 변수에 입력값 대입
                field = value
            }
    }
}