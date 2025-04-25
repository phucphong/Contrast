package com.contrast.Contrast.presentation.components.image


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.contrast.Contrast.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LimitedAccessBottomSheet(
    onPickMorePhotos: () -> Unit,
    onOpenSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        containerColor = Color.White,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Nội dung hướng dẫn
            Text(
                text = stringResource(R.string.limited_access_instruction),
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Nút: Chọn thêm ảnh
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPickMorePhotos() }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.pick_more_photos),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Divider()

            // Nút: Thay đổi cài đặt
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenSettings() }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Settings, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.change_settings),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
