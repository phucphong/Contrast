package com.contrast.Contrast.presentation.features.payment.amountInput.ui

import android.annotation.SuppressLint
import androidx.compose.ui.tooling.preview.Preview
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.topAppBar.CustomTitleBack
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.theme.FCFCFC
import com.contrast.Contrast.presentation.theme.FFFF6961

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun PreviewAmountInputScreen() {
    AmountInputScreen(onClose = {}, onConfirm = {})
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AmountInputScreen(onClose: () -> Unit, onConfirm: (String) -> Unit) {
    var amount by remember { mutableStateOf("") }
    Column(
        modifier = Modifier .background(FCFCFC)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(FCFCFC),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            CustomTitleBack(
                title = stringResource(R.string.enter_amount),
                onBackPress = { /* Xử lý quay lại */ }
            )
            Spacer(modifier = Modifier.height(50.dp))
            // Tiêu đề nhập số tiền
            TextField(
                value = amount,
                onValueChange = { newAmount -> amount = newAmount },
                textStyle = TextStyle(fontSize = 32.sp, textAlign = TextAlign.Center), // Căn giữa text
                placeholder = {  Box(
                    modifier = Modifier.fillMaxWidth(), // Căn giữa trong TextField
                    contentAlignment = Alignment.Center
                ) {
                    Text("đ", fontSize = 32.sp, color = Color.Gray)
                }}, // Căn giữa placeholder
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 50.dp)
                    .background(Color.White)
                    .align(Alignment.CenterHorizontally), // Căn giữa TextField trong Column
                visualTransformation = VisualTransformation.None,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent, // Xóa nền
                    focusedContainerColor = Color.Transparent, // Xóa nền khi focus
                    disabledContainerColor = Color.Transparent, // Xóa nền khi bị disable
                    unfocusedIndicatorColor = Color.Transparent, // Ẩn đường line
                    focusedIndicatorColor = Color.Transparent, // Ẩn đường line khi focus
                    disabledIndicatorColor = Color.Transparent // Ẩn line khi disable
                )
            )


            Spacer(modifier = Modifier.height(50.dp))

            // Thông tin số tiền tối thiểu / tối đa
            Text(
                text = stringResource(id = R.string.min_max_amount),
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Nút hoàn tất
            Button(
                onClick = { onConfirm(amount) },
                colors = ButtonDefaults.buttonColors(containerColor = FFFF6961), // Màu hồng nhạt giống UI
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp)
            ) {
                Text(text = stringResource(id = R.string.complete), color = Color.White)
            }
        }
    }
}
