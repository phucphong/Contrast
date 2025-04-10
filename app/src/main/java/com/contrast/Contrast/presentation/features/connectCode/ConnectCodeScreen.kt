package com.contrast.Contrast.presentation.features.connectCode

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.contrast.Contrast.R
@Preview(showBackground = true)
@Composable
fun ConnectCodeScreen(navController: NavController) {
    var connectCode by remember { mutableStateOf("https://spa.ezmax.vn") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Logo + slogan
        Image(
            painter = painterResource(id = R.drawable.logo), // Thay logo thực tế
            contentDescription = "EZMAX Logo",
            modifier = Modifier
                .height(100.dp)
                .padding(top = 32.dp)
        )
        Text(
            text = stringResource(R.string.slogan),
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Label: Mã kết nối
        Text(
            text = stringResource(R.string.connect_code_label),
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        // TextField
        OutlinedTextField(
            value = connectCode,
            onValueChange = { connectCode = it },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(text = stringResource(R.string.connect_code_placeholder))
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Nút Tiếp tục
        Button(
            onClick = {
                navController.navigate("login")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00AA88))
        ) {
            Text(
                text = stringResource(R.string.continue_button),
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Đăng ký với QR Code
        Text(
            text = stringResource(R.string.register_qr),
            color = Color(0xFF00AA88),
            fontSize = 14.sp,
            modifier = Modifier.clickable {
                // TODO: scan QR code
            }
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Footer
        Text(
            text = stringResource(R.string.copyright),
            color = Color(0xFF00AA88),
            fontSize = 13.sp
        )
        Text(
            text = stringResource(R.string.version),
            color = Color.Gray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
