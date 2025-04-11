package com.contrast.Contrast.presentation.components.swiperefresh_custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight


import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import com.contrast.Contrast.presentation.theme.TealGreen
import com.google.accompanist.swiperefresh.*

@Composable
fun CustomSwipeRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    content: @Composable () -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = onRefresh,
        indicator = { state, refreshTrigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = refreshTrigger,
                backgroundColor = backgroundColor,
                contentColor = TealGreen,
                scale = true
            )
        },
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .fillMaxHeight()
    ) {
        content()
    }
}
