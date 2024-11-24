package com.example.hwaa.data.model

class TestModel(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val words: List<WordStatModel>
) {
    constructor() : this(0, "", "", "", emptyList())
}