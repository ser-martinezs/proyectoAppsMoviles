package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.service.RetroFitInstance
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



data class idkWhatToReadErrors(
    val pageCode : Int = CodeConsts.NOTHING,
    val postCode : Int = CodeConsts.NOTHING
){}


data class IdkWhatToNameState(
    val post : Post? = null,
    val page : List<Post> = listOf(),
    val errors : idkWhatToReadErrors= idkWhatToReadErrors(),
    val pageNumber: Int=0,
    val postID : Long?= null,
    val pageCount : Int= 0
)


class PostReadViewModel : ViewModel() {
    private val _state = MutableStateFlow<IdkWhatToNameState>(IdkWhatToNameState())
    val state : StateFlow<IdkWhatToNameState> = _state

    fun fetchPost(postID: Long){

        viewModelScope.launch {
            _state.update{it.copy(
                errors= idkWhatToReadErrors(postCode = CodeConsts.LOADING, pageCode = it.errors.pageCode),
                postID=postID, pageNumber = it.pageNumber, page = it.page
            )}


            val safecall = async { runCatching { RetroFitInstance.postApi.getByPostID(postID) } }.await()

            if (!safecall.isSuccess) {
                _state.update { it.copy(
                    errors = idkWhatToReadErrors(postCode = CodeConsts.CONNECTION_ERROR,pageCode = it.errors.pageCode),
                    pageNumber = it.pageNumber, page = it.page
                )}
                return@launch
            }
            val response = safecall.getOrNull()
            _state.update { it.copy(
                post=response!!.body(),
                errors = idkWhatToReadErrors(postCode = response.code(), pageCode = it.errors.pageCode),
                pageNumber = it.pageNumber, page = it.page
            )}

       }
    }
    fun fetchPage(pageNumber : Int = 0){
        fetchPageCount()

        viewModelScope.launch {

            _state.update { it.copy(errors = idkWhatToReadErrors(pageCode = CodeConsts.LOADING,postCode = it.errors.postCode), pageNumber = pageNumber, postID = it.postID, post = it.post) }


            val safecall = async { runCatching { RetroFitInstance.postApi.getByPage(pageNumber) } }.await()

            if (!safecall.isSuccess) {
                _state.update { it.copy( page=(listOf()), errors = idkWhatToReadErrors(pageCode = CodeConsts.CONNECTION_ERROR, postCode = it.errors.postCode), pageNumber = 0, postID = it.postID, post = it.post ) }
                return@launch
            }

            val response = safecall.getOrNull()
            _state.update { it.copy( page=(response!!.body() ?: listOf()), errors = idkWhatToReadErrors(pageCode = response.code(), postCode = it.errors.postCode), pageNumber = pageNumber, postID = it.postID, post = it.post ) }

        }
    }
    fun fetchPageCount(){
        viewModelScope.launch {
            val safecall = async { runCatching { RetroFitInstance.postApi.getOverallPages() } }.await()

            if (!safecall.isSuccess) {
                _state.update { it.copy( pageCount = 0) }
                return@launch
            }

            val response = safecall.getOrNull()
            Log.println(Log.INFO,"idk",response.toString())
            _state.update { it.copy(pageCount = response?.body()?:0) }
        }
    }

    fun setPage(pageNumber: Int){
        _state.update { it.copy(pageNumber = pageNumber) }
    }


}