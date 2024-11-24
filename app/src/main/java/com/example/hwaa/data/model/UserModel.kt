package com.example.hwaa.data.model

import com.example.hwaa.domain.entity.UserEntity
import com.example.hwaa.domain.entity.UserLevel
import java.io.Serializable

class UserModel(
    var uid: String,
    var displayName: String,
    var email: String,
    var profileImage: String,
    var streak: Int,
    var level: UserLevel,
    var stars: Int
) : Serializable {

    override fun toString(): String {
        return "UserModel(uid='$uid', displayName='$displayName', email='$email', profileImage='$profileImage', streak=$streak, level=$level, stars=$stars)"
    }
}