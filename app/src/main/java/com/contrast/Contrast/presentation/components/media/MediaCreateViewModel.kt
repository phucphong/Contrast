package com.contrast.Contrast.presentation.components.media

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.extensions.formatSizeInMB
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.media.CustomMedia
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
    private val _selectedMedia = MutableStateFlow<List<CustomMedia>>(emptyList())
    val selectedMedia: StateFlow<List<CustomMedia>> = _selectedMedia
  private val _validateMessage = MutableStateFlow<String>("")
    val validateMessage: StateFlow<String> = _validateMessage
    private val _imageSelect = MutableStateFlow<Int>(0)
    val imageSelect: StateFlow<Int> = _imageSelect
    private val _videoSelect = MutableStateFlow<Int>(0)
    val videoSelect: StateFlow<Int> = _videoSelect
    private val _totalSizeInMB = MutableStateFlow<Double>(0.0)
    val totalSizeInMB: StateFlow<Double> = _totalSizeInMB

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


}
