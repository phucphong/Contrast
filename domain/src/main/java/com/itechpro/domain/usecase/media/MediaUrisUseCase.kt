package com.itechpro.domain.usecase.media



import android.content.Context
import android.net.Uri
import android.util.Log
import com.itechpro.domain.model.CheckMediaResult
import com.itechpro.domain.model.media.CustomMedia
import com.itechpro.domain.repository.MediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import java.io.File
import java.net.HttpURLConnection
import java.net.URL
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

    /**
     * Tính tổng dung lượng của danh sách CustomMedia (local + remote)
     */
    suspend fun calculateTotalSize(context: Context, medias: List<CustomMedia>): Long = withContext(Dispatchers.IO) {
        val resolver = context.contentResolver
        var totalSize = 0L

        for (media in medias) {
            try {
                // Nếu có uri local thì lấy size từ máy
                media.uri?.let { uri ->
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
                }

                // Nếu có remoteUrl thì lấy size từ server
                media.remoteUrl?.let { url ->
                    val remoteSize = getRemoteFileSize(url)
                    totalSize += remoteSize
                }

            } catch (e: Exception) {
                e.printStackTrace()
                // Bỏ qua nếu lỗi
            }
        }

        totalSize
    }

    /**
     * Lấy kích thước file từ remote server (bytes) bằng HEAD request
     */
    private suspend fun getRemoteFileSize(remoteUrl: String): Long = withContext(Dispatchers.IO) {
        try {
            val url = URL(remoteUrl)
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "HEAD"
                connectTimeout = 5000
                readTimeout = 5000
            }
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                connection.getHeaderFieldLong("Content-Length", 0L)
            } else {
                0L
            }
        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }
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
        uris: List<Uri?>,
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
                val mimeType = uri?.let { resolver.getType(it).orEmpty() }

                if (mimeType != null) {
                    if (mimeType.startsWith("image")) {
                        imageCount++
                    } else if (mimeType.startsWith("video")) {
                        videoCount++
                    }
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
