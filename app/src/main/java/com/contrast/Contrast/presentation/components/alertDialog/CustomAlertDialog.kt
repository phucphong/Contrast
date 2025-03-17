package com.contrast.Contrast.presentation.components.alertDialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.contrast.Contrast.R

@Preview(showBackground = true)
@Preview(name = "Light Mode", showBackground = true)
@Composable
fun CustomAlertDialog(
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(16.dp), // ✅ Bo góc mềm mại
        containerColor = Color.White, // ✅ Màu nền trắng
        title = {
            Text(
                text = stringResource(id = R.string.alert_title), // ✅ Hỗ trợ đa ngôn ngữ
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = message,
                textAlign = TextAlign.Center, // ✅ Căn giữa nội dung
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center // ✅ Căn giữa nút OK
            ) {
                TextButton(
                    onClick = { onDismiss() }
                ) {
                    Text(
                        text = stringResource(id = R.string.ok), // ✅ Hỗ trợ đa ngôn ngữ
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00B2FF) // ✅ Màu xanh giống UI bạn gửi
                    )
                }
            }
        },
        modifier = Modifier.padding(horizontal = 20.dp) // ✅ Khoảng cách với mép màn hình
    )
}
