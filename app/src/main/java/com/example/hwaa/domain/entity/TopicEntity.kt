package com.example.hwaa.domain.entity

import com.example.hwaa.data.model.TopicModel
import com.example.hwaa.data.model.TopicStatModel
import java.io.Serializable

class TopicEntity(
    var id: String,
    var title: String,
    var description: String,
    var lessons: List<LessonStatEntity>
) : Serializable {
    override fun toString(): String {
        return "TopicEntity(id=$id, title='$title', description='$description', lessons=$lessons)\n"
    }

    constructor() : this("", "", "", emptyList())

    companion object {
        fun translateToEntity(topicModel: TopicModel): TopicEntity {
            return TopicEntity().apply {
                id = topicModel.id
                title = topicModel.title
                description = topicModel.description
                lessons = topicModel.lessons.map { LessonStatEntity.translateToEntity(it) }
            }
        }

        fun translateToModel(topicEntity: TopicEntity): TopicModel {
            return TopicModel().apply {
                id = topicEntity.id
                title = topicEntity.title
                description = topicEntity.description
                lessons = topicEntity.lessons.map { LessonStatEntity.translateToModel(it) }
            }
        }
    }
}

class TopicStatEntity(
    var topicEntity: TopicEntity,
    var totalFinished: String?,
) {
    constructor() : this(TopicEntity(), "")

    companion object {
        fun translateToEntity(topicStatModel: TopicStatModel): TopicStatEntity {
            return TopicStatEntity().apply {
                topicEntity = TopicEntity.translateToEntity(topicStatModel.topicModel)
                totalFinished = topicStatModel.totalFinished.toString()
            }
        }

        fun translateToModel(topicStatEntity: TopicStatEntity): TopicStatModel {
            return TopicStatModel().apply {
                topicModel = TopicEntity.translateToModel(topicStatEntity.topicEntity)
                totalFinished = try {
                    topicStatEntity.totalFinished?.toInt()
                } catch (e: Exception) {
                    0
                }
            }
        }
    }
}