package com.example.hwaa.presentation.util.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.example.hwaa.R
import com.example.hwaa.databinding.TbFlashCardBinding
import com.example.hwaa.databinding.TbBookFragmentBinding
import com.example.hwaa.databinding.TbDetailPostFragmentBinding
import com.example.hwaa.databinding.TbForumFragmentBinding
import com.example.hwaa.databinding.TbLessonFragmentBinding
import com.example.hwaa.databinding.TbVocabFragmentBinding
import com.google.android.material.appbar.CollapsingToolbarLayout

interface HwaaToolBarCallBack {
    fun backBookClickListener()
    fun backPostClickListener()
    fun backIconClickListener()
}

interface TagClickListener {
    fun tagSelectedListener(tagType: TagType)
    fun startReviseListener()
    fun backFromFlashCard()
}

class HwaaToolBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    private var binding: ViewBinding? = null
    var callBack: HwaaToolBarCallBack? = null
    var tagClickListener: TagClickListener? = null

    fun inflateToolBarLayout(
        layoutId: Int? = null, collapsingToolbarLayout: CollapsingToolbarLayout
    ) {
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

            R.layout.tb_vocab_fragment -> {
                val binding =
                    TbVocabFragmentBinding.inflate(LayoutInflater.from(context), this, true)
                setListenerForVocab(binding)
                binding
            }

            R.layout.tb_flash_card -> {
                val binding =
                    TbFlashCardBinding.inflate(LayoutInflater.from(context), this, true)
                setListenerForFlashCard(binding)
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

    }

    private fun setListenerForBook(binding: TbBookFragmentBinding) {

    }

    private fun setListenerForDetailPost(binding: TbDetailPostFragmentBinding) {
        binding.ibBack.setOnClickListener {
            callBack?.backPostClickListener()
        }
    }

    private fun setListenerForVocab(binding: TbVocabFragmentBinding) {
        binding.ibQuickRevise.setOnClickListener {
            tagClickListener?.startReviseListener()
        }
    }

    private fun setListenerForFlashCard(binding: TbFlashCardBinding) {
        binding.ibBack.setOnClickListener {
            tagClickListener?.backFromFlashCard()
        }
    }
}