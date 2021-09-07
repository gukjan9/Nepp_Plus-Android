package com.gukjang.colosseum_210903

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.gukjang.colosseum_210903.datas.SideData
import com.gukjang.colosseum_210903.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_edit_reply.*
import org.json.JSONObject

class EditReplyActivity : BaseActivity() {

    lateinit var mSelectedSide : SideData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reply)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        postReplyBtn.setOnClickListener {

            // 1. 입력한 내용 받아오기
            val inputContent = contentEdt.text.toString()

            // 2. 내용이 10글자 미만이라면 토스트 메세지
            if(inputContent.length < 10){
                Toast.makeText(mContext, "10자 이상 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 3. 검사를 통과했다면 서버에 게시글 등록
            val alert = AlertDialog.Builder(mContext)
            alert.setMessage("정말 등록하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                ServerUtil.postRequestTopicReply(mContext, mSelectedSide.topicId, inputContent, object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObj: JSONObject) {
                        runOnUiThread {
                            Toast.makeText(mContext, "의견 등록에 성공했습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                })
                alert.setNegativeButton("취소", null)
                alert.show()
            })
        }
    }

    override fun setValues() {
        mSelectedSide = intent.getSerializableExtra("selectedSide") as SideData
    }
}