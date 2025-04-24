package com.contrast.Contrast.presentation.components.image

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReviewCreateViewModel @Inject constructor() : ViewModel() {
    private val _selectedMedia = MutableStateFlow<List<Uri>>(emptyList())
    val selectedMedia: StateFlow<List<Uri>> = _selectedMedia

    fun setSelectedMedia(uris: List<Uri>) {
        _selectedMedia.value = uris
    }
}
