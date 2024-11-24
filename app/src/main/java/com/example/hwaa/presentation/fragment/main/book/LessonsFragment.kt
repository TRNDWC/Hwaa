package com.example.hwaa.presentation.fragment.main.book

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hwaa.R
import com.example.hwaa.data.model.LessonStatModel
import com.example.hwaa.data.model.TopicStatModel
import com.example.hwaa.data.model.UserModel
import com.example.hwaa.databinding.FragmentLessonListBinding
import com.example.hwaa.databinding.TbBookFragmentBinding
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.TopicStatEntity
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.presentation.navigation.book.BookNavigation
import com.example.hwaa.presentation.util.UserProvider
import com.example.hwaa.presentation.util.ui.BounceEdgeEffectFactory
import com.example.hwaa.presentation.viewmodel.LessonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LessonsFragment :
    BaseFragment<FragmentLessonListBinding, LessonViewModel>(R.layout.fragment_lesson_list),
    LessonsCallback {
    private val viewModel: LessonViewModel by activityViewModels()
    override fun getVM() = viewModel
    private val adapter: LessonsAdapter by lazy { LessonsAdapter(this) }
    private var user: UserModel = UserProvider.getUser()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val updatedData = intent?.getStringExtra("key")
            if (updatedData == "Updated Data") {
                user = UserProvider.getUser()
            }
        }
    }

    @Inject
    lateinit var bookNavigation: BookNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvBook.adapter = adapter
        binding.rvBook.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvBook.edgeEffectFactory = BounceEdgeEffectFactory()
        viewModel.getTopics()
        handleFlow()
    }


    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter("com.example.DATA_CHANGED")
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }

    override fun onItemClicked(lessonStatModel: LessonStatModel, topicStatModel: TopicStatModel) {
        viewModel.selectedLesson = lessonStatModel
        viewModel.selectedTopic = TopicStatModel.copy(topicStatModel)
        bookNavigation.fromBookToLesson(null)
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarLessons()
        setUser()
    }

    private fun setUser() {
        val toolbarBinding =
            (activity as MainActivity).getBinding().toolbar.getBinding() as TbBookFragmentBinding
        toolbarBinding.apply {
            Glide.with(root.context).load(user.profileImage).circleCrop().into(ivAvatar)
            tvStreak.apply {
                text = user.streak.toString()
                setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        if (user.streak > 0) R.color.streak_show else R.color.streak_hide
                    )
                )
            }

            tvStar.apply {
                text = user.stars.toString()
                setTextColor(
                    ContextCompat.getColor(
                        root.context, if (user.stars > 0) R.color.star_show else R.color.star_hide
                    )
                )
            }

            tvStar.text = user.stars.toString()

            ivStreak.setImageResource(
                if (user.streak > 0) R.drawable.ic_streak_show else R.drawable.ic_streak_hide
            )

            ivStar.setImageResource(
                if (user.stars > 0) R.drawable.ic_star_show else R.drawable.ic_star_hide
            )

        }
    }

    private fun handleFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flowGetTopic.collect {
                when (it) {
                    is Response.Success -> {
                        adapter.updateList(it.data.map { entity ->
                            TopicStatEntity.translateToModel(
                                entity
                            )
                        })
                    }

                    is Response.Error -> {
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flowUpdateWord.collect { response ->
                when (response) {
                    is Response.Success -> {
                        adapter.updateTopic(viewModel.selectedTopic!!)
                    }

                    is Response.Error -> {
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flowUpdateLesson.collect { lesson ->
                adapter.updateTopic(viewModel.selectedTopic!!)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flowRewind.collect {
                binding.rvBook.smoothScrollToPosition(0)
            }
        }
    }

    override fun onBookmarkClicked(position: Int, view: View) {
        view.isSelected = !view.isSelected
    }
}