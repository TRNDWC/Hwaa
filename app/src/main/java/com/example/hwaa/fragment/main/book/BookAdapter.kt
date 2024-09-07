package com.example.hwaa.fragment.main.book

//recyclerview adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hwaa.R
import com.example.hwaa.databinding.ItemLessonBinding
import com.example.hwaa.databinding.ItemLessonHeaderBinding

enum class BookType {
    HEADER, LESSON
}


interface BookItemCallback {
    fun onItemClicked(position: Int)
    fun onBookmarkClicked(position: Int, view: View)
}

class BookAdapter (val callback: BookItemCallback) : RecyclerView.Adapter<ViewHolder>() {

    inner class LessonViewHolder(itemView: View) : ViewHolder(itemView) {
        val binding = ItemLessonBinding.bind(itemView)
        init {
            binding.root.setOnClickListener {
                callback.onItemClicked(adapterPosition)
            }
            binding.ibBookmark.setOnClickListener {
                callback.onBookmarkClicked(adapterPosition, it)
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : ViewHolder(itemView) {
        val binding = ItemLessonHeaderBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            BookType.HEADER.ordinal -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson_header, parent, false)
                HeaderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false)
                LessonViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return createBookList().size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is LessonViewHolder -> {
                holder.binding.tvLessonTitle.text = "Lesson $position"
            }
            is HeaderViewHolder -> {
                holder.binding.tvLessonHeader.text = "Chapter $position"
                holder.binding.tvLessonTopic.text = "Topic $position"
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return createBookList()[position].ordinal
    }

    private fun createBookList(): List<BookType> {
//        moi 5 bai se co 1 header
        val list = mutableListOf<BookType>()
        for (i in 0 until 100) {
            if (i % 7 == 0) {
                list.add(BookType.HEADER)
            } else {
                list.add(BookType.LESSON)
            }
        }
        return list
    }

}