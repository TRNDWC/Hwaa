package com.example.hwaa.data.repository.learning

import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.LessonEntity
import com.example.hwaa.domain.entity.LessonStatEntity
import com.example.hwaa.domain.entity.QuestionEntity
import com.example.hwaa.domain.entity.TestEntity
import com.example.hwaa.domain.entity.TopicEntity
import com.example.hwaa.domain.entity.TopicStatEntity
import com.example.hwaa.domain.entity.WordLessonEntity
import com.example.hwaa.domain.entity.WordStatEntity
import com.example.hwaa.presentation.util.UserProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber

@ActivityScoped
class LearningRepositoryImpl @Inject constructor(
    private val realtimeDataBase: FirebaseDatabase, private val storage: FirebaseStorage
) : LearningRepository {

    override suspend fun getTopics(): Flow<Response<List<TopicStatEntity>>> = channelFlow {
        try {
            // Reference to the topics root
            val ref = realtimeDataBase.getReference("topics")
            val topicsSnapshot = ref.get().await()
            val user = UserProvider.getUser()

            // Create a list of deferred results for topics
            val topicDeferreds = topicsSnapshot.children.map { topic ->
                async {
                    // Fetch topic stats for the user
                    val topicStatDeferred = async {
                        realtimeDataBase.getReference("topicStats").child(user.uid)
                            .child(topic.key!!).get().await()
                    }

                    // Fetch lessons for this topic in parallel
                    val lessonsSnapshot = topic.child("lessons").children
                    val lessonsDataDeferred = async {
                        val lessonsData = mutableListOf<LessonStatEntity>()

                        // Process each lesson concurrently
                        lessonsSnapshot.map { lesson ->
                            async {
                                // Fetch lesson stats for the user
                                val lessonStatDeferred = async {
                                    realtimeDataBase.getReference("lessonStats").child(user.uid)
                                        .child(lesson.key!!).get().await()
                                }

                                // Fetch words for this lesson in parallel
                                val wordIds =
                                    lesson.child("words").value as? List<String> ?: emptyList()
                                val wordsDataDeferred = async {
                                    val wordsData = mutableListOf<WordStatEntity>()

                                    // Fetch word data in parallel
                                    wordIds.map { wordId ->
                                        async {
                                            val wordRef =
                                                realtimeDataBase.getReference("words").child(wordId)
                                            val wordSnapshot = wordRef.get().await()

                                            // Fetch word stats for the user in parallel
                                            val wordStatDeferred = async {
                                                realtimeDataBase.getReference("wordStats")
                                                    .child(user.uid).child(wordId).get().await()
                                            }

                                            if (wordSnapshot.exists()) {
                                                val questionSnapshot =
                                                    wordSnapshot.child("question")
                                                val optionsData =
                                                    questionSnapshot.child("options").children.map { it.value.toString() }

                                                // Await the result of word stats fetch
                                                val wordStatSnapshot = wordStatDeferred.await()

                                                return@async WordStatEntity().apply {
                                                    wordLessonEntity = WordLessonEntity(
                                                        id = wordSnapshot.child("id").value.toString(),
                                                        hanzi = wordSnapshot.child("hanzi").value.toString(),
                                                        translation = wordSnapshot.child("translation").value.toString(),
                                                        pinyinTone = wordSnapshot.child("pinyinTone").value.toString(),
                                                        example = wordSnapshot.child("example").value.toString(),
                                                        exampleTranslation = wordSnapshot.child("exampleTranslation").value.toString(),
                                                        question = QuestionEntity().apply {
                                                            id = 0.toString()
                                                            question =
                                                                questionSnapshot.child("question").value.toString()
                                                            answer =
                                                                questionSnapshot.child("answer").value.toString()
                                                            options = optionsData
                                                        },
                                                        image = wordSnapshot.child("image").value.toString(),
                                                        audio = wordSnapshot.child("audio").value.toString()
                                                    )
                                                    if (wordStatSnapshot.exists()) {
                                                        isRemembered =
                                                            wordStatSnapshot.child("isRemembered").value.toString()
                                                        lastTimeLeaned =
                                                            wordStatSnapshot.child("lastTimeLeaned").value.toString()
                                                        score =
                                                            wordStatSnapshot.child("score").value.toString()
                                                    } else {
                                                        isRemembered = "false"
                                                        lastTimeLeaned = "0"
                                                        score = "0"
                                                    }
                                                }
                                            }
                                            return@async null // Return null if the word doesn't exist
                                        }
                                    }.awaitAll()
                                        .filterNotNull() // Await all words and filter out nulls
                                }

                                // Await the result of words data fetch
                                val wordsDataResult = wordsDataDeferred.await()

                                return@async LessonStatEntity().apply {
                                    lessonEntity = LessonEntity().apply {
                                        id = lesson.child("id").value.toString()
                                        title = lesson.child("title").value.toString()
                                        image = lesson.child("image").value.toString()
                                        words = wordsDataResult
                                    }
                                    stars = if (lessonStatDeferred.await().exists()) {
                                        lessonStatDeferred.await().child("stars").value.toString()
                                    } else {
                                        "0" // Default value if no stats exist
                                    }
                                    isLearned = if (lessonStatDeferred.await().exists()) {
                                        lessonStatDeferred.await()
                                            .child("isLearned").value.toString()
                                    } else {
                                        "false" // Default value if no stats exist
                                    }
                                }
                            }
                        }.awaitAll() // Await all lessons and return them
                    }

                    // Await both topic stats and lessons data results
                    val topicStatSnapshotResult = topicStatDeferred.await();
                    val lessonsDataResult = lessonsDataDeferred.await();

                    return@async TopicStatEntity().apply {
                        topicEntity = TopicEntity(
                            id = topic.child("id").value.toString(),
                            title = topic.child("title").value.toString(),
                            description = topic.child("description").value.toString(),
                            lessons = lessonsDataResult
                        )
                        totalFinished =
                            if (topicStatSnapshotResult.exists()) topicStatSnapshotResult.child("totalFinished").value.toString() else "0"
                    }
                }
            }

            send(Response.Success(topicDeferreds.awaitAll()))
        } catch (e: Exception) {
            send(Response.Error(e.toString()))
        }
    }

    override suspend fun updateTopic(topic: TopicStatEntity): Flow<Response<Boolean>> {
        return channelFlow {
            try {
                val user = UserProvider.getUser()
                val topicStatHash = mapOf(
                    "totalFinished" to topic.totalFinished
                )
                val topicRef = realtimeDataBase.getReference("topicStats").child(user.uid)
                    .child(topic.topicEntity.id)

                topicRef.setValue(topicStatHash).await()

                val lessonRef = realtimeDataBase.getReference("lessonStats")
                topic.topicEntity.lessons.forEach { lessonStatEntity ->
                    val lessonStatHash = mapOf(
                        "stars" to lessonStatEntity.stars,
                        "isLearned" to lessonStatEntity.isLearned
                    )
                    lessonRef.child(user.uid).child(lessonStatEntity.lessonEntity.id)
                        .setValue(lessonStatHash).await()

                    val wordRef = realtimeDataBase.getReference("wordStats")
                    lessonStatEntity.lessonEntity.words.forEach { wordStatEntity ->
                        val wordStatHash = mapOf(
                            "isRemembered" to wordStatEntity.isRemembered,
                            "lastTimeLeaned" to wordStatEntity.lastTimeLeaned,
                            "score" to wordStatEntity.score
                        )
                        wordRef.child(user.uid).child(wordStatEntity.wordLessonEntity.id)
                            .setValue(wordStatHash).await()
                    }
                }
                send(Response.Success(true))
            } catch (e: Exception) {
                send(Response.Error(e.toString()))
            }
        }
    }

    override suspend fun getWordStatList(): Flow<Response<List<WordStatEntity>>> = callbackFlow {
        val user = UserProvider.getUser()
        val wordStatsRef = realtimeDataBase.getReference("wordStats").child(user.uid)

        val wordStatsListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Use async to handle asynchronous Firebase calls
                        val wordStats = snapshot.children.mapNotNull { wordStat ->
                            val wordId = wordStat.key ?: return@mapNotNull null
                            async {
                                val wordRef = realtimeDataBase.getReference("words").child(wordId)
                                val wordSnapshot = wordRef.get().await()

                                if (wordSnapshot.exists()) {
                                    val questionSnapshot = wordSnapshot.child("question")
                                    val optionsData =
                                        questionSnapshot.child("options").children.map { it.value.toString() }

                                    WordStatEntity().apply {
                                        wordLessonEntity = WordLessonEntity(
                                            id = wordSnapshot.child("id").value.toString(),
                                            hanzi = wordSnapshot.child("hanzi").value.toString(),
                                            translation = wordSnapshot.child("translation").value.toString(),
                                            pinyinTone = wordSnapshot.child("pinyinTone").value.toString(),
                                            example = wordSnapshot.child("example").value.toString(),
                                            exampleTranslation = wordSnapshot.child("exampleTranslation").value.toString(),
                                            question = QuestionEntity().apply {
                                                id = 0.toString()
                                                question =
                                                    questionSnapshot.child("question").value.toString()
                                                answer =
                                                    questionSnapshot.child("answer").value.toString()
                                                options = optionsData
                                            },
                                            image = wordSnapshot.child("image").value.toString(),
                                            audio = wordSnapshot.child("audio").value.toString()
                                        )
                                        isRemembered =
                                            wordStat.child("isRemembered").value.toString()
                                        lastTimeLeaned =
                                            wordStat.child("lastTimeLeaned").value.toString()
                                        score = wordStat.child("score").value.toString()
                                    }
                                } else null
                            }
                        }

                        // Wait for all async tasks to complete
                        val wordStatList = wordStats.mapNotNull { it.await() }
                            .filter { it.lastTimeLeaned != "0" }

                        trySend(Response.Success(wordStatList)).isSuccess
                    } catch (e: Exception) {
                        trySend(Response.Error(e.message ?: "Unknown error")).isSuccess
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Response.Error(error.message)).isSuccess
            }
        }

        wordStatsRef.addValueEventListener(wordStatsListener)

        awaitClose {
            wordStatsRef.removeEventListener(wordStatsListener)
        }
    }


    override suspend fun getPushWordStat(): Flow<Response<WordStatEntity>> {
        return flow {
            try {
                val user = UserProvider.getUser()
                val wordStatsSnapshot =
                    realtimeDataBase.getReference("wordStats").child(user.uid).get().await()
                val wordStats = wordStatsSnapshot.children.map { wordStat ->
                    WordStatEntity().apply {
                        val wordId = wordStat.key!!
                        val wordRef = realtimeDataBase.getReference("words").child(wordId)
                        val wordSnapshot = wordRef.get().await()

                        if (wordSnapshot.exists()) {
                            val questionSnapshot = wordSnapshot.child("question")
                            val optionsData =
                                questionSnapshot.child("options").children.map { it.value.toString() }

                            wordLessonEntity = WordLessonEntity(
                                id = wordSnapshot.child("id").value.toString(),
                                hanzi = wordSnapshot.child("hanzi").value.toString(),
                                translation = wordSnapshot.child("translation").value.toString(),
                                pinyinTone = wordSnapshot.child("pinyinTone").value.toString(),
                                example = wordSnapshot.child("example").value.toString(),
                                exampleTranslation = wordSnapshot.child("exampleTranslation").value.toString(),
                                question = QuestionEntity().apply {
                                    id = 0.toString()
                                    question =
                                        questionSnapshot.child("question").value.toString()
                                    answer = questionSnapshot.child("answer").value.toString()
                                    options = optionsData
                                },
                                image = wordSnapshot.child("image").value.toString(),
                                audio = wordSnapshot.child("audio").value.toString()
                            )
                            isRemembered = wordStat.child("isRemembered").value.toString()
                            lastTimeLeaned = wordStat.child("lastTimeLeaned").value.toString()
                            score = wordStat.child("score").value.toString()
                        }
                    }
                }
//                lay tu co diem thap nhat neu cung diem thi lay tu co lastTimeLeaned la thap nhat neu cung lastTimeLeaned thi lay tu dau tien co isRemembered la false
                val pushWordStat = wordStats.minByOrNull { it.score.toInt() }
                    ?.let { minScoreWordStat ->
                        wordStats.filter { it.score == minScoreWordStat.score }
                            .minByOrNull { it.lastTimeLeaned.toLong() }
                            ?.let { minLastTimeWordStat ->
                                wordStats.filter { it.lastTimeLeaned == minLastTimeWordStat.lastTimeLeaned }
                                    .find { it.isRemembered == "false" }
                                    ?: minLastTimeWordStat
                            }
                    }
                if (pushWordStat != null) {
                    Timber.tag("trndwcs")
                        .e("pushWordStat: ${pushWordStat.wordLessonEntity} ${pushWordStat.score} ${pushWordStat.isRemembered}")
                    emit(Response.Success(pushWordStat))
                } else {
                    emit(Response.Error("No word to push"))
                }
            } catch (e: Exception) {
                emit(Response.Error(e.toString()))
            }
        }
    }

    override suspend fun updateWordStat(
        wordId: String,
        score: Int,
        isRemembered: Boolean
    ): Flow<Response<Boolean>> {
        return flow {
            try {
                val user = UserProvider.getUser()
                val wordStatHash = mapOf(
                    "isRemembered" to isRemembered.toString(),
                    "lastTimeLeaned" to System.currentTimeMillis().toString(),
                    "score" to score.toString()
                )
                val wordRef =
                    realtimeDataBase.getReference("wordStats").child(user.uid).child(wordId)
                wordRef.setValue(wordStatHash).await()
                emit(Response.Success(true))
            } catch (e: Exception) {
                emit(Response.Error(e.toString()))
            }
        }
    }

    override suspend fun getTestList(): Flow<Response<List<TestEntity>>> {
        return flow {
            Timber.tag("trndwcs").d("getTestList invoke")
            try {
                val user = UserProvider.getUser()
                val tests = mutableListOf<TestEntity>()

                // Collect results from getWordStatList directly in this flow
                getWordStatList().collect { wordStatListResponse ->
                    when (wordStatListResponse) {
                        is Response.Success -> {
                            val words = wordStatListResponse.data
                            Timber.tag("trndwcs").d("Fetched words: $words") // Log fetched words

                            // 1. Nhóm từ chưa thuộc
                            val notRememberedWords =
                                words.filter { it.isRemembered != "true" }.take(5)
                            if (notRememberedWords.isNotEmpty()) {
                                tests.add(
                                    createTestFromWords(
                                        notRememberedWords,
                                        "Nhóm từ chưa thuộc",
                                        "Hãy ôn tập những từ chưa thuộc"
                                    )
                                )
                                Timber.tag("trndwcs").d("Added test for 'Nhóm từ chưa thuộc'")
                            }

                            // 2. Nhóm từ đã thuộc nhưng lâu chưa ôn tập
                            val longTimeNotPracticed = words.filter { it.isRemembered == "true" }
                                .sortedBy { it.lastTimeLeaned.toLong() }
                                .take(5)
                            if (longTimeNotPracticed.isNotEmpty()) {
                                tests.add(
                                    createTestFromWords(
                                        longTimeNotPracticed,
                                        "Nhóm từ lâu chưa ôn tập",
                                        "Hãy ôn tập những từ đã chưa ôn tập"
                                    )
                                )
                                Timber.tag("trndwcs")
                                    .d("Added test for 'Nhóm từ đã thuộc nhưng lâu chưa ôn tập'")
                            }

                            // 3. Nhóm từ có điểm thấp
                            val lowScoreWords = words.sortedBy { it.score.toLong() }.take(5)
                            if (lowScoreWords.isNotEmpty()) {
                                tests.add(
                                    createTestFromWords(
                                        lowScoreWords,
                                        "Nhóm từ có điểm thấp",
                                        "Hãy ôn tập những từ có điểm thấp"
                                    )
                                )
                                Timber.tag("trndwcs").d("Added test for 'Nhóm từ có điểm thấp'")
                            }

                            // 4. Nhóm từ có điểm cao và lâu chưa ôn tập
                            val highScoreLongTimeNotPracticed =
                                words.filter { it.score.toLong() >= 70 }
                                    .sortedBy { it.lastTimeLeaned.toLong() }
                                    .take(5)
                            if (highScoreLongTimeNotPracticed.isNotEmpty()) {
                                tests.add(
                                    createTestFromWords(
                                        highScoreLongTimeNotPracticed,
                                        "Nhóm từ có điểm cao nhưng lâu chưa ôn tập",
                                        "Hãy ôn tập những từ có điểm cao nhưng lâu chưa ôn tập"
                                    )
                                )
                                Timber.tag("trndwcs")
                                    .d("Added test for 'Nhóm từ có điểm cao nhưng lâu chưa ôn tập'")
                            }

                            // 5. Nhóm từ mới học gần đây
                            val recentlyLearnedWords =
                                words.filter { it.lastTimeLeaned.toLong() > System.currentTimeMillis() - 86400000 }
                                    .take(5)
                            if (recentlyLearnedWords.isNotEmpty()) {
                                tests.add(
                                    createTestFromWords(
                                        recentlyLearnedWords,
                                        "Nhóm từ mới học gần đây",
                                        "Hãy ôn tập những từ mới học gần đây"
                                    )
                                )
                                Timber.tag("trndwcs").d("Added test for 'Nhóm từ mới học gần đây'")
                            }

                            // 6. Nhóm từ ngẫu nhiên
                            if (words.isNotEmpty()) {
                                val randomWords = words.shuffled().take(5)
                                tests.add(
                                    createTestFromWords(
                                        randomWords,
                                        "Nhóm từ ngẫu nhiên",
                                        "Hãy ôn tập những từ ngẫu nhiên"
                                    )
                                )
                                Timber.tag("trndwcs").d("Added test for 'Nhóm từ ngẫu nhiên'")
                            }

                            // 7. Nhóm từ ngẫu nhiên khác
                            if (words.isNotEmpty()) {
                                val randomWords2 = words.shuffled().take(5)
                                tests.add(
                                    createTestFromWords(
                                        randomWords2,
                                        "Nhóm từ ngẫu nhiên khác",
                                        "Hãy ôn tập những từ ngẫu nhiên khác"
                                    )
                                )
                                Timber.tag("trndwcs").d("Added test for 'Nhóm từ ngẫu nhiên khác'")
                            }
                            emit(Response.Success(tests))
                            Timber.tag("trndwcs").d("Emitting tests: $tests")
                        }

                        is Response.Error -> {
                            Timber.tag("trndwcs")
                                .e("getWordStatList error: ${wordStatListResponse.exception}")
                        }
                    }
                }
            } catch (e: Exception) {
                emit(Response.Error(e.toString()))
            }
        }
    }

    private fun createTestFromWords(words: List<WordStatEntity>, s: String, d: String): TestEntity {
        return TestEntity(
            id = 0,
            name = s,
            description = d,
            image = "",
            words = words
        )
    }
}