package com.example.hwaa.domain.authentication

import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.UserEntity
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun login(user: UserEntity): Flow<Response<AuthResult>>
    suspend fun register(user: UserEntity): Flow<Response<AuthResult>>
    suspend fun resetPassword(email: String): Flow<Response<Void?>>
    suspend fun logout()
    suspend fun userUid(): String
    suspend fun isLoggedIn(): Boolean
}