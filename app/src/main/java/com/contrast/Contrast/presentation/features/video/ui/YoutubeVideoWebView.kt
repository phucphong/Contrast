package com.contrast.Contrast.presentation.features.video.ui

import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun YoutubeVideoWebView(videoId: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.mediaPlaybackRequiresUserGesture = false
                webChromeClient = WebChromeClient()

                val html = """
                    <html>
                      <body style="margin:0;padding:0;">
                        <iframe width="100%" height="100%" 
                            src="https://www.youtube.com/embed/$videoId?playsinline=1"
                            frameborder="0"
                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                            allowfullscreen>
                        </iframe>
                      </body>
                    </html>
                """.trimIndent()

                loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    )
}
