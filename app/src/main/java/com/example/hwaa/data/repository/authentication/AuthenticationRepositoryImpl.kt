package com.example.hwaa.data.repository.authentication

import androidx.core.net.toUri
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.UserEntity
import com.example.hwaa.domain.entity.UserLevel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ActivityScoped
class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val realtimeDatabase: FirebaseDatabase,
    private val storage: FirebaseStorage
) : AuthenticationRepository {

    override suspend fun userUid(): String = auth.currentUser?.uid ?: ""

    override suspend fun isLoggedIn(): Boolean = auth.currentUser != null

    override suspend fun logout() = auth.signOut()

    override suspend fun login(user: UserEntity): Flow<Response<AuthResult>> = flow {
        try {
            val data = auth.signInWithEmailAndPassword(user.email, user.password).await()
            val userData =
                realtimeDatabase.reference.child("users").child(data.user?.uid ?: "").get().await()

            try {
                if (userData.exists()) {
                    val userMap = userData.value as HashMap<*, *>
                    user.uid = data.user?.uid ?: ""
                    user.displayName = userMap["displayName"] as String
                    user.profileImage = userMap["profileImage"] as String
                    user.streak = userMap["streak"] as Int
                    user.level = UserLevel.valueOf(userMap["level"] as String)
                    user.stars = userMap["stars"] as Int
                }
            } catch (e: Exception) {
                emit(Response.Error(e.localizedMessage ?: "Oops, something went wrong."))
            }

            emit(Response.Success(data))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Oops, something went wrong."))
        }
    }

    override suspend fun register(user: UserEntity): Flow<Response<AuthResult>> =
        flow {
            try {
                val data = auth.createUserWithEmailAndPassword(user.email, user.password).await()
                try {
                    val newUserMap = user.toHashMap()
                    realtimeDatabase.reference.child("users").child(data.user?.uid ?: "")
                        .setValue(newUserMap).await()

                    user.uid = data.user?.uid ?: ""
                } catch (e: Exception) {
                    emit(Response.Error(e.localizedMessage ?: "Oops, something went wrong."))
                }
                emit(Response.Success(data))
            } catch (e: Exception) {
                emit(Response.Error(e.localizedMessage ?: "Oops, something went wrong."))
            }
        }

    override suspend fun resetPassword(email: String): Flow<Response<Void?>> = flow {
        try {
            val data = auth.sendPasswordResetEmail(email).await()
            emit(Response.Success(data))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Oops, something went wrong."))
        }
    }

    override suspend fun updateProfile(user: UserEntity): Flow<Response<Void?>> = flow {
        try {
//            check if hwaa have child user.uid and profileImage
            if (user.profileImage.isNotEmpty()) {
                val storageRef = storage.reference.child("hwaa").child("profileImages/${user.uid}")
                val uri = user.profileImage.toUri()
                val uploadTask = storageRef.putFile(uri).await()
                user.profileImage = uploadTask.storage.downloadUrl.await().toString()
            }

            val userMap = user.toHashMap()
            realtimeDatabase.reference.child("users").child(user.uid).updateChildren(userMap)
                .await()

            emit(Response.Success(null))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Oops, something went wrong."))
        }
    }
}