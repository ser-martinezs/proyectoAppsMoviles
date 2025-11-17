package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.local.CredentialRepository
import com.example.myapplication.data.repository.UserRepository

class UserViewModelFactory(
    private val repository: CredentialRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            Log.println(Log.INFO,"why","is this being made??")
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository = UserRepository(), credentialRepository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}