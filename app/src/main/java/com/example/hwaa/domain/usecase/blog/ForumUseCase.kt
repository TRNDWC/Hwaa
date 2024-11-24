package com.example.hwaa.domain.usecase.blog

import com.example.hwaa.data.model.BlogModel
import com.example.hwaa.data.repository.blog.ForumRepository
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.BlogEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class GetBlogUseCase @Inject constructor(
    private val forumRepository: ForumRepository
) {
    suspend operator fun invoke(offset: Int, limit: Int): Flow<Response<List<BlogModel>>> {
        return forumRepository.getBlog(limit, offset).map { response ->
            when (response) {
                is Response.Success -> {
                    val blogList = response.data.map { blogEntity ->
                        BlogEntity.translateToBlogModel(blogEntity)
                    }
                    Response.Success(blogList)
                }

                is Response.Error -> {
                    Response.Error(response.exception)
                }
            }
        }
    }
}

class UpdateBlogUseCase @Inject constructor(
    private val forumRepository: ForumRepository
) {
    suspend operator fun invoke(blogEntity: BlogModel) =
        forumRepository.updateBlog(BlogEntity.translateFromBlogModel(blogEntity))
}

class PostBlogUseCase @Inject constructor(
    private val forumRepository: ForumRepository
) {
    suspend operator fun invoke(blogEntity: BlogModel) =
        forumRepository.postBlog(BlogEntity.translateFromBlogModel(blogEntity))
}

class GetBlogDetailUseCase @Inject constructor(
    private val forumRepository: ForumRepository
) {
    suspend operator fun invoke(blogEntity: BlogModel): Flow<Response<BlogModel>> {
        return forumRepository.getBlogDetail(BlogEntity.translateFromBlogModel(blogEntity)).map { response ->
            when (response) {
                is Response.Success -> {
                    Response.Success(BlogEntity.translateToBlogModel(response.data))
                }

                is Response.Error -> {
                    Response.Error(response.exception)
                }
            }
        }
    }
}