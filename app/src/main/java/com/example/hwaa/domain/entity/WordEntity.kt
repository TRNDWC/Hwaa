package com.example.hwaa.domain.entity

import java.io.Serializable

data class WordEntryEntity(
    var traditional: String,
    var pinyin: String,
    var definitions: List<String>
) : Serializable {
    constructor() : this("", "", listOf(""))

    override fun toString(): String {
        return "WordEntryEntity(traditional='$traditional', pinyin='$pinyin', definitions=$definitions)"
    }
}

data class PronunciationEntity(
    var url: String
) : Serializable {
    constructor() : this("")

    override fun toString(): String {
        return "PronunciationEntity(url='$url')"
    }
}

data class WordBasicEntity(
    var simplified: String,
    var rank: Int,
    var entries: List<WordEntryEntity>
) : Serializable {
    constructor() : this("", 0, listOf(WordEntryEntity("", "", listOf(""))))

    override fun toString(): String {
        return "WordBasicEntity(simplified='$simplified', rank=$rank, entries=$entries)"
    }
}

data class WordExampleEntity(
    var hanzi: String,
    var pinyin: String,
    var translation: String,
    var audio: String,
    var level: UserLevel
) : Serializable {
    constructor() : this("", "", "", "", UserLevel.ELEMENTARY)

    override fun toString(): String {
        return "WordExampleEntity(hanzi='$hanzi', pinyin='$pinyin', translation='$translation', audio='$audio', level=$level)"
    }
}

data class WordEntity(
    var wordBasic: WordBasicEntity,
    var pronunciation: PronunciationEntity,
    var example: List<WordExampleEntity>
) : Serializable {
    constructor() : this(
        WordBasicEntity(
            "",
            0,
            listOf(
                WordEntryEntity(
                    "",
                    "",
                    listOf("")
                )
            )
        ),
        PronunciationEntity(""),
        listOf(
            WordExampleEntity(
                "",
                "",
                "",
                "",
                UserLevel.ELEMENTARY
            )
        )
    )

    override fun toString(): String {
        return "WordEntity(wordBasic=$wordBasic, pronunciation=$pronunciation, example=$example)"
    }
}