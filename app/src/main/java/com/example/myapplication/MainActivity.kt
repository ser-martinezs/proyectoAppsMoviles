package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.data.service.RetroFitInstance
import com.example.myapplication.navigation.BottomBar
import com.example.myapplication.navigation.BottomNavItem
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.screens.FuckingAroundScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.LoadImageScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.PostingScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.ui.screens.RegisterScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewmodel.PostViewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.example.myapplication.comoponents.LoginResquestMessage
import com.example.myapplication.data.model.Post
import com.example.myapplication.ui.viewmodel.PostReadViewModel
import kotlinx.coroutines.launch

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
fun App() {

    val postViewModel: PostViewModel = viewModel()
    val imageViewModel : PostReadViewModel = viewModel()
    val navController = rememberNavController()
    val bottomItems = listOf(BottomNavItem.Home, BottomNavItem.Upload, BottomNavItem.Profile)

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
            composable(
                route = Routes.PROFILE,
                arguments = listOf(navArgument("id") { nullable = true })
            ) { backStackEntry ->
                postViewModel.setBitmap(null)
                val profileID: Long? = (backStackEntry.arguments?.getString("id"))?.toLong()
                if (profileID == null) {
                    /*
                    TODO: change to current logged used ID
                    If no user is logged then thell them to log in
                    */
                }

                Text("$profileID")
                ProfileScreen(navController)
            }
            composable(Routes.UPLOAD) {
                // TODO: make this check be something that tells us "hey is there someone logged in"
                if (false) {
                    LoginResquestMessage(navController)
                    return@composable
                }

                LoadImageScreen(navController, postViewModel)
            }

            composable(
                route = Routes.IMAGE,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { backStackEntry ->
                postViewModel.setBitmap(null)
                val id = backStackEntry.arguments?.getLong("id") ?: -1
                imageViewModel.fetchPost(id)
                FuckingAroundScreen(id,imageViewModel);


            }
            composable(route = Routes.POST) {
                PostingScreen(postViewModel)
            }
            composable(Routes.LOGIN) {
                postViewModel.setBitmap(null)
                LoginScreen(navController)
            }
            composable(Routes.REGISTER) {
                postViewModel.setBitmap(null)
                RegisterScreen(navController)
            }

        }
    }

}