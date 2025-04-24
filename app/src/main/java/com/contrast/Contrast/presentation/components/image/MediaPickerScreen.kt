package com.contrast.Contrast.presentation.components.image

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage


@Composable
fun MediaPickerScreen(
    maxCount: Int = 6,
    allowImage: Boolean = true,
    allowVideo: Boolean = false,
    viewModel: MediaPickerViewModel = hiltViewModel(),
    onSendClick: (List<Uri>) -> Unit
) {
    val mediaUris by viewModel.mediaUris.collectAsState()

    val launchPicker = rememberPhotoPickerLauncher(
        maxCount = maxCount,
        onUrisPicked = { uris ->
            viewModel.addUris(
                uris = uris,
                maxCount = maxCount
            )
        }
    )

    LaunchedEffect(Unit) {
        launchPicker()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.weight(1f).padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemsIndexed(mediaUris) { index, uri ->
                Box(modifier = Modifier.aspectRatio(1f)) {
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .background(Color.Black.copy(alpha = 0.6f), shape = CircleShape)
                            .size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("${index + 1}", color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        }

        Button(
            onClick = { onSendClick(mediaUris) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            enabled = mediaUris.isNotEmpty()
        ) {
            Text("Gửi ${mediaUris.size}")
        }
    }
}
