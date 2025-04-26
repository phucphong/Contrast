package com.contrast.Contrast.presentation.components.media

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

// Một thư viện Compose cho Android 14+ cho phép chọn ảnh, video hoặc cả hai bằng Photo Picker API

@HiltViewModel
class MediaPickerViewModel @Inject constructor() : ViewModel() {
    private val _mediaUris = MutableStateFlow<List<Uri>>(emptyList())
    val mediaUris: StateFlow<List<Uri>> get() = _mediaUris

    fun addUris(uris: List<Uri>, maxCount: Int) {
        val remaining = maxCount - _mediaUris.value.size
        if (remaining > 0) {
            _mediaUris.update { it + uris.take(remaining) }
        }
    }

    fun removeUri(uri: Uri) {
        _mediaUris.update { it - uri }
    }

    fun clearAll() {
        _mediaUris.value = emptyList()
    }
}

@Composable
fun rememberPhotoPickerLauncher(
    maxCount: Int = 6,
    allowImage: Boolean = true,
    allowVideo: Boolean = true,
    onUrisPicked: (List<Uri>) -> Unit
): () -> Unit {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
        onUrisPicked(uris.take(maxCount))
    }

    return {
        val mediaType = when {
            allowImage && allowVideo -> ActivityResultContracts.PickVisualMedia.ImageAndVideo
            allowImage -> ActivityResultContracts.PickVisualMedia.ImageOnly
            allowVideo -> ActivityResultContracts.PickVisualMedia.VideoOnly
            else -> ActivityResultContracts.PickVisualMedia.ImageAndVideo // fallback
        }
        launcher.launch(PickVisualMediaRequest(mediaType))
    }
}