package com.example.myapplication.ui.screens

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.myapplication.BuildConfig
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.viewmodel.PostViewModel
import java.io.File




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadImageScreen(navController: NavController,viewModel: PostViewModel) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    //val permissionLauncherCamera = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {}

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            if (it) {
                imageUri?.let { uri ->
                    context.contentResolver.openInputStream(uri).let { inputStream ->
                        val transform : Matrix= Matrix();
                        transform.postRotate(90f)
                        val img = BitmapFactory.decodeStream(inputStream)
                        viewModel.setBitmap(Bitmap.createBitmap(img,0,0,img.width,img.height,transform,true))
                        navController.navigate(Routes.postRoute(uri))

                    }
                }
            }
        }
    )
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { uri ->
                context.contentResolver.openInputStream(uri).let {
                    inputStream ->
                    viewModel.setBitmap(BitmapFactory.decodeStream(inputStream))
                    navController.navigate(Routes.postRoute(uri))
                }
            }
        }
    )
    val permissionLauncherCamera = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
        if (it){
            imageUri = viewModel.createTempImage(context)
            cameraLauncher.launch(imageUri!!)
        }
        else Toast.makeText(context, "se requieren los permisos de camara para poder usarla", Toast.LENGTH_SHORT).show()

    }
    val permissionLauncherGallery = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
        if (it){ galleryLauncher.launch("image/*") }
        else Toast.makeText(context, "se requieren los permisos de galeria para poder usarla", Toast.LENGTH_SHORT).show()
    }


    if (state.postBitmap != null){
        Image(
            bitmap=state.postBitmap!!.asImageBitmap(), null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally),
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Bottom
    ) {

        Button(onClick = { permissionLauncherCamera.launch(Manifest.permission.CAMERA)}) { Text("Sacar Foto") }
        Button(onClick = { permissionLauncherGallery.launch(Manifest.permission.READ_MEDIA_IMAGES)}) { Text("Cargar Foto") }
    }









}

