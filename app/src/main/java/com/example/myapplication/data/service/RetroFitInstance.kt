package com.example.myapplication.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetroFitInstance {
    private const val SERVER_LOCATION = ""
    private const val BASE_URL = "$SERVER_LOCATION/api/v1/users/" // gonna get a proper url soon
    val userApi : UserRepository by lazy {
        Retrofit.Builder().
        baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).
        build().
        create(UserRepository::class.java)
    }
}