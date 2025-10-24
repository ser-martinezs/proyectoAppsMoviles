package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.service.RetroFitInstance
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



data class idkWhatToReadErrors(
    val pageError : String? = null,
    val postError : String? = null
)


data class IdkWhatToNameState(
    val post : Post? = null,
    val page : List<Post> = listOf(),
    val errors : idkWhatToReadErrors= idkWhatToReadErrors()
)


class PostReadViewModel : ViewModel() {
    private val _state = MutableStateFlow<IdkWhatToNameState>(IdkWhatToNameState())
    val state : StateFlow<IdkWhatToNameState> = _state

    fun fetchPost(postID: Long){

        viewModelScope.launch {
            try {
                val resp = async { RetroFitInstance.postApi.getByPostID(postID) }.await()
                Log.println(Log.INFO,"FetchPostRequest",resp.toString())
                _state.value.copy(post = resp.body())

            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }
    }
    fun fetchPage(pageNumber : Int = 0){

        viewModelScope.launch {
            try {
                val resp = async { RetroFitInstance.postApi.getByPage(pageNumber) }.await()

                Log.println(Log.INFO,"FetchPageRequest",resp.toString())
                val paige :List<Post>?= resp.body()


            //_state.value.copy(page = (paige ?: listOf()).toList() )


            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }
    }




}