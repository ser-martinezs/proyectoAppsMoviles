package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.User
import com.example.myapplication.data.repository.PostRepository
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.data.service.RetroFitInstance
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class ProfileResponseCodes(
    val userResponse : String= CodeConsts.NOTHING,
    val pageResponse :String= CodeConsts.NOTHING
)
data class ProfileState(
    val user : User? = null,
    val posts : List<Post> = listOf(),
    val page : Int= 0,
    val responses :ProfileResponseCodes= ProfileResponseCodes()
)


class ProfileViewModel(val userRepo: UserRepository = UserRepository(), val postRepo: PostRepository = PostRepository()) : ViewModel(){
    private val _state = MutableStateFlow(ProfileState())
    val state : StateFlow<ProfileState> = _state



    fun loadUser(userID: Long){
        _state.update { it.copy(responses = it.responses.copy(userResponse = CodeConsts.LOADING)) }

        viewModelScope.launch {
            var user : User?= null
            var errorMsg :String = ""


            try {
                user = userRepo.getByUserID(userID)
            }catch (error: Exception){
                error.printStackTrace()
                errorMsg = error.message?:CodeConsts.UNDEFINED_ERROR
            }

            _state.update { it.copy(responses = it.responses.copy(userResponse = errorMsg), user = user) }


        }

    }

    fun loadUserPosts(userID: Long, pageNumber: Int){
        _state.update { it.copy(responses = it.responses.copy(pageResponse = CodeConsts.LOADING)) }

        viewModelScope.launch {
            var posts :List<Post> = listOf()
            var errorMsg = ""

            try {
                posts = postRepo.getUserPostsByPage(userID,pageNumber)
            }catch (error: Exception){
                error.printStackTrace()
                errorMsg = error.message?:CodeConsts.UNDEFINED_ERROR
            }
            _state.update { it.copy( posts = (posts), responses = it.responses.copy(pageResponse = errorMsg)) }

        }






    }

}