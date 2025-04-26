package com.contrast.Contrast.presentation.components.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.theme.FF28A745
import kotlinx.coroutines.delay
import java.lang.Error

@Composable
fun CustomToastTop(
    message: String,
    isError: Boolean = true,
    showToast: Boolean,
    modifier: Modifier = Modifier,
    durationMillis: Long = 4000
) {
    var visible by remember { mutableStateOf(showToast) }

    LaunchedEffect(showToast) {
        if (showToast) {
            visible = true
            delay(durationMillis)
            visible = false
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp) // Cách top màn hình 50dp
            .wrapContentHeight()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(if(isError)Color.Red else FF28A745)
                .padding(vertical = 20.dp, horizontal = 20.dp)
        ) {
            Text(
                text = message,
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
