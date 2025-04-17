package com.contrast.Contrast.presentation.features.navigator

sealed class HomeNavEvent {
    data class GoToProduct(val categoryId: String) : HomeNavEvent()
    object None : HomeNavEvent() // trạng thái mặc định
}
