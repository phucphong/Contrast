package com.itechpro.domain.model

data class FileUpload(
    val name: String,
    val mimeType: String,
    val byteArray: ByteArray
)
