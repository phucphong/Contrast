package com.contrast.Contrast.presentation.features.product.detail.ui

import android.util.Log
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
import com.contrast.Contrast.presentation.components.button.SeeMoreButton
import com.contrast.Contrast.presentation.components.webview.WebView

@Composable
fun WebViewProduct(htmlContent:String) {

    var isExpanded by remember { mutableStateOf(false) }
    Column {
        Log.e("myHtmlString",htmlContent)


        WebView(
            htmlContent = htmlContent,
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
