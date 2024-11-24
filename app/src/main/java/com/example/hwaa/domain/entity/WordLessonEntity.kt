package com.example.hwaa.domain.entity

import com.example.hwaa.data.model.WordModel
import com.example.hwaa.data.model.WordStatModel

class WordLessonEntity(
    var id: String,
    var hanzi: String,
    var translation: String,
    var pinyinTone: String,
    var example: String,
    var exampleTranslation: String,
    var question: QuestionEntity,
    var image: String,
    var audio: String
) {

    constructor() : this("", "", "", "", "", "", QuestionEntity(), "", "")

    override fun toString(): String {
        return "WordLessonEntity(id='$id', hanzi='$hanzi', translation='$translation', pinyinTone='$pinyinTone', example='$example', exampleTranslation='$exampleTranslation', question=$question, image='$image', audio='$audio')\n"
    }

    companion object {
        fun translateToEntity(it: WordModel): WordLessonEntity {
            return WordLessonEntity().apply {
                id = it.id
                hanzi = it.hanzi
                translation = it.translation
                pinyinTone = it.pinyinTone
                example = it.example
                exampleTranslation = it.exampleTranslation
                question = QuestionEntity.translateToEntity(it.question)
                image = it.image
                audio = it.audio
            }
        }

        fun translateToModel(it: WordLessonEntity): WordModel {
            return WordModel().apply {
                id = it.id
                hanzi = it.hanzi
                translation = it.translation
                pinyinTone = it.pinyinTone
                example = it.example
                exampleTranslation = it.exampleTranslation
                question = QuestionEntity.translateToModel(it.question)
                image = it.image
                audio = it.audio
            }
        }
    }
}

class WordStatEntity(
    var wordLessonEntity: WordLessonEntity,
    var isRemembered: String,
    var lastTimeLeaned: String,
    var score: String
) {
    constructor() : this(WordLessonEntity(), "", "", "")

    companion object {
        fun translateToEntity(it: WordStatModel): WordStatEntity {
            return WordStatEntity().apply {
                wordLessonEntity = WordLessonEntity.translateToEntity(it.word)
                isRemembered = it.isRemembered.toString()
                lastTimeLeaned = it.lastTimeLeaned.toString()
                score = it.score.toString()
            }
        }

        fun translateToModel(it: WordStatEntity): WordStatModel {
            return WordStatModel().apply {
                word = WordLessonEntity.translateToModel(it.wordLessonEntity)
                isRemembered = it.isRemembered.toBoolean()
                lastTimeLeaned = it.lastTimeLeaned.toLong()
                score = it.score.toInt()
            }
        }
    }
}