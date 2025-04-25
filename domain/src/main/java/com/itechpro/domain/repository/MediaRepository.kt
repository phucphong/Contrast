package com.itechpro.domain.repository



import android.content.Context
import android.net.Uri
import java.io.File

interface MediaRepository {
    suspend fun compressUri(context: Context, uri: Uri): File
}
