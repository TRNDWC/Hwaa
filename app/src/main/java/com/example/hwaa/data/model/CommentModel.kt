package com.example.hwaa.data.model

import java.io.Serializable

class CommentModel(
    var id: String,
    var content: String,
    var author: UserModel?,
    var date: String,
    var upVotes: Int,
    var downVotes: Int
) : Serializable {
    constructor() : this("", "", null, "", 0, 0)
}