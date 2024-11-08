package com.example.hwaa.presentation.fragment.main.book

//recyclerview adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hwaa.R
import com.example.hwaa.databinding.ItemLessonBinding
import com.example.hwaa.databinding.ItemLessonCurrentLevelBinding
import com.example.hwaa.databinding.ItemLessonHeaderBinding
import kotlin.math.abs

enum class BookType {
    HEADER, LESSON, LEVEL
}


interface LessonsCallback {
    fun onItemClicked(position: Int)
    fun onBookmarkClicked(position: Int, view: View)
}

class LessonsAdapter(val callback: LessonsCallback) : RecyclerView.Adapter<ViewHolder>() {

    inner class LessonViewHolder(itemView: View) : ViewHolder(itemView) {
        val binding = ItemLessonBinding.bind(itemView)

        init {
            binding.clLesson.setOnClickListener {
                callback.onItemClicked(adapterPosition)
            }
            addStart(2, binding.llLessonStar)
        }
    }

    inner class HeaderViewHolder(itemView: View) : ViewHolder(itemView) {
        val binding = ItemLessonHeaderBinding.bind(itemView)
    }

    inner class LevelViewHolder(itemView: View) : ViewHolder(itemView) {
        val binding = ItemLessonCurrentLevelBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            BookType.HEADER.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_lesson_header, parent, false)
                HeaderViewHolder(view)
            }

            BookType.LEVEL.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_lesson_current_level, parent, false)
                LevelViewHolder(view)
            }

            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false)
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

            is LevelViewHolder -> {
                holder.binding.tvProgress.text = "50%"
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return createBookList()[position].ordinal
    }

    private fun createBookList(): List<BookType> {
        val list = mutableListOf<BookType>()
        list.add(BookType.LEVEL)
        for (i in 0 until 100) {
            if (i % 7 == 0) {
                list.add(BookType.HEADER)
            } else {
                list.add(BookType.LESSON)
            }
        }
        return list
    }

    private fun addStart(quantity: Int, view: LinearLayout) {
        for (i in 0 until quantity) {
            val imageView = ImageView(view.context)
            imageView.setImageResource(R.drawable.ic_star_filled)
            imageView.imageTintList = view.context.getColorStateList(R.color.start_lesson_color)
            imageView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            imageView.setPadding(0, 0, 4, 0)
            view.addView(imageView)
        }

        for (i in 0 until abs(3 - quantity)) {
            val imageView = ImageView(view.context)
            imageView.setImageResource(R.drawable.ic_star_outline)
            imageView.imageTintList = view.context.getColorStateList(R.color.start_lesson_color)
            imageView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            imageView.setPadding(0, 0, 4, 0)
            view.addView(imageView)
        }
    }

}