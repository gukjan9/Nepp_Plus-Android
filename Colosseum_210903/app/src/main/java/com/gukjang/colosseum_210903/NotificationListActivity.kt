package com.gukjang.colosseum_210903

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gukjang.colosseum_210903.adapters.NotiAdapter
import com.gukjang.colosseum_210903.datas.NotiData
import com.gukjang.colosseum_210903.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_notification_list.*
import org.json.JSONObject

class NotificationListActivity : BaseActivity() {
    // 알림 목록을 담을 ArrayList
    val mNotiList = ArrayList<NotiData>()

    lateinit var mNotiAdapter : NotiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        getNotiListFromServer()

        mNotiAdapter = NotiAdapter(mContext, R.layout.notification_list_item, mNotiList)
        notiListView.adapter = mNotiAdapter
    }

    fun getNotiListFromServer(){
        ServerUtil.getRequestNotificationCountOrList(mContext, true, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val dataObj = jsonObj.getJSONObject("data")
                val notificationArr = dataObj.getJSONArray("notifications")

                for(i in 0 until notificationArr.length()){
                    val notiObj = notificationArr.getJSONObject(i)

                    val notiData = NotiData.getNotiDataFromJson(notiObj)

                    mNotiList.add(notiData)
                }
                runOnUiThread{
                    // 어댑터 새로 고침
                    mNotiAdapter.notifyDataSetChanged()
                }
                // 알림 목록 불러오면 맨위 알림까지는 읽었다고 서버에 전파
                ServerUtil.postRequestNotificationRead(mContext, mNotiList[0].id, null)
            }

        })
    }
}