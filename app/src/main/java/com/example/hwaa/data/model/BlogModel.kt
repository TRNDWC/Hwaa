package com.example.hwaa.data.model

import java.io.Serializable


class BlogModel(
    var id: String,
    var content: String,
    var image: String,
    var author: UserModel?,
    var date: String,
    var comments: ArrayList<CommentModel>,
    var likes: MutableSet<String>,
) : Serializable {
    constructor() : this("", "", "", null, "", ArrayList(), mutableSetOf())
}