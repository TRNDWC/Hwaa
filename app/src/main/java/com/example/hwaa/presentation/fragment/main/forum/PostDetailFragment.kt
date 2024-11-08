package com.example.hwaa.presentation.fragment.main.forum

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.hwaa.R
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentPostDetailBinding
import com.example.hwaa.presentation.viewmodel.ForumViewModel

class PostDetailFragment :
    BaseFragment<FragmentPostDetailBinding, ForumViewModel>(R.layout.fragment_post_detail) {
    private val viewModel: ForumViewModel by viewModels()
    override fun getVM(): ForumViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarDetailPost()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostDetailFragment().apply {

            }
    }

}