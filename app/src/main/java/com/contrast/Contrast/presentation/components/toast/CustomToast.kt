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
import com.google.android.material.behavior.SwipeDismissBehavior.OnDismissListener
import kotlinx.coroutines.delay
import java.lang.Error
@Composable
fun CustomToast(
    message: String = "",
    isError: Boolean = true,
    showToast: Boolean = true,
    toastPosition: ToastPosition = ToastPosition.TOP,
    modifier: Modifier = Modifier,
    durationMillis: Long = 4000,
    onDismiss: () -> Unit,
) {
    var visible by remember { mutableStateOf(showToast) }

    LaunchedEffect(showToast) {
        if (showToast) {
            visible = true
            delay(durationMillis)
            visible = false
            onDismiss()
        }
    }

    // Xác định vị trí hiển thị
    val alignment = when (toastPosition) {
        ToastPosition.TOP -> Alignment.TopCenter
        ToastPosition.CENTER -> Alignment.Center
        ToastPosition.BOTTOM -> Alignment.BottomCenter
    }

    // Padding top hoặc bottom tùy vị trí
    val verticalPadding = when (toastPosition) {
        ToastPosition.TOP -> Modifier.padding(top = 50.dp)
        ToastPosition.BOTTOM -> Modifier.padding(bottom = 50.dp)
        ToastPosition.CENTER -> Modifier
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentAlignment = alignment
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .then(verticalPadding)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (isError) Color.Red else FF28A745)
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
}
