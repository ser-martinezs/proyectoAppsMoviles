package com.example.myapplication.data.service

import com.example.myapplication.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserRepository {

    @PUT("/api/v1/users/login")
    suspend fun login(@Body credentials: User) : Response<User>

    @POST("/api/v1/users/register")
    suspend fun register(@Body newUser: User) : Response<User>
    @GET("/api/v1/users/{id}")
    suspend fun getByUserID(@Path("id") userID: Long) : Response<User>

}