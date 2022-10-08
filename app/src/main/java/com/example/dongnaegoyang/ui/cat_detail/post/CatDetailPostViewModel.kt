package com.example.dongnaegoyang.ui.cat_detail.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dongnaegoyang.data.remote.model.BaseResponse
import com.example.dongnaegoyang.data.remote.model.response.PostListResponse
import com.example.dongnaegoyang.data.remote.repository.PostRepository
import com.example.dongnaegoyang.ui.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatDetailPostViewModel @Inject constructor(
    private val repository: PostRepository
): ViewModel() {
    private val _catDetailPostResponse = MutableLiveData<Event<BaseResponse<PostListResponse>>>()
    val catDetailPostResponse: LiveData<Event<BaseResponse<PostListResponse>>> = _catDetailPostResponse
    private val _createPostResponse = MutableLiveData<BaseResponse<Int>>()
    val createPostResponse: LiveData<BaseResponse<Int>> = _createPostResponse

    fun getCatDetailPost(catIdx: Long, page: Int)  = viewModelScope.launch {
        kotlin.runCatching {
            repository.getCatPost(catIdx, page)
        }.onSuccess {
            _catDetailPostResponse.value = Event(it)
        }.onFailure {
            Log.d("mmm", " get cat detail post fail: ${it.message}")
        }
    }

    fun postCatPost(catIdx: Long, token: String, content: String)  = viewModelScope.launch {
        kotlin.runCatching {
            repository.postCatPost(catIdx, token, content)
        }.onSuccess {
            _createPostResponse.value = it
        }.onFailure {
            Log.d("mmm", " get cat detail create post fail: ${it.message}")
        }
    }
}