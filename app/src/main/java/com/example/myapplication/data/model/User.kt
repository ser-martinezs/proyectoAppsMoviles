package com.example.myapplication.data.model

data class User(
    val userID: Long,
    val userName:String,
    val email:String ,
    val passwordHash:String
)
