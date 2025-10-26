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
    val postID : Long?= null
    )


class PostReadViewModel : ViewModel() {
    private val _state = MutableStateFlow<IdkWhatToNameState>(IdkWhatToNameState())
    val state : StateFlow<IdkWhatToNameState> = _state

    fun fetchPost(postID: Long){
        // avoid reload spam
        //if (_state.value.errors.pageCode == CodeConsts.LOADING && _state.value.postID == postID) return

        viewModelScope.launch {
            _state.update{it.copy(errors= idkWhatToReadErrors(postCode = CodeConsts.LOADING),postID=postID)}

            val safecall = async { runCatching { RetroFitInstance.postApi.getByPostID(postID) } }.await()

            if (!safecall.isSuccess) {
                _state.update { it.copy( post = null, errors = idkWhatToReadErrors(postCode = CodeConsts.CONNECTION_ERROR),postID=null ) }
                return@launch
            }
            val response = safecall.getOrNull()
            _state.update { it.copy( post=response!!.body(), errors = idkWhatToReadErrors(postCode = response.code(), pageCode = it.errors.pageCode),postID=postID ) }
       }
    }
    fun fetchPage(pageNumber : Int = 0){

        viewModelScope.launch {

            _state.update { it.copy(errors = idkWhatToReadErrors(pageCode = CodeConsts.LOADING), pageNumber = 0 ) }


            val safecall = async { runCatching { RetroFitInstance.postApi.getByPage(pageNumber) } }.await()

            if (!safecall.isSuccess) {
                _state.update { it.copy( page=(listOf()), errors = idkWhatToReadErrors(pageCode = CodeConsts.CONNECTION_ERROR, postCode = it.errors.postCode), pageNumber = 0 ) }
                return@launch
            }

            val response = safecall.getOrNull()
            _state.update { it.copy( page=(response!!.body() ?: listOf()), errors = idkWhatToReadErrors(pageCode = response.code(), postCode = it.errors.postCode), pageNumber = pageNumber ) }

        }
    }

    fun setupReload(){
        Log.println(Log.WARN,"setupReload","deprecated lol")
    }

    fun setupPostReload(){
        Log.println(Log.WARN,"setupPostReload","deprecated lol")
    }

}