package com.contrast.Contrast.presentation.components.webview

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
@Composable
fun HtmlContentWebView(
    htmlContent: String,

    modifier: Modifier = Modifier,
    domain: String,
) {
    val context = LocalContext.current
    Log.e("htmlContent", htmlContent)

    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT // <--- FIX: không dùng WRAP_CONTENT
                )
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
                    defaultTextEncodingName = "utf-8"
                  mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    loadsImagesAutomatically = true

                }

                webViewClient = object : WebViewClient() {
                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        Log.e("WebViewError", "Error loading page: ${error?.description}")
                    }
                }

                webChromeClient = WebChromeClient()
                loadDataWithBaseURL(domain, htmlContent, "text/html", "utf-8", null)
            }
        },
        modifier = modifier
    )

}
