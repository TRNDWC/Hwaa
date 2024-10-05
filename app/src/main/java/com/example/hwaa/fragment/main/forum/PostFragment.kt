package com.example.hwaa.fragment.main.forum

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hwaa.R
import com.example.hwaa.activity.main.MainActivity
import com.example.hwaa.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentPostBinding
import com.example.hwaa.navigation.forum.ForumNavigation
import com.example.hwaa.util.ui.BounceEdgeEffectFactory
import com.example.hwaa.viewmodel.ForumViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostFragment : BaseFragment<FragmentPostBinding, ForumViewModel>(R.layout.fragment_post),
    ForumCallback {
    private val viewModel: ForumViewModel by viewModels()
    override fun getVM(): ForumViewModel = viewModel
    private lateinit var postAdapter: PostAdapter

    @Inject
    lateinit var forumNavigation: ForumNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postAdapter = PostAdapter(
            listOf(
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3",
                "1",
                "2",
                "3"
            ), layoutInflater, this
        )
        postAdapter.createForumItemList()
        binding.rvPost.adapter = postAdapter
        binding.rvPost.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvPost.edgeEffectFactory = BounceEdgeEffectFactory()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarForum()
    }

    override fun onItemClicked(position: Int) {
        forumNavigation.fromPostToPostDetail(Bundle())
    }

    override fun onCreatePostClicked() {
        val bottomSheet = CreatePostFragment()
        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostFragment().apply {
            }
    }
}