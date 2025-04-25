package com.itechpro.data.repository



import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.ReturnCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

object MediaCompressor {

    suspend fun compressMedia(context: Context, uri: Uri): File {
        val mimeType = context.contentResolver.getType(uri) ?: ""

        return when {
            mimeType.startsWith("image/") -> compressImage(context, uri)
            mimeType.startsWith("video/") -> compressVideo(context, uri)
            else -> copyFile(context, uri)
        }
    }

    private suspend fun compressImage(context: Context, uri: Uri): File {
        val originalFile = copyFile(context, uri)
        return Compressor.compress(context, originalFile) {
            default(width = 1080) // resize về chiều rộng tối đa 1080px
        }
    }

    private suspend fun compressVideo(context: Context, uri: Uri): File {
        val inputFile = copyFile(context, uri)
        val outputFile = File(context.cacheDir, "compressed_video_${System.currentTimeMillis()}.mp4")

        val command = "-i ${inputFile.absolutePath} -vcodec libx264 -crf 28 ${outputFile.absolutePath}"

        return withContext(Dispatchers.IO) {
            val session = FFmpegKit.execute(command)
            if (ReturnCode.isSuccess(session.returnCode)) {
                outputFile
            } else {
                throw RuntimeException("Video compression failed: ${session.failStackTrace}")
            }
        }
    }

    private fun copyFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Cannot open input stream from URI: $uri")

        val fileName = getFileName(context, uri) ?: "temp_file_${System.currentTimeMillis()}"
        val tempFile = File(context.cacheDir, fileName)

        FileOutputStream(tempFile).use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        return tempFile
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        var name: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                name = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
        return name
    }
}
