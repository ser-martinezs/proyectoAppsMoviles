package com.example.myapplication.ui.screens.networked

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.components.FullScreenLoading
import com.example.myapplication.components.FullScreenNetError
import com.example.myapplication.components.PageButtons
import com.example.myapplication.components.PostContainer
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.ProfileViewModel
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    userID: Long,
    viewModel: ProfileViewModel = viewModel(),
){

    val state by viewModel.state.collectAsState()

    if (state.user?.userID != userID && state.responses.userResponse.isEmpty()  ){
        viewModel.loadUser(userID)
        viewModel.loadUserPosts(userID =userID, pageNumber = state.page)

    }

    if (state.pageCount == -1 && state.responses.pageCountResponse.isEmpty()){
        viewModel.fetchPageCount()
    }



    // what the fuck?? 12-11-2025
    if (
        state.responses.userResponse == CodeConsts.LOADING ||
        state.responses.pageResponse == CodeConsts.LOADING ||
        state.responses.pageCountResponse == CodeConsts.LOADING
        )

    {

        FullScreenLoading()
        return
    }
    if (state.responses.userResponse.isNotEmpty()) {
        FullScreenNetError(state.responses.userResponse, showButton = true, onclick = {
            viewModel.loadUser(userID)
            viewModel.loadUserPosts(userID =userID, pageNumber = state.page)
        })

        return
    }
    if (state.responses.pageResponse.isNotEmpty()) {
        FullScreenNetError(state.responses.pageResponse, showButton = true, onclick = {
            viewModel.loadUser(userID)
            viewModel.loadUserPosts(userID =userID, pageNumber = state.page)
        })

        return
    }
    if (state.responses.pageCountResponse.isNotEmpty()) {
        FullScreenNetError(state.responses.pageCountResponse, showButton = true, onclick = {
            viewModel.loadUser(userID)
            viewModel.loadUserPosts(userID =userID, pageNumber = state.page)
        })

        return
    }



    if (state.user == null){
        FullScreenNetError("error raro que el usuario no puede ser conseguido sin algun error??", showButton = true, onclick = {
            viewModel.loadUser(userID)
            viewModel.loadUserPosts(userID =userID, pageNumber = state.page)
        })

        return
    }

    PostContainer(
        navController,state.posts,
        state.page,
        state.pageCount,
        {
            viewModel.reload()
        },
        {
            Log.println(Log.INFO,"pene",it.toString())
            viewModel.loadUserPosts(userID,it)
        },
        {Text("Posts por ${state.user!!.userName}:", style = Typography.headlineLarge)}
    )



}
