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




data class HomeScreenState(
    val page : List<Post> = listOf(),
    val error : String="",
    val pageNumber: Int=0,
    val pageCount : Int= 0
)


class HomeScreenViewModel(val repository: PostRepository = PostRepository()) : ViewModel() {
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState())
    val state : StateFlow<HomeScreenState> = _state

    init {
        fetchPage(0)
    }


    fun fetchPage(pageNumber : Int = 0){
        fetchPageCount()

        _state.update { it.copy(error = CodeConsts.LOADING) }

        viewModelScope.launch {
            var page : List<Post> = listOf()
            var errorMsg = ""

            try {
                page = repository.getPage(pageNumber)
            }catch (error: Exception){
                error.printStackTrace()
                errorMsg = error.message?:CodeConsts.UNDEFINED_ERROR
            }

            _state.update { it.copy(error = errorMsg, page = page, pageNumber = pageNumber) }


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