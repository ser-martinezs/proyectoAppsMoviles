package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
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
import com.example.myapplication.ui.screens.LoadImageScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                App()
            }
        }

    }
}

@Composable
fun App(){
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
                HomeScreen(navController)
            }
            composable(Routes.PROFILE) {
                ProfileScreen()
            }
            composable(Routes.UPLOAD) {
                LoadImageScreen(navController)
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
            composable(
                route= Routes.POST,
                arguments = listOf(navArgument("img"){type= NavType.StringType})
            ){
                backStackEntry ->
                val rawFileStr : String= Uri.decode(backStackEntry.arguments?.getString("img")) ?: "wh"

                val fileId = rawFileStr.filter { !(it=='{' || it=='}') }.toUri()
                var image: Bitmap?= null

                Log.println(Log.INFO,"image",fileId.toString())

                LocalContext.current.contentResolver.openInputStream(fileId).let { inputStream -> image = BitmapFactory.decodeStream(inputStream) }

                if (image != null){
                    Image(
                        bitmap= image.asImageBitmap(), null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }


            }

        }
    }

}