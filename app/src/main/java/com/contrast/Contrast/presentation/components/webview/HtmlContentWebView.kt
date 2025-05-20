package com.contrast.Contrast.presentation.components.webview

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.contrast.Contrast.utils.Util

@Composable
fun HtmlContentWebView(
    htmlContent: String,
    domain: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = context.resources.displayMetrics.density
    val screenWidthPx = (configuration.screenWidthDp * density).toInt()

    AndroidView(
        factory = {
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true

                settings.defaultTextEncodingName = "utf-8"
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                webChromeClient = WebChromeClient()

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        val jsScript = """
                            var imgs = document.getElementsByTagName('img');
                            for (var i = 0; i < imgs.length; i++) {
                                imgs[i].style.maxWidth = '100%';
                                imgs[i].style.height = 'auto';
                            }
                        """.trimIndent()
                        view?.evaluateJavascript(jsScript, null)
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        Log.e("WebView", "Error loading page: ${error?.description}")
                    }
                }
            }
        },
        update = { webView ->
            val cleanedBody = Util.updateImageSrc(
                htmlContent,
                domain
            ).replace("{{chieurong}}", "${screenWidthPx}px")
                .replace("{{chieucao}}", "${screenWidthPx / 2}px")

            val fullHtml = """
                <html>
                <head>
                    <style type="text/css">
                        body {
                font-size: medium;
                text-align: justify;
                color: #333;
                line-height: 1.6;
                padding: 10px;
            }
                        img { max-width: 100%; height: auto; }
                    </style>
                </head>
                <body>
                    $cleanedBody
                </body>
                </html>
            """.trimIndent()

            webView.loadDataWithBaseURL(null, fullHtml, "text/html", "utf-8", null)
        },
        modifier = modifier.fillMaxWidth()
    )
}
