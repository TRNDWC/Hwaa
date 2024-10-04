package com.example.hwaa.fragment.main.book

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hwaa.R
import com.example.hwaa.activity.main.MainActivity
import com.example.hwaa.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentLessonListBinding
import com.example.hwaa.navigation.book.BookNavigation
import com.example.hwaa.viewmodel.LessonViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LessonsFragment :
    BaseFragment<FragmentLessonListBinding, LessonViewModel>(R.layout.fragment_lesson_list),
    LessonsCallback {
    private val viewModel: LessonViewModel by viewModels()
    override fun getVM() = viewModel
    private val adapter: LessonsAdapter by lazy { LessonsAdapter(this) }

    @Inject
    lateinit var bookNavigation: BookNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvBook.adapter = adapter
        binding.rvBook.layoutManager = LinearLayoutManager(context)

//        (requireActivity() as MainActivity).getBinding().toolbar.getBinding().apply {
//            clLesson.visibility = View.GONE
//            clMain.visibility = View.VISIBLE
//        }
//
//        (requireActivity() as MainActivity).getBinding().toolbar.backIconClickListener = (requireActivity() as MainActivity)
    }

    override fun onItemClicked(position: Int) {
        (activity as MainActivity).showAppBar()
        bookNavigation.fromBookToLesson(null)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setAppBarHideOnScroll(true)
    }

    override fun onBookmarkClicked(position: Int, view: View) {
        view.isSelected = !view.isSelected
    }
}