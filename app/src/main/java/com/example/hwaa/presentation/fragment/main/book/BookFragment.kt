package com.example.hwaa.presentation.fragment.main.book

import android.app.ActionBar.LayoutParams
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.hwaa.R
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentBookBinding
import com.example.hwaa.presentation.navigation.book.BookNavigation
import com.example.hwaa.presentation.viewmodel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookFragment : BaseFragment<FragmentBookBinding, BookViewModel>(R.layout.fragment_book) {

    val viewModel: BookViewModel by viewModels()
    override fun getVM() = viewModel

    @Inject
    lateinit var bookNavigation: BookNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHost = childFragmentManager.findFragmentById(R.id.bookNavHostFragment) as NavHostFragment
        val navController = navHost.navController
        bookNavigation.bind(navController)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).getBinding().appBarLayout.layoutParams.height = LayoutParams.WRAP_CONTENT
        (requireActivity() as MainActivity).apply {
            showAppBar()
        }
    }

    companion object BookFragment {
        fun newInstance(key: String, color: String): Fragment {
            val fragment = BookFragment()
            val argument = Bundle()
            fragment.arguments = argument
            return fragment
        }
    }

}