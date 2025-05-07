package com.itechpro.domain.usecase.dowloadFile

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.util.Base64

interface DownloadUseCase {
    operator fun invoke(url: String, fileName: String)

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveBase64ImageToGallery(
        context: Context,
        base64: String,
        filename: String = "image_${System.currentTimeMillis()}.jpg"
    ): Boolean {
        return try {
            val pureBase64 = base64.substringAfter(",")
            val decodedBytes = Base64.getDecoder().decode(pureBase64)

            val resolver = context.contentResolver
            val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
            }

            val imageUri = resolver.insert(imageCollection, contentValues) ?: return false

            resolver.openOutputStream(imageUri).use { outputStream ->
                outputStream?.write(decodedBytes)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(imageUri, contentValues, null, null)
            }

            true
        } catch (e: Exception) {
            false
        }
    }
}