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
import androidx.navigation.NavController
import com.example.myapplication.components.FullScreenLoading
import com.example.myapplication.components.FullScreenNetError
import com.example.myapplication.components.PostContainer
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController,viewModel: ProfileViewModel){
    val state by viewModel.state.collectAsState()

    if (state.responses.userResponse == CodeConsts.LOADING || state.responses.userResponse== CodeConsts.LOADING){
        FullScreenLoading()
        return
    }
    if (state.responses.userResponse == CodeConsts.CONNECTION_ERROR) {FullScreenNetError();return}
    if (state.responses.userResponse == 404) {
        Column(modifier=Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.Center) {Text("No se pudo cargar el usuario")}
        return
    }


    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Text("Posts por ${state.user!!.userName}:", style = Typography.headlineLarge)
        PostContainer(navController,state.posts)
    }


}
