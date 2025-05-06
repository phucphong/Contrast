package com.contrast.Contrast.presentation.components.toast

import androidx.compose.runtime.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Flow<String>.toastCollect(onToast: (String) -> Unit) {
    LaunchedEffect(this) {
        this@toastCollect.collectLatest { message ->
            onToast(message)
        }
    }
}