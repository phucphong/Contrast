package com.contrast.Contrast.presentation.components.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

@Composable
fun ImageFullDialog(
    imageUrl: String,
    onDismiss: () -> Unit,
    onDownloadClick: (String) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        var showControls  by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.95f))
        ) {
            // Ảnh hiển thị chính
            NetworkImage(
                imageUrl = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit, // hoặc ContentScale.Inside
                modifier = Modifier
                    .fillMaxSize() // ✅ full cả chiều ngang + cao
                    .align(Alignment.Center)
                    .noRippleClickableComposable { showControls = !showControls }
            )


            // Nút điều khiển (back, download)a
            if(showControls ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xCC333333)) // nền xám đậm
                        .padding(8.dp)
                        .align(Alignment.TopCenter),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { onDownloadClick(imageUrl) }) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = "Download",
                            tint = Color.White
                        )
                    }
                }
            }

        }
    }
}
