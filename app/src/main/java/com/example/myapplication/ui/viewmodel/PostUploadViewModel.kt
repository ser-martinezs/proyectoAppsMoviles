package com.example.myapplication.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.example.myapplication.BuildConfig
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
        files.add(fileUri)
        _state.update { it.copy(tempFiles=files) }
        return fileUri
    }

    fun setBitmap(bitmap: Bitmap?){ _state.update { it.copy(postBitmap = bitmap) } }

    fun setPostTitle(title: String) : Boolean{
         if (title.length > 32) return false
        _state.update { it.copy(postTitle = title) }
        return true
    }

    fun setPostDescription(desc: String) : Boolean{
        if (desc.length > 512) return false
        _state.update { it.copy(postDesc = desc) }
        return true
    }








}