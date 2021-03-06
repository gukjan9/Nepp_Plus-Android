package com.gukjang.myfinalproject_210910.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.gukjang.myfinalproject_210910.MainActivity
import com.gukjang.myfinalproject_210910.R

class AlarmReceiver : BroadcastReceiver() {
    companion object{
        val ALARM_ID = 1001
        val PRIMARY_CHANNEL_ID = "PRIMARY_CHANNEL_ID"
    }

    // 알림을 관리하는 클래스를 멤버변수로 선언
    lateinit var mNotificationManager : NotificationManager

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("알람 울림", "테스트 로그")
        mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // oreo 버전 이후로는 채널을 설정해야함, 그 이전은 안됨
        createNotificationChannel()

        // 채널 설정 끝 -> 실제로 알림 울릴 준비
        deliverNotification(context)
    }

    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // 맞을 때만 알림 채널 설정
            val notiChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID, "Primary Channel", NotificationManager.IMPORTANCE_HIGH)

            notiChannel.enableLights(true)
            notiChannel.lightColor = Color.RED

            notiChannel.enableVibration(true)
            notiChannel.description = "알람을 통한 Notification 테스트"

            // 알림 매니저를 통해 채널 등록
            mNotificationManager.createNotificationChannel(notiChannel)
        }
    }

    fun deliverNotification(context: Context){
        // 알림을 누르면 어느 화면으로 갈지
        val contentIntent = Intent(context, MainActivity::class.java)

        // 실제로 알림이 눌릴 때까지 대기하는 Intent
        val pendingIntent = PendingIntent.getActivity(context, ALARM_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // 알림에 대한 정보 설정
        val notiBuilder = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)         // mipmap 이 아니면 일부 폰에서 앱이 죽음 - 모양을 바꾸려면 투명 배경 활용
            .setContentTitle("테스트 알람")
            .setContentText("알람이 울립니다.")            // 메세지 개념으로 생각
            .setContentIntent(pendingIntent)            // 대기 Intent 사용, 그 안에 contentIntent 도 첨부
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)        // 기본 셋팅 - 알림 소리, 진동 패턴 커스터마이징 가능

        mNotificationManager.notify(ALARM_ID, notiBuilder.build())
    }
}