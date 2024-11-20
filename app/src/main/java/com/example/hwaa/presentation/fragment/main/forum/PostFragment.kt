package com.example.hwaa.presentation.fragment.main.forum

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hwaa.R
import com.example.hwaa.data.model.BlogModel
import com.example.hwaa.data.model.CommentModel
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentPostBinding
import com.example.hwaa.domain.Response
import com.example.hwaa.presentation.navigation.forum.ForumNavigation
import com.example.hwaa.presentation.util.UserProvider
import com.example.hwaa.presentation.util.ui.BounceEdgeEffectFactory
import com.example.hwaa.presentation.viewmodel.ForumViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PostFragment : BaseFragment<FragmentPostBinding, ForumViewModel>(R.layout.fragment_post),
    ForumCallback {
    private val viewModel: ForumViewModel by activityViewModels()
    override fun getVM(): ForumViewModel = viewModel
    private lateinit var postAdapter: PostAdapter
    private val blogList = mutableListOf<BlogModel>()
    private val limit = 10
    private var offset = 0

    @Inject
    lateinit var forumNavigation: ForumNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postAdapter = PostAdapter(
            this
        )
        binding.rvPost.adapter = postAdapter
        binding.rvPost.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvPost.edgeEffectFactory = BounceEdgeEffectFactory()
        postAdapter.createForumItemList()
        loadData(0, 10)
        handleFlow()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarForum()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.blogList.collect { response ->
                when (response) {
                    is Response.Success -> {
                        val newBlogs = response.data
                        newBlogs.forEach {
                            if (blogList.find { blog -> blog.id == it.id } == null) {
                                blogList.add(it)
                                offset++
                            } else {
                                blogList[blogList.indexOfFirst { blog -> blog.id == it.id }] = it
                            }
                        }
                        postAdapter.updateBlogList(blogList)
                    }

                    is Response.Error -> {
                        Toast.makeText(context, response.exception, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.updateBlogFlow.collect { response ->
                (requireActivity() as MainActivity).hiddenLoading()
                when (response) {
                    is Response.Success -> {
                        (requireActivity() as MainActivity).getBinding().apply {
                            etComment.text.clear()
                            etComment.clearFocus()
                            etComment.visibility = View.GONE
                        }
                    }

                    is Response.Error -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Error")
                            .setMessage(response.exception)
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
            }
        }
    }

    private fun loadData(offset: Int, limit: Int) {
        viewModel.getBlogList(offset, limit)
    }

    override fun onItemClicked(position: Int) {
        val bundle = Bundle()
        viewModel.selectedBlog = blogList[position - 1]
        forumNavigation.fromPostToPostDetail(bundle)
    }

    override fun onCreatePostClicked() {
        val bottomSheet = CreatePostFragment()
        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }

    override fun onLikeClicked(blog: PostItem.Post) {
        blog.isLiked = !blog.isLiked
        if (blog.isLiked) {
            blog.blogModel.likes.add(UserProvider.getUser().uid)
        } else {
            blog.blogModel.likes.remove(UserProvider.getUser().uid)
        }
        viewModel.updateBlog(blog.blogModel)
    }

    override fun onCommentClicked(blog: PostItem.Post) {
        forumNavigation.fromPostToPostDetail(Bundle())
        viewModel.selectedBlog = blog.blogModel
    }

    companion object {
        fun newInstance() = PostFragment()
    }
}