package com.example.hwaa.data.api

import com.example.hwaa.domain.entity.PronunciationEntity
import com.example.hwaa.domain.entity.WordBasicEntity
import com.example.hwaa.domain.entity.WordExampleEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WordApi {
    @GET("api/lookup/{word}")
    suspend fun getWordBasic(@Path("word") word: String): Response<WordBasicEntity>

    @GET("api/{word}")
    suspend fun getWordPronunciation(@Path("word") word: String): Response<PronunciationEntity>

    @GET("api/sentences/{word}")
    suspend fun getWordExample(
        @Path("word") word: String,
        @Query("level") level: String
    ): Response<List<WordExampleEntity>>
}