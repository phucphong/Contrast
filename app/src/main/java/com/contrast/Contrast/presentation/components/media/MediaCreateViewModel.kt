package com.contrast.Contrast.presentation.components.media

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.usecase.media.MediaUrisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaCreateViewModel @Inject constructor(private val context: Context,
                                                 private val stringProvider: StringProvider, private val mediaUrisUseCase: MediaUrisUseCase) : ViewModel() {
    private val _selectedMedia = MutableStateFlow<List<Uri>>(emptyList())
    val selectedMedia: StateFlow<List<Uri>> = _selectedMedia
  private val _validateMessage = MutableStateFlow<String>("")
    val validateMessage: StateFlow<String> = _validateMessage
    private val _typeAttach = MutableStateFlow<String>("")
    val typeAttach: StateFlow<String> = _typeAttach
    private val _imageSelect = MutableStateFlow<Int>(0)
    val imageSelect: StateFlow<Int> = _imageSelect
    private val _videoSelect = MutableStateFlow<Int>(0)
    val videoSelect: StateFlow<Int> = _videoSelect

    fun setSelectedMedia(uris: List<Uri>) {
        _selectedMedia.value = uris
    }

    fun addSelectedMedia(newUris: List<Uri>) {
        _selectedMedia.value = (_selectedMedia.value + newUris).distinct()
    }


    fun removeMedia(uri: Uri) {
        _selectedMedia.update { list ->
            list.filterNot { it == uri }
        }
    }

    fun getTotalMediaSize(uris: List<Uri>) {
        viewModelScope.launch {
            val totalSize = mediaUrisUseCase.calculateTotalSize(uris)
            val totalMb = totalSize / (1024 * 1024)
            // update UI hiển thị
        }
    }
    fun checkMediaLimit(uris: List<Uri>,type:String) {
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

    fun typeAttach(type: String) {
        _typeAttach.value =type
    }
}
