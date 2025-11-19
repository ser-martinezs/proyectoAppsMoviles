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
import com.example.myapplication.ui.viewmodel.HomeScreenViewModel
import com.example.myapplication.ui.viewmodel.PostReadViewModel


// TODO: somehow get posts from a server and store them in RAM
@Composable
fun HomeScreen(navController: NavController,homeViewModel: HomeScreenViewModel) {
    val state by homeViewModel.state.collectAsState()


    if (state.error == CodeConsts.LOADING){
        FullScreenLoading()
        return
    }
    if (state.error.isNotEmpty()){
        FullScreenNetError(state.error, showButton = true, onclick = {
            homeViewModel.reload()
        })
        return
    }

    PostContainer(
        navController,
        state.page,
        state.pageNumber,
        state.pageCount,
        {homeViewModel.fetchPage(state.pageNumber)},
        {homeViewModel.fetchPage(it)}
    )

}

