package com.example.hwaa.data.model

import com.example.hwaa.domain.entity.WordStatEntity
import java.io.Serializable

class LessonModel(
    var id: String,
    var title: String,
    var image: String,
    var words: List<WordStatModel>
) : Serializable {
    constructor() : this("", "", "", emptyList())

    override fun toString(): String {
        return "LessonModel(id='$id', title='$title', image='$image', words=$words)"
    }

    companion object {
        fun copy(lessonModel: LessonModel): LessonModel {
            return LessonModel(
                lessonModel.id,
                lessonModel.title,
                lessonModel.image,
                lessonModel.words.map { WordStatModel.copy(it) })
        }
    }
}

class LessonStatModel(
    var lessonModel: LessonModel,
    var star: Int,
    var isLearned: Boolean
) : Serializable {
    constructor() : this(LessonModel(), 0, false)

    override fun toString(): String {
        return "LessonStatModel(lessonModel=$lessonModel, star=$star)"
    }

    companion object {
        fun copy(lessonStatModel: LessonStatModel): LessonStatModel {
            return LessonStatModel(
                LessonModel.copy(lessonStatModel.lessonModel),
                lessonStatModel.star,
                lessonStatModel.isLearned
            )
        }
    }
}