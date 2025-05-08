package com.contrast.Contrast.presentation.features.video.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.NetworkResponse

import com.itechpro.domain.model.Video
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.video.VideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                                         private val useCase: VideoUseCase,
                                         private val stringProvider: StringProvider,
                                         @IoDispatcher private val dispatcher: CoroutineDispatcher,) : ViewModel() {



    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos


    private val _obj = MutableStateFlow<Video?>(null)
    val obj: StateFlow<Video?> = _obj

    private val _categoryNews = MutableStateFlow<List<Category>>(emptyList())
    val categoryNews: StateFlow<List<Category>> = _categoryNews

    private val _categoryNewsHeart = MutableStateFlow<List<Category>>(emptyList())
    val categoryNewsHeart: StateFlow<List<Category>> = _categoryNewsHeart

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError
    private val _domain = MutableStateFlow<String?>("")
    val domain: StateFlow<String?> = _domain


    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private var currentUserInfo: CurrentUserInfo? = null

    private val _navigationEvent = MutableStateFlow<NavEvent>(ProductNavEvent.None)
    val navigationEvent: StateFlow<NavEvent> = _navigationEvent

    init {
        viewModelScope.launch(dispatcher) {
            try {
                currentUserInfo = getCurrentUserUseCase()
                _domain.value = currentUserInfo!!.domain?:""



                getCategory("nhomvideo","modenhomvideo")
                getCategoryVideoHeart("videokh","modelaythumuc")

            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    fun onTabSelected(index: Int) {
        _selectedTab.value = index
    }

    // Sau khi navigate xong, reset lại state
    fun resetNavigation() {
        _navigationEvent.value = ProductNavEvent.None
    }
    fun onItemNotificationSelected( ) {
        _navigationEvent.value = NotificationNavEvent.GoToNotifications(
            startDate = "",
            endDate = "",

            )
    }
    fun onItemCarts( ) {
        _navigationEvent.value = CartNavEvent.GoToCats
    }

    fun getVideos(idCategory: String) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                useCase.getVideos(user.isOfflineMode, idCategory, user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                            _isLoading.value = true
                        }
                        is NetworkResponse.Success -> {
                            _isLoading.value = false
                            _videos.value = result.data
                        }
                        is NetworkResponse.Error -> {
                            _isLoading.value = false
                            _validationError.value = result.message
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    fun getCategory(obj: String,mode: String) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                useCase.getCategory(user.isOfflineMode,obj, mode,  user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                            _isLoading.value = true
                        }
                        is NetworkResponse.Success -> {
                            _isLoading.value = false
                            _categoryNews.value = result.data
                        }
                        is NetworkResponse.Error -> {
                            _isLoading.value = false
                            _validationError.value = result.message
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }
    fun getCategoryVideoHeart(obj: String,mode: String) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                useCase.getCategory(user.isOfflineMode,obj, mode,  user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                            _isLoading.value = true
                        }
                        is NetworkResponse.Success -> {
                            _isLoading.value = false
                            _categoryNewsHeart.value = result.data
                        }
                        is NetworkResponse.Error -> {
                            _isLoading.value = false
                            _validationError.value = result.message
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }




}
