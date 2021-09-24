package com.gukjang.myfinalproject_210910.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import com.gukjang.myfinalproject_210910.datas.BasicResponse
import com.gukjang.myfinalproject_210910.receivers.AlarmReceiver
import com.gukjang.myfinalproject_210910.utils.ContextUtil
import com.gukjang.myfinalproject_210910.utils.GlobalData
import com.gukjang.myfinalproject_210910.web.ServerAPI
import com.gukjang.myfinalproject_210910.web.ServerAPIService
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MyJobService : JobService() {
    companion object{
        // 어떤 작업인지 구별하기 쉽게 숫자를 변수로 담자
        val JOB_TIME_SET = 1000
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.d("예약 작업 시작", p0!!.jobId.toString())

        // 약속 시간 - (API 에서 알려준) 교통 소요시간 - 내 준비시간 계산된 시간에 알람
        val retrofit = ServerAPI.getRetrofit(applicationContext)
        val apiService = retrofit.create(ServerAPIService::class.java)

        apiService.getRequestAppointmentDetail(p0!!.jobId).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val basicResponse = response.body()!!

                    val appointmentData = basicResponse.data.appointment

                    val myODsayService = ODsayService.init(applicationContext, "uAM+of2PdQ84i6tahlikW4YObpDlEiko5y83eKYeOkM")

                    myODsayService.requestSearchPubTransPath(
                        appointmentData.startLongitude.toString(),
                        appointmentData.startLatitude.toString(),
                        appointmentData.longitude.toString(),
                        appointmentData.latitude.toString(),
                        null,
                        null,
                        null,
                        object : OnResultCallbackListener {
                            override fun onSuccess(p0: ODsayData?, p1: API?) {
                                val jsonObj = p0!!.json
                                val resultObj = jsonObj.getJSONObject("result")
                                val pathArr = resultObj.getJSONArray("path")

                                val firstPath = pathArr.getJSONObject(0)

                                val infoObj = firstPath.getJSONObject("info")

                                val totalTime = infoObj.getInt("totalTime")

                                val hour = totalTime / 60
                                val minute = totalTime % 60

                                // 예상 시간 몇 분 걸리는지 파악 완료 -> 알람 띄우는데 활용
                                // 알람 시간 : 약속시간 - 교통 소요시간 - 내 준비시간
                                val now = Calendar.getInstance()
                                appointmentData.datetime.time += now.timeZone.rawOffset

                                val alarmTime = appointmentData.datetime.time - totalTime * 60 * 1000 - ContextUtil.getMyReadyMinute(applicationContext) * 60 * 1000
                                setAlarmByMilliSecond(alarmTime)

                            }

                            override fun onError(p0: Int, p1: String?, p2: API?) {
                                Log.d("예상시간 실패", p1!!)
                            }
                        })
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
        return false
    }

    // 언제 알람을 울릴지 계산해서 넘겨주면 단순히 울리기만 하는 함수
    fun setAlarmByMilliSecond(timeInMillis : Long){
        // 약속시간 - 교통 소요시간 - 내 준비시간 -> 계산된 시간에 알람

        // 알람 울리게 도와주는 도구 -> Broadcast 송신
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        // 실제로 알람이 올리면 실행할 코드 -> BroadcastReceiver 에 작업
        val myIntent = Intent(this, AlarmReceiver::class.java)

        // 할 일을 가지고 대기(pending) 해주는 Intent
        val pendingIntent = PendingIntent.getBroadcast(
            this, AlarmReceiver.ALARM_ID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // 알람이 울릴 시간 설정 (임시 30초)
        // val triggerTime = SystemClock.elapsedRealtime() + 30 * 1000

        val triggerTime = timeInMillis

        // 실제 알람 기능 설정
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)          // WAKEUP - 폰이 잠겨 있어도 알람
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return false
    }
}