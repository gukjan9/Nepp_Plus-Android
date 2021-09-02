package com.gukjang.phonebook_210902

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_edit_phone_num.*
import java.text.SimpleDateFormat
import java.util.*

class EditPhoneNumActivity : BaseActivity() {

    // 캘린더 기본값 : 화면을 연 일시
    val mSelectedDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_phone_num)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        selectBirthDayBtn.setOnClickListener {

            // 달력처럼 날짜 선택 팝업 출현
            val dateSetListener = object : DatePickerDialog.OnDateSetListener{

                // 날짜가 선택되면 실행해줄 코드
                // 날짜 선택이 완료되면 birthDayTxt 에 반영
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    mSelectedDate.set(year, month, dayOfMonth)

                    val sdf = SimpleDateFormat("yyyy. MM. dd.")

                    birthDayTxt.text = sdf.format(mSelectedDate.time)
                }
            }

            // 달력이 뜰 때 기본으로 설정된 날짜 : 오늘 날짜로 설정하기
            val datePickerDialog = DatePickerDialog(mContext, dateSetListener,
                mSelectedDate.get(Calendar.YEAR), mSelectedDate.get(Calendar.MONTH), mSelectedDate.get(Calendar.DAY_OF_MONTH))

            datePickerDialog.show()
        }
    }

    override fun setValues() {
        TODO("Not yet implemented")
    }
}