package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.User
import com.example.myapplication.data.service.RetroFitInstance
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class ProfileResponseCodes(
    val userResponse :Int= CodeConsts.NOTHING,
    val pageResponse :Int= CodeConsts.NOTHING
)
data class ProfileState(
    val user : User? = null,
    val posts : List<Post> = listOf(),
    val page : Int= 0,
    val responses :ProfileResponseCodes= ProfileResponseCodes()
)


class ProfileViewModel : ViewModel(){
    private val _state = MutableStateFlow(ProfileState())
    val state : StateFlow<ProfileState> = _state

    fun loadUser(userID: Long){
        if (userID == _state.value.user?.userID) return

        viewModelScope.launch {
            _state.update { it.copy(
                responses = ProfileResponseCodes(userResponse = CodeConsts.LOADING, pageResponse = it.responses.pageResponse),
                page = it.page, posts = it.posts
            ) }


            val safecall = async { runCatching { RetroFitInstance.userApi.getByUserID(userID) } }.await()

            if (!safecall.isSuccess) {
                _state.update { it.copy( responses = ProfileResponseCodes(userResponse = CodeConsts.CONNECTION_ERROR)) }
                return@launch
            }

            val response = safecall.getOrNull()
            _state.update { it.copy( user = response!!.body(),
                responses = ProfileResponseCodes(userResponse = response.code(),pageResponse = it.responses.pageResponse),
                page = it.page, posts = it.posts
            ) }


        }
    }

    fun loadUserPosts(userID: Long, pageNumber: Int){

        viewModelScope.launch {
            _state.update { it.copy(responses = ProfileResponseCodes(pageResponse = CodeConsts.LOADING, userResponse = it.responses.userResponse), user = it.user) }

            val safecall = async { runCatching { RetroFitInstance.postApi.getUserPostsByPage(userID,pageNumber) } }.await()

            if (!safecall.isSuccess) {
                _state.update { it.copy( responses = ProfileResponseCodes(pageResponse = CodeConsts.CONNECTION_ERROR)) }
                return@launch
            }

            val response = safecall.getOrNull()
            _state.update { it.copy( posts = (response!!.body() ?: listOf()), responses = ProfileResponseCodes(pageResponse = response.code(), userResponse = it.responses.userResponse),user = it.user ) }
        }





    }

}