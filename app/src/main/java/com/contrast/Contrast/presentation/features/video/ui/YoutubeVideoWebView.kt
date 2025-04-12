package com.contrast.Contrast.presentation.features.video.ui

import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun YoutubeVideoWebView(videoId: String) {
    val videoUrl = "https://www.youtube.com/embed/$videoId?playsinline=1"

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    mediaPlaybackRequiresUserGesture = false
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }

                webChromeClient = WebChromeClient()

                webViewClient = object : WebViewClient() {
                    override fun onReceivedError(
                        view: WebView?,
                        errorCode: Int,
                        description: String?,
                        failingUrl: String?
                    ) {
                        Log.e("YoutubeWebView", "WebView error: $description, url: $failingUrl")
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        Log.d("YoutubeWebView", "Page loaded: $url")
                    }
                }

                // ✅ Load trực tiếp link YouTube
                loadUrl(videoUrl)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    )
}
