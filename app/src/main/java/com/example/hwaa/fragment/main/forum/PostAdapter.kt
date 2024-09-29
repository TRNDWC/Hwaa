package com.example.hwaa.fragment.main.forum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hwaa.R
import com.example.hwaa.databinding.ItemForumCreateBinding
import com.example.hwaa.databinding.ItemForumPostBinding

enum class PostType {
    CREATE_POST, POST
}

interface ForumCallback {
    fun onItemClicked(position: Int)
    fun onCreatePostClicked()
}

class PostAdapter(
    private val postList: List<String>,
    private val layoutInflater: LayoutInflater,
    private val callback: ForumCallback
) : RecyclerView.Adapter<ViewHolder>() {

    private val forumItemList = mutableListOf<PostType>()

    inner class PostViewHolder(itemView: View) : ViewHolder(itemView) {
        val binding = ItemForumPostBinding.bind(itemView)

        init {
            binding.clItemForumPost.setOnClickListener {
                callback.onItemClicked(adapterPosition)
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
        return forumItemList[position].ordinal
    }

    override fun getItemCount(): Int {
        return forumItemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> {
                holder.binding.tvContent.text = forumItemList[position].toString()
            }

            is CreatePostViewHolder -> {
                holder.binding.tvName.text = forumItemList[position].toString()
            }
        }
    }

    fun createForumItemList() {
        forumItemList.clear()
        forumItemList.add(PostType.CREATE_POST)
        postList.forEach { _ ->
            forumItemList.add(PostType.POST)
        }
    }
}