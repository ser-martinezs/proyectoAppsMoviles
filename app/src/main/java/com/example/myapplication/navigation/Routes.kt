package com.example.myapplication.navigation

import android.net.Uri

object Routes {
    const val HOME = "home"
    const val UPLOAD = "upload";
    const val POST = "post";
    const val LOGIN = "login";
    const val REGISTER = "register";
    const val PROFILE = "profile"
    const val IMAGE = "image/{id}"
    fun imageRoute(id: Int) = "image/$id"
    fun profileRoute(id: Long) = "profile/{${id}}"


}