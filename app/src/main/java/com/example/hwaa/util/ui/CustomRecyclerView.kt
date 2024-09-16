package com.example.hwaa.util.ui

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        val adjustedVelocityX = (velocityX * 0.6f).toInt()
        val adjustedVelocityY = (velocityY * 0.6f).toInt()
        return super.fling(adjustedVelocityX, adjustedVelocityY)
    }
}
