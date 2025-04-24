package com.contrast.Contrast.presentation.components.image



import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width

import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

import com.itechpro.domain.model.product.AttachFile

@Composable
fun AttachFileItem(
    attachFile: AttachFile,
    domain: String,
    isDelete:Boolean=false,
    isEdit:Boolean=false,
    onDownloadClick: (String) -> Unit
) {
    val fullUrl = domain.trimEnd('/') + (attachFile.dinhkem ?: "")
    var showDialog by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        NetworkImage(
            imageUrl = fullUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(70.dp)
                .height(90.dp).noRippleClickableComposable {
                    showDialog = true // 👉 khi ảnh được nhấn, mở dialog
                }

        )
    }

    // ✅ Hiển thị ảnh full khi được nhấn
    if (showDialog) {
        ImageFullDialog(
            imageUrl = fullUrl,
            onDismiss = { showDialog = false },
            onDownloadClick={
                onDownloadClick(it)
            }
        )
    }
}
