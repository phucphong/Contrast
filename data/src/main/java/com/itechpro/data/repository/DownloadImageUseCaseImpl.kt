package com.itechpro.data.repository



import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.itechpro.domain.usecase.dowloadFile.DownloadImageUseCase

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DownloadImageUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DownloadImageUseCase {

    override fun invoke(url: String, fileName: String) {
        try {
            val uri = Uri.parse(url)
            val request = DownloadManager.Request(uri).apply {
                setTitle("Tải ảnh...")
                setDescription("Đang tải $fileName")
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                setAllowedOverMetered(true)
                setAllowedOverRoaming(true)
            }

            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)

            Toast.makeText(context, "Đang tải ảnh...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Lỗi tải ảnh: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
