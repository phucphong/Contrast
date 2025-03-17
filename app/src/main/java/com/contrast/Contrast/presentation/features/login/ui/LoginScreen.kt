package com.contrast.Contrast.presentation.features.login.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.inputs.CustomTextFieldPassword
import com.contrast.Contrast.presentation.components.PasswordRequirements

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}

@Composable
fun LoginScreen(navController: NavController) {
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") } // Thông báo lỗi

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(18.dp)
            .verticalScroll(rememberScrollState())
    ) {


        Spacer(modifier = Modifier.height(100.dp))

        // Tiêu đề
        Text(
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))
        CustomText(
            stringResource(id = R.string.phoneNumber)
        )
        // Ô nhập số điện thoại
        CustomTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            placeholder = stringResource(id = R.string.phone_placeholder),
            keyboardType = KeyboardType.Phone
        )

        // Mật khẩu
        CustomText(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.password))
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            }.toString()
        )

        CustomTextFieldPassword(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(id = R.string.password_placeholder),
            keyboardType = KeyboardType.Password
        )

        // Hiển thị điều kiện mật khẩu nếu có nhập
        if (password.isNotEmpty()) {
            PasswordRequirements(password = password)
        }

        // Hiển thị lỗi nếu có
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Đẩy nội dung lên trên

        // Nút đăng nhập
        Button(
            onClick = {
//                if (phoneNumber.isEmpty() || password.isEmpty()) {
//                    errorMessage = stringResource(id = R.string.error_invalid_login)
//                } else {
//                    errorMessage = "" // Xóa lỗi nếu hợp lệ
//                    navController.navigate("home")
//                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = stringResource(id = R.string.login), color = Color.White, fontSize = 16.sp)
        }

        // Nút "Quên mật khẩu"
        Text(
            text = stringResource(id = R.string.forgot_password),
            color = Color.Gray,
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline, // Thêm gạch chân
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 15.dp, bottom = 90.dp)
                .clickable {
                    navController.navigate("forgotPassword")
                }
        )
    }
}
