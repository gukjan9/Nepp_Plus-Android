package com.gukjang.listview_210820

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gukjang.listview_210820.adapters.StudentAdapter
import com.gukjang.listview_210820.datas.StudentData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mStudentList = ArrayList<StudentData>()

    lateinit var mAdapter : StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mStudentList.add(StudentData("조경진", birthYear = 1988, address = "서울시 동대문구"))
        mStudentList.add(StudentData("권유진", birthYear = 1996, address = "서울시 강남구"))
        mStudentList.add(StudentData("김경윤", birthYear = 1997, address = "경기도 파주시"))
        mStudentList.add(StudentData("김현우", birthYear = 1996, address = "서울시 마포구"))
        mStudentList.add(StudentData("김현지", birthYear = 1995, address = "서울시 은평구"))
        mStudentList.add(StudentData("김희섭", birthYear = 1989, address = "서울시 관악구"))
        mStudentList.add(StudentData("송병섭", birthYear = 1989, address = "서울시 광진구"))
        mStudentList.add(StudentData("안수지", birthYear = 1989, address = "서울시 동대문구"))
        mStudentList.add(StudentData("유병재", birthYear = 1995, address = "경기도 부천시"))
        mStudentList.add(StudentData("이재환", birthYear = 1997, address = "경기도 남양주시"))
        mStudentList.add(StudentData("이준서", birthYear = 2000, address = "경기도 의왕시"))
        mStudentList.add(StudentData("장혜린", birthYear = 1995, address = "인천시 남동구"))

        mAdapter = StudentAdapter(this, R.layout.student_list_item, mStudentList)

        studentListView.adapter = mAdapter

        studentListView.setOnItemClickListener { adapterView, view, position, l ->
            val clickedStudent = mStudentList[position]

            Toast.makeText(this, clickedStudent.name, Toast.LENGTH_SHORT).show()
        }

        studentListView.setOnItemLongClickListener{adapterView, view, position, l ->
            val student = mStudentList[position]

            Toast.makeText(this, "${student.name} 길게 눌림", Toast.LENGTH_SHORT).show()

            return@setOnItemLongClickListener true
        }
    }
}