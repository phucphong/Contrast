package com.contrast.Contrast.presentation.features.video.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse

import com.contrast.Contrast.utils.StringProvider

import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.Video
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.video.VideoUiState

import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.video.VideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: VideoUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(VideoUiState())
    val state: StateFlow<VideoUiState> = _state



    private var currentUser: CurrentUserInfo? = null
    init {
        viewModelScope.launch(dispatcher) {
            val user = runCatching { getCurrentUserUseCase() }.getOrNull()
            currentUser = user
            if (user != null) {
                _state.update {
                    it.copy(domain = user.domain)
                }
            }
        }
    }

    fun loadUserInfo() {
        getCategory("nhomvideo", "modenhomvideo")
        getCategoryVideoHeart("videokh", "modelaythumuc")
    }
    fun setInitialVideo(videos: List<Video>) {
        _state.update { it.copy(pagedVideos = videos.take(10)) }
    }
    fun onTabSelected(index: Int) {
        _state.update { it.copy(selectedTab = index) }

    }

    fun resetNavigation() {
        _state.update { it.copy(navEvent = ProductNavEvent.None) }
    }

    fun onItemNotificationSelected() {
        _state.update { it.copy(navEvent = NotificationNavEvent.GoToNotifications("", "")) }
    }

    fun onItemCarts() {
        _state.update { it.copy(navEvent = CartNavEvent.GoToCats) }
    }


    fun getVideos(categoryId: String) {
        val user = currentUser ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getVideos(
                categoryId, user.token ?: ""
            ).collectResponse(

                dispatcher = dispatcher,
                onLoading = { _state.update { it.copy(isLoading = true) } },
                onSuccess = { news ->
                    _state.update { it.copy(videos = news, isLoading = false) }
                },
                onError = { msg ->
                    _state.update {
                        it.copy(
                            errorMessage = msg, isLoading = false
                        )
                    }
                })
        }
    }


    fun getCategory(obj: String, mode: String) {
        val user = currentUser ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getCategory(obj, mode,
                user.token
            ).collectResponse(dispatcher = dispatcher,
                onLoading = { _state.update { it.copy(isLoading = true) } },
                onSuccess = { list ->
                    _state.update {
                        it.copy(
                            categoryNews = list, isLoading = false
                        )
                    }
                },
                onError = { msg ->
                    _state.update {
                        it.copy(
                            errorMessage = msg, isLoading = false
                        )
                    }
                })
        }
    }

    fun getCategoryVideoHeart(obj: String, mode: String) {
        val user = currentUser ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getCategory( obj, mode, user.token).collectResponse(
                dispatcher = dispatcher,
                onLoading = { _state.update { it.copy(isLoading = true) } },
                onSuccess = { data -> _state.update { it.copy(categoryNewsHeart = data, isLoading = false) } },
                onError = { msg -> _state.update { it.copy(errorMessage = msg, isLoading = false) } }
            )
        }
    }


    fun searchLocal(textSearch: String, list: List<Video>) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            useCase.searchLocal(textSearch, list).collect { filteredList ->
                _state.update { it.copy(
                    pagedVideos = filteredList,
                    isLoading = false,
                    errorMessage = null
                ) }
            }
        }
    }

}
