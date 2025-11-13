package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.data.model.User
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.data.service.RetroFitInstance
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserViewModelState(
    val user: User? = null,
    val error : String ="" // both use the same because you aren't logging and registering at once
)

class UserViewModel(val repository: UserRepository) : ViewModel() {
    private val _state = MutableStateFlow(UserViewModelState())
    val state : StateFlow<UserViewModelState> = _state



    fun tryLogin(credentials: User){
        viewModelScope.launch {
            _state.update { it.copy(error = CodeConsts.LOADING) }
            var user : User?= null;
            var errorMsg = ""

            try {
                user = repository.login(credentials)
            }catch (error: Exception){
                error.printStackTrace()
                errorMsg = error.message?:CodeConsts.UNDEFINED_ERROR
            }


            _state.update { it.copy(error = errorMsg, user = user) }


        }
    }

    fun tryRegister(credentials: User){
        viewModelScope.launch {
        }

    }

}
