package com.contrast.Contrast.presentation.features.forgotPassword



import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.topAppBar.CustomTitleBack

@Preview(showBackground = true)

@Composable
fun ForgotPasswordScreen(onBackPress: () -> Unit, onSendCode: (String) -> Unit) {
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // Thanh tiêu đề với nút đóng
        CustomTitleBack(
            title = stringResource(R.string.forgot_password_title),
            onBackPress = { /* Xử lý quay lại */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hướng dẫn nhập số điện thoại
        Text(
            text = stringResource(R.string.forgot_password_desc),
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Label "Số điện thoại"
        Text(
            text = stringResource(R.string.phone_label),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Ô nhập số điện thoại
        CustomTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            placeholder = stringResource(R.string.phone_placeholder),
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.weight(1f))

        // Nút Gửi mã xác nhận
        Button(
            onClick = { onSendCode(phoneNumber) },
            enabled = phoneNumber.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (phoneNumber.isNotEmpty()) Color.Red else Color(0xFFFFC0C0),
                disabledBackgroundColor = Color(0xFFFFC0C0)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = stringResource(R.string.send_code_button), color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp)) // Khoảng cách tránh che navigation bar
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewForgotPasswordScreen() {
    ForgotPasswordScreen(onBackPress = {}, onSendCode = {})
}
