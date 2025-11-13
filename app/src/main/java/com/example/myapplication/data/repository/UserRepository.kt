package com.example.myapplication.data.repository

import com.example.myapplication.data.model.User
import com.example.myapplication.data.service.RetroFitInstance


class UserRepository {
    suspend fun login(credentials: User) : User{
        return RetroFitInstance.userApi.login(credentials)
    }


    suspend fun register(newUser: User) : User{
        return RetroFitInstance.userApi.register(newUser)
    }

    suspend fun getByUserID(userID: Long) : User{
        return RetroFitInstance.userApi.getByUserID(userID)
    }
    
}