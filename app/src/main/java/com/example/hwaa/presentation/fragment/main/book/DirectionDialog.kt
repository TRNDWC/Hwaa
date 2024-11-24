package com.example.hwaa.presentation.fragment.main.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.hwaa.databinding.DialogDirectionBinding
import com.example.hwaa.presentation.navigation.book.BookNavigation
import kotlinx.coroutines.Job

class DirectionDialog(
    val bookNavigation: BookNavigation,
    val testCallback: () -> Job,
    val closeCallback: () -> Unit
) :
    DialogFragment() {

    private val binding by lazy {
        DialogDirectionBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            dismiss()
            closeCallback.invoke()
        }

        binding.btnTest.setOnClickListener {
            bookNavigation.fromLessonToQuiz(null)
            dismiss()
        }

        binding.btnRevise.setOnClickListener {
            testCallback.invoke()
            dismiss()
        }
    }
}