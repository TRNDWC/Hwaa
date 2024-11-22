package com.example.hwaa.domain.entity

import com.example.hwaa.data.model.LessonModel
import com.example.hwaa.data.model.LessonStatModel
import java.io.Serializable

class LessonEntity(
    var id: String,
    var title: String,
    var image: String,
    var words: List<WordStatEntity>
) : Serializable {
    override fun toString(): String {
        return "LessonEntity(id=$id, title='$title', image='$image', words=$words)\n"
    }

    constructor() : this("", "", "", emptyList())

    companion object {
        fun translateToEntity(lessonModel: LessonModel): LessonEntity {
            return LessonEntity().apply {
                id = lessonModel.id.toString()
                title = lessonModel.title
                image = lessonModel.image
                words = lessonModel.words.map { WordStatEntity.translateToEntity(it) }
            }
        }

        fun translateToModel(it: LessonEntity): LessonModel {
            return LessonModel().apply {
                id = it.id
                title = it.title
                image = it.image
                words = it.words.map { WordStatEntity.translateToModel(it) }
            }
        }
    }

}

class LessonStatEntity(
    var lessonEntity: LessonEntity,
    var stars: String,
    var isLearned: String
) {
    constructor() : this(LessonEntity(), "", "")

    companion object {
        fun translateToEntity(lessonStatModel: LessonStatModel): LessonStatEntity {
            return LessonStatEntity().apply {
                lessonEntity = LessonEntity.translateToEntity(lessonStatModel.lessonModel)
                stars = lessonStatModel.star.toString()
                isLearned = lessonStatModel.isLearned.toString()
            }
        }

        fun translateToModel(lessonStatEntity: LessonStatEntity): LessonStatModel {
            return LessonStatModel().apply {
                lessonModel = LessonEntity.translateToModel(lessonStatEntity.lessonEntity)
                star = lessonStatEntity.stars.toInt()
                isLearned = lessonStatEntity.isLearned.toBoolean()
            }
        }
    }
}