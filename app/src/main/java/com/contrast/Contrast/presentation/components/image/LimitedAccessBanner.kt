package com.contrast.Contrast.presentation.components.image

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R

@Composable
fun LimitedAccessBanner(appName: String,  onPickMorePhotos: () -> Unit,) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    // Banner giống Zalo
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val text = buildAnnotatedString {
            val baseText = stringResource(id = R.string.limited_access_message, appName)
            val linkText = stringResource(id = R.string.change_permission_here)

            append(baseText + " ")
            pushStringAnnotation(tag = "CHANGE", annotation = "change_permission")
            withStyle(style = SpanStyle(color = Color(0xFF007AFF), textDecoration = TextDecoration.Underline)) {
                append(linkText)
            }
            pop()
        }

        ClickableText(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
            onClick = { offset ->
                text.getStringAnnotations("CHANGE", offset, offset)
                    .firstOrNull()?.let {
                        showDialog = true
                    }
            }
        )
    }

    // Bottom sheet giống Messenger
    if (showDialog) {
        LimitedAccessBottomSheet(
            onDismiss = { showDialog = false },
            onPickMorePhotos = {
                showDialog = false
                onPickMorePhotos()

            },
            onOpenSettings = {
                showDialog = false
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }
        )
    }
}
