package com.example.myapplication.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetroFitInstance {
    private const val SERVER_LOCATION = ""
    const val IMAGE_LINK = "${SERVER_LOCATION}images/" // gonna get a proper url soon


    val userApi : UserRepository by lazy {
        Retrofit.Builder().
        baseUrl(SERVER_LOCATION).addConverterFactory(GsonConverterFactory.create()).
        build().
        create(UserRepository::class.java)
    }

    val postApi : PostRepository by lazy {
        Retrofit.Builder().
        baseUrl(SERVER_LOCATION).addConverterFactory(GsonConverterFactory.create()).
        build().
        create(PostRepository::class.java)
    }

}