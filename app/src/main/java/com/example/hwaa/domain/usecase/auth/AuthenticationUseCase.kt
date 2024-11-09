package com.example.hwaa.domain.usecase.auth

import com.example.hwaa.domain.authentication.AuthenticationRepository
import com.example.hwaa.domain.entity.UserEntity
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(email: String) = authenticationRepository.resetPassword(email)
}

class RegisterUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(user: UserEntity) = authenticationRepository.register(user)
}

class LogoutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke() = authenticationRepository.logout()
}

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(user: UserEntity) =
        authenticationRepository.login(user)
}

class IsLoggedInUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke() = flow {
        emit(authenticationRepository.isLoggedIn())
    }
}

class GetUserUidUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke() = flow { emit(authenticationRepository.userUid()) }
}