package com.contrast.Contrast.presentation.components.webview

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.delay

@Composable
fun RetriableWebView(
    htmlContent: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var attempt by remember { mutableStateOf(0) }
    var canCreateWebView by remember { mutableStateOf(true) }

    // Retry lại nếu trước đó lỗi
    LaunchedEffect(attempt) {
        if (!canCreateWebView) {
            delay(2000) // đợi 2 giây rồi thử lại
            canCreateWebView = true
        }
    }

    if (canCreateWebView) {
        AndroidView(
            factory = {
                try {
                    WebView(it).apply {
                        settings.javaScriptEnabled = true
                        webViewClient = WebViewClient()
                        loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
                    }
                } catch (e: Exception) {
                    Log.e("WebViewError", "Lỗi tạo WebView: ${e.message}")
                    canCreateWebView = false
                    android.view.View(context) // trả về view rỗng để không crash
                }
            },
            modifier = modifier
        )
    } else {
        // Có thể hiện loading hoặc thông báo
        Box(modifier = modifier.padding(16.dp)) {
            Text(
                text = "Đang khởi tạo trình xem nội dung...",
                color = Color.Gray
            )
        }

        // Sau 2 giây `LaunchedEffect` sẽ tăng `attempt` và thử lại
        LaunchedEffect(Unit) {
            attempt++
        }
    }
}
