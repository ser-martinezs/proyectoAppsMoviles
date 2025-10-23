package com.example.myapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.Typography

@Composable
fun RegisterScreen(navController: NavController){
    val loginString = buildAnnotatedString {
        append("tienes una cuenta? ")
        withLink(
            LinkAnnotation.Clickable(
                tag = "login_screen",
                linkInteractionListener = { navController.navigate(Routes.REGISTER) }
            )
        ) {
            withStyle(style = SpanStyle(color = Color.Blue))
            {
                append("Iniciar Sesion")
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value="",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.headlineMedium,
            label = {Text("Correo Electonico")},
            isError = true
        )
        OutlinedTextField(
            value="",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.headlineMedium,
            label = {Text("Nombre De Usuario")},
            isError = true
        )
        OutlinedTextField(
            value="",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.headlineMedium,
            label = {Text("Contraseña")},
            isError = true
        )
        Button(
            onClick = {navController.navigate(Routes.HOME)},
            modifier = Modifier.fillMaxWidth()
        ) {Text("Crear Cuenta")}
        Text(loginString)
    }


}

