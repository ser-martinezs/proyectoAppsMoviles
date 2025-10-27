package com.example.myapplication.ui.screens

import android.service.autofill.FieldClassification
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.myapplication.components.ErrorDialog
import com.example.myapplication.components.FullScreenLoading
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.data.model.User
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.UserViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController,viewModel: UserViewModel){
    val state by viewModel.state.collectAsState()
    var email by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}
    var username by remember {mutableStateOf("")}
    val emailRegex = "[a-z0-9]+[_a-z0-9\\.-]*[a-z0-9]+@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})"
    val pattern: Pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE)

    if (state.responseCode == CodeConsts.LOADING){
        FullScreenLoading()
        return
    }

    if (state.responseCode == 400 || state.responseCode == 500){
        ErrorDialog({viewModel.resetState()},"Hubo un problema para crear el usuario.",)
        return
    }
    if (state.responseCode == 409){
        ErrorDialog({viewModel.resetState()},"este nombre de usuario o correo ya esta siendo usado.",)
        return
    }
    if (state.responseCode == CodeConsts.CONNECTION_ERROR){
        ErrorDialog({viewModel.resetState()},"Hubo un problema para conectarse con el servidor")
        return
    }
    if (state.responseCode == 201) {
        navController.popBackStack()
        return
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

    var emailError = !(email.isNotBlank()  && pattern.matcher(email).matches())
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp), verticalArrangement = Arrangement.Center) {
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
            value=username,
            onValueChange = {if (it.length < 32) username=it},
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.headlineMedium,
            label = {Text("Nombre De Usuario")},
            isError = !username.isNotBlank(),

            supportingText = {Text(if (!username.isNotBlank()) CodeConsts.INVALID_USERNAME else "")}
        )
        OutlinedTextField(
            value=password,
            onValueChange = {if (it.length < 255) password=it},
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.headlineMedium,
            label = {Text("Contraseña")},
            isError = !password.isNotBlank(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Unspecified, autoCorrectEnabled = false,
            imeAction = ImeAction.Unspecified, keyboardType = KeyboardType.Password),
            supportingText = {Text(if (!password.isNotBlank()) CodeConsts.INVALID_PASSWORD else "")},
            visualTransformation = PasswordVisualTransformation()
        )



        Button(
            onClick = { viewModel.tryRegister(User(0,username,email,password)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !emailError || password.isNotBlank() || !username.isNotBlank()
        ) {Text("Crear Cuenta")}
        Text(loginString)
    }


}

