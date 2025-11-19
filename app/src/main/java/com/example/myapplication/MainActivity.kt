package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.navigation.BottomBar
import com.example.myapplication.navigation.BottomNavItem
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.screens.networked.PostDisplayScreen
import com.example.myapplication.ui.screens.networked.HomeScreen
import com.example.myapplication.ui.screens.LoadImageScreen
import com.example.myapplication.ui.screens.networked.LoginScreen
import com.example.myapplication.ui.screens.networked.PostingScreen
import com.example.myapplication.ui.screens.networked.ProfileScreen
import com.example.myapplication.ui.screens.RegisterScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewmodel.PostViewModel


import com.example.myapplication.comoponents.LoginResquestMessage
import com.example.myapplication.data.local.CredentialRepository
import com.example.myapplication.ui.viewmodel.HomeScreenViewModel
import com.example.myapplication.ui.viewmodel.PostReadViewModel
import com.example.myapplication.ui.viewmodel.ProfileViewModel
import com.example.myapplication.ui.viewmodel.UserViewModel
import com.example.myapplication.ui.viewmodel.UserViewModelFactory
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import kotlin.math.log


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
    override fun onDestroy(){
        super.onDestroy()
        // does this only kill images or are databases and shit stored there too for some reason
            val images = baseContext.cacheDir.listFiles()
            if (images == null) return

            for (image in images){
                try{ image.delete() }
                catch (e: Exception){}

            }
    }
}


@Composable
fun App(
    repo :CredentialRepository=CredentialRepository(LocalContext.current),
    homeScreenViewModel : HomeScreenViewModel = viewModel(),
    postViewModel: PostViewModel = viewModel(),
    userViewModel : UserViewModel = viewModel(factory = UserViewModelFactory(repo))
) {

    val navController = rememberNavController()
    val bottomItems = listOf(BottomNavItem.Home, BottomNavItem.Upload, BottomNavItem.Profile)
    val loginState by userViewModel.state.collectAsState()
    //credentialRepo.dataFlow.collect()




    Scaffold(
        bottomBar = { BottomBar(navController, bottomItems,{a,b ->}) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            // TODO: reload home on posting / let users #reload manually
            composable(Routes.HOME) {
                HomeScreen(navController,homeScreenViewModel)
            }

            composable(
                route = Routes.PROFILE,
                arguments = listOf(navArgument("id") { nullable = true })
            ) { backStackEntry ->
                // TODO: avoid crashing when bad id
                postViewModel.setBitmap(null)
                var profileID: Long? = null
                try { profileID = (backStackEntry.arguments?.getString("id"))?.toLong() }
                catch (e: Exception){profileID=null}

                if (profileID == null) {
                    if (loginState.user == null) {
                        LoginResquestMessage(navController)
                        return@composable
                    }
                    profileID = loginState.user!!.userID
                }
                ProfileScreen(navController, userID = profileID)
            }

            composable(Routes.UPLOAD) {

                if (loginState.user == null) {
                    LoginResquestMessage(navController)
                    return@composable
                }
                LoadImageScreen(navController, postViewModel)
            }

            composable(
                route = Routes.IMAGE,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) {
                backStackEntry ->
                postViewModel.setBitmap(null)
                val id = backStackEntry.arguments?.getLong("id") ?: -1
                PostDisplayScreen(id,navController);

            }
            composable(route = Routes.POST) {
                if (loginState.user == null) {
                    LoginResquestMessage(navController)
                    return@composable
                }

                PostingScreen(postViewModel,loginState.user!!,navController)
            }
            composable(Routes.LOGIN) {
                postViewModel.setBitmap(null)
                LoginScreen(navController,userViewModel)
            }
            composable(Routes.REGISTER) {
                postViewModel.setBitmap(null)
                RegisterScreen(navController,userViewModel)
            }
        }
    }

}