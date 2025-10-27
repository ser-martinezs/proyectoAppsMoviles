package com.example.myapplication.data.service

import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class CustomClient : OkHttpClient(){
    override fun newCall(request: Request): Call {
        return super.newCall(request)
    }
}

object RetroFitInstance {
    private const val SERVER_LOCATION = "http://IP_HERE/"
    const val IMAGE_LINK = "${SERVER_LOCATION}images/" // gonna get a proper url soon


    val userApi : UserRepository by lazy {
        Retrofit.Builder().
        baseUrl(SERVER_LOCATION).addConverterFactory(GsonConverterFactory.create()).
        build().
        create(UserRepository::class.java)
    }

    val postApi : PostRepository by lazy {
        Retrofit.Builder().
        baseUrl(SERVER_LOCATION).
        addConverterFactory(ScalarsConverterFactory.create()).
        addConverterFactory(GsonConverterFactory.create()).

        build().
        create(PostRepository::class.java)
    }


}