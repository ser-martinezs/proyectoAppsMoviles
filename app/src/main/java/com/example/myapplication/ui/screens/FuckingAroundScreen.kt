package com.example.myapplication.ui.screens


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.service.RetroFitInstance
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.PostReadViewModel
import java.io.FileOutputStream


// TODO: setup this to use data obtained from a server
// oh god how am i gonna get images over the internet and save them
// oh god how will i even do this API in time
// well, atleast the android design side is mostly done IG
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuckingAroundScreen(imageID : Long, imageViewModel: PostReadViewModel){

    /*

    if (post != null){
        Column(modifier = Modifier.fillMaxSize()) {
            Text("${RetroFitInstance.IMAGE_LINK}${post!!.postID}.${post!!.fileExtension}")
            Text("${post!!.postID}.${post!!.fileExtension}")

            AsyncImage(
                model = "${RetroFitInstance.IMAGE_LINK}${post!!.postID}.${post!!.fileExtension}",
                placeholder = null,
                error = painterResource(id = R.drawable.transparentfatty),
                contentDescription = post!!.postDescription,
                modifier = Modifier.fillMaxSize()
            )
        }
    }*/



    val post by imageViewModel.state.collectAsState();
    val animationDuration = 200
    var descriptionPressed by remember { mutableStateOf(false );}
    val textScale = animateDpAsState(if (descriptionPressed) 256.dp else 64.dp, animationSpec = tween(animationDuration))
    val blurScale = animateDpAsState(if (descriptionPressed) 8.dp else 0.dp, animationSpec = tween(animationDuration))
    val imageColor = animateColorAsState(if (descriptionPressed) Color(0xFF363636) else Color(0xFFFFFFFF), animationSpec = tween(animationDuration))



    if (post == null){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
        return
    }


    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize().background(Color.Black)
    ){
        Text(post!!.postTitle, style = Typography.headlineLarge,modifier = Modifier.fillMaxWidth(), color = Color.White)
        AsyncImage(
            model = post?.getImageURL(),
            placeholder = null,
            error = painterResource(id = R.drawable.transparentfatty),
            contentDescription = post?.postDescription,
            modifier = Modifier.fillMaxSize().blur(blurScale.value, blurScale.value),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(imageColor.value, blendMode = BlendMode.Multiply)
        )

    }

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp, alignment = Alignment.Bottom)
    ){
        // TODO: actually save picture to device
        Button(onClick = {


        }, modifier = Modifier.fillMaxWidth()){Text("Guardar Foto")}

        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .size(textScale.value)
                .padding(8.dp)
                .clickable(onClick = { descriptionPressed = !descriptionPressed}),
        ) {
             Text(post!!.postDescription, style = Typography.bodyLarge, color = Color(0xFFFFFFFF))
        }


    }

}