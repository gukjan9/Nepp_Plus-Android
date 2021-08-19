package com.gukjang.kotlingrammar_210819

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myName : String
        var myBirthYear : Int       // var - 변경 가능한 변수, val - 변경 불가능한 변수

        myName = "GukJang"
        Log.d("변수 확인", myName)          // String만 출력

        myBirthYear = 1997
        Log.d("출생 년도", myBirthYear.toString())


        // 조건문
        val userAge = 18

        if(userAge >= 20){
            Log.d("나이 확인", "성인입니다.")
        }
        else if (userAge >= 17){
            Log.d("나이 확인", "고등학생입니다.")
        }
        else{
            Log.d("나이 확인", "나이 확인 불가")
        }


        // 논리연산자
        val companyASalary = 5800
        val companyADistance = 12.5
        val companyABalance = true

        if(companyASalary >= 4000){

        }

        if(companyADistance < 10 && companyASalary >= 3000){

        }

        if(companyABalance || companyASalary >= 5000){              // == true (x)

        }
    }
}