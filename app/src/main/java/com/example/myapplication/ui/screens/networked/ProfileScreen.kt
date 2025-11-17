package com.example.myapplication.ui.screens.networked

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    userID: Long,
    viewModel: ProfileViewModel = viewModel(),
){

    val state by viewModel.state.collectAsState()

    if (state.user?.userID != userID && state.responses.userResponse.isEmpty()){
        viewModel.loadUser(userID)
        viewModel.loadUserPosts(userID =userID, pageNumber = state.page)
    }


    // what the fuck?? 12-11-2025
    if (state.responses.userResponse == CodeConsts.LOADING || state.user == null){
        FullScreenLoading()
        return
    }
    if (state.responses.userResponse.isNotEmpty()) {
        FullScreenNetError(state.responses.userResponse)

        return
    }


    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Text("Posts por ${state.user!!.userName}:", style = Typography.headlineLarge)
        PostContainer(navController,state.posts,0,0,{})
    }


}
