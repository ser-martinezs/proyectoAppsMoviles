package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.data.local.CredentialRepository
import com.example.myapplication.data.model.User
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.data.service.RetroFitInstance
import com.example.myapplication.ui.screens.networked.LoginScreen
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.math.log

data class UserViewModelState(
    val user: User? = null,
    val error : String ="" // both use the same because you aren't logging and registering at once
)

class UserViewModel(val repository: UserRepository = UserRepository(),val credentialRepository: CredentialRepository) : ViewModel() {
    private val _state = MutableStateFlow(UserViewModelState())
    val state : StateFlow<UserViewModelState> = _state

    init {
        var attempt = 0
        viewModelScope.launch {
            Log.println(Log.INFO,"tst",_state.value.user.toString())
            while (_state.value.user == null){
                val id = credentialRepository.userFlow.first()?:-1L
                val password = credentialRepository.passwordFlow.first()?:""

                if (id == -1L || password.isEmpty()){ return@launch }
                if (_state.value.error == "HTTP 401") return@launch

                Log.println(Log.INFO,"Login Attempt",attempt++.toString())
                async { tryLogin(User(userID = id, passwordHash = password, userName = "", email = "")) }
            }
        }
    }


            if (id != -1L && password.isNotEmpty()) {
                tryLogin(User(userID =id ,passwordHash=password, userName = "", email = ""))
            }



        }
    }

    fun tryLogin(credentials: User){
        _state.update { it.copy(error = CodeConsts.LOADING) }
        viewModelScope.launch {
            var user : User?= null;
            var errorMsg = ""

            try {
                user = repository.login(credentials)
                credentialRepository.saveUserID(user.userID)
                credentialRepository.savePassword(user.passwordHash)
            }catch (error: Exception){
                error.printStackTrace()
                errorMsg = error.message?:CodeConsts.UNDEFINED_ERROR
            }
            _state.update { it.copy(error = errorMsg, user = user) }
            if (user != null){
                //Log.println(Log.INFO,"nosepo","${user.email},${user.passwordHash}")
                credentialRepository.saveID(user.userID)
                credentialRepository.savePassword(user.passwordHash)
            }
        }
    }

    fun tryRegister(credentials: User){
        _state.update { it.copy(error = CodeConsts.LOADING) }
        viewModelScope.launch {
            var user : User?= null;
            var errorMsg = ""

            try {
                user = repository.register(credentials)
                credentialRepository.saveUserID(user.userID)
                credentialRepository.savePassword(user.passwordHash)

            }catch (error: Exception){
                error.printStackTrace()
                errorMsg = error.message?:CodeConsts.UNDEFINED_ERROR
            }
            _state.update { it.copy(error = errorMsg, user = user) }
            if (user != null){
                credentialRepository.saveID(user.userID)
                credentialRepository.savePassword(user.passwordHash)
            }

        }

    }

    fun resetState(){
        _state.update { UserViewModelState() }
    }

}
