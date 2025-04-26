package com.contrast.Contrast.presentation.components.media

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter

@Composable
fun LocalImage(
    uriString: String,
    modifier: Modifier = Modifier,
) {
    val painter = rememberAsyncImagePainter(model = Uri.parse(uriString))

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier
    )
}
