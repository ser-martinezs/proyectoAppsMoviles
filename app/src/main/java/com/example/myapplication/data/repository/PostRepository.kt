package com.example.myapplication.data.repository

import android.graphics.Bitmap
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.service.RetroFitInstance


class PostRepository {

    suspend fun getByPostID(postID: Long) :Post{
        return RetroFitInstance.postApi.getByPostID(postID)
    }
    suspend fun getPage(pageNumber: Int) : List<Post>{
        return RetroFitInstance.postApi.getByPage(pageNumber)
    }
    suspend fun getUserPostsByPage(userID:Long, page:Int) : List<Post>{
        return RetroFitInstance.postApi.getUserPostsByPage(userID,page)
    }
    suspend fun getPageCount(): Int{
        return RetroFitInstance.postApi.getOverallPages()
    }

    suspend fun getUserPageCount(userID: Long): Int{ return RetroFitInstance.postApi.getUserPages(userID) }

    suspend fun uploadPost(image : Bitmap, post: Post) : String{
        throw NotImplementedError()
        //return RetroFitInstance.postApi.uploadPost(image,post)
    }




}