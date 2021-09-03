package com.gukjang.phonebook_210902

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import com.gukjang.phonebook_210902.datas.PhoneNumData
import kotlinx.android.synthetic.main.activity_edit_phone_num.*
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
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

        okBtn.setOnClickListener {
            // 1. 입력한 값들을 변수에 저장
            val inputName = nameEdt.text.toString()
            val inputPhoneNum = phoneNumEdt.text.toString()

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            // val birthDayStr = sdf.format(mSelectedDate.time)

            // 2. 폰번 데이터 객체 만들기
            val savePhoneNumData = PhoneNumData(inputName, inputPhoneNum)           // 폰번데이터의 생년월일 -> 선택한 날짜에 적힌 년월일 그대로 대입
            savePhoneNumData.birthDay.time = mSelectedDate.time

            // 3. 해당 폰번을 양식대로 가공 -> 파일에 저장
            val saveStr = savePhoneNumData.getFileFormatData()

            savePhoneNumToFile(saveStr)

            // 4. 토스트로 저장 성공 안내 + 화면 종료
            Toast.makeText(mContext, "전화번호가 추가로 저장되었습니다.", Toast.LENGTH_SHORT).show()
            finish()

        }

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

//    fun savePhoneNumToFile(content : String){
//        val mainFolder = File("${Environment.getExternalStorageDirectory()}/phoneBookData")
//
//        var success = true
//
//        // 해당 폴더 없으면 새로 만들고 -> 성공 여부 저장
//        if(!mainFolder.exists()) success = mainFolder.mkdir()
//
//        // 폴더가 만들어졌다면
//        if(success){
//            val myFile = File("phoneNumData.txt")
//
//            if(!myFile.exists()) success = myFile.mkdir()
//
//            // 폴더 / 파일 전부 준비된 상태
//            if(success){
//                val realFilePath = File(mainFolder, "phoneNumData.txt")
//
//                val fw = FileWriter(realFilePath)
//                val bw = BufferedWriter(fw)
//
//                bw.append(content)
//                bw.newLine()
//
//                bw.close()
//                fw.close()
//
//                Log.d("폰번 추가 성공", content)
//            }
//        }
//    }

    override fun setValues() {

    }

    fun savePhoneNumToFile(content: String){
        val myFile = File(filesDir, "phoneBook.txt")

        val fw = FileWriter(myFile, true)
        val bw = BufferedWriter(fw)

        bw.append(content)
        bw.newLine()

        fw.close()
        bw.close()

        Log.d("파일 추가", content)
    }
}