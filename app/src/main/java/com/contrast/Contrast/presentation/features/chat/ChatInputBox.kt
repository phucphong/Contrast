package com.contrast.Contrast.presentation.features.chat

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Send

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ChatInputBox(
    text: String,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onAttachClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon đính kèm
        IconButton(onClick = onAttachClick) {
            Icon(
                imageVector = Icons.Default.AttachFile, // cần import từ `material-icons-extended`
                contentDescription = "Đính kèm",
                tint = Color.Gray
            )
        }

        // TextField bo góc tròn
        TextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = { Text("Nhập nội dung") },
            modifier = Modifier
                .weight(1f)
                .height(45.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                disabledContainerColor = Color(0xFFF5F5F5),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
            ,
            shape = RoundedCornerShape(50)
        )

        // Nút gửi
        IconButton(onClick = onSendClick) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Gửi",
                tint = Color(0xFF007AFF)
            )
        }
    }
}
