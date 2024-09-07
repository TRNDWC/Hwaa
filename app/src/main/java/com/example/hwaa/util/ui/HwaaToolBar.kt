package com.example.hwaa.util.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.hwaa.R
import com.example.hwaa.databinding.TbMainActivyBinding

class HwaaToolBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {
    private val binding: TbMainActivyBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.tb_main_activy,
        this,
        true
    )
}