package com.example.myapplication.data.service

import com.example.myapplication.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserRepository {

    @PUT("/login")
    fun login(@Body credentials: String) : Response<User>
    @POST("/register")
    fun register(@Body newUser: String) : Response<User>
    @GET("/{id}")
    fun getByUserID(@Path("id") userID: Long) : Response<User>

    @GET("/testcall")
    fun testCall() : Response<String>
}