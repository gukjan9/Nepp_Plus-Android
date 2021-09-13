package com.gukjang.myfinalproject_210910

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import com.gukjang.myfinalproject_210910.databinding.ActivityEditAppointmentBinding
import java.text.SimpleDateFormat
import java.util.*

class EditAppointmentActivity : BaseActivity() {
    lateinit var binding : ActivityEditAppointmentBinding

    // 선택한 약속 일시를 저장할 변수
    val mSelectedDateTime = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_appointment)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        // 확인 버튼이 눌리면?
        binding.okBtn.setOnClickListener {
            // 입력 값 받아오기
            // 1. 일정 제목
            val inputTitle = binding.titleEdt.text.toString()

            // 2. 약속 일시
            // 날짜 / 시간 설정 안했으면 하라고 Toast 날리기


            // 3. 약속 장소
            // 장소 이름
            val inputPlaceName = binding.placeSearchEdt.text.toString()

            // 장소 위도/경도도
        }

        // 날짜 선택
        binding.dateTxt.setOnClickListener {
            // DataPicker 띄우기 -> 입력 완료되면 연/월/일 제공
            // mSelec... 에 연/월/일 저장

            val dateSetListener = object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(p0: DatePicker?, year : Int, month: Int, day : Int){
                    // 선택된 날짜로서 지정
                    mSelectedDateTime.set(year, month, day)

                    // 선택된 날짜로 문구 변경
                    val sdf = SimpleDateFormat("yyyy. M. d (E)")
                    binding.dateTxt.text = sdf.format(mSelectedDateTime.time)
                }
            }
            val dpd = DatePickerDialog(mContext, dateSetListener,
                mSelectedDateTime.get(Calendar.YEAR),
                mSelectedDateTime.get(Calendar.MONTH),
                mSelectedDateTime.get(Calendar.DAY_OF_MONTH))

            dpd.show()
        }

        // 시간 선택
        binding.timeTxt.setOnClickListener {
            val tsl = object : TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
                    mSelectedDateTime.set(Calendar.HOUR_OF_DAY, hour)
                    mSelectedDateTime.set(Calendar.MINUTE, minute)

                    // 오후 6:05 형태로 가공
                    val sdf = SimpleDateFormat("a h:mm")
                    binding.timeTxt.text = sdf.format(mSelectedDateTime.time)
                }
            }
            TimePickerDialog(mContext, tsl,
                mSelectedDateTime.get(Calendar.HOUR_OF_DAY),
                mSelectedDateTime.get(Calendar.MINUTE),
                false).show()
        }
    }

    override fun setValues() {

    }
}