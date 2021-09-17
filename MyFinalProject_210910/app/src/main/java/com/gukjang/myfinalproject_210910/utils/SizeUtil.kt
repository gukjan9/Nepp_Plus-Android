package com.gukjang.myfinalproject_210910.utils

import android.content.Context
import android.util.TypedValue

class SizeUtil {
    companion object {
        fun dpToPx(context: Context, dp: Float) : Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
        }
    }
}