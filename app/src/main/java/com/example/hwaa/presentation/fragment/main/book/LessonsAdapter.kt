package com.example.hwaa.presentation.fragment.main.book

//recyclerview adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.hwaa.R
import com.example.hwaa.data.model.LessonStatModel
import com.example.hwaa.data.model.TopicStatModel
import com.example.hwaa.databinding.ItemLessonBinding
import com.example.hwaa.databinding.ItemLessonCurrentLevelBinding
import com.example.hwaa.databinding.ItemLessonHeaderBinding
import com.j256.ormlite.misc.BaseDaoEnabled
import kotlin.math.abs

enum class BookType {
    HEADER, LESSON, LEVEL
}

sealed class BookItem {
    data class Header(
        val topicStatModel: TopicStatModel,
        val topicIndex: Int,
        val isTopicEnabled: Boolean
    ) : BookItem()

    data class Lesson(
        val lessonStatModel: LessonStatModel,
        val topicStatModel: TopicStatModel,
        val lessonIndex: Int,
        val isLessonEnabled: Boolean
    ) :
        BookItem()

    data class Level(val progress: Int) : BookItem()
}

interface LessonsCallback {
    fun onItemClicked(lessonStatModel: LessonStatModel, topicStatModel: TopicStatModel)
    fun onBookmarkClicked(position: Int, view: View)
}

class LessonsAdapter(val callback: LessonsCallback) : RecyclerView.Adapter<ViewHolder>() {

    private val list = mutableListOf<BookItem>()

    inner class LessonViewHolder(itemView: View) : ViewHolder(itemView) {
        val binding = ItemLessonBinding.bind(itemView)
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
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val item = list[position] as BookItem.Header
                holder.binding.apply {
                    tvLessonHeader.text = item.topicStatModel.topicModel.title
                    tvLessonTopic.text = item.topicStatModel.topicModel.description
                    tvLessonComplete.text =
                        "${item.topicStatModel.totalFinished} / ${item.topicStatModel.topicModel.lessons.size}"
                }
            }

            is LessonViewHolder -> {
                val item = list[position] as BookItem.Lesson
                holder.binding.apply {
                    tvLessonTitle.text = item.lessonStatModel.lessonModel.title
                    Glide.with(root.context)
                        .load(item.lessonStatModel.lessonModel.image)
                        .into(ivLessonIcon)
                    addStart(item.lessonStatModel.star, llLessonStar)
                    root.setOnClickListener {
                        if (item.isLessonEnabled)
                            callback.onItemClicked(item.lessonStatModel, item.topicStatModel)
                        else {
                            AlertDialog.Builder(root.context)
                                .setTitle("Not available")
                                .setMessage("Please finish the previous lesson's test to unlock this lesson")
                                .setPositiveButton("OK") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        }
                    }
                    flLessonIcon.isEnabled = item.isLessonEnabled
                    when (checkEnableCase(item)) {
                        EnableCase.BOTH -> {
                            vLessonTopLine.visibility = View.VISIBLE
                            vLessonBottomLine.visibility = View.VISIBLE
                        }

                        EnableCase.NONE -> {
                            vLessonTopLine.visibility = View.INVISIBLE
                            vLessonBottomLine.visibility = View.INVISIBLE
                        }

                        EnableCase.TOP -> {
                            vLessonTopLine.visibility = View.VISIBLE
                            vLessonBottomLine.visibility = View.INVISIBLE
                        }

                        EnableCase.BOTTOM -> {
                            vLessonTopLine.visibility = View.INVISIBLE
                            vLessonBottomLine.visibility = View.VISIBLE
                        }
                    }
                }
                holder.binding.clLesson.alpha = if (item.isLessonEnabled) 1f else 0.5f
            }

            is LevelViewHolder -> {
                val item = list[position] as BookItem.Level
                holder.binding.apply {
                    tvProgress.text = "${item.progress}%"
                    circularProgressIndicator.progress = item.progress
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is BookItem.Header -> BookType.HEADER.ordinal
            is BookItem.Lesson -> BookType.LESSON.ordinal
            is BookItem.Level -> BookType.LEVEL.ordinal
        }
    }

    private fun createBookList() {
        list.clear()
        list.add(BookItem.Level(50))
    }

    private fun addStart(quantity: Int, view: LinearLayout) {
        view.removeAllViews()
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


    fun updateList(data: List<TopicStatModel>) {
        list.clear()
        list.add(BookItem.Level(50))
        for (topicIndex in data.indices) {
            val topic = data[topicIndex]
            val isTopicEnable = if (topicIndex == 0) {
                true
            } else {
                data[topicIndex - 1].totalFinished == data[topicIndex - 1].topicModel.lessons.size
            }
            list.add(BookItem.Header(topic, topicIndex, isTopicEnable))

            val lessonList = topic.topicModel.lessons
            for (lessonIndex in lessonList.indices) {
                val lesson = lessonList[lessonIndex]
                if (!isTopicEnable) {
                    list.add(BookItem.Lesson(lesson, topic, lessonIndex, false))
                    continue
                }
                val isLessonEnable = if (lessonIndex == 0) {
                    true
                } else {
                    lessonList[lessonIndex - 1].star != 0
                }
                list.add(BookItem.Lesson(lesson, topic, lessonIndex, isLessonEnable))
            }
            notifyDataSetChanged()
        }
    }

    fun updateTopic(topicStatModel: TopicStatModel) {
        val index = list.indexOfFirst {
            it is BookItem.Header && it.topicStatModel.topicModel.id == topicStatModel.topicModel.id
        }
        if (index != -1) {
            list[index] = BookItem.Header(topicStatModel, index, true)
            updateLesson(topicStatModel.topicModel.lessons, topicStatModel)
            notifyDataSetChanged()
        }
    }

    fun updateLesson(lessons: List<LessonStatModel>, topicStatModel: TopicStatModel) {
        for (lessonIndex in lessons.indices) {
            val lesson = lessons[lessonIndex]
            val index = list.indexOfFirst {
                it is BookItem.Lesson && it.lessonStatModel.lessonModel.id == lesson.lessonModel.id
            }
            if (index != -1) {
                val isLessonEnable = if (lessonIndex == 0) {
                    true
                } else {
                    lessons[lessonIndex - 1].star != 0
                }
                list[index] = BookItem.Lesson(lesson, topicStatModel, lessonIndex, isLessonEnable)
            }
        }
        notifyDataSetChanged()
    }

    enum class EnableCase {
        BOTH, NONE, TOP, BOTTOM
    }

    fun checkEnableCase(item: BookItem.Lesson): EnableCase {
        return if (!item.isLessonEnabled) {
            EnableCase.NONE
        } else {
            when (item.lessonIndex) {
                0 -> {
                    if (item.lessonStatModel.star > 0) {
                        EnableCase.BOTTOM
                    } else {
                        EnableCase.NONE
                    }
                }

                item.topicStatModel.topicModel.lessons.size - 1 -> {
                    if (item.lessonStatModel.star > 0) {
                        EnableCase.TOP
                    } else {
                        EnableCase.NONE
                    }
                }

                else -> {
                    if (item.lessonStatModel.star > 0) {
                        EnableCase.BOTH
                    } else if (item.topicStatModel.topicModel.lessons[item.lessonIndex - 1].star > 0) {
                        EnableCase.TOP
                    } else {
                        EnableCase.NONE
                    }
                }
            }
        }
    }
}