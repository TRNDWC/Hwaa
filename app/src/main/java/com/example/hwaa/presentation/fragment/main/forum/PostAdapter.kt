package com.example.hwaa.presentation.fragment.main.forum

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.hwaa.R
import com.example.hwaa.data.model.BlogModel
import com.example.hwaa.data.model.UserModel
import com.example.hwaa.databinding.ItemForumCreateBinding
import com.example.hwaa.databinding.ItemForumPostBinding
import com.example.hwaa.presentation.util.UserProvider
import java.text.SimpleDateFormat
import java.util.Date

enum class PostType {
    CREATE_POST, POST
}

interface ForumCallback {
    fun onItemClicked(position: Int)
    fun onCreatePostClicked()
    fun onLikeClicked(blog: PostItem.Post)
    fun onCommentClicked(blog: PostItem.Post)
}

sealed class PostItem(val type: PostType) {
    data class CreatePost(val userModel: UserModel) : PostItem(PostType.CREATE_POST)
    data class Post(
        var blogModel: BlogModel, var isLiked: Boolean = false
    ) : PostItem(PostType.POST)
}

class PostAdapter(
    private val callback: ForumCallback
) : RecyclerView.Adapter<ViewHolder>() {

    private val forumItemList = mutableListOf<PostItem>()

    inner class PostViewHolder(itemView: View) : ViewHolder(itemView) {
        val binding = ItemForumPostBinding.bind(itemView)

        init {
            binding.clItemForumPost.setOnClickListener {
                callback.onItemClicked(adapterPosition)
            }
            binding.ibLike.setOnClickListener {
                val blog = forumItemList[adapterPosition] as PostItem.Post
                callback.onLikeClicked(blog)
            }
            binding.ibComment.setOnClickListener {
                val blog = forumItemList[adapterPosition] as PostItem.Post
                callback.onCommentClicked(blog)
            }
        }
    }

    inner class CreatePostViewHolder(itemView: View) : ViewHolder(itemView) {
        val binding = ItemForumCreateBinding.bind(itemView)

        init {
            binding.clCreate.setOnClickListener {
                callback.onCreatePostClicked()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            PostType.CREATE_POST.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_forum_create, parent, false)
                CreatePostViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_forum_post, parent, false)
                PostViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return forumItemList[position].type.ordinal
    }

    override fun getItemCount(): Int {
        return forumItemList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> {
                holder.binding.apply {
                    val blogModel = forumItemList[position] as PostItem.Post
                    tvName.text = blogModel.blogModel.author?.displayName
                    tvTime.text = getDateTime(blogModel.blogModel.date)
                    tvContent.text = blogModel.blogModel.content
                    tvLike.text = blogModel.blogModel.likes.size.toString()
                    tvComment.text = blogModel.blogModel.comments.size.toString()
                    Glide.with(holder.itemView.context)
                        .load(blogModel.blogModel.author?.profileImage)
                        .into(ivAvatar)
                    if (blogModel.blogModel.image.isNotEmpty()) {
                        Glide.with(holder.itemView.context)
                            .load(blogModel.blogModel.image)
                            .centerCrop()
                            .into(ivPost)
                        ivPost.visibility = View.VISIBLE
                    }
                    ibLike.setImageResource(
                        if (blogModel.isLiked) R.drawable.ic_liked
                        else R.drawable.ic_like
                    )
                }
            }

            is CreatePostViewHolder -> {
                holder.binding.apply {
                    val userModel = (forumItemList[position] as PostItem.CreatePost).userModel
                    Glide.with(holder.itemView.context)
                        .load(userModel.profileImage)
                        .into(ivAvatar)
                    tvName.text = userModel.displayName
                }
            }
        }
    }

    private fun getDateTime(dateTime: String): String {
        val time = dateTime.toLong()
        val date = Date(time)
        val displayDateFormat = SimpleDateFormat("dd MMM yyyy")
        val displayTimeFormat = SimpleDateFormat("hh:mm a")
        return "${displayDateFormat.format(date)} at ${displayTimeFormat.format(date)}"
    }

    fun createForumItemList() {
        forumItemList.clear()
        forumItemList.add(PostItem.CreatePost(UserProvider.getUser()))
    }

    fun updateBlogList(blogList: List<BlogModel>) {
        forumItemList.clear()
        forumItemList.add(PostItem.CreatePost(UserProvider.getUser()))
        blogList.forEach {
            forumItemList.add(
                PostItem.Post(
                    it, it.likes.contains(UserProvider.getUser().uid)
                )
            )
        }
        notifyDataSetChanged()
    }
}