package com.example.myapplication.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(onclick : Function0<Unit>){

    Button(onClick = onclick) { Text("press for image") }
}