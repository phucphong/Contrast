package com.contrast.Contrast.extensions


import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object MultipartUtils {

    /**
     * Tạo MultipartBody.Part từ 1 file với tên field (partName)
     */
    fun prepareFilePart(partName: String, file: File): MultipartBody.Part {
        val mediaType = "multipart/form-data".toMediaTypeOrNull()
        val requestFile = file.asRequestBody(mediaType)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    /**
     * Tạo list MultipartBody.Part từ danh sách File
     */
    fun prepareMultiPartList(partName: String, files: List<File>): List<MultipartBody.Part> {
        return files.map { prepareFilePart(partName, it) }
    }
}
