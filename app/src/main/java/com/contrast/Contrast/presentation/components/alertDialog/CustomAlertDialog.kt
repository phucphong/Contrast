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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R

@Preview(showBackground = true)

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

                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 21.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF151515),

                    textAlign = TextAlign.Center,
                    letterSpacing = 0.1.sp,
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = message,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 21.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF151515),

                    textAlign = TextAlign.Center,
                    letterSpacing = 0.1.sp,
                ),
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
