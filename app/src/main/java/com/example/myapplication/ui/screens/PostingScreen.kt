package com.example.myapplication.ui.screens

import android.graphics.Bitmap
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.User
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.PostViewModel


@Composable
fun PostingScreen(viewModel: PostViewModel,curUser: User){

    val state by viewModel.state.collectAsState()
    val image: Bitmap?= state.postBitmap
    val errors = state.postTitle.isEmpty() || image == null


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OutlinedTextField(
                value=state.postTitle,
                onValueChange = { viewModel.setPostTitle(it) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = Typography.headlineLarge,
                label = {Text("Titlo del Post")},
                isError = state.postTitle.isEmpty(),
            )
        },
        bottomBar = {
            Column{
                OutlinedTextField(
                    value=state.postDesc,
                    onValueChange = { viewModel.setPostDescription(it) },
                    modifier = Modifier.fillMaxWidth().size(256.dp),
                    textStyle = Typography.bodyLarge,
                    label = {Text("Descripcion del Post")},
                )

                // TODO: actually post the image
                Button(onClick = {
                    viewModel.postImage(Post(postedBy = curUser, postID =-1, postTitle = state.postTitle, postDescription = state.postDesc))
                }, enabled = !errors, modifier = Modifier.fillMaxWidth()) {Text("Subir Post")}
            }
        }

    ){

        innerPadding->
        if (image != null){
            Image(
                bitmap= image.asImageBitmap(), null,
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentScale = ContentScale.Fit
            )
        }

    }

}