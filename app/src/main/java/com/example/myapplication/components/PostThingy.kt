package com.example.myapplication.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.data.model.Post
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.Typography

@Composable
fun PostThingy(post: Post, navController: NavController){

    Box(
        modifier = Modifier.fillMaxWidth().clickable(onClick = { navController.navigate(Routes.imageRoute(post.postID)) }),
    ){

        Row(Modifier.fillMaxWidth()) {
            AsyncImage(
                model = post.getImageURL(),
                contentDescription = post.postDescription,
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.fillMaxWidth()){
                Text(post.postTitle, style= Typography.headlineMedium)
                Text(post.postDescription, modifier = Modifier.fillMaxWidth().size(64.dp), style=Typography.bodyMedium)
            }

        }


    }
}
