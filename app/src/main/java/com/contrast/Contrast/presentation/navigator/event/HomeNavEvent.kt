package com.contrast.Contrast.presentation.navigator.event

sealed class HomeNavEvent {
    data class GoToProduct(val categoryId: String) : HomeNavEvent()
    object None : HomeNavEvent() // trạng thái mặc định
}
