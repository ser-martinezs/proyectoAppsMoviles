package com.example.myapplication.data.service

import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface PostRepository {

    @GET("/api/v1/posts/{id}")
    suspend fun getByPostID(@Path("id") postID: Long) : Response<Post>

    @GET("/api/v1/page/{pageNumber}")
    suspend fun getByPage(@Path("pageNumber") pageNumber: Int) : Response<List<Post>>

    @GET("/api/v1/user/{userID}")
    suspend fun getUserPostsByPage(@Path("userID") userID:Long, @Query("page") page:Int) : Response<List<Post>>

    @POST("/api/v1/upload")
    @Multipart
    suspend fun uploadPost(@Part image : MultipartBody.Part, @Part post: Post) : Response<Post>


}