package com.example.myapplication.ui.screens.networked


import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.components.FullScreenLoading
import com.example.myapplication.components.FullScreenNetError
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.PostReadViewModel


// TODO: setup this to use data obtained from a server
// oh god how am i gonna get images over the internet and save them
// oh god how will i even do this API in time
// well, atleast the android design side is mostly done IG
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDisplayScreen(imageID : Long, imageViewModel: PostReadViewModel, navController: NavController){

    val state by imageViewModel.state.collectAsState();
    val animationDuration = 200
    var descriptionPressed by remember { mutableStateOf(false );}
    val textScale = animateDpAsState(if (descriptionPressed) 256.dp else 64.dp, animationSpec = tween(animationDuration))
    val blurScale = animateDpAsState(if (descriptionPressed) 8.dp else 0.dp, animationSpec = tween(animationDuration))
    val imageColor = animateColorAsState(if (descriptionPressed) Color(0xFF363636) else Color(0xFFFFFFFF), animationSpec = tween(animationDuration))

    imageViewModel.fetchPost(postID = imageID)

    Log.println(Log.INFO,"PostDisplayScreen ik","${state.errors.postCode} ${state.post} ${state.postID},${imageID}")

    when (state.errors.postCode){
        CodeConsts.NOTHING -> {
            return
        }
        CodeConsts.CONNECTION_ERROR ->{
            FullScreenNetError()
            return
        }
        404 -> {
            Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text("El post no se pudo encontrar", style = Typography.headlineLarge)
            }
            return
        }
    }
    if (state.errors.postCode == CodeConsts.LOADING || state.errors.postCode== CodeConsts.NOTHING){
        FullScreenLoading()
        return
    }
    if (state.errors.postCode != 200) return

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize().background(Color.Black)
    ){



        Text(state.post!!.postTitle, style = Typography.headlineLarge,modifier = Modifier.fillMaxWidth(), color = Color.White)
        AsyncImage(
            model = state.post?.getImageURL(),
            placeholder = null,
            error = painterResource(id = R.drawable.transparentfatty),
            contentDescription = state.post?.postDescription,
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
        Text(
            state.post!!.postedBy.userName,
            modifier = Modifier.clickable(onClick = { navController.navigate(Routes.profileRoute(state.post!!.postedBy.userID))}).dropShadow(RectangleShape, Shadow(16.dp, spread = 1.dp)),
            color = Color.Blue
        )

        // TODO: actually save picture to device
        Button(onClick = {


        }, modifier = Modifier.fillMaxWidth()){Text("Guardar Foto")}

        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .size(textScale.value)
                .padding(8.dp)
                .clickable(onClick = { descriptionPressed = !descriptionPressed})

        ) {
             Text(
                 state.post!!.postDescription,
                 style = Typography.bodyLarge,
                 color = Color(0xFFFFFFFF),
                 modifier = Modifier.fillMaxWidth().dropShadow(RectangleShape, Shadow(16.dp, spread = 1.dp))

             )
        }


    }

}
