package com.contrast.Contrast.presentation.features.product.detail.ui

import androidx.compose.foundation.layout.Column
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
import com.contrast.Contrast.presentation.components.button.SeeMoreButton
import com.contrast.Contrast.presentation.components.webview.WebView

@Composable
fun WebViewProduct(htmlContent:String) {

    var isExpanded by remember { mutableStateOf(false) }
    Column {
        WebView(
            htmlContent = htmlContent,
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isExpanded) Dp.Unspecified else 100.dp) // Mở rộng hoặc thu gọn
        )

        SeeMoreButton(
            isExpanded = isExpanded,
            onToggleClick = { isExpanded = !isExpanded }
        )
    }


}
