package com.example.hwaa.presentation.fragment.main.book

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hwaa.R
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentLessonListBinding
import com.example.hwaa.presentation.navigation.book.BookNavigation
import com.example.hwaa.presentation.util.ui.BounceEdgeEffectFactory
import com.example.hwaa.presentation.viewmodel.LessonViewModel
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
        binding.rvBook.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvBook.edgeEffectFactory = BounceEdgeEffectFactory()
    }

    override fun onItemClicked(position: Int) {
        (activity as MainActivity).showAppBar()
        bookNavigation.fromBookToLesson(null)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarLessons()
    }

    override fun onBookmarkClicked(position: Int, view: View) {
        view.isSelected = !view.isSelected
    }
}