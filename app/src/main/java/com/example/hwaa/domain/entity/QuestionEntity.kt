package com.example.hwaa.domain.entity

import com.example.hwaa.data.model.QuestionModel

class QuestionEntity(
    var id: String,
    var question: String,
    var answer: String,
    var options: List<String>
) {
    constructor() : this("", "", "", emptyList())

    companion object {
        fun translateToEntity(question: QuestionModel): QuestionEntity {
            return QuestionEntity().apply {
                id = question.id
                this.question = question.question
                answer = question.answer
                options = question.options
            }
        }

        fun translateToModel(question: QuestionEntity): QuestionModel {
            return QuestionModel().apply {
                id = question.id
                this.question = question.question
                answer = question.answer
                options = question.options
            }
        }
    }
}