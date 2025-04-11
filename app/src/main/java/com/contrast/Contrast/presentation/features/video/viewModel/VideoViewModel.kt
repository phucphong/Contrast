package com.contrast.Contrast.presentation.features.video.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.Video
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.news.NewsUseCase
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


    private val _videos = MutableStateFlow(fakeVideos())
    val videos: StateFlow<List<Video>> = _videos
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab

    private lateinit var currentUserInfo: CurrentUserInfo
    init {
        viewModelScope.launch {
            currentUserInfo = getCurrentUserUseCase()

        }
    }
    fun onTabSelected(index: Int) {
        _selectedTab.value = index
        // TODO: Gọi API tương ứng nếu cần
    }
//    https://www.youtube.com/watch?v=gwlFDnUJO1c
    private fun fakeVideos(): List<Video> {
        return listOf(
            Video(
                title = "TRANG GIẤY TRẮNG, NGỠ ♫ NGÂN NGÂN COVER NHẠC BALLAD XU HƯỚNG TRIỆU VIEW - ALLBUM HOT TREND 2025",
                videoId = "gwlFDnUJO1c", // 👈 lấy từ URL YouTube
                thumbnailUrl = "https://www.youtube.com/watch?v=gwlFDnUJO1c",
                avatarUrl = "https://yt3.ggpht.com/ytc/AKedOLT-MYAVATAR=s88-c-k-c0x00ffffff-no-rj",
                channelName = "KNLD"
            ),
            Video(
                title = "TRANG GIẤY TRẮNG, NGỠ ♫ NGÂN NGÂN COVER NHẠC BALLAD XU HƯỚNG TRIỆU VIEW - ALLBUM HOT TREND 2025",
                videoId = "gwlFDnUJO1c", // 👈 lấy từ URL YouTube
                thumbnailUrl = "https://www.youtube.com/watch?v=gwlFDnUJO1c",
                avatarUrl = "https://yt3.ggpht.com/ytc/AKedOLT-MYAVATAR=s88-c-k-c0x00ffffff-no-rj",
                channelName = "KNLD"
            )
        )
    }

}
