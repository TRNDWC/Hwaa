package com.example.hwaa.domain.entity

import com.example.hwaa.data.model.UserModel
import com.google.firebase.database.snapshot.StringNode

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
    var streak: String,
    var level: String,
    var stars: String
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "https://media.istockphoto.com/id/1495088043/vector/user-profile-icon-avatar-or-person-icon-profile-picture-portrait-symbol-default-portrait.jpg?s=612x612&w=0&k=20&c=dhV2p1JwmloBTOaGAtaA3AW1KSnjsdMt7-U_3EZElZ0=",
        "0",
        UserLevel.NEWBIE.toString(),
        "0"
    )

    override fun toString(): String {
        return "UserEntity(uid='$uid', displayName='$displayName', email='$email', password='$password', profileImage='$profileImage', streak='$streak', level='$level', stars='$stars')"
    }

    companion object {

        fun translateFromUserModel(user: UserModel): UserEntity {
            return UserEntity(
                user.uid,
                user.displayName,
                user.email,
                "",
                user.profileImage,
                user.streak.toString(),
                user.level.toString(),
                user.stars.toString()
            )
        }

        fun translateToUserModel(entity: UserEntity): UserModel {
            return UserModel(
                entity.uid,
                entity.displayName,
                entity.email,
                entity.profileImage,
                entity.streak.toInt(),
                UserLevel.valueOf(entity.level),
                entity.stars.toInt()
            )
        }

        fun toHashMap(user: UserEntity): HashMap<String, Any> {
            return hashMapOf(
                "uid" to user.uid,
                "displayName" to user.displayName,
                "email" to user.email,
                "profileImage" to user.profileImage,
                "streak" to user.streak,
                "level" to user.level,
                "stars" to user.stars
            )
        }

        fun fromHashMap(map: HashMap<String, Any>): UserEntity {
            return UserEntity(
                map["uid"] as String,
                map["displayName"] as String,
                map["email"] as String,
                "",
                map["profileImage"] as String,
                map["streak"] as String,
                map["level"] as String,
                map["stars"] as String
            )
        }
    }
}