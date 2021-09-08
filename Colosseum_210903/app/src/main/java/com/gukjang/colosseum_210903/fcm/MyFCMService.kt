package com.gukjang.colosseum_210903.fcm

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCMService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        Log.d("푸시알림", "수신이벤트")

        // 토스트로 받은 내용 출력
        val title = p0.notification!!.title

        val myHandler = Handler(Looper.getMainLooper())
        myHandler.post {
            // runOnUiThread 와 같은 역할
            Toast.makeText(applicationContext, title,Toast.LENGTH_SHORT).show()
        }
    }
}