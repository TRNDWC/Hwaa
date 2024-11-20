package com.example.hwaa.domain.entity

import com.example.hwaa.data.model.BlogModel
import com.example.hwaa.data.model.CommentModel
import java.io.Serializable

class BlogEntity(
    var id: String,
    var content: String,
    var image: String,
    var author: UserEntity?,
    var date: String,
    var comments: List<CommentEntity>,
    var likes: String,
) : Serializable {
    fun copy(comments: List<CommentEntity>): BlogEntity {
        return BlogEntity(id, content, image, author, date, comments, likes)
    }

    constructor() : this("", "", "", null, "", emptyList(), "")

    companion object {
        fun translateFromBlogModel(model: BlogModel): BlogEntity {
            val blogEntity = BlogEntity()
            blogEntity.apply {
                id = model.id
                content = model.content
                image = model.image
                author = model.author?.let { UserEntity.translateFromUserModel(it) }
                date = model.date
                comments = model.comments.map { CommentEntity.translateFromModel(it) }
                likes = model.likes.joinToString(",")
            }
            return blogEntity
        }

        fun translateToBlogModel(entity: BlogEntity): BlogModel {
            val blogModel = BlogModel()
            blogModel.apply {
                id = entity.id
                content = entity.content
                image = entity.image
                author = entity.author?.let { UserEntity.translateToUserModel(it) }
                date = entity.date
                comments =
                    entity.comments.map { CommentEntity.translateToModel(it) } as ArrayList<CommentModel>
                likes = entity.likes.split(",").filter { it.isNotEmpty() }.toMutableSet()
            }
            return blogModel
        }

        fun toHashMap(blog: BlogEntity): HashMap<String, Any> {
            return hashMapOf(
                "id" to blog.id,
                "content" to blog.content,
                "image" to blog.image,
                "author" to (blog.author?.uid ?: ""),
                "date" to blog.date,
                "likes" to blog.likes,
                "comments" to blog.comments.map { CommentEntity.toHashMap(it) }
            )
        }
    }
}