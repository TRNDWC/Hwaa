package com.example.hwaa.presentation.fragment.main.vocabulary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.hwaa.databinding.DialogStartReviseBinding
import com.example.hwaa.presentation.navigation.vocabulary.VocabularyNavigation

class CardSettingDialog(var navigation: VocabularyNavigation) : DialogFragment() {

    private val binding by lazy {
        DialogStartReviseBinding.inflate(layoutInflater)
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
        binding.btnStart.setOnClickListener {
            navigation.fromVocabularyToFlashCard()
            dismiss()
        }
    }
}