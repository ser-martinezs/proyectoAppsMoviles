package com.example.myapplication.ui.screens

import android.util.Log
import android.widget.ScrollView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.ScrollingView
import androidx.navigation.NavController
import coil.compose.AsyncImage

import com.example.myapplication.R
import com.example.myapplication.data.model.Post
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.PostReadViewModel


// TODO: somehow get posts from a server and store them in RAM
@Composable
fun HomeScreen(navController: NavController,imageViewModel: PostReadViewModel) {


    val state by imageViewModel.state.collectAsState();
    Column(modifier = Modifier.fillMaxSize().padding(8.dp).verticalScroll(rememberScrollState())) {
        for (post in state.page)PostThingy(post,navController)
    }
}

@Composable
fun PostThingy(post: Post, navController: NavController){

    Box(
        modifier = Modifier.fillMaxWidth().clickable(onClick = {
            navController.navigate(Routes.imageRoute(post.postID))
        }),
    ){

        Row(Modifier.fillMaxWidth()) {
            AsyncImage(
                model = post.getImageURL(),
                contentDescription = post.postDescription,
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Fit
            )
            Column(modifier = Modifier.fillMaxWidth()){
                Text(post.postTitle, style= Typography.headlineMedium)
                Text(post.postDescription, modifier = Modifier.fillMaxWidth().size(64.dp), style=Typography.bodyMedium)
            }

        }


    }



}
