package com.example.myapplication.ui.screens.networked

import android.util.Log
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
import com.example.myapplication.components.ErrorDialog
import com.example.myapplication.components.FullScreenLoading
import com.example.myapplication.components.FullScreenNetError
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.data.model.User
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.UserViewModel
import java.util.regex.Pattern

@Composable
fun LoginScreen(navController: NavController,viewmodel: UserViewModel){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by viewmodel.state.collectAsState()
    val emailRegex = "[a-z0-9]+[_a-z0-9\\.-]*[a-z0-9]+@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})"
    val pattern: Pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE)


    if (state.error == CodeConsts.LOADING){
        FullScreenLoading()
        return
    }
    if (state.error.isNotEmpty()){
        ErrorDialog({viewmodel.resetState()},state.error)
        return
    }

    /*
    if (state.error.isNotEmpty()){
        ErrorDialog({viewmodel.resetState()},state.error)
        return
    }
    if (state.responseCode == CodeConsts.CONNECTION_ERROR){
        ErrorDialog({viewmodel.resetState()},"Hubo un problema para conectarse con el servidor")
        return
    }
    if (state.responseCode == 401){
        ErrorDialog({viewmodel.resetState()},"Credenciales incorrectas.")
        return
    }*/


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

    var emailError = !(email.isNotBlank()  && pattern.matcher(email).matches())

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value=email,
            onValueChange = {
                if (it.length < 255) {email=it}
                emailError = !(email.isNotBlank()  && pattern.matcher(email).matches())
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.headlineMedium,
            label = {Text("Correo Electonico")},
            isError = emailError,
            supportingText = {Text(if (emailError) CodeConsts.INVALID_EMAIL else "")}

        )
        OutlinedTextField(
            value=password,
            onValueChange = {password=it},
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.headlineMedium,
            label = {Text("Contraseña")},
            isError = !password.isNotBlank(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Unspecified, autoCorrectEnabled = false,
                imeAction = ImeAction.Unspecified, keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation(),
            supportingText = {Text(if (!password.isNotBlank()) CodeConsts.INVALID_PASSWORD else "")},
        )

        Button(
            onClick = {
                viewmodel.tryLogin(User(userID = 0, email=email, passwordHash = password, userName = ""))
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = (password.isNotBlank() && !emailError)
        ) {Text("Iniciar Sesion")}
        Text(registerString)
    }

}

