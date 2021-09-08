package com.gukjang.colosseum_210903

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.gukjang.colosseum_210903.datas.ReplyData
import com.gukjang.colosseum_210903.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import kotlinx.android.synthetic.main.reply_list_item.*
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {
    lateinit var  mReplyData : ReplyData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    // 댓글 상세보기 화면에 댓글 갖고오기
    override fun setValues() {
        mReplyData = intent.getSerializableExtra("replyData") as ReplyData

        sideAndNicknameTxt.text = "(${mReplyData.selectedSide.title}) ${mReplyData.writer.nickname}"

        replyContentTxt.text = mReplyData.content

        okBtn.setOnClickListener {
            val inputContent = contentEdt.text.toString()

            if(inputContent.length < 5){
                Toast.makeText(mContext, "5자 이상 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ServerUtil.postRequestChildReply(mContext, inputContent, mReplyData.id, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
                    runOnUiThread{
                        contentEdt.setText("")

                        // 답글 입력 후 키보드 숨김처리 (구글링)
                        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                    }
                }

            })
        }
    }
}