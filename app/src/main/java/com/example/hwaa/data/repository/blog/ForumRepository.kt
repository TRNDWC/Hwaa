package com.example.hwaa.data.repository.blog

import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.BlogEntity
import kotlinx.coroutines.flow.Flow

interface ForumRepository {
    suspend fun getBlog(limit: Int, offset: Int) : Flow<Response<List<BlogEntity>>>
    suspend fun postBlog(blog: BlogEntity) : Flow<Response<Boolean>>
    suspend fun updateBlog(blog: BlogEntity) : Flow<Response<Boolean>>
    suspend fun getBlogDetail(blogEntity: BlogEntity) : Flow<Response<BlogEntity>>
}