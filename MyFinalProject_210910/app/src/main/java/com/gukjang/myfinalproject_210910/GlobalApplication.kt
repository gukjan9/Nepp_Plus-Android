package com.gukjang.myfinalproject_210910

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, "93c06c309e92fc6259e148ae8fc11b43")
    }
}