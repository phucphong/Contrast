package com.contrast.Contrast.presentation.features.product.detail.ui

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.contrast.Contrast.presentation.components.button.SeeMoreButton
import com.contrast.Contrast.presentation.components.webview.HtmlContentWebView

@Composable
fun WebViewProduct(htmlContent:String) {

    var isExpanded by remember { mutableStateOf(false) }
    Column {




        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT // <--- FIX: không dùng WRAP_CONTENT
                    )
                    settings.apply {
                        javaScriptEnabled = true
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
                    loadDataWithBaseURL("", htmlContent, "text/html", "utf-8", null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isExpanded) Dp.Unspecified else 200.dp)
                .defaultMinSize(minHeight = 200.dp)
        )

        SeeMoreButton(
            isExpanded = isExpanded,
            onToggleClick = { isExpanded = !isExpanded }
        )
    }


}

@Composable
fun HtmlContentWebView(htmlContent: String, domain: String, modifier: Modifier) {

}
