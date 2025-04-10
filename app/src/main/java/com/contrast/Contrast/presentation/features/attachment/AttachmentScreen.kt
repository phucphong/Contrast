package com.contrast.Contrast.presentation.features.attachment

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Videocam

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.topAppBar.CustomAppBarBackTitleSave
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitle
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AttachmentScreen(navController: NavController,
                     message: String,
                     onMessageChange: (String) -> Unit,
                     onSaveClick: () -> Unit,
                     onBackClick: () -> Unit,
                     onAttachImage: () -> Unit,
                     onAttachMap: () -> Unit,
                     onAttachVideo: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CustomTopAppBarBackTitleSave(title = stringResource(id = R.string.customer_add),
            onBackClick = { onBackClick },
            onSaveClick = {onSaveClick

            })


        // TextField nhập nội dung
        OutlinedTextField(
            value = message,
            onValueChange = onMessageChange,
            placeholder = { Text("Nhập nội dung trao đổi") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp)
        )

        // 3 icon: Ảnh, Map, Video
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AttachmentOption(icon = Icons.Default.PhotoCamera, label = "Ảnh", onClick = onAttachImage)
            AttachmentOption(icon = Icons.Default.Place, label = "Map", onClick = onAttachMap)
            AttachmentOption(icon = Icons.Default.Videocam, label = "Video", onClick = onAttachVideo)
        }
    }
}

@Composable
fun AttachmentOption(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.noRippleClickableComposable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.Red,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 14.sp)
    }
}
