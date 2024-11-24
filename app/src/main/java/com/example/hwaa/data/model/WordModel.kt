package com.example.hwaa.data.model

class WordModel(
    var id: String,
    var hanzi: String,
    var translation: String,
    var pinyinTone: String,
    var example: String,
    var exampleTranslation: String,
    var question: QuestionModel,
    var image: String,
    var audio: String
) {
    constructor() : this("", "", "", "", "", "", QuestionModel(), "", "")

    companion object{
        fun copy(wordModel: WordModel): WordModel {
            return WordModel(wordModel.id, wordModel.hanzi, wordModel.translation, wordModel.pinyinTone, wordModel.example, wordModel.exampleTranslation, question = wordModel.question, image = wordModel.image, audio = wordModel.audio)
        }
    }
}

class WordStatModel(
    var word: WordModel,
    var isRemembered: Boolean,
    var lastTimeLeaned: Long,
    var score: Int
) {
    constructor() : this(WordModel(), false, 0, 0)

    override fun toString(): String {
        return "WordStatModel(word=$word, isRemembered=$isRemembered, lastTimeLeaned=$lastTimeLeaned, score=$score)"
    }

    companion object{
        fun copy(wordStatModel: WordStatModel): WordStatModel {
            return WordStatModel(WordModel.copy(wordStatModel.word)
                , wordStatModel.isRemembered, wordStatModel.lastTimeLeaned, wordStatModel.score)
        }
    }
}