package com.itechpro.domain.usecase.media



import android.content.Context
import android.net.Uri
import android.util.Log
import com.itechpro.domain.model.CheckMediaResult
import com.itechpro.domain.repository.MediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    /**
     * Tính tổng dung lượng (bytes) của danh sách Uri file trước khi nén.
     *
     * @param uris Danh sách các Uri ảnh/video.
     * @return Tổng dung lượng các file, đơn vị bytes.
     */
    suspend fun calculateTotalSize(uris: List<Uri>): Long = withContext(Dispatchers.IO) {
        val resolver = context.contentResolver
        var totalSize = 0L

        for (uri in uris) {
            try {
                resolver.query(
                    uri,
                    arrayOf(android.provider.OpenableColumns.SIZE),
                    null,
                    null,
                    null
                )?.use { cursor ->
                    val sizeIndex = cursor.getColumnIndexOrThrow(android.provider.OpenableColumns.SIZE)
                    if (cursor.moveToFirst()) {
                        val size = cursor.getLong(sizeIndex)
                        totalSize += size
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Bỏ qua file lỗi
            }
        }

        totalSize
    }


    /**
     * Kiểm tra số lượng ảnh/video theo giới hạn cho phép.
     *
     * @param listData Danh sách ImageVideo (model có type: "image/jpeg" hoặc "video/mp4"...)
     * @param type Kiểm tra theo loại: "image" hoặc "video"
     * @return validateMessage + số lượng ảnh, video
     */
    suspend fun checkMediaLimit(
        context: Context,
        uris: List<Uri>,
        type: String,
    ): CheckMediaResult {
        var validate = ""
        var imageCount = 0
        var imageSelect = 0
        var videoCount = 0
        var videoSelect = 0

        val resolver = context.contentResolver

        for (uri in uris) {
            try {
                val mimeType = resolver.getType(uri).orEmpty()

                if (mimeType.startsWith("image")) {
                    imageCount++
                } else if (mimeType.startsWith("video")) {
                    videoCount++
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Nếu lỗi khi lấy mimeType thì bỏ qua
            }
        }

        if (type == "image" && imageCount >= 5) {
            validate = "5"
        }
        imageSelect = 5-imageCount
        videoSelect = 1-videoCount

        if (type == "video" && videoCount >=1) {
            validate = "1"
        }
Log.e("imageCount","$imageCount $videoCount")
        return CheckMediaResult(
            validateMessage = validate,
            imageSelect = imageSelect,
            videoSelect = videoSelect,
        )
    }

}
