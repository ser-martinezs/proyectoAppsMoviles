package com.example.myapplication.data.model

import android.graphics.Bitmap

data class User(
    val userID: Long,
    val userName:String,
    val email:String ,
    val passwordHash:String
)
