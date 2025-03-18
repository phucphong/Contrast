package com.contrast.Contrast.presentation.features.news


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarTittleBack

import com.itechpro.domain.model.NewsDetail
@Preview(showBackground = true)
@Composable
fun NewsDetailScreen(newsDetail: NewsDetail, onBackPress: () -> Unit) {
    Scaffold(
        topBar = {
            CustomTopAppBarTittleBack(
                title = "",
                Color.Red,
                onBackClick = { onBackPress }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            HeaderImage(newsDetail.imageRes)
            NewsInfoSection(newsDetail)
            WebViewContent(newsDetail.contentHtml)
        }
    }
}

@Composable
fun HeaderImage(imageRes: Int) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun NewsInfoSection(newsDetail: NewsDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = newsDetail.title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red, shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.back), contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${newsDetail.startDate} - ${newsDetail.endDate}")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.ic_location), contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = newsDetail.location)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewContent(contentHtml: String) {
    val context = LocalContext.current
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        factory = { ctx ->
            WebView(ctx).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webChromeClient = WebChromeClient()
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                    }

                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        return false
                    }
                }
                loadData(contentHtml, "text/html", "UTF-8")
            }
        }
    )
}

