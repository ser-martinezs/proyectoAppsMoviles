package com.example.myapplication.data.model

import android.graphics.Bitmap


data class Post(
    val postID: Long,
    val postTitle: String,
    val postDescription: String = "",
    val postedBy: User,
    val fileExtension: String = "",
    val imageLink : String
)


