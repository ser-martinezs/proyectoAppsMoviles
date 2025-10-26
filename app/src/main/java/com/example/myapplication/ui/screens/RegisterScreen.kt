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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.model.User
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.UserViewModel
import com.example.myapplication.ui.viewmodel.UserViewModelState

@Composable
fun RegisterScreen(navController: NavController,viewModel: UserViewModel){
    val state by viewModel.state.collectAsState()
    var email by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}
    var username by remember {mutableStateOf("")}


    if (state.user != null) {
        navController.popBackStack()
        return;
    }


    val loginString = buildAnnotatedString {
        append("tienes una cuenta? ")
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

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value=email,
            onValueChange = {email=it},
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.headlineMedium,
            label = {Text("Correo Electonico")},
            isError = !email.isNotBlank()
        )
        OutlinedTextField(
            value=username,
            onValueChange = {username=it},
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.headlineMedium,
            label = {Text("Nombre De Usuario")},
            isError = !username.isNotBlank()
        )
        OutlinedTextField(
            value=password,
            onValueChange = {password=it},
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.headlineMedium,
            label = {Text("Contraseña")},
            isError = !password.isNotBlank()
        )
        Button(
            onClick = { viewModel.tryRegister(User(0,username,email,password)) },
            modifier = Modifier.fillMaxWidth()
        ) {Text("Crear Cuenta")}
        Text(loginString)
    }


}

