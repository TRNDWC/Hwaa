package com.example.hwaa.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.hwaa.data.model.BlogModel
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.BlogEntity
import com.example.hwaa.domain.usecase.blog.GetBlogDetailUseCase
import com.example.hwaa.domain.usecase.blog.GetBlogUseCase
import com.example.hwaa.domain.usecase.blog.PostBlogUseCase
import com.example.hwaa.domain.usecase.blog.UpdateBlogUseCase
import com.example.hwaa.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ForumViewModel @Inject constructor(
    private val postBlogUseCase: PostBlogUseCase,
    private val getBlogUseCase: GetBlogUseCase,
    private val updateBlogUseCase: UpdateBlogUseCase,
    private val getBlogDetailUseCase: GetBlogDetailUseCase
) : BaseViewModel() {

    var selectedBlog: BlogModel = BlogModel()

    private var _postBlogFlow = MutableSharedFlow<Response<Boolean>>()
    val postBlogFlow = _postBlogFlow

    private var _blogList = MutableSharedFlow<Response<List<BlogModel>>>()
    val blogList = _blogList

    private var _updateBlogFlow = MutableSharedFlow<Response<Boolean>>()
    val updateBlogFlow = _updateBlogFlow

    private var _blogDetail = MutableSharedFlow<Response<BlogModel>>()
    val blogDetail = _blogDetail


    fun postBlog(blog: BlogModel) {
        viewModelScope.launch {
            postBlogUseCase.invoke(blog).collect { response ->
                _postBlogFlow.emit(response)
            }
        }
    }

    fun getBlogList(offset: Int, limit: Int) {
        viewModelScope.launch {
            getBlogUseCase.invoke(offset, limit).collect { response ->
                _blogList.emit(response)
            }
        }
    }

    fun updateBlog(blog: BlogModel) {
        viewModelScope.launch {
            updateBlogUseCase.invoke(blog).collect { response ->
                _updateBlogFlow.emit(response)
            }
        }
    }

    fun getBlogDetail(blog: BlogModel) {
        viewModelScope.launch {
            getBlogDetailUseCase.invoke(blog).collect { response ->
                _blogDetail.emit(response)
            }
        }
    }

}