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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.myapplication.ui.theme.Typography



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuckingAroundScreen(imageID : Int){
    val test_text = "Potemkin is god awful. Just look at the images and say \"it sucks\" to yourself for almost every single thing here and you have Potemkin. Potemkin's normals are horribly slow, he can't dash, he can't infinite despite having a charge, said charge is also sluggish, he lacks any future moves such as F.D.B or Hammerfall to assist him in approaching zoners or pressuring, he's gigantic, and he has twelve frames of prejump (jump startup) which renders him glued to the ground and thus susceptible to all kinds of malarkey in this game such as the near-universal CC infinites.\n" +
            "\n" +
            "None of Potemkin's strengths are real. High damage normals don't matter due to the abundance of infinites in this game. Potemkin Buster has good range and comes out instantly, but the opponent can tech immediately after being hit thus rendering any follow-up pressure impossible. Even then, this begs the question of \"how did Potemkin get close and grab someone in the first place?\". The most practical place to use Buster is defensively, which is splendid since Potemkin will either be opened up instantly or alternatively get zoned all day rendering the strength moot. His super is an almost fullscreen megapunch that's air unblockable, a rare trait in GGML, but he'll never get to use it any way so who cares?\n" +
            "\n" +
            "Don't play him. You won't have fun, and you certainly won't win.\n"

    val animationDuration = 200
    var descriptionPressed by remember { mutableStateOf(false );}
    val textScale = animateDpAsState(if (descriptionPressed) 256.dp else 64.dp, animationSpec = tween(animationDuration))
    val blurScale = animateDpAsState(if (descriptionPressed) 8.dp else 0.dp, animationSpec = tween(animationDuration))
    val imageColor = animateColorAsState(if (descriptionPressed) Color(0xFF363636) else Color(0xFFFFFFFF), animationSpec = tween(animationDuration))



    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize().background(Color.Black)
    ){
        Text("Test Title", style = Typography.headlineLarge,modifier = Modifier.fillMaxWidth(), color = Color.White)
        Image(
            painter = painterResource(id=imageID),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .blur(blurScale.value, blurScale.value),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(
                imageColor.value,
                blendMode = BlendMode.Multiply

            )
        )
    }

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp, alignment = Alignment.Bottom)
    ){
        // TODO: actually save picture to device
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()){Text("Guardar Foto")}

        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .size(textScale.value)
                .padding(8.dp)
                .clickable(onClick = { descriptionPressed = !descriptionPressed}),
        ) {
            Text(test_text, style = Typography.bodyLarge, color = Color(0xFFFFFFFF))
        }

    }

}