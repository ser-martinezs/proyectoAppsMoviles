package com.example.myapplication.ui.screens

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathSegment
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.example.myapplication.R
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.Typography
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout


@Composable
@Preview(showSystemUi = false, showBackground = false, name = "dsa")
fun FuckingAroundScreen(){
    val test_text = "Potemkin is god awful. Just look at the images and say \"it sucks\" to yourself for almost every single thing here and you have Potemkin. Potemkin's normals are horribly slow, he can't dash, he can't infinite despite having a charge, said charge is also sluggish, he lacks any future moves such as F.D.B or Hammerfall to assist him in approaching zoners or pressuring, he's gigantic, and he has twelve frames of prejump (jump startup) which renders him glued to the ground and thus susceptible to all kinds of malarkey in this game such as the near-universal CC infinites.\n" +
            "\n" +
            "None of Potemkin's strengths are real. High damage normals don't matter due to the abundance of infinites in this game. Potemkin Buster has good range and comes out instantly, but the opponent can tech immediately after being hit thus rendering any follow-up pressure impossible. Even then, this begs the question of \"how did Potemkin get close and grab someone in the first place?\". The most practical place to use Buster is defensively, which is splendid since Potemkin will either be opened up instantly or alternatively get zoned all day rendering the strength moot. His super is an almost fullscreen megapunch that's air unblockable, a rare trait in GGML, but he'll never get to use it any way so who cares?\n" +
            "\n" +
            "Don't play him. You won't have fun, and you certainly won't win.\n"

    var descriptionPressed by remember { mutableStateOf(false);}
    val textScale = animateDpAsState(if (descriptionPressed) 256.dp else 64.dp, animationSpec = tween(500))
    val blurScale = animateDpAsState(if (descriptionPressed) 8.dp else 0.dp, animationSpec = tween(500))
    val imageColor = animateColorAsState(if (descriptionPressed) Color(0x404040FF) else Color(0xFFFFFFFF), animationSpec = tween(500))


    Scaffold(
        topBar ={
            Text("i love this stupid teto💞", style = Typography.headlineLarge,modifier = Modifier.fillMaxWidth())
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding).
            fillMaxSize()
        ){
            Image(
                painter = painterResource(id=R.drawable.uni),
                contentDescription = "teto",
                modifier = Modifier.fillMaxSize()
                    .background(Color(0, 0, 0, 255))
                    .blur(blurScale.value,blurScale.value),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    imageColor.value,
                    blendMode = BlendMode.Multiply

                )
            )
        }





        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp, alignment = Alignment.Bottom)
        ){
            // TODO: actually save picture to device
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()){Text("Guardar Foto")}

            // TODO: actually animate text going up
            Button(
                onClick = { descriptionPressed = !descriptionPressed},
                modifier = Modifier.background(Color.Transparent).fillMaxWidth().size(textScale.value),
                shape = RoundedCornerShape(0),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent,),
            ) {
                Text(test_text, style = Typography.bodyLarge, color = Color(0xFFFFFFFF))
            }

        }

    }


}