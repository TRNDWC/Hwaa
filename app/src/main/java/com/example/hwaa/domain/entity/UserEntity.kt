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
    var password: String,
    var profileImage: String,
    var streak: Int,
    var level: UserLevel,
    var stars: Int
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "https://media.istockphoto.com/id/1495088043/vector/user-profile-icon-avatar-or-person-icon-profile-picture-portrait-symbol-default-portrait.jpg?s=612x612&w=0&k=20&c=dhV2p1JwmloBTOaGAtaA3AW1KSnjsdMt7-U_3EZElZ0=",
        0,
        UserLevel.NEWBIE,
        0
    )

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

    fun translateFromUserModel(user: UserModel) : UserEntity {
        return UserEntity(
            user.uid,
            user.displayName,
            user.email,
            "",
            user.profileImage,
            user.streak,
            user.level,
            user.stars
        )
    }

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

    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "displayName" to displayName,
            "email" to email,
            "profileImage" to profileImage,
            "streak" to streak,
            "level" to level.name,
            "stars" to stars
        )
    }

    override fun toString(): String {
        return "UserEntity(uid='$uid', displayName='$displayName', email='$email', password='$password', profileImage='$profileImage', level=$level, stars=$stars)"
    }
}