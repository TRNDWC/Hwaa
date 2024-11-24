package com.example.hwaa.presentation.fragment.main.forum

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
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
import com.example.hwaa.databinding.FragmentPostDetailBinding
import com.example.hwaa.domain.Response
import com.example.hwaa.presentation.util.UserProvider
import com.example.hwaa.presentation.util.ui.BounceEdgeEffectFactory
import com.example.hwaa.presentation.viewmodel.ForumViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailFragment :
    BaseFragment<FragmentPostDetailBinding, ForumViewModel>(R.layout.fragment_post_detail) {
    private val viewModel: ForumViewModel by activityViewModels()

    override fun getVM(): ForumViewModel = viewModel
    private lateinit var commentAdapter: CommentAdapter
    private var blogModel: BlogModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        blogModel = viewModel.selectedBlog
        viewModel.getBlogDetail(blogModel!!)
        commentAdapter = CommentAdapter(
            object : CommentCallback {
                override fun onLikeClicked(blog: CommentItem.BlogDetail) {
                    blog.isLiked = !blog.isLiked
                    if (blog.isLiked) {
                        blog.blogModel.likes.add(UserProvider.getUser().uid)
                    } else {
                        blog.blogModel.likes.remove(UserProvider.getUser().uid)
                    }
                    viewModel.updateBlog(blog.blogModel)
                }
            }
        )
        binding.rvComments.adapter = commentAdapter
        binding.rvComments.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvComments.edgeEffectFactory = BounceEdgeEffectFactory()
        handleFlow()
        handleSendComment()
    }

    private fun handleFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.updateBlogFlow.collect { response ->
                (requireActivity() as MainActivity).hiddenLoading()
                when (response) {
                    is Response.Success -> {
                        (requireActivity() as MainActivity).apply {
                            getBinding().etComment.text.clear()
                            hideKeyboard()
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.blogDetail.collect { response ->
                (requireActivity() as MainActivity).hiddenLoading()
                when (response) {
                    is Response.Success -> {
                        blogModel = response.data
                        commentAdapter.updateCommentList(blogModel!!)
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

    private fun handleSendComment() {
        (requireActivity() as MainActivity).getBinding().apply {
            btnSend.setOnClickListener {
                val comment = CommentModel().apply {
                    content = etComment.text.toString()
                    author = UserProvider.getUser()
                    date = System.currentTimeMillis().toString()
                }
                blogModel?.apply {
                    comments.add(comment)
                    viewModel.updateBlog(blogModel!!)
                    (requireActivity() as MainActivity).showLoading()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarDetailPost()
    }
}