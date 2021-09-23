package com.gukjang.myfinalproject_210910.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import com.gukjang.myfinalproject_210910.receivers.AlarmReceiver
import java.util.*

class MyJobService : JobService() {
    companion object{
        // 어떤 작업인지 구별하기 쉽게 숫자를 변수로 담자
        val JOB_TIME_SET = 1000
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.d("예약 작업 시작", p0!!.jobId.toString())

        // 약속시간 - 교통 소요시간 - 내 준비시간 -> 계산된 시간에 알람

        // 알람 울리게 도와주는 도구 -> Broadcast 송신
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        // 실제로 알람이 올리면 실행할 코드 -> BroadcastReceiver 에 작업
        val myIntent = Intent(this, AlarmReceiver::class.java)

        // 할 일을 가지고 대기(pending) 해주는 Intent
        val pendingIntent = PendingIntent.getBroadcast(
            this, AlarmReceiver.ALARM_ID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // 알람이 울릴 시간 설정
        val now = Calendar.getInstance()
        val triggerTime = SystemClock.elapsedRealtime() + 30 * 1000

        // 실제 알람 기능 설정
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)          // WAKEUP - 폰이 잠겨 있어도 알람

        return false
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return false
    }

}