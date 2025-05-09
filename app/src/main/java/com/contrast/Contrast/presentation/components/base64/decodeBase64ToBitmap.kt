package com.contrast.Contrast.presentation.components.base64

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun decodeBase64ToBitmap(base64: String): ImageBitmap? {
    return try {
        val pureBase64 = base64.substringAfter(",")
        val decodedBytes = Base64.decode(pureBase64, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        null
    }
}