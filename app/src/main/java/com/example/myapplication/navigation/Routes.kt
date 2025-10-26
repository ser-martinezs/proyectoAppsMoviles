package com.example.myapplication.navigation

import android.net.Uri

object Routes {
    const val HOME = "home"
    const val UPLOAD = "upload";
    const val POST = "post";
    const val LOGIN = "login";
    const val REGISTER = "register";

    const val USER_PROFILE = "profile/"
    const val PROFILE = "profile/{id}"
    const val IMAGE = "image/{id}"
    fun imageRoute(id: Long) = "image/$id"
    fun profileRoute(id: Long) = "profile/${id}"


}