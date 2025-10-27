package com.example.myapplication.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.CodeConsts
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.User
import com.example.myapplication.data.service.RetroFitInstance
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception

data class PostUIState(
    val tempFile : Uri = Uri.EMPTY,
    val postBitmap : Bitmap?=null,
    val postTitle : String="",
    val postDesc : String="",
    val postResult:Int = CodeConsts.NOTHING
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

        _state.update { it.copy(tempFile=fileUri) }
        return fileUri
    }

    fun setBitmap(bitmap: Bitmap?){ _state.update { it.copy(postBitmap = bitmap) } }

    fun cleanupPost(){
        setBitmap(null)
        if (_state.value.tempFile == Uri.EMPTY) return
        val file = File(_state.value.tempFile.path!!)
        file.delete()

        _state.update { it.copy(tempFile = Uri.EMPTY) }


    }

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

    // TODO: fix this
    fun postImage(postData: Post){
        viewModelScope.launch {
            _state.update { it.copy(postResult = CodeConsts.LOADING) }
            val postImage = _state.value.postBitmap
            if (postImage == null) return@launch
            val bitmapStream = ByteArrayOutputStream()
            try {
                postImage.compress(Bitmap.CompressFormat.WEBP_LOSSLESS,0,bitmapStream)
            } catch (e: Exception){
                _state.update { it.copy(postResult = CodeConsts.IMAGE_ERROR) }
                bitmapStream.close()
                return@launch
            }

            val reqBody = bitmapStream.toByteArray().toRequestBody("image/webp".toMediaTypeOrNull())
            val imageFile: MultipartBody.Part = MultipartBody.Part.createFormData("file", "thisisgettingoverridenanywaysright", reqBody)

            val safecall = async { runCatching { RetroFitInstance.postApi.uploadPost(imageFile, postData) } }.await()

            if (!safecall.isSuccess) {
                _state.update { it.copy(postResult = CodeConsts.IMAGE_ERROR) }
                return@launch
            }
            _state.update { it.copy(postResult = safecall.getOrNull()!!.code())}

            bitmapStream.close()
        }


    }

    fun resetSendState(){
        _state.update { it.copy(postResult = CodeConsts.NOTHING) }
    }




}