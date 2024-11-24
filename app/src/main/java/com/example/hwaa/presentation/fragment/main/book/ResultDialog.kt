package com.example.hwaa.presentation.fragment.main.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.hwaa.databinding.LayoutEndQuizDialogBinding
import com.example.hwaa.presentation.core.navigatorComponent.BaseNavigator
import com.example.hwaa.presentation.navigation.book.BookNavigation
import com.example.hwaa.presentation.navigation.vocabulary.VocabularyNavigation

class ResultDialog(val score: Int, val navigation: BaseNavigator) : DialogFragment() {

    private val binding by lazy {
        LayoutEndQuizDialogBinding.inflate(layoutInflater)
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
            if (navigation is BookNavigation) {
                navigation.fromQuizToLessons(null)
            } else if (navigation is VocabularyNavigation) {
                navigation.navigateUp()
            }
        }
        binding.tvDescription.text = "$score"
    }

}

