package com.example.myapplication.data.repository

import android.graphics.Bitmap
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.service.RetroFitInstance
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream


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

    suspend fun uploadPost(stream:ByteArrayOutputStream, post: Post) : String{

        val reqBody = stream.toByteArray().toRequestBody("image/webp".toMediaTypeOrNull())
        val imageFile: MultipartBody.Part = MultipartBody.Part.createFormData("file", "thisisgettingoverridenanywaysright", reqBody)

        return RetroFitInstance.postApi.uploadPost(imageFile,post)
    }




}