package com.example.myapplication.navigation

object Routes {
    const val HOME = "home"
    const val UPLOAD = "upload";
    const val PROFILE = "profile"
    const val IMAGE = "image/{id}"

    fun imageRoute(id: Int) = "image/$id"
}