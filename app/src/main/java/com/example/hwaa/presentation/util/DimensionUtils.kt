package com.example.hwaa.presentation.util

import android.content.Context
import android.util.TypedValue

object DimensionUtils {

    // Convert dp to pixels
    fun dpToPx(context: Context, dp: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics)
    }

    // Convert pixels to dp
    fun pxToDp(context: Context, px: Int): Float {
        return px / context.resources.displayMetrics.density
    }
}