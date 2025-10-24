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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.ScrollingView
import androidx.navigation.NavController

import com.example.myapplication.R
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.Typography


// TODO: somehow get posts from a server and store them in RAM
@Composable
fun HomeScreen(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize().padding(8.dp).verticalScroll(rememberScrollState())) {

        for (i in 0..6) {
            PostThingy(R.drawable.transparentfatty,i.toLong(),navController)
        }


    }
}

@Composable
fun PostThingy(imageID : Int, postID : Long,navController: NavController){
    val test_text = "Potemkin is god awful. Just look at the images and say \"it sucks\" to yourself for almost every single thing here and you have Potemkin. Potemkin's normals are horribly slow, he can't dash, he can't infinite despite having a charge, said charge is also sluggish, he lacks any future moves such as F.D.B or Hammerfall to assist him in approaching zoners or pressuring, he's gigantic, and he has twelve frames of prejump (jump startup) which renders him glued to the ground and thus susceptible to all kinds of malarkey in this game such as the near-universal CC infinites.\n" +
            "\n" +
            "None of Potemkin's strengths are real. High damage normals don't matter due to the abundance of infinites in this game. Potemkin Buster has good range and comes out instantly, but the opponent can tech immediately after being hit thus rendering any follow-up pressure impossible. Even then, this begs the question of \"how did Potemkin get close and grab someone in the first place?\". The most practical place to use Buster is defensively, which is splendid since Potemkin will either be opened up instantly or alternatively get zoned all day rendering the strength moot. His super is an almost fullscreen megapunch that's air unblockable, a rare trait in GGML, but he'll never get to use it any way so who cares?\n" +
            "\n" +
            "Don't play him. You won't have fun, and you certainly won't win.\n"

    Box(
        modifier = Modifier.fillMaxWidth().clickable(onClick = {
            val route = Routes.imageRoute(postID);
            Log.println(Log.INFO,"what the fuck",postID.toString())
            Log.println(Log.INFO,"what the fuck",route)


            navController.navigate(route)

        }),
    ){

        Row(Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id=imageID),
                contentDescription = "",
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Fit,
            )
            Column(modifier = Modifier.fillMaxWidth()){
                Text("Test Title", style= Typography.headlineMedium)
                Text(test_text, modifier = Modifier.fillMaxWidth().size(64.dp), style=Typography.bodyMedium)
            }

        }


    }



}
