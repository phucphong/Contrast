package com.itechpro.domain.model.media

import android.net.Uri

data class CustomMedia(
    val localUri: Uri? = null,      // ảnh/video vừa chọn từ máy
    val remoteUrl: String? = null,  // ảnh/video đã có sẵn từ server
    val isVideo: Boolean = false,   // true = video, false = ảnh
    val durationMs: Long? = null    // nếu là video, có thể lưu thêm thời lượng (ms)
)
