package com.example.hwaa.domain.entity

import com.example.hwaa.data.model.CommentModel

class CommentEntity(
    var id: String,
    var content: String,
    var author: UserEntity?,
    var date: String,
    var upVotes: String,
    var downVotes: String
) {
    constructor() : this("", "", null, "", "0", "0")

    companion object {
        fun translateFromModel(model: CommentModel): CommentEntity {
            return CommentEntity(
                id = model.id,
                content = model.content,
                author = model.author?.let { UserEntity.translateFromUserModel(it) },
                date = model.date,
                upVotes = model.upVotes.toString(),
                downVotes = model.downVotes.toString()
            )
        }

        fun translateToModel(entity: CommentEntity): CommentModel {
            return CommentModel(
                id = entity.id,
                content = entity.content,
                author = entity.author?.let { UserEntity.translateToUserModel(it) },
                date = entity.date,
                upVotes = entity.upVotes.toInt(),
                downVotes = entity.downVotes.toInt()
            )
        }

        fun toHashMap(it: CommentEntity): HashMap<String, Any> {
            return hashMapOf(
                "id" to it.id,
                "content" to it.content,
                "author" to (it.author?.uid ?: ""),
                "date" to it.date,
                "upVotes" to it.upVotes,
                "downVotes" to it.downVotes
            )
        }
    }
}