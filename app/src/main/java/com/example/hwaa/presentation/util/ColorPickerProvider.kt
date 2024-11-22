package com.example.hwaa.presentation.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.palette.graphics.Palette
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class ColorPickerProvider private constructor() {

    companion object {
        @Volatile
        private var instance: ColorPickerProvider? = null

        fun getInstance(): ColorPickerProvider {
            return instance ?: synchronized(this) {
                instance ?: ColorPickerProvider().also { instance = it }
            }
        }
    }

    fun getMainColorFromImageUrl(imageUrl: String, callback: (Int?) -> Unit) {

        if (imageUrl.isEmpty()) {
            callback(null)
            return
        }

        val target = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                if (bitmap != null) {
                    Palette.from(bitmap).generate { palette ->
                        val dominantColor = palette?.dominantSwatch?.rgb
                        callback(dominantColor)
                    }
                } else {
                    callback(null)
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                callback(null)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }
        }

        Picasso.get().load(imageUrl).into(target)
    }
}