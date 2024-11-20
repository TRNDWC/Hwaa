package com.example.hwaa.data.repository.blog

import androidx.core.net.toUri
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.BlogEntity
import com.example.hwaa.domain.entity.CommentEntity
import com.example.hwaa.domain.entity.UserEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class ForumRepositoryImpl @Inject constructor(
    private val realtimeDataBase: FirebaseDatabase,
    private val storage: FirebaseStorage
) : ForumRepository {

    override suspend fun getBlog(limit: Int, offset: Int): Flow<Response<List<BlogEntity>>> =
        callbackFlow {
            val blogListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        val blogFlow = snapshot.children.map { dataSnapshot ->
                            async {
                                val blogMap = dataSnapshot.value as? Map<*, *> ?: return@async null
                                val blogId = dataSnapshot.key ?: return@async null

                                combine(
                                    getAuthor(blogMap["author"] as String?),
                                    getComments(blogId)
                                ) { authorFlow, commentsFlow ->
                                    when {
                                        authorFlow is Response.Success && commentsFlow is Response.Success -> {
                                            Timber.tag("trndwcs").d("Author: ${authorFlow.data}")
                                            Timber.tag("trndwcs")
                                                .d("Comments: ${commentsFlow.data}")
                                            BlogEntity().apply {
                                                id = blogId
                                                content = blogMap["content"] as String
                                                image = blogMap["image"] as String
                                                date = blogMap["date"] as String
                                                author = authorFlow.data
                                                comments = commentsFlow.data
                                                likes = blogMap["likes"] as String
                                            }
                                        }

                                        authorFlow is Response.Error -> {
                                            Timber.tag("trndwcs").e(authorFlow.exception)
                                            throw Exception(authorFlow.exception)
                                        }

                                        commentsFlow is Response.Error -> {
                                            Timber.tag("trndwcs").e(commentsFlow.exception)
                                            throw Exception(commentsFlow.exception)
                                        }

                                        else -> null
                                    }
                                }.firstOrNull()
                            }
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            val blogList = blogFlow.awaitAll().filterNotNull()
                            trySend(Response.Success(blogList))
                        }
                    } catch (e: Exception) {
                        trySend(Response.Error(e.message ?: "Unknown error"))
                        close(e)
                    }
                }


                override fun onCancelled(error: DatabaseError) {
                    trySend(Response.Error(error.message))
                    close(error.toException())
                }
            }


            realtimeDataBase.getReference("blogs").addValueEventListener(blogListener)
            awaitClose {
                realtimeDataBase.getReference("blogs").removeEventListener(blogListener)
            }
        }


    private fun getAuthor(authorId: String?): Flow<Response<UserEntity>> =
        callbackFlow {
            if (authorId == null) {
                close()
                return@callbackFlow
            }
            val userRef = realtimeDataBase.reference.child("users").child(authorId)
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.value as HashMap<*, *>
                    val userEntity = UserEntity().apply {
                        uid = snapshot.key!!
                        email = user["email"] as String
                        displayName = user["displayName"] as String
                        level = user["level"] as String
                        profileImage = user["profileImage"] as String
                        streak = user["streak"] as String
                        stars = user["stars"] as String
                    }
                    trySend(Response.Success(userEntity)).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Response.Error(error.message)).isSuccess
                }
            }
            userRef.addValueEventListener(listener)
            awaitClose {
                userRef.removeEventListener(listener)
            }
        }

    private fun getComments(blogId: String): Flow<Response<List<CommentEntity>>> = callbackFlow {
        val commentRef = realtimeDataBase.reference.child("blogs").child(blogId).child("comments")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val commentList = mutableListOf<CommentEntity>()

                CoroutineScope(Dispatchers.IO).launch {
                    val authorDeferreds = mutableListOf<Deferred<Response<UserEntity>>>()

                    snapshot.children.forEach { dataSnapshot ->
                        Timber.tag("trndwcs").d("Comment: ${dataSnapshot.value}")
                        val comment = dataSnapshot.value as HashMap<*, *>

                        val deferredAuthor = async {
                            getAuthor(comment["author"] as String).first()
                        }
                        authorDeferreds.add(deferredAuthor)

                        commentList.add(
                            CommentEntity(
                                id = dataSnapshot.key ?: "",
                                content = comment["content"] as String,
                                author = null, // Placeholder for now
                                date = comment["date"] as String,
                                upVotes = comment["upVotes"] as String,
                                downVotes = comment["downVotes"] as String
                            )
                        )
                    }

                    authorDeferreds.forEachIndexed { index, deferred ->
                        when (val authorResponse = deferred.await()) {
                            is Response.Success -> {
                                commentList[index].author = authorResponse.data
                            }

                            is Response.Error -> {
                                trySend(Response.Error(authorResponse.exception)).isSuccess
                            }
                        }
                    }

                    Timber.tag("trndwcs").d("Comment List: ${commentList.size}")
                    trySend(Response.Success(commentList)).isSuccess
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Response.Error(error.message)).isSuccess
            }
        }

        commentRef.addValueEventListener(listener)
        awaitClose {
            commentRef.removeEventListener(listener)
        }
    }

    override suspend fun postBlog(blog: BlogEntity): Flow<Response<Boolean>> = flow {
        try {
            val blogRef = realtimeDataBase.reference.child("blogs").push()
            val newBlogId = blogRef.key
            blog.id = newBlogId ?: ""
            if (blog.image.isNotEmpty()) {
                val storageRef = storage.reference.child("hwaa").child("blogImages/${blog.id}")
                val uri = blog.image.toUri()
                storageRef.putFile(uri).await()
                blog.image = storageRef.downloadUrl.await().toString()
            }
            val blogHashMap = BlogEntity.toHashMap(blog)
            blogRef.setValue(blogHashMap).await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Oops, something went wrong."))
        }
    }

    override suspend fun updateBlog(blog: BlogEntity): Flow<Response<Boolean>> = flow {
        try {
            val blogRef = realtimeDataBase.reference.child("blogs").child(blog.id)

            val updatedComments = blog.comments.map { comment ->
                if (comment.id.isEmpty()) {
                    // Generate a new unique ID for the comment
                    val newCommentRef = blogRef.child("comments").push()
                    comment.id =
                        newCommentRef.key ?: "" // Assign the generated key to the comment ID
                }
                comment
            }

            val updatedBlog = blog.copy(comments = updatedComments)
            val blogHashMap = BlogEntity.toHashMap(updatedBlog)

            blogRef.updateChildren(blogHashMap).await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Oops, something went wrong."))
        }
    }

    override suspend fun getBlogDetail(blogEntity: BlogEntity): Flow<Response<BlogEntity>> =
        callbackFlow {
            val blogRef = realtimeDataBase.reference.child("blogs").child(blogEntity.id)

            val blogDetailListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (snapshot.exists()) {
                            combine(
                                getAuthor(snapshot.child("author").value as String),
                                getComments(blogEntity.id)
                            ) { authorFlow, commentsFlow ->
                                when {
                                    authorFlow is Response.Success && commentsFlow is Response.Success -> {
                                        BlogEntity().apply {
                                            id = snapshot.key!!
                                            content = snapshot.child("content").value as String
                                            image = snapshot.child("image").value as String
                                            date = snapshot.child("date").value as String
                                            author = authorFlow.data
                                            comments = commentsFlow.data
                                            likes = snapshot.child("likes").value as String
                                        }
                                    }

                                    authorFlow is Response.Error -> {
                                        throw Exception(authorFlow.exception)
                                    }

                                    commentsFlow is Response.Error -> {
                                        throw Exception(commentsFlow.exception)
                                    }

                                    else -> null
                                }
                            }.firstOrNull()?.let {
                                trySend(Response.Success(it)).isSuccess
                            }
                        } else {
                            trySend(Response.Error("No blog found with ID: ${blogEntity.id}")).isSuccess
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Response.Error(error.message)).isSuccess
                }
            }

            blogRef.addValueEventListener(blogDetailListener)
            awaitClose {
                blogRef.removeEventListener(blogDetailListener)
            }
        }
}