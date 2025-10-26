package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.User
import com.example.myapplication.data.service.RetroFitInstance
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserViewModelState(
    val user: User? = null,
    var responseCode : Int = -1
)

class UserViewModel : ViewModel() {
    private val _state = MutableStateFlow(UserViewModelState())
    val state : StateFlow<UserViewModelState> = _state



    fun tryLogin(credentials: User){
        viewModelScope.launch {
            try {
                val resp = async { RetroFitInstance.userApi.login(credentials) }.await()
                Log.println(Log.INFO,"user login",resp.code().toString())
                Log.println(Log.INFO,"user login",resp.body().toString())
                _state.update { it.copy(user = resp.body()) }
            }
            catch (e: Exception){
                e.printStackTrace()
                _state.update { it.copy(user = null) }

            }
        }
    }

    fun tryRegister(credentials: User){
        viewModelScope.launch {
            try {
                val resp = async { RetroFitInstance.userApi.register(credentials) }.await()
                Log.println(Log.INFO,"user register",resp.code().toString())
                Log.println(Log.INFO,"user register",resp.body().toString())
                _state.update { it.copy(user = resp.body()) }
            }
            catch (e: Exception){
                e.printStackTrace()
                _state.update { it.copy(user = null) }

            }
        }
    }

}
