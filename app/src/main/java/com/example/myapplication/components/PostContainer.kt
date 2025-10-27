package com.example.myapplication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.User
import com.example.myapplication.ui.viewmodel.PostReadViewModel

@Composable
fun PostContainer(navController: NavController,posts:List<Post>,curPage: Int,maxPage:Int,onPageChanged:(Int)-> Unit) {

    Column(modifier = Modifier.fillMaxSize().padding(8.dp).verticalScroll(rememberScrollState())) {
        for (post in posts) PostThingy(post,navController)

        //PageButtons(maxPage,curPage,onPageChanged)
    }

}

