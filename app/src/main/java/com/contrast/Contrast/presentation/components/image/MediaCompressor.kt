package com.contrast.Contrast.presentation.components.image

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.ReturnCode
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import java.io.File
import java.io.IOException

object MediaCompressor {

    /**
     * Tự động nén ảnh hoặc video từ Uri, trả về File đã nén
     */
    suspend fun compressMedia(context: Context, uri: Uri): File {
        val type = context.contentResolver.getType(uri)
        return when {
            type?.startsWith("image") == true -> compressImage(context, uri)
            type?.startsWith("video") == true -> compressVideo(context, uri)
            else -> throw IllegalArgumentException("Unsupported media type: $type")
        }
    }

    private suspend fun compressImage(context: Context, uri: Uri): File {
        val inputFile = uri.toFile(context)
        return Compressor.compress(context, inputFile) {
            resolution(1280, 720) // Resize
            quality(75) // Quality (0–100)
            format(Bitmap.CompressFormat.JPEG)
        }
    }

    private suspend fun compressVideo(context: Context, uri: Uri): File {
        val inputFile = uri.toFile(context)
        val outputFile = File(context.cacheDir, "compressed_${System.currentTimeMillis()}.mp4")

        val cmd = "-y -i ${inputFile.absolutePath} -vcodec libx264 -crf 28 -preset veryfast -acodec aac ${outputFile.absolutePath}"
        val session = FFmpegKit.execute(cmd)

        if (ReturnCode.isSuccess(session.returnCode)) {
            return outputFile
        } else {
            throw IOException("Video compression failed: ${session.failStackTrace}")
        }
    }

    private fun Uri.toFile(context: Context): File {
        val inputStream = context.contentResolver.openInputStream(this)
            ?: throw IOException("Unable to open URI: $this")
        val outputFile = File(context.cacheDir, "input_${System.currentTimeMillis()}")
        outputFile.outputStream().use { inputStream.copyTo(it) }
        return outputFile
    }
}
