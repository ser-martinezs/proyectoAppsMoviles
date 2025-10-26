package com.example.myapplication.ui.screens.networked

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.model.User
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.UserViewModel

@Composable
fun LoginScreen(navController: NavController,viewmodel: UserViewModel){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by viewmodel.state.collectAsState()

    if (state.user != null) {
        navController.popBackStack()
        return
    }

    val registerString = buildAnnotatedString {
        append("No tienes una cuenta?")
        withLink(
            LinkAnnotation.Clickable(
                tag = "register_screen",
                linkInteractionListener = { navController.navigate(Routes.REGISTER) }
            )
        ) {
            withStyle(style = SpanStyle(color = Color.Blue))
            {
                append("Registrate")
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
            value=password,
            onValueChange = {password=it},
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.headlineMedium,
            label = {Text("Contraseña")},
            isError = !password.isNotBlank(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Unspecified, autoCorrectEnabled = false,
                imeAction = ImeAction.Unspecified
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                viewmodel.tryLogin(User(userID = 0, email=email, passwordHash = password, userName = ""))
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = (password.isNotBlank() && email.isNotBlank())
        ) {Text("Iniciar Sesion")}
        Text(registerString)
    }



}

