package com.example.hwaa.presentation.fragment.main.forum

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.example.hwaa.R
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentForumBinding
import com.example.hwaa.presentation.navigation.forum.ForumNavigation
import com.example.hwaa.presentation.viewmodel.ForumViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForumFragment : BaseFragment<FragmentForumBinding, ForumViewModel>(R.layout.fragment_forum) {
    private val viewModel: ForumViewModel by viewModels()
    override fun getVM(): ForumViewModel = viewModel

    @Inject
    lateinit var forumNavigation: ForumNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.forumNavHostFragment) as NavHostFragment
        forumNavigation.bind(navHostFragment.navController)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForumFragment().apply {

            }
    }

}