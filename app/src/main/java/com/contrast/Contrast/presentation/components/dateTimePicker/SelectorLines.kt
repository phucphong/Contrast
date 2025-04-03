package com.contrast.Contrast.presentation.components.dateTimePicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


@Composable
fun SelectorLines(itemHeight: Dp, totalHeight: Dp) {
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = -itemHeight / 2)
                .fillMaxWidth()
        )
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = itemHeight / 2)
                .fillMaxWidth()
        )
    }
}
