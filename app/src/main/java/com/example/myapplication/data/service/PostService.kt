package com.example.myapplication.data.service

import com.example.myapplication.data.model.Post
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface PostService {

    @GET("/api/v1/posts/{id}")
    suspend fun getByPostID(@Path("id") postID: Long) : Post

    @GET("/api/v1/posts/page/{pageNumber}")
    suspend fun getByPage(@Path("pageNumber") pageNumber: Int) : List<Post>

    @GET("/api/v1/posts/user/{userID}")
    suspend fun getUserPostsByPage(@Path("userID") userID:Long, @Query("pageNumber") page:Int) : List<Post>

    @POST("/api/v1/posts/upload")
    @Multipart
    suspend fun uploadPost(@Part image : MultipartBody.Part, @Part("postData") post: Post) : String

    @GET("/api/v1/posts/pagecount/{userID}")
    suspend fun getUserPages(@Path("userID") userID: Long?): Int

    @GET("/api/v1/posts/pagecount")
    suspend fun getOverallPages(): Int

}