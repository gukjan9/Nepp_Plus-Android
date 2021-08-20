package com.gukjang.listviewpractice_210820

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.gukjang.listviewpractice_210820.adapters.LanguageAdapter
import com.gukjang.listviewpractice_210820.datas.LanguageData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val languageList = ArrayList<LanguageData>()

    lateinit var mAdapter: LanguageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        languageList.add(LanguageData(R.drawable.c_512x512, "C Language", "Hello"))
        languageList.add(LanguageData(R.drawable.cpp_512x512, "C++", "Hello"))
        languageList.add(LanguageData(R.drawable.csharp_512x512, "C#", "Hello"))
        languageList.add(LanguageData(R.drawable.java_512x512, "Java", "Hello"))
        languageList.add(LanguageData(R.drawable.javascript_512x512, "Java Script", "Hello"))
        languageList.add(LanguageData(R.drawable.kotlin_512x512, "Kotlin", "Hello"))
        languageList.add(LanguageData(R.drawable.python_512x512, "Python", "Hello"))
        languageList.add(LanguageData(R.drawable.swift_512x512, "Swift", "Hello"))

        mAdapter = LanguageAdapter(this, R.layout.lang_list_item, languageList)

        langListView.adapter = mAdapter
    }
}