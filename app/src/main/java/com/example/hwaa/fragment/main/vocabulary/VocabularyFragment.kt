package com.example.hwaa.fragment.main.vocabulary

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hwaa.R
import com.example.hwaa.viewmodel.VocabularyViewModel

class VocabularyFragment : Fragment() {

    companion object {
        fun newInstance(key: String, color: String): Fragment {
            val fragment = VocabularyFragment()
            val argument = Bundle()
            fragment.arguments = argument
            return fragment
        }
    }

    private val viewModel: VocabularyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_vocabulary, container, false)
    }
}