package com.example.hwaa.presentation.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.github.dhaval2404.imagepicker.ImagePicker

object ImagePickerProvider {
    fun pickImage(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
        crop: Boolean = true,
        compressSize: Int = 1024,
        maxWidth: Int = 1080,
        maxHeight: Int = 1080
    ) {
        val picker = ImagePicker.with(context as Activity)
        if (crop) picker.crop() // Enable cropping
        picker.compress(compressSize) // Set compression size in KB
            .maxResultSize(maxWidth, maxHeight) // Set max resolution
            .createIntent { intent ->
                launcher.launch(intent) // Start ImagePicker via the ActivityResultLauncher
            }
    }

    fun handleResult(
        result: ActivityResult,
        onImagePicked: (Uri?) -> Unit
    ) {
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val fileUri: Uri? = result.data?.data
                onImagePicked(fileUri)
            }
            ImagePicker.RESULT_ERROR -> {
                onImagePicked(null)
            }
            else -> {
                onImagePicked(null)
            }
        }
    }
}

