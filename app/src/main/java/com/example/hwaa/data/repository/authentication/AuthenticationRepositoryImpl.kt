package com.example.hwaa.data.repository.authentication

import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.UserEntity
import com.example.hwaa.domain.entity.UserLevel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ActivityScoped
class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : AuthenticationRepository {

    override suspend fun userUid(): String = auth.currentUser?.uid ?: ""

    override suspend fun isLoggedIn(): Boolean = auth.currentUser != null

    override suspend fun logout() = auth.signOut()

    override suspend fun login(user: UserEntity): Flow<Response<AuthResult>> = flow {
        try {
            val data = auth.signInWithEmailAndPassword(user.email, user.password).await()
            val userData =
                fireStore.collection("users").document(data.user?.uid ?: "").get().await()

            try {
                user.uid = data.user?.uid ?: ""
                user.displayName = userData.getString("displayName") ?: ""
                user.profileImage = userData.getString("profileImage") ?: ""
                user.level = UserLevel.valueOf(userData.getString("level") ?: UserLevel.NEWBIE.name)
                user.stars = userData.getString("stars")?.toInt() ?: 0
                user.streak = userData.getString("streak")?.toInt() ?: 0
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
                    val userMap = user.toHashMap()
                    fireStore.collection("users").document(data.user?.uid ?: "").set(userMap)
                        .await()
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

}