package com.example.myapplication.data.model

import android.graphics.Bitmap
import com.example.myapplication.data.service.RetroFitInstance


data class Post(
    val postID: Long,
    val postTitle: String,
    val postDescription: String = "",
    val postedBy: User,
    val fileExtension: String = "",
){
    fun getImageURL() : String{
        return "${RetroFitInstance.IMAGE_LINK}${postID}.${fileExtension}"
    }
}


