package com.example.myapplication

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.navigation.BottomBar
import com.example.myapplication.navigation.BottomNavItem
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.screens.FuckingAroundScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.ui.screens.UploadScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                App(baseContext)
            }
        }

    }
}

@Composable
fun App(context: Context){
    val navController = rememberNavController()
    val bottomItems = listOf(BottomNavItem.Home,BottomNavItem.Upload,BottomNavItem.Profile)

    Scaffold(
        bottomBar = { BottomBar(navController, bottomItems) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                Log.println(Log.WARN,"sda",Routes.imageRoute(R.drawable.uni))
                HomeScreen(navController)
            }
            composable(Routes.PROFILE) {
                ProfileScreen()
            }
            composable(Routes.UPLOAD) {
                UploadScreen(context = context)
            }
            composable(
                route = Routes.IMAGE,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: -1
                FuckingAroundScreen(id);

                //val vm: MainViewModel = viewModel()
                //DetailScreen(itemId = id, viewModel = vm, onBack = { navController.popBackStack() })
            }

        }
    }

}