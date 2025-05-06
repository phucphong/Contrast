package com.contrast.Contrast.presentation.components.toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CollectToast(
    toastFlow: Flow<String>,
    onToast: (String) -> Unit
) {
    val lifecycleScope = rememberCoroutineScope()
    LaunchedEffect(toastFlow) {
        toastFlow.collectLatest { message ->
            onToast(message)
        }
    }
}
