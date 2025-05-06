package com.itechpro.data.repository

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.itechpro.domain.usecase.dowloadFile.DownloadUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL
import javax.inject.Inject

class DownloadUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DownloadUseCase {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun invoke(url: String, fileName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val resolver = context.contentResolver

                val extension = fileName.substringAfterLast('.', "")
                val mimeType = MimeTypeMap.getSingleton()
                    .getMimeTypeFromExtension(extension.lowercase()) ?: "application/octet-stream"

                // Xác định collection và relative path (thư mục EZMAX)
                val (collection, relativePath) = when (extension.lowercase()) {
                    "jpg", "jpeg", "png", "gif", "webp" ->
                        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY) to "Pictures/EZMAX"

                    "mp4", "mkv", "3gp", "webm" ->
                        MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY) to "Movies/EZMAX"

                    "mp3", "wav", "m4a", "aac" ->
                        MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY) to "Music/EZMAX"

                    else ->
                        MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY) to "Download/EZMAX"
                }

                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                    put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }

                val itemUri: Uri? = resolver.insert(collection, contentValues)

                if (itemUri != null) {
                    resolver.openOutputStream(itemUri).use { outputStream ->
                        val inputStream: InputStream = URL(url).openStream()
                        inputStream.copyTo(outputStream!!)
                        inputStream.close()
                    }

                    contentValues.clear()
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(itemUri, contentValues, null, null)

                    launch(Dispatchers.Main) {
                        Toast.makeText(context, "Đã lưu vào $relativePath/$fileName", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    launch(Dispatchers.Main) {
                        Toast.makeText(context, "Không thể tạo file tải về", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    Toast.makeText(context, "Lỗi tải file: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
