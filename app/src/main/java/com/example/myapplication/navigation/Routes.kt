package com.example.myapplication.navigation

import android.net.Uri

object Routes {
    const val HOME = "home"
    const val UPLOAD = "upload";
    const val POST = "post/{img}";

    const val PROFILE = "profile"
    const val IMAGE = "image/{id}"
    fun imageRoute(id: Int) = "image/$id"
    fun postRoute(img: Uri) = "post/{${Uri.encode(img.toString())}}"
}