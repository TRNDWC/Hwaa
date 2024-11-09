package com.example.hwaa.domain.entity

import com.example.hwaa.data.authentication.model.UserModel

enum class UserLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;

    fun nextLevel(): UserLevel {
        return when (this) {
            BEGINNER -> INTERMEDIATE
            INTERMEDIATE -> ADVANCED
            ADVANCED -> ADVANCED
        }
    }

    fun previousLevel(): UserLevel {
        return when (this) {
            BEGINNER -> BEGINNER
            INTERMEDIATE -> BEGINNER
            ADVANCED -> INTERMEDIATE
        }
    }

}

class UserEntity(
    var uid: String,
    var displayName: String,
    var email: String,
    val password: String,
    var profileImage: String,
    var streak: Int,
    var level: UserLevel,
    var stars: Int
) {

    constructor(displayName: String, email: String, password: String) : this(
        "",
        displayName,
        email,
        password,
        "",
        0,
        UserLevel.BEGINNER,
        0
    )

    constructor(email: String, password: String) : this(
        "",
        "",
        email,
        password,
        "",
        0,
        UserLevel.BEGINNER,
        0
    )

    fun translateToUserModel(): UserModel {
        return UserModel(
            uid,
            displayName,
            email,
            profileImage,
            streak,
            level,
            stars
        )
    }

    override fun toString(): String {
        return "UserEntity(uid='$uid', displayName='$displayName', email='$email', password='$password', profileImage='$profileImage', level=$level, stars=$stars)"
    }
}