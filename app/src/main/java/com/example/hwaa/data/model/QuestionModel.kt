package com.example.hwaa.data.model

class QuestionModel(
    var id: String,
    var question: String,
    var answer: String,
    var options: List<String>
) {
    constructor() : this("", "", "", emptyList())
}