package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.data.model.User
import com.example.myapplication.data.service.RetroFitInstance
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserViewModelState(
    val user: User? = null,
    var responseCode : Int = CodeConsts.NOTHING // both use the same because you aren't logging and registering at once
)

class UserViewModel : ViewModel() {
    private val _state = MutableStateFlow(UserViewModelState())
    val state : StateFlow<UserViewModelState> = _state



    fun tryLogin(credentials: User){

        viewModelScope.launch {
            _state.update { it.copy(user=null,CodeConsts.LOADING) }
            val safecall = async { runCatching { RetroFitInstance.userApi.login(credentials) } }.await()


            if (!safecall.isSuccess) {
                _state.update { it.copy(responseCode = CodeConsts.CONNECTION_ERROR) }
                return@launch
            }

            val response = safecall.getOrNull()

            _state.update { it.copy(user = response!!.body(), responseCode = response.code()) }

        }
    }

    fun tryRegister(credentials: User){
        viewModelScope.launch {
            _state.update { it.copy(user=null,CodeConsts.LOADING) }
            val safecall = async { runCatching { RetroFitInstance.userApi.register(credentials) } }.await()


            if (!safecall.isSuccess) {
                _state.update { it.copy(responseCode = CodeConsts.CONNECTION_ERROR) }
                return@launch
            }

            val response = safecall.getOrNull()

            _state.update { it.copy(user = response!!.body(), responseCode = response.code()) }

        }

    }
    fun resetState(){_state.update { it.copy(responseCode = CodeConsts.NOTHING) }}

}
