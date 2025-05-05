package com.contrast.Contrast.presentation.components.webview



import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.contrast.Contrast.databinding.LayoutWebviewProductBinding

@Composable
fun WebViewProductXml(
    htmlContent: String,
    modifier: Modifier = Modifier
) {
    AndroidViewBinding(LayoutWebviewProductBinding::inflate, modifier) {
        webviewHtml.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
        }
    }
}
