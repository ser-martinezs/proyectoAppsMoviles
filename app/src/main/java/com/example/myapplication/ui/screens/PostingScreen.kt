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
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.PostViewModel


@Composable
fun PostingScreen(viewModel: PostViewModel){



    val test_text = "Potemkin is god awful. Just look at the images and say \"it sucks\" to yourself for almost every single thing here and you have Potemkin. Potemkin's normals are horribly slow, he can't dash, he can't infinite despite having a charge, said charge is also sluggish, he lacks any future moves such as F.D.B or Hammerfall to assist him in approaching zoners or pressuring, he's gigantic, and he has twelve frames of prejump (jump startup) which renders him glued to the ground and thus susceptible to all kinds of malarkey in this game such as the near-universal CC infinitesinfinites"




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
                isError = state.postTitle.isEmpty()
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