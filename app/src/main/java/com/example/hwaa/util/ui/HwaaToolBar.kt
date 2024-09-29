package com.example.hwaa.util.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.example.hwaa.R
import com.example.hwaa.databinding.TbBookFragmentBinding
import com.example.hwaa.databinding.TbDetailPostFragmentBinding
import com.example.hwaa.databinding.TbForumFragmentBinding
import com.example.hwaa.databinding.TbLessonFragmentBinding
import com.google.android.material.appbar.CollapsingToolbarLayout

interface HwaaToolBarCallBack {
    fun backBookClickListener()
    fun backPostClickListener()
}

class HwaaToolBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    private var binding: ViewBinding? = null
    var callBack: HwaaToolBarCallBack? = null

    fun inflateToolBarLayout(layoutId: Int, collapsingToolbarLayout: CollapsingToolbarLayout) {
        removeAllViews()
        binding = when (layoutId) {
            R.layout.tb_lesson_fragment -> {
                val binding =
                    TbLessonFragmentBinding.inflate(LayoutInflater.from(context), this, true)
                setListenerForLesson(binding)
                binding
            }

            R.layout.tb_forum_fragment -> {
                val binding =
                    TbForumFragmentBinding.inflate(LayoutInflater.from(context), this, true)
                setListenerForForum(binding)
                binding
            }

            R.layout.tb_book_fragment -> {
                val binding =
                    TbBookFragmentBinding.inflate(LayoutInflater.from(context), this, true)
                setListenerForBook(binding)
                binding
            }

            R.layout.tb_detail_post_fragment -> {
                val binding =
                    TbDetailPostFragmentBinding.inflate(LayoutInflater.from(context), this, true)
                setListenerForDetailPost(binding)
                binding
            }

            else -> null
        }

        post {
            collapsingToolbarLayout.requestLayout()
            collapsingToolbarLayout.invalidate()
        }
    }

    private fun setListenerForLesson(binding: TbLessonFragmentBinding) {
        binding.ibBack.setOnClickListener {
            callBack?.backBookClickListener()
        }
    }

    private fun setListenerForForum(binding: TbForumFragmentBinding) {
//        binding.ivBack.setOnClickListener {
//            callBack?.backIconClickListener()
//        }
    }

    private fun setListenerForBook(binding: TbBookFragmentBinding) {

    }

    private fun setListenerForDetailPost(binding: TbDetailPostFragmentBinding) {
        binding.ibBack.setOnClickListener {
            callBack?.backPostClickListener()
        }
    }
}