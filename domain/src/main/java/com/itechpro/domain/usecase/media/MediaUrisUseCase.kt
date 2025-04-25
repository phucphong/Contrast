package com.itechpro.domain.usecase.media



import android.content.Context
import android.net.Uri
import com.itechpro.domain.repository.MediaRepository

import java.io.File
import javax.inject.Inject

class MediaUrisUseCase @Inject constructor(
    private val context: Context,
    private val mediaCompressorRepository: MediaRepository
) {
    suspend operator fun invoke(uris: List<Uri>): List<File> {
        return uris.mapNotNull { uri ->
            try {
                mediaCompressorRepository.compressUri(context, uri)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
