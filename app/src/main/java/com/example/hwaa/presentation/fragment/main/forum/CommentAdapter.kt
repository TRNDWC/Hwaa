package com.example.hwaa.presentation.fragment.main.forum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hwaa.R
import com.example.hwaa.data.model.BlogModel
import com.example.hwaa.data.model.CommentModel
import com.example.hwaa.databinding.ItemBlogCommentBinding
import com.example.hwaa.databinding.ItemForumPostBinding
import com.example.hwaa.presentation.util.UserProvider

enum class CommentType {
    BLOG_DETAIL, COMMENT
}

interface CommentCallback {
    fun onLikeClicked(blog: CommentItem.BlogDetail)
}

sealed class CommentItem {
    data class Comment(val commentModel: CommentModel) : CommentItem()
    data class BlogDetail(
        val blogModel: BlogModel, var isLiked: Boolean = false
    ) : CommentItem()
}

class CommentAdapter(
    private val callback: CommentCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val commentList = mutableListOf<CommentItem>()

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemBlogCommentBinding.bind(itemView)
    }

    inner class BlogDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemForumPostBinding.bind(itemView)

        init {
            binding.ibLike.setOnClickListener {
                val blog = commentList[adapterPosition] as CommentItem.BlogDetail
                callback.onLikeClicked(blog)
            }
        }
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (commentList[position]) {
            is CommentItem.Comment -> CommentType.COMMENT.ordinal
            is CommentItem.BlogDetail -> CommentType.BLOG_DETAIL.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CommentType.COMMENT.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_blog_comment, parent, false)
                CommentViewHolder(view)
            }

            CommentType.BLOG_DETAIL.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_forum_post, parent, false)
                BlogDetailViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommentViewHolder -> {
                val comment = commentList[position] as CommentItem.Comment
                holder.binding.tvContent.text = comment.commentModel.content
                holder.binding.tvName.text = comment.commentModel.author?.displayName
                Glide.with(holder.itemView.context)
                    .load(comment.commentModel.author?.profileImage)
                    .circleCrop()
                    .placeholder(R.drawable.ic_profile)
                    .into(holder.binding.ivAvatar)
            }

            is BlogDetailViewHolder -> {
                val blogDetail = commentList[position] as CommentItem.BlogDetail
                holder.binding.apply {
                    tvContent.text = blogDetail.blogModel.content
                    tvName.text = blogDetail.blogModel.author?.displayName
                    Glide.with(holder.itemView.context)
                        .load(blogDetail.blogModel.author?.profileImage)
                        .circleCrop()
                        .placeholder(R.drawable.ic_profile)
                        .into(holder.binding.ivAvatar)
                    Glide.with(holder.itemView.context)
                        .load(blogDetail.blogModel.image)
                        .into(holder.binding.ivPost)
                    ivPost.visibility =
                        if (blogDetail.blogModel.image.isEmpty()) View.GONE else View.VISIBLE
                    tvTime.text = blogDetail.blogModel.date
                    tvLike.text = blogDetail.blogModel.likes.size.toString()
                    tvComment.text = blogDetail.blogModel.comments.size.toString()
                    ibLike.setImageResource(
                        if (blogDetail.isLiked) R.drawable.ic_liked
                        else R.drawable.ic_like
                    )
                }
            }
        }
    }

    fun updateCommentList(blogModel: BlogModel) {
        this.commentList.clear()
        this.commentList.add(
            CommentItem.BlogDetail(
                blogModel,
                blogModel.likes.contains(UserProvider.getUser().uid)
            )
        )
        this.commentList.addAll(blogModel.comments.map { CommentItem.Comment(it) })
        notifyDataSetChanged()
    }
}