package com.contrast.Contrast.presentation.components.alertDialog


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
@Preview(showBackground = true)
@Preview(name = "Light Mode", showBackground = true)
@Composable
fun FaceIDPermissionDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color(0xFF1E1E1E), // Màu nền tối
        title = {
            Text(
                text = stringResource(id = R.string.faceid_title),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.faceid_message),
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm() }
            ) {
                Text(
                    text = stringResource(id = R.string.agree),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A90E2) // Màu xanh
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(
                    text = stringResource(id = R.string.decline),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A90E2) // Màu xanh
                )
            }
        },
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}
