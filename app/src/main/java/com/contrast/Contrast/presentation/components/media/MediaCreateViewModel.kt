package com.contrast.Contrast.presentation.components.media

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatSizeInMB
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.FileUpload
import com.itechpro.domain.model.media.CustomMedia
import com.itechpro.domain.usecase.media.MediaUrisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class MediaCreateViewModel @Inject constructor(private val context: Context,
                                                 private val stringProvider: StringProvider, private val mediaUrisUseCase: MediaUrisUseCase) : ViewModel() {
    private val _selectedMedia = MutableStateFlow<List<CustomMedia>>(emptyList())
    val selectedMedia: StateFlow<List<CustomMedia>> = _selectedMedia

        private val _fileUploads = MutableStateFlow<List<CustomMedia>>(emptyList())
    val fileUploads: StateFlow<List<CustomMedia>> = _fileUploads


  private val _validateMessage = MutableStateFlow<String>("")
    val validateMessage: StateFlow<String> = _validateMessage
    private val _imageSelect = MutableStateFlow<Int>(0)
    val imageSelect: StateFlow<Int> = _imageSelect
    private val _videoSelect = MutableStateFlow<Int>(0)
    val videoSelect: StateFlow<Int> = _videoSelect
    private val _totalSizeInMB = MutableStateFlow<Double>(0.0)
    val totalSizeInMB: StateFlow<Double> = _totalSizeInMB
    private val _fileUploadList = MutableStateFlow<List<MultipartBody.Part>>(emptyList())
    val fileUploadList: StateFlow<List<MultipartBody.Part>> = _fileUploadList

    private val _notificationToast = MutableSharedFlow<String>()
    val notificationToast = _notificationToast.asSharedFlow()

    suspend fun showNotificationToast(message: String) {
        _notificationToast.emit(message)
    }

    fun setSelectedMedia(uris: List<CustomMedia>) {
        _selectedMedia.value = uris
    }

    fun addSelectedMedia(newUris: List<CustomMedia>) {
        _selectedMedia.value = (_selectedMedia.value + newUris).distinct()
    }


    fun removeMedia(uri: CustomMedia) {
        _selectedMedia.update { list ->
            list.filterNot { it == uri }
        }
    }

    fun getTotalMediaSize(uris: List<CustomMedia>) {
        viewModelScope.launch {
            val totalSize = mediaUrisUseCase.calculateTotalSize(context,uris)
            _totalSizeInMB.value = formatSizeInMB(totalSize)

            if (_totalSizeInMB.value > 25) {
                showNotificationToast(stringProvider.getString(R.string.total_file_25))
            }else{
                uriListToFileUploadList(uris)
            }
        }
    }
    fun checkMediaLimit(uris: List<Uri?>, type:String) {
        viewModelScope.launch {
            val result = mediaUrisUseCase.checkMediaLimit(context, uris, type)
            _validateMessage.value = result.validateMessage
            _imageSelect.value = result.imageSelect
            _videoSelect.value = result.videoSelect

        }

    }

    fun clearValidationError() {
        _validateMessage.value = ""
    }


    fun uriListToFileUploadList(uris: List<CustomMedia>) {

        _fileUploadList.value = uriListToMultipartBodyParts(context,uris)
    }


    fun uriListToMultipartBodyParts(
        context: Context,
        mediaList: List<CustomMedia>,
        partName: String = "files"
    ): List<MultipartBody.Part> {
        val parts = mutableListOf<MultipartBody.Part>()
        val contentResolver = context.contentResolver

        mediaList.forEachIndexed { index, media ->
            try {
                val inputStream = contentResolver.openInputStream(media.uri) ?: return@forEachIndexed
                val byteArray = inputStream.readBytes()
                inputStream.close()

                val mimeType = contentResolver.getType(media.uri) ?: "application/octet-stream"
                val fileName = context.getFileNameFromUri(media.uri)

                val requestBody = byteArray.toRequestBody(mimeType.toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData(
                    "$partName[$index]",  // Ví dụ: files[0], files[1]
                    fileName,
                    requestBody
                )
                parts.add(part)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return parts
    }


    fun Context.getFileNameFromUri(uri: Uri): String {
        var name = "file"
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex != -1) {
                name = cursor.getString(nameIndex)
            }
        }
        return name
    }


}
