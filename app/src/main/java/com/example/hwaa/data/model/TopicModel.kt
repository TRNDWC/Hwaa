package com.example.hwaa.data.model

import java.io.Serializable

class TopicModel(
    var id: String,
    var title: String,
    var description: String,
    var lessons: List<LessonStatModel>
) : Serializable {
    constructor() : this("", "", "", emptyList())

    companion object {
        fun copy(topicModel: TopicModel): TopicModel {
            return TopicModel(
                topicModel.id,
                topicModel.title,
                topicModel.description,
                topicModel.lessons.map { LessonStatModel.copy(it) })
        }
    }
}

class TopicStatModel(
    var topicModel: TopicModel,
    var totalFinished: Int?
) {
    constructor() : this(TopicModel(), 0)

    companion object {
        fun copy(topicStatModel: TopicStatModel): TopicStatModel {
            return TopicStatModel(
                TopicModel.copy(topicStatModel.topicModel),
                topicStatModel.totalFinished
            )
        }
    }
}