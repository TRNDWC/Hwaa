package com.example.hwaa.domain.entity

import com.example.hwaa.data.model.UserModel

enum class UserLevel {
    NEWBIE, ELEMENTARY, INTERMEDIATE, UPPER_INTERMEDIATE;

    fun nextLevel(): UserLevel {
        return when (this) {
            NEWBIE -> ELEMENTARY
            ELEMENTARY -> INTERMEDIATE
            INTERMEDIATE -> UPPER_INTERMEDIATE
            UPPER_INTERMEDIATE -> UPPER_INTERMEDIATE
        }
    }

    fun previousLevel(): UserLevel {
        return when (this) {
            NEWBIE -> NEWBIE
            ELEMENTARY -> NEWBIE
            INTERMEDIATE -> ELEMENTARY
            UPPER_INTERMEDIATE -> INTERMEDIATE
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
        UserLevel.NEWBIE,
        0
    )

    constructor(email: String, password: String) : this(
        "",
        "",
        email,
        password,
        "",
        0,
        UserLevel.NEWBIE,
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