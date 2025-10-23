package com.example.myapplication.comoponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginResquestMessage(navController: NavController) {

    val loginString = buildAnnotatedString {
        append("Tienes una cuenta? ")
        withLink(
            LinkAnnotation.Clickable(
                tag = "login_screen",
                linkInteractionListener = { navController.navigate(Routes.LOGIN) }
            )
        ) {
            withStyle(style = SpanStyle(color = Color.Blue))
            {
                append("Iniciar Sesion")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.uni),
            contentDescription = null,
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Crea una cuenta para subir imagenes y ver tus publicaciones",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate(Routes.REGISTER) },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Registrarse") }

        Spacer(modifier = Modifier.height(12.dp))

        Text(loginString)

    }
}