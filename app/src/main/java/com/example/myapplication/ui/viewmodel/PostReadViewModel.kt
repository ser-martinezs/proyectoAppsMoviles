package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.repository.PostRepository
import com.example.myapplication.data.service.RetroFitInstance
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



data class idkWhatToReadErrors(
    val pageError : String = CodeConsts.NOTHING,
    val postError : String = CodeConsts.NOTHING
)


data class IdkWhatToNameState(
    val post : Post? = null,
    val page : List<Post> = listOf(),
    val errors : idkWhatToReadErrors= idkWhatToReadErrors(),
    val pageNumber: Int=0,
    val pageCount : Int= 0
)


class PostReadViewModel(val repository: PostRepository = PostRepository()) : ViewModel() {
    private val _state = MutableStateFlow<IdkWhatToNameState>(IdkWhatToNameState())
    val state : StateFlow<IdkWhatToNameState> = _state

    init {
        fetchPage(0)
    }

    fun fetchPost(postID: Long){
        viewModelScope.launch {
            _state.update { it.copy(errors = idkWhatToReadErrors(postError = CodeConsts.LOADING)) }
            var post : Post? = null
            var errorMsg = ""

            try {
                post = repository.getByPostID(postID)

            }catch (error: Exception){
                error.printStackTrace()
                errorMsg = error.message?:CodeConsts.UNDEFINED_ERROR
            }

            _state.update { it.copy(errors = idkWhatToReadErrors(postError = errorMsg), post = post) }
       }
    }
    fun fetchPage(pageNumber : Int = 0){
        fetchPageCount()
        viewModelScope.launch {
            _state.update { it.copy(errors = idkWhatToReadErrors(pageError = CodeConsts.LOADING)) }

            var page : List<Post> = listOf()
            var errorMsg = ""

            try {
                page = repository.getPage(pageNumber)
            }catch (error: Exception){
                error.printStackTrace()
                errorMsg = error.message?:CodeConsts.UNDEFINED_ERROR
            }

            _state.update { it.copy(errors = idkWhatToReadErrors(pageError = errorMsg), page = page, pageNumber = pageNumber) }


        }
    }

    fun fetchPageCount(){
        viewModelScope.launch {
            var pageCount = 0
            try {
                pageCount = repository.getPageCount()
            }catch (error: Exception){
                error.printStackTrace()
            }
            _state.update { it.copy(pageCount=pageCount) }
        }
    }



}