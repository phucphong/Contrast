package com.itechpro.data.repository


import android.content.Context
import android.net.Uri

import com.itechpro.domain.repository.MediaRepository
import java.io.File
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor() : MediaRepository {
    override suspend fun compressUri(context: Context, uri: Uri): File {
        return MediaCompressor.compressMedia(context, uri)
    }
}
