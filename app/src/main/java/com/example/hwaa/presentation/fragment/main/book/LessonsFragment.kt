package com.example.hwaa.presentation.fragment.main.book

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hwaa.R
import com.example.hwaa.data.model.UserModel
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentLessonListBinding
import com.example.hwaa.databinding.TbBookFragmentBinding
import com.example.hwaa.presentation.navigation.book.BookNavigation
import com.example.hwaa.presentation.util.UserProvider
import com.example.hwaa.presentation.util.ui.BounceEdgeEffectFactory
import com.example.hwaa.presentation.viewmodel.LessonViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LessonsFragment :
    BaseFragment<FragmentLessonListBinding, LessonViewModel>(R.layout.fragment_lesson_list),
    LessonsCallback {
    private val viewModel: LessonViewModel by viewModels()
    override fun getVM() = viewModel
    private val adapter: LessonsAdapter by lazy { LessonsAdapter(this) }
    private val user: UserModel? = UserProvider.getUser()

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
        val toolbarBinding =
            (activity as MainActivity).getBinding().toolbar.getBinding() as TbBookFragmentBinding
        toolbarBinding.apply {
            Glide.with(root.context)
                .load(user?.profileImage)
                .circleCrop()
                .into(ivAvatar)
            tvStreak.apply {
                text = user?.streak.toString()
                setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        if ((user?.streak ?: 0) > 0) R.color.streak_show else R.color.streak_hide
                    )
                )
            }

            tvStar.apply {
                text = user?.stars.toString()
                setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        if ((user?.stars ?: 0) > 0) R.color.star_show else R.color.star_hide
                    )
                )
            }

            tvStar.text = user?.stars.toString()

            ivStreak.setImageResource(
                if ((user?.streak
                        ?: 0) > 0
                ) R.drawable.ic_streak_show else R.drawable.ic_streak_hide
            )

            ivStar.setImageResource(
                if ((user?.stars ?: 0) > 0) R.drawable.ic_star_show else R.drawable.ic_star_hide
            )

        }
    }

    override fun onBookmarkClicked(position: Int, view: View) {
        view.isSelected = !view.isSelected
    }
}