package com.example.myapplication.ui.screens.networked

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

import com.example.myapplication.components.FullScreenLoading
import com.example.myapplication.components.FullScreenNetError
import com.example.myapplication.components.PostContainer
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.ui.viewmodel.PostReadViewModel


// TODO: somehow get posts from a server and store them in RAM
@Composable
fun HomeScreen(navController: NavController,imageViewModel: PostReadViewModel) {
    val state by imageViewModel.state.collectAsState()


    if (state.errors.pageError == CodeConsts.LOADING){
        FullScreenLoading()
        return
    }
    if (state.errors.pageError.isNotEmpty()){
        FullScreenNetError(state.errors.pageError)
        return
    }

    PostContainer(
        navController,
        state.page,
        state.pageNumber,
        state.pageCount,
        {imageViewModel.fetchPage(it)},
    )

}

