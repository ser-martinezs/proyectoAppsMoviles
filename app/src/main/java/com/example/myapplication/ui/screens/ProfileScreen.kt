package com.example.myapplication.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.comoponents.LoginResquestMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController){
    LoginResquestMessage(navController)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {

    val navController = rememberNavController()

    ProfileScreen(navController = navController)
}