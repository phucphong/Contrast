package com.contrast.Contrast.presentation.components.image

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MediaItem(
    val uri: Uri,
    val isVideo: Boolean,
    val durationMs: Long = 0L,
    val dateAdded: Long = 0L
)

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class MediaPickerViewModelNew @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val pageSize = 18
    private var currentOffset = 0
    private var isLoading = false

    private val _mediaItems = MutableStateFlow<List<MediaItem>>(emptyList())
    val mediaItems: StateFlow<List<MediaItem>> = _mediaItems

    private val _selectedUris = MutableStateFlow<Set<Uri>>(emptySet())
    val selectedUris: StateFlow<Set<Uri>> = _selectedUris

    private var allowImage = true
    private var allowVideo = true

    fun loadMedia(image: Boolean, video: Boolean) {
        allowImage = image
        allowVideo = video
        _mediaItems.value = emptyList()
        currentOffset = 0
        loadMore()
    }

    fun loadMore() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            val items = loadPage(offset = currentOffset, limit = pageSize)
            _mediaItems.update { current -> (current + items).distinctBy { it.uri } }
            currentOffset += pageSize
            isLoading = false
        }
    }

    private fun loadPage(offset: Int, limit: Int): List<MediaItem> {
        val resolver = getApplication<Application>().contentResolver
        val items = mutableListOf<MediaItem>()

        if (allowImage) {
            val queryArgs = Bundle().apply {
                putInt(android.content.ContentResolver.QUERY_ARG_LIMIT, limit)
                putInt(android.content.ContentResolver.QUERY_ARG_OFFSET, offset)
                putString(
                    android.content.ContentResolver.QUERY_ARG_SORT_COLUMNS,
                    MediaStore.Images.Media.DATE_ADDED
                )
                putInt(
                    android.content.ContentResolver.QUERY_ARG_SORT_DIRECTION,
                    android.content.ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
                )
            }

            val cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_ADDED),
                queryArgs,
                null
            )
            cursor?.use {
                val idCol = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val dateCol = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
                while (it.moveToNext()) {
                    val id = it.getLong(idCol)
                    val date = it.getLong(dateCol)
                    val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    items.add(MediaItem(uri = uri, isVideo = false, dateAdded = date))
                }
            }
        }

        if (allowVideo) {
            val queryArgs = Bundle().apply {
                putInt(android.content.ContentResolver.QUERY_ARG_LIMIT, limit)
                putInt(android.content.ContentResolver.QUERY_ARG_OFFSET, offset)
                putString(
                    android.content.ContentResolver.QUERY_ARG_SORT_COLUMNS,
                    MediaStore.Video.Media.DATE_ADDED
                )
                putInt(
                    android.content.ContentResolver.QUERY_ARG_SORT_DIRECTION,
                    android.content.ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
                )
            }

            val cursor = resolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.DATE_ADDED
                ),
                queryArgs,
                null
            )
            cursor?.use {
                val idCol = it.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val durCol = it.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                val dateCol = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)
                while (it.moveToNext()) {
                    val id = it.getLong(idCol)
                    val duration = it.getLong(durCol)
                    val date = it.getLong(dateCol)
                    val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                    items.add(MediaItem(uri = uri, isVideo = true, durationMs = duration, dateAdded = date))
                }
            }
        }

        return items.sortedByDescending { it.dateAdded }
    }

    fun toggleSelect(uri: Uri) {
        _selectedUris.update {
            if (it.contains(uri)) it - uri else it + uri
        }
    }

    fun addLimitedUris(uris: List<Uri>) {
        val newItems = uris.map { uri ->
            MediaItem(uri = uri, isVideo = false, dateAdded = System.currentTimeMillis())
        }
        _mediaItems.update { current -> (current + newItems).distinctBy { it.uri } }
    }
}
