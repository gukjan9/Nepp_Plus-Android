package com.gukjang.pizzaorderapp_210825

import androidx.appcompat.app.AppCompatActivity

// 1. abstract 함수가 있으면 class 도 abstract
// 2. 상속 받기 위해선 open / abstract
abstract class BaseActivity : AppCompatActivity() {
    val mContext = this

    abstract fun setupEvents()
    abstract fun setValues()
}