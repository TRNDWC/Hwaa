package com.example.hwaa.domain.entity

import com.example.hwaa.data.model.TestModel

class TestEntity(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val words: List<WordStatEntity>
) {
    constructor() : this(0, "", "", "", emptyList())

    companion object {
        fun translateToModel(testEntity: TestEntity): TestModel {
            return TestModel(
                testEntity.id,
                testEntity.name,
                testEntity.description,
                testEntity.image,
                testEntity.words.map { WordStatEntity.translateToModel(it) }
            )
        }

        fun translateToEntity(testModel: TestModel): TestEntity {
            return TestEntity(
                testModel.id,
                testModel.name,
                testModel.description,
                testModel.image,
                testModel.words.map { WordStatEntity.translateToEntity(it) }
            )
        }
    }
}