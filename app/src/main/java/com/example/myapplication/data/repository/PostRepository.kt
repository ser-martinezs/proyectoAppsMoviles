package com.example.myapplication.data.repository

import android.graphics.Bitmap
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.User
import com.example.myapplication.data.service.RetroFitInstance
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream


open class PostRepository {

    open suspend fun getByPostID(postID: Long) :Post{
        return RetroFitInstance.postApi.getByPostID(postID)
    }
    open suspend fun getPage(pageNumber: Int) : List<Post>{
        return RetroFitInstance.postApi.getByPage(pageNumber)
    }
    open suspend fun getUserPostsByPage(userID:Long, page:Int) : List<Post>{
        return RetroFitInstance.postApi.getUserPostsByPage(userID,page)
    }
    open suspend fun getPageCount(): Int{
        return RetroFitInstance.postApi.getOverallPages()
    }

    open suspend fun getUserPageCount(userID: Long): Int{ return RetroFitInstance.postApi.getUserPages(userID) }

    open suspend fun uploadPost(stream:ByteArrayOutputStream, post: Post) : String{

        val reqBody = stream.toByteArray().toRequestBody("image/webp".toMediaTypeOrNull())
        val imageFile: MultipartBody.Part = MultipartBody.Part.createFormData("file", "thisisgettingoverridenanywaysright", reqBody)

        return RetroFitInstance.postApi.uploadPost(imageFile,post)
    }
}

open class TestablePostRepository : PostRepository() {

    override suspend fun getByPostID(postID: Long) :Post{
        return Post(
            postID=0,
            postedBy = User(0,"","",""),
            postTitle = "",
            postDescription = "",
            fileExtension = ".webp"
        )
    }

    override suspend fun getPage(pageNumber: Int) : List<Post>{ return emptyList<Post>() }

    override suspend fun getUserPostsByPage(userID:Long, page:Int) : List<Post>{ return emptyList<Post>()}
    override suspend fun getPageCount(): Int{return 0}

    override suspend fun getUserPageCount(userID: Long): Int{ return 0 }

    override suspend fun uploadPost(stream:ByteArrayOutputStream, post: Post) : String{ return "" }


}




