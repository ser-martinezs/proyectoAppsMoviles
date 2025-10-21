package com.example.myapplication.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.repository.ImageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.File

data class PostUIState(
    val tempFiles : Set<Uri> = mutableSetOf(),
    val postBitmap : Bitmap?=null,
    val postTitle : String="",
    val postDesc : String=""


)

class PostViewModel() :ViewModel(){
    private val _state = MutableStateFlow(PostUIState())
    val state : StateFlow<PostUIState> = _state


    fun createTempImage(context: Context): Uri {
        val imageFileName = "${System.currentTimeMillis()}"
        val tmpFile =
            File.createTempFile(imageFileName, ".jpeg", context.cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }

        val fileUri = FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            tmpFile
        )
        val files = state.value.tempFiles.toMutableSet()
        files.add(fileUri);
        _state.update { it.copy(tempFiles=files) }
        return fileUri
    }
    fun setBitmap(bitmap: Bitmap?){ _state.update { it.copy(postBitmap = bitmap) } }







}