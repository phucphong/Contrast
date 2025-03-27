package com.contrast.Contrast.presentation.components.alertDialog


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

@Composable
fun FaceIDPermissionDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(20.dp))
        ) {
            // Tiêu đề
            Text(
                text = "Bạn có muốn cho phép “Contrast” sử dụng FaceID không?",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp)
            )

            // Nội dung
            Text(
                text = "Contrast yêu cầu quyền truy cập FaceID để cho phép bạn truy cập nhanh chóng và bảo mật thông tin",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Divider(color = Color.DarkGray, thickness = 1.dp)

            // 2 nút
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f).fillMaxHeight()
                        .noRippleClickableComposable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.decline),
                        color = Color(0xFF4A90E2),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,

                    )
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(Color.DarkGray)
                )

                Box(
                    modifier = Modifier
                        .weight(1f).fillMaxHeight()
                        .noRippleClickableComposable { onConfirm() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.agree),
                        color = Color(0xFF4A90E2),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,

                    )
                }
            }
        }
    }
}