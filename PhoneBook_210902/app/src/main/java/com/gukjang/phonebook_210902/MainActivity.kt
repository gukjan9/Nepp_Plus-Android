package com.gukjang.phonebook_210902

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gukjang.phonebook_210902.adapters.PhoneNumAdapter
import com.gukjang.phonebook_210902.datas.PhoneNumData
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.text.SimpleDateFormat

class MainActivity : BaseActivity() {
    val mPhoneNumList = ArrayList<PhoneNumData>()

    // 1. 멤버변수로 빼는게 편하다.
    // 2. 초기화할 때 화면정보 (Context)가 필요한가?
    lateinit var mAdapter : PhoneNumAdapter

    // 1. 화면에 들어오면 -> 파일에 저장된 "이름,폰번,1997-04-14" 문장 불러내기
    // 안드로이드에서 파일 다루는법

    // 2. 불러낸 데이터들을 ListView 에 뿌려준다.

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
//        addPhoneNumBtn.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//
//            }

        // JAVA의 interface 를 개량한 버전의 코드 (위 참고)
        // EditPhoneNumActivity 로 이동 : intent
        addPhoneNumBtn.setOnClickListener{
            val myIntent = Intent(mContext, EditPhoneNumActivity::class.java)
            startActivity(myIntent)         // (= super.startActivity)
        }
    }

    override fun setValues() {
//        mPhoneNumList.add(PhoneNumData("테스트1", "010-1111-2222"))
//        mPhoneNumList.add(PhoneNumData("테스트1", "010-1111-2222"))
//        mPhoneNumList.add(PhoneNumData("테스트1", "010-1111-2222"))

        // 어댑터 초기화
        mAdapter = PhoneNumAdapter(mContext, R.layout.phone_num_list_item, mPhoneNumList)

        // ListView의 Adapter로 연결
        phoneNumListView.adapter = mAdapter

        readPhoneBookFromFile()
    }

    override fun onResume() {
        super.onResume()

        // 파일에서 폰번을 화면에 올 때마다 새로 읽어준다.
        readPhoneBookFromFile()
    }


    fun readPhoneBookFromFile(){
        val myFile = File(filesDir, "phoneBook.txt")

        // 처음 깔았으면 txt 없음 -> 파일 읽기 막기
        if(!myFile.exists()){
            return
        }

        val fr = FileReader(myFile)
        val br = BufferedReader(fr)

        val sdf = SimpleDateFormat("yyyy-MM-dd")

        // 코드가 반복 실행되면 데이터가 누적으로 쌓임
        // 기존에 있던건 날리고 새로 데이터 담아준다.
        mPhoneNumList.clear()

        while(true){
            val line = br.readLine()

            if(line == null) break

            // 읽어온 line 을 , 기준으로 분리
            val info = line.split(",")

            val phoneNumData = PhoneNumData(info[0], info[1])

            // SimpleDateFormat 의 parse 기능
            phoneNumData.birthDay.time = sdf.parse(info[2])

            // 폰번을 데이터로 저장
            mPhoneNumList.add(phoneNumData)
        }

        br.close()
        fr.close()

        // 목록에 내용물이 추가됨 -> 리스트뷰도 인지해야함
        mAdapter.notifyDataSetChanged()
    }
}