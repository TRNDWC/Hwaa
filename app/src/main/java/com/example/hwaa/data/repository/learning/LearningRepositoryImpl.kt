package com.example.hwaa.data.repository.learning

import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.LessonEntity
import com.example.hwaa.domain.entity.LessonStatEntity
import com.example.hwaa.domain.entity.QuestionEntity
import com.example.hwaa.domain.entity.TopicEntity
import com.example.hwaa.domain.entity.TopicStatEntity
import com.example.hwaa.domain.entity.WordLessonEntity
import com.example.hwaa.domain.entity.WordStatEntity
import com.example.hwaa.presentation.util.UserProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.channelFlow
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
}