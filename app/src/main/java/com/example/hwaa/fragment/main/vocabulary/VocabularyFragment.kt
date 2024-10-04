package com.example.hwaa.fragment.main.vocabulary

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.hwaa.R
import com.example.hwaa.activity.main.MainActivity
import com.example.hwaa.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentVocabularyBinding
import com.example.hwaa.navigation.vocabulary.VocabularyNavigation
import com.example.hwaa.viewmodel.VocabularyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VocabularyFragment :
    BaseFragment<FragmentVocabularyBinding, VocabularyViewModel>(R.layout.fragment_vocabulary) {

    private val viewModel: VocabularyViewModel by viewModels()
    override fun getVM() = viewModel

    @Inject
    lateinit var vocabularyNavigation: VocabularyNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.vocabularyNavHostFragment) as NavHostFragment
        vocabularyNavigation.bind(navHostFragment.navController)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarVocab()
    }

    companion object VocabularyFragment {
        fun newInstance(key: String, color: String): Fragment {
            val fragment = VocabularyFragment()
            val argument = Bundle()
            fragment.arguments = argument
            return fragment
        }
    }
}