package com.contrast.Contrast.presentation.components.media

import android.app.Application
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.itechpro.domain.model.FileUpload
import com.itechpro.domain.model.media.CustomMedia
import com.itechpro.domain.model.media.MediaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class MediaPickerViewModelNew @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {

    private val pageSize = 18
    private var currentOffset = 0
    private var isLoading = false

    private val _mediaItems = MutableStateFlow<List<MediaItem>>(emptyList())
    val mediaItems: StateFlow<List<MediaItem>> = _mediaItems

    private val _selectedMedia = MutableStateFlow<List<CustomMedia>>(emptyList())
    val selectedMedia: StateFlow<List<CustomMedia>> = _selectedMedia

    private var allowImage = true
    private var allowVideo = true
    private var currentCameraFile: File? = null

    fun getCurrentCameraFile(): File? = currentCameraFile

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
        val context = getApplication<Application>().applicationContext
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
                    Log.e("uriMediaItem",uri.toString())
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
                    var duration = it.getLong(durCol)
                    val date = it.getLong(dateCol)
                    val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)

                    // Nếu duration từ MediaStore bằng 0, fallback dùng MediaMetadataRetriever
                    if (duration <= 0) {
                        duration = try {
                            val retriever = android.media.MediaMetadataRetriever()
                            retriever.setDataSource(context, uri)
                            val time = retriever.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION)
                            retriever.release()
                            time?.toLong() ?: 0
                        } catch (e: Exception) {
                            0
                        }
                    }

                    items.add(MediaItem(uri = uri, isVideo = true, durationMs = duration, dateAdded = date))
                }
            }
        }

        return items.sortedByDescending { it.dateAdded }
    }

    fun toggleSelect(mediaItem: MediaItem) {
        val customMedia = CustomMedia(
            uri = mediaItem.uri,
            isVideo = mediaItem.isVideo,
            durationMs = mediaItem.durationMs
        )

        _selectedMedia.update { currentList ->
            if (currentList.any { it.uri == customMedia.uri }) {
                currentList.filterNot { it.uri == customMedia.uri }
            } else {
                currentList + customMedia
            }
        }
    }

    fun addLimitedUris(context: Context, uris: List<Uri>) {
        val resolver = context.contentResolver

        val newItems = uris.map { uri ->
            val mimeType = resolver.getType(uri) ?: ""
            val isVideo = mimeType.startsWith("video/")
            val duration = if (isVideo) {
                getDurationFromUri(context, uri)
            } else 0L

            MediaItem(
                uri = uri,
                isVideo = isVideo,
                durationMs = duration,
                dateAdded = System.currentTimeMillis()
            )
        }

        _mediaItems.update { current -> (current + newItems).distinctBy { it.uri } }
    }



    fun getDurationFromUri(context: Context, uri: Uri): Long {
        val resolver = context.contentResolver
        val cursor = resolver.query(uri, arrayOf(MediaStore.Video.Media.DURATION), null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val durIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                return it.getLong(durIndex)
            }
        }
        return 0L
    }


    fun scanFileToMediaStore(context: Context, file: File, onScanned: () -> Unit) {
        val mimeType = if (file.name.endsWith(".mp4")) "video/mp4" else "image/jpeg"
        MediaScannerConnection.scanFile(
            context,
            arrayOf(file.absolutePath),
            arrayOf(mimeType)
        ) { _, _ -> onScanned() }
    }



//    fun openCameraIntent(context: Context): Pair<Uri, Intent>? {
//        val photoFile = createImageFile(context)
//        currentCameraFile = photoFile // ✅ lưu file thật
//
//        val authority = context.packageName + ".provider"
//        val uri = FileProvider.getUriForFile(context, authority, photoFile)
//
//
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
//            putExtra(MediaStore.EXTRA_OUTPUT, uri)
//            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//        }
//        return Pair(uri, intent)
//    }


    fun createImageUri(context: Context): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/EZMAX")
        }

        return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }


    fun createVideoUri(context: Context): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, "VID_${System.currentTimeMillis()}.mp4")
            put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            put(MediaStore.Video.Media.RELATIVE_PATH, "DCIM/EZMAX")
        }

        return context.contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)
    }


    fun openCameraIntent(context: Context, isVideo: Boolean): Pair<Uri, Intent>? {
        Log.e("isVideo",isVideo.toString())
        var uri :Uri?
        if(isVideo){
            uri = createVideoUri(context)
        }else{
            uri = createImageUri(context)
        }

        val intent = if (isVideo) {
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
        } else {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
        }

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        return Pair(uri!!, intent)
    }

    fun openCameraVideoIntent(context: Context, isVideo: Boolean): Pair<Uri, Intent>? {
        Log.e("isVideo",isVideo.toString())
        var uri :Uri?
        if(isVideo){
            uri = createVideoUri(context)
        }else{
            uri = createImageUri(context)
        }

        val intent = if (isVideo) {
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
        } else {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
        }

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        return Pair(uri!!, intent)
    }


}
