package com.itechpro.domain.model.media

import android.net.Uri

// Data model load từ MediaStore
data class MediaItem(
    val uri: Uri,
    val isVideo: Boolean,
    val durationMs: Long = 0L,
    val dateAdded: Long = 0L
)