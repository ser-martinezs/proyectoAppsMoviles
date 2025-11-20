package com.example.myapplication.data.repository

import com.example.myapplication.data.model.User
import com.example.myapplication.data.service.RetroFitInstance


open class UserRepository {
    open suspend fun login(credentials: User) : User{
        return RetroFitInstance.userApi.login(credentials)
    }
    open suspend fun register(newUser: User) : User{
        return RetroFitInstance.userApi.register(newUser)
    }
    open suspend fun getByUserID(userID: Long) : User{
        return RetroFitInstance.userApi.getByUserID(userID)
    }
    
}

class TestableUserRepository: UserRepository(){
    var dummyUsers = listOf(
        User(0,"userA","test1@gmail.com","A"),
        User(1,"userB","test2@gmail.com","B"),
        User(2,"userC","test3@gmail.com","C"),
        User(3,"userD","test4@gmail.com","D"),
    )
    override suspend fun login(credentials: User) : User{

        return User(0,"","","")
        //return RetroFitInstance.userApi.login(credentials)
    }
    override suspend fun getByUserID(userID: Long) : User{
        return User(0,"","","")
    }

    override suspend fun register(newUser: User): User {
        return User(0,"","","")
    }
}