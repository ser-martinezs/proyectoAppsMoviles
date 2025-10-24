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
import kotlinx.coroutines.launch

class PostReadViewModel : ViewModel() {
    private val _state = MutableStateFlow<Post?>(null)
    val state : StateFlow<Post?> = _state

    fun fetchPost(postID: Long){

        viewModelScope.launch {
            try {
                val resp = async { RetroFitInstance.postApi.getByPostID(postID) }.await()

                Log.println(Log.INFO,"post",resp.toString())
                _state.value = resp.body();
            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun shittyTempClear(){
        _state.value = null
    }

}